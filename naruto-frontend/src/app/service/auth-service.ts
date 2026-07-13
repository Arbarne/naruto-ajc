import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthRequest } from '../model/auth-request';
import { AuthResponse } from '../model/auth-response';
import { Role } from '../model/role';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http: HttpClient = inject(HttpClient);
  private _token: string = sessionStorage.getItem("token") ?? "";
  private _role: Role = (sessionStorage.getItem("role") as Role) ?? "";
  private _equipeId: number | null = sessionStorage.getItem("equipeId") ? parseInt(sessionStorage.getItem("equipeId")!) : null
  private _login: string = sessionStorage.getItem("login") ?? "";

  public get token(): string {
    return this._token;
  }

  public set token(value: string) {
    this._token = value;
    sessionStorage.setItem("token", value);
  }

  public get role(): Role {
    return this._role;
  }

  public set role(value: Role) {
    this._role = value;
    sessionStorage.setItem("role", value);
  }

  public get equipeId(): number | null {
    return this._equipeId;
  }

  public set equipeId(value: number | null) {
    this._equipeId = value;
    if (value === null) {
      sessionStorage.removeItem("equipeId");
    } else {
      sessionStorage.setItem("equipeId", "" + value);
    }
  }

  public get login(): string {
    return this._login;
  }

  public set login(value: string) {
    this._login = value;
    sessionStorage.setItem("login", value);
  }

  public auth(authRequest: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>('/auth', authRequest);
  }

  public isLogged() {
    return !!this._token;
  }

  public resetAuth() {
    this._token = "";
    this._role = "";
    this._equipeId = null;
    this._login = "";
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("role");
    sessionStorage.removeItem("equipeId");
    sessionStorage.removeItem("login");
  }
}
