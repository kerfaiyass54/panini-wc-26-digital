import {
  ChangeDetectorRef,
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';
import Keycloak from 'keycloak-js';
import { RecommendationService, TradeResponse } from '../services/recommandation-service';




@Component({
  selector: 'app-swap-public-recommend',

  standalone: true,

  imports: [
    CommonModule
  ],

  templateUrl:
    './swap-public-recommend.html',

  styleUrl:
    './swap-public-recommend.scss'
})
export class SwapPublicRecommend
  implements OnInit {

  private readonly service =
    inject(
      RecommendationService
    );

  private readonly cdr =
    inject(ChangeDetectorRef);

  private readonly keycloak =
    inject(Keycloak);

  loading = false;

  result?: TradeResponse;

  get email(): string {

    return (
      this.keycloak
        .tokenParsed?.[
        'email'
        ] as string
    ) ?? '';
  }

  ngOnInit(): void {

    this.generate();
  }

  generate(): void {

    this.loading = true;

    this.service
      .generate(
        this.email
      )
      .subscribe({

        next: response => {

          this.result =
            response;

          this.loading = false;

          this.cdr.detectChanges();
        },

        error: () => {

          this.loading = false;

          this.cdr.detectChanges();
        }
      });
  }
}
