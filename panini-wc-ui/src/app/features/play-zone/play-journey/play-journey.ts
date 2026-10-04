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
  selector: 'app-play-journey',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './play-journey.html',
  styleUrl: './play-journey.scss',
})
export class PlayJourney
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  tournamentId = 0;

  nextJourney: number | null =
    null;

  loading = true;

  playing = false;

  ngOnInit(): void {

    this.tournamentId =
      Number(
        this.route.snapshot.paramMap.get(
          'id'
        )
      );

    this.loadJourney();
  }

  loadJourney(): void {

    this.tournamentService
      .getNextJourney(
        this.tournamentId
      )
      .subscribe({
        next: journey => {

          this.nextJourney =
            journey;

          this.loading = false;
        },
      });
  }

  playJourney(): void {

    if (
      this.nextJourney === null
    ) {
      return;
    }

    this.playing = true;

    this.tournamentService
      .playJourney(
        this.tournamentId,
        this.nextJourney
      )
      .subscribe({
        next: () => {

          this.playing = false;

          this.loadJourney();
        },
      });
  }
}
