import {
  Component,
  OnInit,
  inject,
} from '@angular/core';

import {
  CommonModule,
} from '@angular/common';

import Keycloak from 'keycloak-js';

import {
  UserStatisticsService,
} from '../services/user-statistics.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.scss',
})
export class Profile
  implements OnInit
{
  private readonly keycloak =
    inject(Keycloak);

  private readonly statisticsService =
    inject(
      UserStatisticsService
    );

  loading = true;

  statistics: any = null;

  get email(): string {

    return (
      this.keycloak
        .tokenParsed?.[
        'email'
        ] as string
    ) ?? '';
  }

  get username(): string {

    return (
      this.keycloak
        .tokenParsed?.[
        'preferred_username'
        ] as string
    ) ?? '';
  }

  ngOnInit(): void {

    this.loadStatistics();
  }

  loadStatistics(): void {

    this.statisticsService
      .getStatistics(
        this.email
      )
      .subscribe({
        next: stats => {

          this.statistics =
            stats;

          this.loading =
            false;
        },
      });
  }

  get winRate(): number {

    if (
      !this.statistics ||
      this.statistics
        .matchesPlayed === 0
    ) {
      return 0;
    }

    return Math.round(
      (
        this.statistics.matchesWon /
        this.statistics
          .matchesPlayed
      ) * 100
    );
  }
}
