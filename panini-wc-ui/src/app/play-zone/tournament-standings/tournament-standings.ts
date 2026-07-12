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
  selector: 'app-tournament-standings',
  standalone: true,
  imports: [
    CommonModule,
  ],
  templateUrl:
    './tournament-standings.html',
  styleUrl:
    './tournament-standings.scss',
})
export class TournamentStandings
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  tournamentId = 0;

  standings: any[] = [];

  loading = true;

  ngOnInit(): void {
    this.tournamentId =
      Number(
        this.route.snapshot.paramMap.get(
          'id'
        )
      );

    this.loadStandings();
  }

  loadStandings(): void {
    this.tournamentService
      .getStandings(
        this.tournamentId
      )
      .subscribe({
        next: response => {
          this.standings =
            response;

          this.loading =
            false;
        },
      });
  }

  getRankClass(
    index: number
  ): string {
    if (index === 0) {
      return 'gold';
    }

    if (index === 1) {
      return 'silver';
    }

    if (index === 2) {
      return 'bronze';
    }

    return '';
  }
}
