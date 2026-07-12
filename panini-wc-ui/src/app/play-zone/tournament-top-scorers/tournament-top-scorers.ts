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
  selector: 'app-tournament-top-scorers',
  standalone: true,
  imports: [CommonModule],
  templateUrl:
    './tournament-top-scorers.html',
  styleUrl:
    './tournament-top-scorers.scss',
})
export class TournamentTopScorers
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  scorers: any[] = [];

  tournamentId = 0;

  ngOnInit(): void {

    this.tournamentId =
      Number(
        this.route.snapshot.paramMap.get(
          'id'
        )
      );

    this.loadScorers();
  }

  loadScorers(): void {

    this.tournamentService
      .getTopScorers(
        this.tournamentId
      )
      .subscribe({
        next: data =>
          (this.scorers = data),
      });
  }
}
