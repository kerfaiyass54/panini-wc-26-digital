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
  selector: 'app-tournament-matches',
  standalone: true,
  imports: [
    CommonModule,
  ],
  templateUrl:
    './tournament-matches.html',
  styleUrl:
    './tournament-matches.scss',
})
export class TournamentMatches
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  tournamentId = 0;

  journeys: any[] = [];

  selectedJourney: number | null =
    null;

  matches: any[] = [];

  ngOnInit(): void {
    this.tournamentId =
      Number(
        this.route.snapshot.paramMap.get(
          'id'
        )
      );

    this.loadJourneys();
  }

  loadJourneys(): void {
    this.tournamentService
      .getJourneys(
        this.tournamentId
      )
      .subscribe({
        next: response => {
          this.journeys =
            response;

          if (
            this.journeys.length
          ) {
            this.openJourney(
              this.journeys[0]
                .journey
            );
          }
        },
      });
  }

  openJourney(
    journey: number
  ): void {
    this.selectedJourney =
      journey;

    this.tournamentService
      .getJourneyMatches(
        this.tournamentId,
        journey
      )
      .subscribe({
        next: response =>
          (this.matches =
            response),
      });
  }
}
