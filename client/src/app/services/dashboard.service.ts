import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable, Subject, Subscription } from 'rxjs';
import { CreditResponse } from '../Interface/CreditResponse';
import { MockStockResponse } from '../Interface/MockStockResponse';
import { PaymentHistoryResponse } from '../Interface/paymentHistoryResponse';
import { SellStocksResponse } from '../Interface/SellStocksResponse';
import { StockOrderResponse } from '../Interface/StockOrderResponse';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  creditsUrl = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/user/money';
  stockHistoryUrl = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/user/transactions';
  mockStocksUrl = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/user/stock/all';
  paymentHistoryUrl = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/user/credit-transactions';
  buyStockUrl = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/user/buy';
  sellStockUrl = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/user/sell';
  currentStocksUrl = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/user/stocks';

  Stock: { type: string, name: string, currPrice: string };
  dataChanged = new Subject<void>();
  constructor(private http: HttpClient, private toastr: ToastrService) { }

  setStock(stock) {
    this.Stock = stock;
  }

  getStock() {
    return this.Stock;
  }

  buyStock(buyOrder: { symbol: string, units: number }) {
    this.http.put(this.buyStockUrl, [buyOrder]).subscribe(data => {
      this.toastr.success("Stock Bought !!", "", {
        closeButton: true,
        "positionClass": "toast-bottom-right",
      })
    },
      error => {
        console.log('Something went wrong !')
        this.toastr.error(error.error.errors.error + "", "", {
          closeButton: true,
          "positionClass": "toast-bottom-right",
        })
      });
    this.dataChanged.next();
  }

  sellStock(sellOrder: { symbol: string, units: number }) {
    this.http.put(this.sellStockUrl, [sellOrder]).subscribe(data => {
      this.toastr.success("Stock Sold !!", "", {
        closeButton: true,
        "positionClass": "toast-bottom-right",
      })
    },
      error => {
        console.log('Something went wrong !')
        this.toastr.error(error.error.errors.error + "", "", {
          closeButton: true,
          "positionClass": "toast-bottom-right",
        })
      });
    this.dataChanged.next();
  }

  addCredits(credit: number) {
    this.http.put(this.creditsUrl, { value: credit }).subscribe(data => {
      this.toastr.success("Credit Added !!", "", {
        closeButton: true,
        "positionClass": "toast-bottom-right",
      })
    },
      error => {
        console.log('Something went wrong !')
        this.toastr.error(error.error.errors.error + "", "", {
          closeButton: true,
          "positionClass": "toast-bottom-right",
        })
      });
    this.dataChanged.next();
  }

  fetchUserCredit(): Observable<CreditResponse> {
    return this.http.get<CreditResponse>(this.creditsUrl);
  }

  fetchStockOrderHistory(): Observable<StockOrderResponse> {
    return this.http.get<StockOrderResponse>(this.stockHistoryUrl);
  }

  fetchPaymentHistory(): Observable<PaymentHistoryResponse> {
    return this.http.get<PaymentHistoryResponse>(this.paymentHistoryUrl);
  }

  fetchMockStocks(): Observable<MockStockResponse> {
    return this.http.get<MockStockResponse>(this.mockStocksUrl);
  }

  fetchCurrentStocks(): Observable<SellStocksResponse> {
    return this.http.get<SellStocksResponse>(this.currentStocksUrl);
  }
}
