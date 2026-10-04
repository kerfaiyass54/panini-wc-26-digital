import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AnalyticsService {

  private readonly http =
    inject(HttpClient);

  private readonly baseUrl =
    'http://localhost:9090/api';

  // =========================
  // NATIONS
  // =========================

  getAllNations(
    email: string
  ): Observable<Record<string, number>> {

    return this.http.get<
      any
    >(
      `${this.baseUrl}/stats?email=${email}`
    );
  }

  getNationCount(
    nation: string,
    email: string
  ): Observable<number> {

    return this.http.get<number>(
      `${this.baseUrl}/stats/${nation}?email=${email}`
    );
  }

  // =========================
  // GROUPS
  // =========================

  getAllGroups(
    email: string
  ): Observable<any> {

    return this.http.get<
      any
    >(
      `${this.baseUrl}/groups?email=${email}`
    );
  }

  getGroupCount(
    group: string,
    email: string
  ): Observable<number> {

    return this.http.get<number>(
      `${this.baseUrl}/groups/${group}?email=${email}`
    );
  }

  // =========================
  // CONTINENTS
  // =========================

  getAllContinents(
    email: string
  ): Observable<Record<string, number>> {

    return this.http.get<
      any
    >(
      `${this.baseUrl}/continents?email=${email}`
    );
  }

  getContinentCount(
    continent: string,
    email: string
  ): Observable<number> {

    return this.http.get<number>(
      `${this.baseUrl}/continents/${continent}?email=${email}`
    );
  }
}
