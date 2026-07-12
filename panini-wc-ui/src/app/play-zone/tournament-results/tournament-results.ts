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
  selector: 'app-tournament-results',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tournament-results.html',
  styleUrl: './tournament-results.scss',
})
export class TournamentResults
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  tournamentId = 0;

  loading = true;

  results: any[] = [];

  groupedResults:
    { journey: number; matches: any[] }[]
    = [];

  ngOnInit(): void {

    this.tournamentId =
      Number(
        this.route.snapshot.paramMap.get(
          'id'
        )
      );

    this.loadResults();
  }

  loadResults(): void {

    this.tournamentService
      .getResults(
        this.tournamentId
      )
      .subscribe({
        next: results => {

          this.results = results;

          const grouped =
            new Map<
              number,
              any[]
            >();

          results.forEach(
            match => {

              if (
                !grouped.has(
                  match.journey
                )
              ) {

                grouped.set(
                  match.journey,
                  []
                );
              }

              grouped
                .get(
                  match.journey
                )
                ?.push(match);
            }
          );

          this.groupedResults =
            Array.from(
              grouped.entries()
            ).map(
              ([journey, matches]) => ({
                journey,
                matches,
              })
            );

          this.loading = false;
        },
      });
  }
}
