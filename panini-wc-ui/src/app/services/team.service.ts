import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TeamService {
  private http = inject(HttpClient);

  private api =
    'http://localhost:9094/api/teams';

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  getTeamById(
    teamId: number
  ) {
    return this.http.get<any>(
      `${this.api}/${teamId}`
    );
  }

  getById(
    id: number
  ): Observable<any> {
    return this.http.get<any>(
      `${this.api}/${id}`
    );
  }

  getByEmail(
    email: string
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/email/${email}`
    );
  }

  getRankings(): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/rankings`
    );
  }
}
