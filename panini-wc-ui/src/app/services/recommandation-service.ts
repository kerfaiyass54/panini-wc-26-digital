import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecommendationResponse } from '../constants/recommendation.model';

export interface TradePlayer {

  code: string;

  name: string;

  nationality: string;

  rating: number;

  score: number;
}

export interface TradeResponse {

  give: {

    players: TradePlayer[];

    countries: string[];
  };

  want: {

    players: TradePlayer[];

    countries: string[];
  };
}



@Injectable({
  providedIn: 'root'
})
export class RecommendationService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8000/recommendations';

  private readonly api =
    'http://localhost:8000/trade-request';

  getRecommendations(
    user1: string,
    user2: string
  ): Observable<RecommendationResponse> {

    const params = new HttpParams()
      .set('user1', user1)
      .set('user2', user2);

    return this.http.get<RecommendationResponse>(
      this.apiUrl,
      { params }
    );
  }

  generate(
    email: string
  ): Observable<TradeResponse> {

    const params =
      new HttpParams()
        .set('email', email);

    return this.http.get<TradeResponse>(
      this.api,
      { params }
    );
  }


}
