import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StatisticsService {
  private http = inject(HttpClient);

  private api =
    'http://localhost:9094/api/statistics';

  getUserStatistics(
    email: string
  ): Observable<any> {
    return this.http.get<any>(
      `${this.api}/${email}`
    );
  }

  getLeaderboard() {

    return this.http.get<any[]>(
      `${this.api}/leaderboard`
    );
  }
}
