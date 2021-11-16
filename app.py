from flask import Flask, jsonify, request
from flask_cors import CORS
import requests
from flask import request
import json
from bs4 import BeautifulSoup
import re
import numpy as np
import pandas as pd
import warnings
warnings.filterwarnings('ignore')
import json
import datetime as dt
import math, random
from sklearn.metrics import mean_squared_error
from sklearn.preprocessing import MinMaxScaler
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import Dropout
from keras.layers import LSTM
import math
from sklearn.metrics import  mean_squared_error
import time

def remove_outlier(df_in, col_name):
    q1 = df_in[col_name].quantile(0.25)
    q3 = df_in[col_name].quantile(0.75)
    iqr = q3-q1 #Interquartile range
    fence_low  = q1-1.5*iqr
    fence_high = q3+1.5*iqr
    df_out = df_in.loc[(df_in[col_name] > fence_low) & (df_in[col_name] < fence_high)]
    return df_out

def create_dataset(dataset, time_step=1):
    dataX, dataY= [],[]
    for i in range(len(dataset)-time_step-1):
        a=dataset[i:(i+time_step),0]
        dataX.append(a)
        dataY.append(dataset[i+time_step,0])
    return np.array(dataX), np.array(dataY)


def predict(symbol):
    endepoch = int(time.time())
    startepoch = endepoch - 31556926 
    df = pd.read_csv('https://query1.finance.yahoo.com/v7/finance/download/'+str(symbol)+'?includeAdjustedClose=true&interval=1d&events=history&period2='+str(endepoch)+'&period1='+str(startepoch))
    col = ['date','open','high','low','close','volume','adjclose']
    count=0
    for i in df.columns:
        df.rename(columns={i:col[count]},inplace=True, errors='raise')
        count=count+1
    df.dropna(axis=0, inplace=True)
    df=remove_outlier(df, 'close')
    df1=df.reset_index()["close"]
    scaler= MinMaxScaler(feature_range=(0,1))
    df1= scaler.fit_transform(np.array(df1).reshape(-1,1))
    training_size= int(len(df1)*0.70)
    test_size= len(df1)-training_size
    train_data, test_data= df1[0:training_size,:], df1[training_size:len(df1),:]
    time_step=7
    Xtrain, ytrain=create_dataset(train_data,time_step)
    xtest,ytest= create_dataset(test_data,time_step)

    Xtrain= Xtrain.reshape(Xtrain.shape[0], Xtrain.shape[1],1)
    xtest= xtest.reshape(xtest.shape[0], xtest.shape[1],1 )
    model=Sequential()
    model.add(LSTM(units=50,return_sequences=True,input_shape=(Xtrain.shape[1],1)))
    model.add(Dropout(0.1))
    model.add(LSTM(units=50,return_sequences=True))
    model.add(Dropout(0.1))
    model.add(LSTM(units=50,return_sequences=True))
    model.add(Dropout(0.1))
    model.add(LSTM(units=50))
    model.add(Dropout(0.1))
    model.add(Dense(units=1))
    model.compile(optimizer='adam',loss='mean_squared_error')
    model.fit(Xtrain,ytrain,epochs=20,batch_size=128,verbose=1)
    train_predict=model.predict(Xtrain)
    test_predict= model.predict(xtest)
    train_predict= scaler.inverse_transform(train_predict)
    test_predict= scaler.inverse_transform(test_predict)
    math.sqrt(mean_squared_error(ytrain,train_predict))
    math.sqrt(mean_squared_error(ytest,test_predict))
    dig1= np.empty_like(df1)
    dig1.fill(np.nan)
    dig1[time_step+1:len(train_predict)+time_step+1]= train_predict
    dig2= np.empty_like(df1)
    dig2.fill(np.nan)
    dig1[time_step+len(train_predict)+time_step+2:]= test_predict
    df2= scaler.inverse_transform(df1)
    input_for_future_prediction= dig1.copy()
    for i in range(len(input_for_future_prediction)):
        if np.isnan(input_for_future_prediction[i]):
            input_for_future_prediction[i]= df2[i]
            
    numberOfFuturePrediction = 20
    t=0
    prediction= list()
    while t<numberOfFuturePrediction:
        X= input_for_future_prediction[len(input_for_future_prediction)-time_step:].copy()
        X= scaler.fit_transform(np.array(X))
        X= X.reshape(1,-1)
        X=X.reshape(1,time_step,1)
        Y= model.predict(np.array(X))
        Y_actual = scaler.inverse_transform(Y)
        prediction.append(Y_actual)
        input_for_future_prediction= np.array(input_for_future_prediction).tolist()
        input_for_future_prediction.append(Y_actual)
        input_for_future_prediction= np.array(input_for_future_prediction)
        t=t+1
        df['date'] = pd.to_datetime(df['date'],format= '%Y-%m-%d' )
        last_date = df['date'].iloc[-1]
        
    cols = ['Date','Pred','Change','Percent_Change','From','To']
    df3 = pd.DataFrame(columns=cols, index=range(numberOfFuturePrediction))
    count=0
    for i in range(len(df2),len(df2)+numberOfFuturePrediction,1):
        last_date+=dt.timedelta(days=1)
        difference = input_for_future_prediction[i]-input_for_future_prediction[i-1]
        df3.loc[count].Date = last_date
        df3.loc[count].Change = difference[0]
  
        if difference<0:
            df3.loc[count].Pred = 'Down'
        elif difference>0:
            df3.loc[count].Pred = 'Up'
        else:
            df3.loc[count].Pred = 'No change'
        df3.loc[count].Percent_Change = (difference*100/input_for_future_prediction[i-1])[0]
        df3.loc[count].From = input_for_future_prediction[i-1][0]
        df3.loc[count].To = input_for_future_prediction[i][0]
        count= count+1
            
    return json.loads(df3.to_json(orient ='records'))

def nse(type):
    url =''
    if type == 'losers':
        url = 'https://groww.in/stocks/top-losers'
    elif type == 'gainers':
        url = 'https://groww.in/stocks/top-gainers'
    else:
        return jsonify({"status": 0, "error": "Invalid Type"}) 
    headers = {'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3', 'accept' :'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9'}
    response = requests.get(url, headers = headers, verify = False)
    soup = BeautifulSoup(response.text, "html.parser")
    pattern = re.compile(r"window.__INITIAL_STATE__ = (.*?)$", re.MULTILINE | re.DOTALL)
    script = soup.find("script", text=pattern)
    return json.loads(pattern.search(script.string).group(1))

app = Flask(__name__)   # flask app initialised
CORS(app)               # just to remove CORS error

@app.errorhandler(404)  #error handling for 404
def not_found(e): 
  return jsonify({"status": 0, "error": str(e)})

@app.route("/data")
def scrapeit():
    # only accespts GET requests
    t = request.args.get('type')
    if request.method == "GET" and t is not None : return jsonify({"status": 1, "data": nse(t)})
    else: return jsonify({"status": 0, "error": "Method Not Callable"})

@app.route("/prediction")
def mod():
    symbol = request.args.get('symbol')
    if request.method == "GET" and symbol is not None : return jsonify({"status": 1, "data": predict(symbol)})
    else: return jsonify({"status": 0, "error": "Method Not Callable"})

if __name__ =="__main__": app.run(host="0.0.0.0", port=8080) # just remove dubug = True when using it in production
