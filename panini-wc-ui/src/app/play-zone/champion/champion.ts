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
  selector: 'app-champion',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './champion.html',
  styleUrl: './champion.scss',
})
export class Champion
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  tournamentId = 0;

  status: any;

  statistics: any;

  loading = true;

  ngOnInit(): void {

    this.tournamentId = Number(
      this.route.snapshot.paramMap.get(
        'id'
      )
    );

    this.loadData();
  }

  loadData(): void {

    this.tournamentService
      .getStatus(
        this.tournamentId
      )
      .subscribe({
        next: status => {

          this.status = status;

          this.loadStatistics();
        },
      });
  }

  loadStatistics(): void {

    this.tournamentService
      .getStatistics(
        this.tournamentId
      )
      .subscribe({
        next: statistics => {

          this.statistics =
            statistics;

          this.loading = false;
        },
      });
  }
}
