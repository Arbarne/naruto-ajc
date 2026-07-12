import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NinjaOption } from '../model/ninja-option';

@Injectable({
  providedIn: 'root',
})
export class NinjaService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = '/utilisateur/ninja';

  public findAll(): Observable<NinjaOption[]> {
    return this.http.get<NinjaOption[]>(this.apiUrl);
  }
}
