import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecommendationResponse } from '../constants/recommendation.model';



@Injectable({
  providedIn: 'root'
})
export class RecommendationService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8000/recommendations';

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
}
