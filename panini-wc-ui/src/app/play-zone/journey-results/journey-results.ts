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
  selector: 'app-journey-results',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './journey-results.html',
  styleUrl: './journey-results.scss',
})
export class JourneyResults
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  tournamentId = 0;

  journey = 0;

  matches: any[] = [];

  loading = true;

  ngOnInit(): void {

    this.tournamentId =
      Number(
        this.route.snapshot.paramMap.get(
          'id'
        )
      );

    this.journey =
      Number(
        this.route.snapshot.paramMap.get(
          'journey'
        )
      );

    this.loadMatches();
  }

  loadMatches(): void {

    this.tournamentService
      .getJourneyMatches(
        this.tournamentId,
        this.journey
      )
      .subscribe({
        next: matches => {

          this.matches =
            matches;

          this.loading = false;
        },
      });
  }
}
