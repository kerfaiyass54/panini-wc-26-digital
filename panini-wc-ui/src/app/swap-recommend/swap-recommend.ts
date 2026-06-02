import {
  Component,
  inject,
  ChangeDetectorRef, OnInit
} from '@angular/core';


import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { RecommendationResponse } from '../constants/recommendation.model';
import { RecommendationService } from '../services/recommandation-service';
import Keycloak from 'keycloak-js';

@Component({
  selector: 'app-swap-recommend',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './swap-recommend.html',
  styleUrl: './swap-recommend.scss'
})
export class SwapRecommend implements OnInit{

  private readonly recommendationService =
    inject(RecommendationService);

  private readonly keycloak =
    inject(Keycloak);

  private readonly cdr =
    inject(ChangeDetectorRef);

  private readonly route =
    inject(ActivatedRoute);

  loading = false;

  recommendation?: RecommendationResponse;

  otherUserEmail = '';

  targetEmail = '';

  get email(): string {

    return (
      this.keycloak.tokenParsed?.[
        'email'
        ] as string
    ) ?? '';
  }

  ngOnInit() {
    this.route.paramMap.subscribe(
      params => {

        this.targetEmail =
          params.get('email') ?? '';

        this.cdr.detectChanges();
      }
    );
  }

  generateRecommendations(): void {

    if (!this.targetEmail) {
      return;
    }

    this.loading = true;

    this.recommendationService
      .getRecommendations(
        this.email,
        this.targetEmail
      )
      .subscribe({

        next: result => {

          this.recommendation =
            result;

          this.loading =
            false;

          this.cdr.detectChanges();
        },

        error: () => {

          this.loading =
            false;

          this.cdr.detectChanges();
        }
      });
  }

  downloadJson(): void {

    if (!this.recommendation) {
      return;
    }

    const blob = new Blob(
      [
        JSON.stringify(
          this.recommendation,
          null,
          2
        )
      ],
      {
        type:
          'application/json'
      }
    );

    const url =
      window.URL.createObjectURL(
        blob
      );

    const a =
      document.createElement('a');

    a.href = url;

    a.download =
      'recommendations.json';

    a.click();

    window.URL
      .revokeObjectURL(url);
  }

  downloadCsv(): void {

    if (!this.recommendation) {
      return;
    }

    const rows = this.recommendation.swaps
      .map(s =>
        [
          s.user1_gives.code,
          s.user1_gives.name,
          s.user1_gives.rating,

          s.user2_gives.code,
          s.user2_gives.name,
          s.user2_gives.rating,

          s.score,
          s.fairness
        ].join(',')
      );

    const csv =
      [
        'user1_code,user1_name,user1_rating,user2_code,user2_name,user2_rating,score,fairness',
        ...rows
      ].join('\n');

    const blob =
      new Blob(
        [csv],
        {
          type:
            'text/csv'
        }
      );

    const url =
      URL.createObjectURL(
        blob
      );

    const a =
      document.createElement('a');

    a.href = url;

    a.download =
      'recommendations.csv';

    a.click();

    URL.revokeObjectURL(
      url
    );
  }
}
