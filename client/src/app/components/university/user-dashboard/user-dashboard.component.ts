import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DashboardService } from 'src/app/services/dashboard.service';
import { AddCreditsModalComponent } from '../add-credits-modal/add-credits-modal.component';
import { SubscriptionModalComponent } from '../subscription-modal/subscription-modal.component';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css'],
})
export class UserDashboardComponent implements OnInit {
  userCredits: string;
  userProfits: string;
  subscription: Subscription;
  refresh;
  constructor(
    private http: HttpClient,
    private dashboardService: DashboardService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit(): void {
    window.scrollTo(0, 0);
    this.dashboardService.fetchUserCredit().subscribe((res) => {
      this.userCredits = res.data.money.toFixed(2);
      this.userProfits = res.data.userProfit.toFixed(2);
    });

    this.refresh = setInterval(() => {
      this.dashboardService.fetchCurrentStocks().subscribe((res) => {
        this.dashboardService.fetchUserCredit().subscribe((res) => {
          this.userCredits = res.data.money.toFixed(2);
          this.userProfits = res.data.userProfit.toFixed(2);
        });
      });
    }, 1000);


    this.subscription = this.dashboardService.dataChanged.subscribe(() => {
      this.dashboardService.fetchUserCredit().subscribe((res) => {
        this.userCredits = res.data.money.toFixed(2);
        this.userProfits = res.data.userProfit.toFixed(2);
        console.log("DashBoard Updated");
      });
    });
  }

  addCredits() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    this.dialog.open(AddCreditsModalComponent, dialogConfig);
  }

  navigateStockHistory() {
    this.router.navigateByUrl('/user/sandbox/orders');
  }
  extendSubscription() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "30%";
    this.dialog.open(SubscriptionModalComponent, dialogConfig);
  }

  ngOnDestroy() {
    if (this.refresh) {
      clearInterval(this.refresh);
    }
  }

}
