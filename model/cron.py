import pandas as pd
import requests
import psycopg2
import pandas.io.sql as sqlio
from sqlalchemy import create_engine

user="smartstock"
password="Root%40123"
host="10.111.193.3"
database="smartstock"
input_table="search_table"
output_table="stock_sub"

try:
    conn = psycopg2.connect(database=database, user=user, password=password, host=host, port= '5432')
    conn.autocommit = True
    cursor = conn.cursor()
    cursor.execute("TRUNCATE TABLE "+output_table)
    print("Table empty... ")
    conn.commit()
except: print("Exception thrown. x does not exist.") 
finally: conn.close()

engine = create_engine('postgresql://' + user + ':'+ password + '@' + host + ':5432/' + database, pool_recycle=3600);

cnx = engine.connect()

data = pd.read_sql('SELECT symbol FROM '+ input_table, cnx)

for Company in data['symbol']:
    myset = []
    print(Company)
    url = 'https://smart-stocks-hu20.herokuapp.com/stock/graph/'+ Company
    params = {'range': '1y', 'interval': '1d'}
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:74.0) Gecko/20100101 Firefox/74.0'
    }
    r = requests.get(url = url, params = params, headers=headers, verify=False) 
    print(r)
    if r.status_code == 200:
        response = r.json()
        for i in response['data']:
            i['symbol'] = Company
            myset.append(i)
        dfItem = pd.DataFrame.from_records(myset)
        dfItem.to_sql(output_table, engine, if_exists='append', index=False) #drops old table and creates new empty table
        myset = []
    else: break