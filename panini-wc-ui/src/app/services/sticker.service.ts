// src/app/core/services/sticker.service.ts

import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// ─────────────────────────────────────────────
// DTOs
// ─────────────────────────────────────────────

export interface StickerDTO {
  id?: number;
  name: string;
  type: string;
  nationality: string;
  place: string;
}

export interface StickerStatsDTO {
  total: number;
  logos: number;
  intros: number;
  players: number;
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
// Service
// ─────────────────────────────────────────────

@Injectable({
  providedIn: 'root',
})
export class StickerService {

  private readonly http = inject(HttpClient);

  private readonly API =
    'http://localhost:9090/api/stickers';

  // ─────────────────────────────────────────
  // GET ALL PAGINATED
  // ─────────────────────────────────────────

  getAll(
    page: number = 0,
    size: number = 5,
    sortBy: string = 'id'
  ): Observable<PageResponse<StickerDTO>> {

    return this.http.get<PageResponse<StickerDTO>>(
      `${this.API}?page=${page}&size=${size}&sortBy=${sortBy}`
    );
  }

  // ─────────────────────────────────────────
  // GET STATS
  // ─────────────────────────────────────────

  getStats(): Observable<StickerStatsDTO> {

    return this.http.get<StickerStatsDTO>(
      `${this.API}/stats`
    );
  }

  // ─────────────────────────────────────────
  // CREATE
  // ─────────────────────────────────────────

  create(
    sticker: StickerDTO
  ): Observable<StickerDTO> {

    return this.http.post<StickerDTO>(
      this.API,
      sticker
    );
  }

  // ─────────────────────────────────────────
  // UPDATE
  // ─────────────────────────────────────────

  update(
    id: number,
    sticker: StickerDTO
  ): Observable<StickerDTO> {

    return this.http.put<StickerDTO>(
      `${this.API}/${id}`,
      sticker
    );
  }

  // ─────────────────────────────────────────
  // DELETE
  // ─────────────────────────────────────────

  delete(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.API}/${id}`
    );
  }

  search(
    query: string,
    page: number = 0,
    size: number = 5
  ) {

    return this.http.get<PageResponse<StickerDTO>>(
      `${this.API}/search?query=${query}&page=${page}&size=${size}`
    );
  }
}


