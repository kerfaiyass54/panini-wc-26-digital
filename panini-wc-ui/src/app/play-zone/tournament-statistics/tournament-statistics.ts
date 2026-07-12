import {
  Component,
  OnInit,
  inject,
} from '@angular/core';

import {
  CommonModule,
} from '@angular/common';

import {
  ActivatedRoute,
} from '@angular/router';
import { TournamentService } from '../../services/tournament';


@Component({
  selector:
    'app-tournament-statistics',

  standalone: true,

  imports: [
    CommonModule,
  ],

  templateUrl:
    './tournament-statistics.html',

  styleUrl:
    './tournament-statistics.scss',
})
export class TournamentStatistics
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  tournamentId = 0;

  statistics: any;

  loading = true;

  ngOnInit(): void {
    this.tournamentId =
      Number(
        this.route.snapshot.paramMap.get(
          'id'
        )
      );

    this.loadStatistics();
  }

  loadStatistics(): void {
    this.tournamentService
      .getStatistics(
        this.tournamentId
      )
      .subscribe({
        next: response => {
          this.statistics =
            response;

          this.loading =
            false;
        },
      });
  }
}
