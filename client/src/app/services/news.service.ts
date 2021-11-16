import { Injectable } from '@angular/core';
import { NewsResponse } from '../Interface/NewsResponse';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  baseUrl: string = 'https://ravikumarjavabackend-amxbp6pvia-el.a.run.app/stock/news';
  public newResponse: NewsResponse;

  constructor(private http: HttpClient) { }

  getLatestStockNews(): Observable<any> {
    return this.http.get<any>(this.baseUrl);
  }
}
