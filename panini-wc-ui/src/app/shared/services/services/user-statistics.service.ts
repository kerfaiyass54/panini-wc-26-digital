import { Injectable, inject } from '@angular/core';

import {
  HttpClient,
} from '@angular/common/http';

import {
  Observable,
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserStatisticsService {

  private readonly http =
    inject(HttpClient);

  private readonly api =
    'http://localhost:9094/api/statistics';

  getStatistics(
    email: string
  ): Observable<any> {

    return this.http.get<any>(
      `${this.api}/${email}`
    );
  }
}
