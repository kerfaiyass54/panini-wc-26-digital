import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TeamGeneratorService {
  private http = inject(HttpClient);

  private api =
    'http://localhost:8000';

  getOwnedPlayers(
    email: string
  ): Observable<any> {
    return this.http.get<any>(
      `${this.api}/players/${email}`
    );
  }

  getTeams(
    email: string
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/teams/${email}`
    );
  }

  getTeam(
    teamId: string
  ): Observable<any> {
    return this.http.get<any>(
      `${this.api}/team/${teamId}`
    );
  }

  generateAutoTeam(
    email: string,
    teamName: string
  ): Observable<any> {
    return this.http.post<any>(
      `${this.api}/teams/auto`,
      null,
      {
        params: {
          email,
          team_name: teamName,
        },
      }
    );
  }

  createManualTeam(
    payload: any
  ): Observable<any> {
    return this.http.post<any>(
      `${this.api}/teams/manual`,
      payload
    );
  }

  deleteTeam(
    teamId: string
  ): Observable<any> {
    return this.http.delete(
      `${this.api}/team/${teamId}`
    );
  }
}
