import { inject, Injectable } from '@angular/core';
import { Equipe } from '../model/equipe';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root',
})
export class EquipeService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = '/equipe';
  private refresh$: Subject<void> = new Subject<void>();

  public findAll(): Observable<Equipe[]> {
    return this.http.get<Equipe[]>(this.apiUrl);
  }

  public add(equipe: Equipe): Observable<Equipe> {
    return this.http.post<Equipe>(this.apiUrl, equipe);
  }

  public save(equipe: Equipe): Observable<Equipe> {
      if (!equipe.id) {
        return this.http.post<Equipe>(this.apiUrl, equipe);
      }
      return this.http.put<Equipe>(`${this.apiUrl}/${equipe.id}`, equipe);

    }

  public update(id: number, equipe: Equipe): Observable<Equipe> {
    return this.http.put<Equipe>(`${this.apiUrl}/${id}`, equipe);
  }

  public remove(equipe: Equipe): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${equipe.id}`);
  }
}
