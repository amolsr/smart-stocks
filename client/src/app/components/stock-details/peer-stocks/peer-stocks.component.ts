import { Component, OnInit } from '@angular/core';
import { StockDetailsService } from 'src/app/services/stock-details.service';
import { peerStockModel } from 'src/app/models/peerStockModel';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PeopleStock } from 'src/app/Interface/PeopleStock';

@Component({
  selector: 'app-peer-stocks',
  templateUrl: './peer-stocks.component.html',
  styleUrls: ['./peer-stocks.component.css']
})
export class PeerStocksComponent implements OnInit {

  stockDetails: any;
  displayedColumns: string[] = ['companyName', 'currentPrice', 'previousClose', 'change', 'change%'];
  dataSource: PeopleStock[] = [];
  symbol = '';
  constructor(
    private router: Router,
    private stockDetailsService: StockDetailsService,
    private activatedRoute: ActivatedRoute
  ) {

  }
  ngOnInit(): void {
    this.symbol = this.activatedRoute.snapshot.params['symbol'];
    this.stockDetailsService.fetchPeerStock(this.symbol).subscribe((res) => {
      this.dataSource = res.data;
      console.log("Fetched People Stocks");

    });
  }

  clickedRow(row: any) {
    console.log(row.symbol)
    this.router.navigate(['/stockDetails/' + row.symbol]).then(() => {
      window.location.reload();
    });

  }


}
