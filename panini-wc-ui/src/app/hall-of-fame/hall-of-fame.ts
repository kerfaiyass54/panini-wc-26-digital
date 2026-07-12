import {
  Component,
  OnInit,
  inject,
} from '@angular/core';

import {
  CommonModule,
} from '@angular/common';

import {
  UserStatisticsService,
} from '../services/user-statistics.service';
import { StatisticsService } from '../services/statistics.service';

@Component({
  selector: 'app-hall-of-fame',
  standalone: true,
  imports: [CommonModule],
  templateUrl:
    './hall-of-fame.html',
  styleUrl:
    './hall-of-fame.scss',
})
export class HallOfFame
  implements OnInit
{
  private readonly statisticsService =
    inject(
      StatisticsService
    );

  leaderboard: any[] = [];

  loading = true;

  ngOnInit(): void {

    this.statisticsService
      .getLeaderboard()
      .subscribe({
        next: data => {

          this.leaderboard =
            data;

          this.loading =
            false;
        },
      });
  }

  getWinRate(
    user: any
  ): number {

    if (
      user.matchesPlayed === 0
    ) {
      return 0;
    }

    return Math.round(
      (
        user.matchesWon /
        user.matchesPlayed
      ) * 100
    );
  }
}
