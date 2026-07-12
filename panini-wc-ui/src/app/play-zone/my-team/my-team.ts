import {
  Component,
  OnInit,
  inject,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import Keycloak from 'keycloak-js';
import { TeamGeneratorService } from '../../services/team-generator.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-my-team',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-team.html',
  styleUrl: './my-team.scss',
})
export class MyTeam
  implements OnInit {

  private readonly keycloak =
    inject(Keycloak);

  private readonly teamService =
    inject(TeamGeneratorService);

  goToAutoGeneration(): void {

    this.router.navigate([
      '/generate-team'
    ]);
  }

  goToManualCreation(): void {

    this.router.navigate([
      '/manual-team'
    ]);
  }

  private readonly router =
    inject(Router);

  teams: any[] = [];

  loading = false;

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
  }

  loadTeams(): void {

    this.loading = true;

    this.teamService
      .getTeams(this.email)
      .subscribe({

        next: teams => {

          this.teams = teams;
          this.loading = false;
        },

        error: () => {

          this.loading = false;
        },
      });
  }

  deleteTeam(
    teamId: string
  ): void {

    if (
      !confirm(
        'Delete this team?'
      )
    ) {
      return;
    }

    this.teamService
      .deleteTeam(teamId)
      .subscribe(() => {

        this.loadTeams();
      });
  }

  autoGenerateTeam(): void {

    const teamName =
      prompt('Team name');

    if (!teamName) {
      return;
    }

    this.teamService
      .generateAutoTeam(
        this.email,
        teamName
      )
      .subscribe(() => {

        this.loadTeams();
      });
  }
}
