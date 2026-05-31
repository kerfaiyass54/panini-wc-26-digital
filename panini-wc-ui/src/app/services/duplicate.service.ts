import { Injectable, inject } from '@angular/core';

import {
  HttpClient,
} from '@angular/common/http';

import {
  Observable,
} from 'rxjs';

export interface DuplicateRequest {

  email: string;

  place: string;
}

@Injectable({
  providedIn: 'root',
})
export class DuplicateService {

  private readonly http =
    inject(HttpClient);

  private readonly API =
    'http://localhost:9090';

  getDuplicates(
    email: string
  ): Observable<any[]> {

    return this.http.get<any[]>(
      `${this.API}/duplicates/${email}`
    );
  }

  reduceDuplicate(
    request: DuplicateRequest
  ): Observable<void> {

    return this.http.patch<void>(
      `${this.API}/duplicates/reduce`,
      request
    );
  }

  deleteDuplicate(
    request: DuplicateRequest
  ): Observable<void> {

    return this.http.request<void>(
      'DELETE',
      `${this.API}/duplicates`,
      {
        body: request
      }
    );
  }
}
