import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Profil, ProfilUpdateRequest, UserType } from '../model/profil';
import { InscriptionRequest } from '../model/inscription-request';

@Injectable({
  providedIn: 'root',
})
export class UtilisateurService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = '/utilisateur';

  public findAll(): Observable<Profil[]> {
    return this.http.get<Profil[]>(this.apiUrl);
  }

  public findMine(): Observable<Profil> {
    return this.http.get<Profil>(`${this.apiUrl}/mine`);
  }

  public findById(id: number): Observable<Profil> {
    return this.http.get<Profil>(`${this.apiUrl}/${id}`);
  }

  public inscrire(request: InscriptionRequest): Observable<{ id: number }> {
    return this.http.post<{ id: number }>(`${this.apiUrl}/inscription`, request);
  }

  public create(request: InscriptionRequest): Observable<{ id: number }> {
    return this.http.post<{ id: number }>(this.apiUrl, request);
  }

  public update(id: number, request: ProfilUpdateRequest): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}`, request);
  }

  public remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  public updateType(id: number, type: UserType): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/type`, { type });
  }
}
