import { MissionListView } from './../model/mission-list-view';
import { inject, Injectable } from '@angular/core';
import { Mission } from '../model/mission';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth-service';
import { EntityCreatedOrUpdated } from '../model/entity-created-or-updated'


@Injectable({
  providedIn: 'root',
})
export class MissionService {
  private authService: AuthService = inject(AuthService);

  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = '/mission';
  private refresh$: Subject<void> = new Subject<void>();

  public findAll(): Observable<MissionListView[]> {
    return this.http.get<MissionListView[]>(this.apiUrl);
  }

  public findById(id: number): Observable<Mission> {
    return this.http.get<Mission>(`${this.apiUrl}/${id}`);
  }

  public findMine(): Observable<MissionListView> {
    return this.http.get<MissionListView>(`${this.apiUrl}/mine`);
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

  public assigner(id: number): Observable<EntityCreatedOrUpdated>{
    return this.http.put<EntityCreatedOrUpdated>(`${this.apiUrl}/assign`, {
      id: id, equipeId: this.authService.equipeId
    })
  }

  public demarrer(id: number) {
    return this.http.patch<EntityCreatedOrUpdated>(`${this.apiUrl}/${id}/start`, {
      id: id, equipeId: this.authService.equipeId
    })
  }

  public terminer(id: number) {
    return this.http.patch<EntityCreatedOrUpdated>(`${this.apiUrl}/${id}/terminer`, {
      id: id, equipeId: this.authService.equipeId
    })
  }
}
