import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  // tslint:disable-next-line:variable-name
  constructor(private _authService: UserService,
    // tslint:disable-next-line:variable-name
    private _router: Router) { }

  canActivate(): boolean {
    if (this._authService.loggedIn()) {
      console.log('true')
      return true;
    } else {
      this._router.navigate([''])
      return false;
    }
  }
}