import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly loggedIn = signal<boolean>(false);

  public isLoggedIn = this.loggedIn.asReadonly();

  public login(): void {
    this.loggedIn.set(true);
  }

  public logout(): void {
    this.loggedIn.set(false);
  }
}
