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
  selector: 'app-match-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './match-details.html',
  styleUrl: './match-details.scss',
})
export class MatchDetails
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  matchId = 0;

  loading = true;

  match: any = null;

  goals: any[] = [];

  ngOnInit(): void {

    this.matchId = Number(
      this.route.snapshot.paramMap.get(
        'id'
      )
    );

    this.loadData();
  }

  loadData(): void {

    this.tournamentService
      .getMatch(this.matchId)
      .subscribe({
        next: match => {

          this.match = match;

          this.loadGoals();
        },
      });
  }

  loadGoals(): void {

    this.tournamentService
      .getGoals(this.matchId)
      .subscribe({
        next: goals => {

          this.goals = goals;

          this.loading = false;
        },
      });
  }
}
