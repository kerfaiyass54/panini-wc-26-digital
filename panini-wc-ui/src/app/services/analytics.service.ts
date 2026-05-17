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

  getAllNations():
    Observable<Record<string, number>> {

    return this.http.get<
      Record<string, number>
    >(
      `${this.baseUrl}/nations`
    );
  }

  getNationCount(
    nation: string
  ): Observable<number> {

    return this.http.get<number>(
      `${this.baseUrl}/nations/${nation}`
    );
  }

  // =========================
  // GROUPS
  // =========================

  getAllGroups():
    Observable<Record<string, number>> {

    return this.http.get<
      Record<string, number>
    >(
      `${this.baseUrl}/groups`
    );
  }

  getGroupCount(
    group: string
  ): Observable<number> {

    return this.http.get<number>(
      `${this.baseUrl}/groups/${group}`
    );
  }

  // =========================
  // CONTINENTS
  // =========================

  getAllContinents():
    Observable<Record<string, number>> {

    return this.http.get<
      Record<string, number>
    >(
      `${this.baseUrl}/continents`
    );
  }

  getContinentCount(
    continent: string
  ): Observable<number> {

    return this.http.get<number>(
      `${this.baseUrl}/continents/${continent}`
    );
  }
}
