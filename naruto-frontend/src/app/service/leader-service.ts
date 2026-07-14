import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LeaderOption } from '../model/leader-option';

@Injectable({
  providedIn: 'root',
})
export class LeaderService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = '/utilisateur/leader';

  public findAll(equipeId?: number): Observable<LeaderOption[]> {
    if (equipeId != null) {
      return this.http.get<LeaderOption[]>(this.apiUrl, { params: { equipeId } });
    }
    return this.http.get<LeaderOption[]>(this.apiUrl);
  }
}
