import { inject, Injectable } from '@angular/core';
import { Mission } from '../model/mission';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root',
})
export class MissionService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = '/mission';
  private refresh$: Subject<void> = new Subject<void>();

  public findAll(): Observable<Mission[]> {
    return this.http.get<Mission[]>(this.apiUrl);
  }

  public add(mission: Mission): Observable<Mission> {
    return this.http.post<Mission>(this.apiUrl, mission);
  }

  public save(mission: Mission): Observable<Mission> {
    if (!mission.id) {
      return this.http.post<Mission>(this.apiUrl, mission);
    }
    return this.http.put<Mission>(`${this.apiUrl}/${mission.id}`, mission);

  }

  public update(id: number, mission: Mission): Observable<Mission> {
    return this.http.put<Mission>(`${this.apiUrl}/${id}`, mission);
  }

  public remove(mission: Mission): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${mission.id}`);
  }
  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
