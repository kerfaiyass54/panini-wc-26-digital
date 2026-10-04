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
  selector: 'app-tournament-dashboard',
  standalone: true,
  imports: [
    CommonModule,
  ],
  templateUrl:
    './tournament-dashboard.html',
  styleUrl:
    './tournament-dashboard.scss',
})
export class TournamentDashboard
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  tournamentId = 0;

  loading = true;

  status: any = null;

  statistics: any = null;

  standings: any[] = [];

  topScorers: any[] = [];

  results: any[] = [];

  journeys: any[] = [];

  nextJourney:
    number | null = null;

  ngOnInit(): void {
    this.tournamentId =
      Number(
        this.route.snapshot.paramMap.get(
          'id'
        )
      );

    this.loadData();
  }

  loadData(): void {
    this.loading = true;

    this.loadStatus();

    this.loadStatistics();

    this.loadStandings();

    this.loadTopScorers();

    this.loadResults();

    this.loadJourneys();

    this.loadNextJourney();

    setTimeout(() => {
      this.loading = false;
    }, 1000);
  }

  loadStatus(): void {
    this.tournamentService
      .getStatus(
        this.tournamentId
      )
      .subscribe({
        next: response => {
          this.status =
            response;
        },
      });
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
        },
      });
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
        },
      });
  }

  loadTopScorers(): void {
    this.tournamentService
      .getTopScorers(
        this.tournamentId
      )
      .subscribe({
        next: response => {
          this.topScorers =
            response;
        },
      });
  }

  loadResults(): void {
    this.tournamentService
      .getResults(
        this.tournamentId
      )
      .subscribe({
        next: response => {
          this.results =
            response;
        },
      });
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
        },
      });
  }

  loadNextJourney(): void {
    this.tournamentService
      .getNextJourney(
        this.tournamentId
      )
      .subscribe({
        next: response => {
          this.nextJourney =
            response.journey;
        },
        error: () => {
          this.nextJourney =
            null;
        },
      });
  }

  playNextJourney(): void {
    if (
      this.nextJourney === null
    ) {
      return;
    }

    this.tournamentService
      .playJourney(
        this.tournamentId,
        this.nextJourney
      )
      .subscribe({
        next: () => {
          setTimeout(() => {
            this.loadData();
          }, 2500);
        },
      });
  }

  get champion(): string {
    return (
      this.status?.champion ??
      '-'
    );
  }

  get playedMatches(): number {
    return (
      this.status
        ?.playedMatches ?? 0
    );
  }

  get remainingMatches(): number {
    return (
      this.status
        ?.remainingMatches ??
      0
    );
  }

  get totalMatches(): number {
    return (
      this.status
        ?.totalMatches ?? 0
    );
  }

  get currentJourney():
    | number
    | string {
    return (
      this.status
        ?.currentJourney ??
      '-'
    );
  }

  get tournamentName():
    string {
    return (
      this.status
        ?.tournamentName ??
      'Tournament'
    );
  }

  get finished():
    boolean {
    return (
      this.status
        ?.finished ??
      false
    );
  }
}
