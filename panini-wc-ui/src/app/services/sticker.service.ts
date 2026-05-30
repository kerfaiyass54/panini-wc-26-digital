// src/app/core/services/sticker.service.ts

import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// ─────────────────────────────────────────────
// DTOs
// ─────────────────────────────────────────────

export interface StickerSimpleResponse {
  id: number;
  code: string;
  type: string;
  nationality: string;
  playerName: string;
}

export interface StatisticsResponse {
  totalOwned: number;
  totalDuplicates: number;
  missing: number;
  completionPercentage: number;
}

export interface AddStickerRequest {
  email: string;
  code: string;
}

export interface CheckStickerRequest {
  email: string;
  place: string;
}

export interface DuplicateRequest {
  email: string;
  place: string;
}

export interface Owning {
  id: number;
  email: string;
  place: string;
  code: string;
}

export interface Duplicate {
  id: number;
  email: string;
  place: string;
  count: number;
}

export interface PageResponse<T> {
  content: T[];

  totalElements: number;
  totalPages: number;

  size: number;
  number: number;

  first: boolean;
  last: boolean;

  empty: boolean;
}

// ─────────────────────────────────────────────
// SERVICE
// ─────────────────────────────────────────────

@Injectable({
  providedIn: 'root',
})
export class StickerService {

  private readonly http = inject(HttpClient);

  private readonly API =
    'http://localhost:9090/api';

  // ─────────────────────────────────────────
  // CATALOG
  // ─────────────────────────────────────────

  getCatalog(
    page: number = 0,
    size: number = 50
  ): Observable<any> {

    return this.http.get<any>(
      `${this.API}/catalog/stickers?page=${page}&size=${size}`
    );
  }

  // ─────────────────────────────────────────
  // STATISTICS
  // ─────────────────────────────────────────

  getStatistics(
    email: string
  ): Observable<StatisticsResponse> {

    return this.http.get<StatisticsResponse>(
      `${this.API}/statistics/${email}`
    );
  }

  getFinished(
    email: string
  ): Observable<any> {

    return this.http.get<any>(
      `${this.API}/statistics/finished/${email}`
    );
  }

  // ─────────────────────────────────────────
// COUNT STICKERS BY NATIONALITY
// ─────────────────────────────────────────

  // ─────────────────────────────────────────
// COUNT OWNED STICKERS BY NATIONALITY
// ─────────────────────────────────────────

  countByNationality(
    nationality: string,
    email: string
  ): Observable<number> {

    return this.http.get<number>(
      `${this.API}/catalog/stickers/count/${nationality}/${email}`
    );
  }

  // ─────────────────────────────────────────
  // ADD STICKER
  // ─────────────────────────────────────────

  addSticker(
    request: AddStickerRequest
  ): Observable<Owning> {

    return this.http.post<Owning>(
      `${this.API}/stickers/owning`,
      request
    );
  }

  // ─────────────────────────────────────────
  // CHECK STICKER
  // ─────────────────────────────────────────

  hasSticker(
    request: CheckStickerRequest
  ): Observable<boolean> {

    return this.http.post<boolean>(
      `${this.API}/stickers/has`,
      request
    );
  }

  // ─────────────────────────────────────────
  // CREATE DUPLICATE
  // ─────────────────────────────────────────

  duplicateSticker(
    request: DuplicateRequest
  ): Observable<Duplicate> {

    return this.http.post<Duplicate>(
      `${this.API}/stickers/duplicate`,
      request
    );
  }

  // ─────────────────────────────────────────
  // INCREASE DUPLICATE
  // ─────────────────────────────────────────

  increaseDuplicate(
    request: DuplicateRequest
  ): Observable<Duplicate> {

    return this.http.put<Duplicate>(
      `${this.API}/stickers/duplicate/increase`,
      request
    );
  }

  // ─────────────────────────────────────────
  // GET OWNINGS
  // ─────────────────────────────────────────

  getOwnings(
    email: string,
    page: number = 0,
    size: number = 10
  ): Observable<PageResponse<Owning>> {

    return this.http.get<PageResponse<Owning>>(
      `${this.API}/stickers/ownings/${email}?page=${page}&size=${size}`
    );
  }

  // ─────────────────────────────────────────
  // GET DUPLICATES
  // ─────────────────────────────────────────

  getDuplicates(
    email: string,
    page: number = 0,
    size: number = 10
  ): Observable<PageResponse<Duplicate>> {

    return this.http.get<PageResponse<Duplicate>>(
      `${this.API}/stickers/duplicates/${email}?page=${page}&size=${size}`
    );
  }

  // ─────────────────────────────────────────
  // REMOVE DUPLICATE
  // ─────────────────────────────────────────

  removeDuplicate(
    email: string,
    place: string
  ): Observable<string> {

    return this.http.delete(
      `${this.API}/stickers/duplicate?email=${email}&place=${place}`,
      {
        responseType: 'text'
      }
    );
  }

  // ─────────────────────────────────────────
// GET STICKERS BY NATIONALITY
// ─────────────────────────────────────────

  getByNationality(
    nationality: string
  ): Observable<any> {

    return this.http.get<any>(
      `${this.API}/catalog/stickers/nationality/${nationality}`
    );
  }

  addDate(item: any): Observable<any> {

    return this.http.post<any>(
      `${this.API}/items`, item
    );
  }

}
