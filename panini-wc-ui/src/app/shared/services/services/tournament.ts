import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TournamentService {
  private readonly http =
    inject(HttpClient);

  private readonly api =
    'http://localhost:9095/api/tournaments';

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(
      this.api
    );
  }

  getByEmail(
    email: string
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/email/${encodeURIComponent(email)}`
    );
  }

  getById(
    tournamentId: number
  ): Observable<any> {
    return this.http.get<any>(
      `${this.api}/${tournamentId}`
    );
  }

  create(
    payload: any
  ): Observable<any> {
    return this.http.post(
      this.api,
      payload
    );
  }

  addTeams(
    tournamentId: number,
    teamIds: number[]
  ): Observable<any> {
    return this.http.post(
      `${this.api}/${tournamentId}/teams`,
      {
        teamIds,
      }
    );
  }

  generateFixtures(
    tournamentId: number
  ): Observable<any> {
    return this.http.post(
      `${this.api}/${tournamentId}/generate-fixtures`,
      {}
    );
  }

  initializeStandings(
    tournamentId: number
  ): Observable<any> {
    return this.http.post(
      `${this.api}/${tournamentId}/initialize-standings`,
      {}
    );
  }

  getStandings(
    tournamentId: number
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/${tournamentId}/standings`
    );
  }

  getMatches(
    tournamentId: number
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/${tournamentId}/matches`
    );
  }

  getNextJourney(
    tournamentId: number
  ): Observable<any> {
    return this.http.get(
      `${this.api}/${tournamentId}/next-journey`
    );
  }

  playJourney(
    tournamentId: number,
    journey: number
  ): Observable<any> {
    return this.http.post(
      `${this.api}/${tournamentId}/journey/${journey}/play`,
      {}
    );
  }

  getJourneyMatches(
    tournamentId: number,
    journey: number
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/${tournamentId}/journey/${journey}`
    );
  }

  getJourneys(
    tournamentId: number
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/${tournamentId}/journeys`
    );
  }

  getStatus(
    tournamentId: number
  ): Observable<any> {
    return this.http.get(
      `${this.api}/${tournamentId}/status`
    );
  }

  getStatistics(
    tournamentId: number
  ): Observable<any> {
    return this.http.get(
      `${this.api}/${tournamentId}/statistics`
    );
  }

  getTopScorers(
    tournamentId: number
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/${tournamentId}/top-scorers`
    );
  }

  getMatch(
    matchId: number
  ): Observable<any> {
    return this.http.get(
      `http://localhost:9095/api/matches/${matchId}`
    );
  }

  getGoals(
    matchId: number
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${environment.api.match}/${matchId}/goals`
    );
  }

  getResults(
    tournamentId: number
  ): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.api}/${tournamentId}/results`
    );
  }
}
