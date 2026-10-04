import {
  Component,
  inject,
  OnInit
} from '@angular/core';

import {
  CommonModule
} from '@angular/common';

import {
  FormsModule
} from '@angular/forms';

import {
  Router
} from '@angular/router';

import Keycloak from 'keycloak-js';
import { TeamGeneratorService } from '../../services/team-generator.service';
import { TournamentService } from '../../services/tournament';


@Component({
  selector: 'app-create-tournament',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './create-tournament.html',
  styleUrl: './create-tournament.scss'
})
export class CreateTournament
  implements OnInit {

  private readonly keycloak =
    inject(Keycloak);

  private readonly teamService =
    inject(TeamGeneratorService);

  private readonly tournamentService =
    inject(TournamentService);

  private readonly router =
    inject(Router);

  tournamentName = '';

  teams: any[] = [];

  selectedTeams: any[] = [];

  loading = false;

  get email(): string {

    return (
      this.keycloak.tokenParsed?.[
        'email'
        ] as string
    ) ?? '';
  }

  ngOnInit(): void {

    this.loadTeams();
  }

  loadTeams(): void {

    this.teamService
      .getTeams(
        this.email
      )
      .subscribe({
        next: teams =>
          this.teams = teams
      });
  }

  toggleTeam(
    team: any
  ): void {

    const exists =
      this.selectedTeams.some(
        t =>
          t.team_id ===
          team.team_id
      );

    if (exists) {

      this.selectedTeams =
        this.selectedTeams.filter(
          t =>
            t.team_id !==
            team.team_id
        );

      return;
    }

    this.selectedTeams.push(team);
  }

  isSelected(
    team: any
  ): boolean {

    return this.selectedTeams.some(
      t =>
        t.team_id ===
        team.team_id
    );
  }

  canCreate(): boolean {

    return (
      this.tournamentName.trim().length > 0 &&
      this.selectedTeams.length >= 2 &&
      this.selectedTeams.length % 2 === 0
    );
  }

  createTournament(): void {

    if (!this.canCreate()) {
      return;
    }

    this.loading = true;

    this.tournamentService
      .create({
        tournament:
        this.tournamentName,
        email:
        this.email
      })
      .subscribe({

        next: tournament => {

          const ids =
            this.selectedTeams.map(
              team =>
                team.id
            );

          this.tournamentService
            .addTeams(
              tournament.id,
              ids
            )
            .subscribe({

              next: () => {

                this.tournamentService
                  .generateFixtures(
                    tournament.id
                  )
                  .subscribe({

                    next: () => {

                      this.tournamentService
                        .initializeStandings(
                          tournament.id
                        )
                        .subscribe({

                          next: () => {

                            this.router.navigate([
                              '/tournament',
                              tournament.id
                            ]);
                          }
                        });
                    }
                  });
              }
            });
        }
      });
  }
}
