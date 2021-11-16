import { Injectable } from '@angular/core';
import { GainerResponse } from '../Interface/GainerResponse';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GainerLooserService {

  baseUrl: string = 'https://cleverfoxbackendpython-amxbp6pvia-el.a.run.app/data?type=';
  recommendatedFiveUrl: string = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/stock/recommended-stocks/'
  allStocksUrl: string = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/stock/all?pageNo=';
  topUrl: string = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/stock/top?days='

  public GainerResponse: GainerResponse;

  constructor(private http: HttpClient) { }

  getTopGainer(): Observable<GainerResponse> {
    return this.http.get<GainerResponse>(this.baseUrl + 'gainers');
  }

  getTopLooser(): Observable<GainerResponse> {
    return this.http.get<GainerResponse>(this.baseUrl + 'losers');
  }

  getTopRecommendation(sym: string): Observable<any> {
    return this.http.get<any>(this.recommendatedFiveUrl + sym);
  }

  getallStocks(pageNO:any): Observable<any> {
    return this.http.get<any>(this.allStocksUrl + pageNO + '&size=10')
  }

  getTopInXdays(days:any, params : string ): Observable<any> {
    return this.http.get<any>(this.topUrl + days + '&type=' + params)
  }

}
