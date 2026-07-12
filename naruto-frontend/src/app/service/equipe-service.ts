import { inject, Injectable } from '@angular/core';
import { Equipe } from '../model/equipe';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

export interface EquipeRequest {
  nom: string;
  leaderId: number;
}

@Injectable({
  providedIn: 'root',
})
export class EquipeService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = '/equipe';
  private utilisateurUrl: string = '/utilisateur';

  public findAll(): Observable<Equipe[]> {
    return this.http.get<Equipe[]>(this.apiUrl);
  }

  public findMine(): Observable<Equipe> {
    return this.http.get<Equipe>(`${this.apiUrl}/mine`);
  }

  public findById(id: number): Observable<Equipe> {
    return this.http.get<Equipe>(`${this.apiUrl}/${id}`);
  }

  public add(equipe: EquipeRequest): Observable<Equipe> {
    return this.http.post<Equipe>(this.apiUrl, equipe);
  }

  public update(id: number, equipe: EquipeRequest): Observable<Equipe> {
    return this.http.put<Equipe>(`${this.apiUrl}/${id}`, equipe);
  }

  public remove(equipe: Equipe): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${equipe.id}`);
  }

  public addNinja(equipeId: number, ninjaId: number): Observable<void> {
    return this.http.put<void>(`${this.utilisateurUrl}/${ninjaId}/equipe/${equipeId}`, {});
  }

  public removeNinja(ninjaId: number): Observable<void> {
    return this.http.delete<void>(`${this.utilisateurUrl}/${ninjaId}/equipe`);
  }
}
