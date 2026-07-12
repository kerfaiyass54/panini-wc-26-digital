import {
  Component,
  OnInit,
  inject,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import { Router } from '@angular/router';



import {
  TournamentService,
} from '../services/tournament';

import {
  TeamGeneratorService,
} from '../services/team-generator.service';
import Keycloak from 'keycloak-js';

@Component({
  selector: 'app-play-zone',
  standalone: true,
  imports: [
    CommonModule,
  ],
  templateUrl: './play-zone.html',
  styleUrl: './play-zone.scss',
})
export class PlayZone
  implements OnInit {

  private readonly keycloak =
    inject(Keycloak);

  private readonly tournamentService =
    inject(TournamentService);

  private readonly teamService =
    inject(TeamGeneratorService);

  private readonly router =
    inject(Router);

  tournaments: any[] = [];

  teams: any[] = [];

  get email(): string {

    return (
      this.keycloak
        .tokenParsed?.[
        'email'
        ] as string
    ) ?? '';
  }

  ngOnInit(): void {

    this.loadTeams();

    this.loadTournaments();
  }

  loadTeams(): void {

    this.teamService
      .getTeams(
        this.email
      )
      .subscribe({
        next: (
          teams: any[]
        ) => {

          this.teams =
            teams;
        },
      });
  }

  loadTournaments(): void {

    this.tournamentService
      .getByEmail(
        this.email
      )
      .subscribe({
        next: (
          tournaments: any[]
        ) => {

          this.tournaments =
            tournaments;
        },
      });
  }

  openTournament(
    tournament: any
  ): void {

    void this.router.navigate([
      '/tournament',
      tournament.id,
    ]);
  }

  createTournament(): void {

    void this.router.navigate([
      '/create-tournament',
    ]);
  }

  createTeam(): void {

    void this.router.navigate([
      '/my-teams',
    ]);
  }
}
