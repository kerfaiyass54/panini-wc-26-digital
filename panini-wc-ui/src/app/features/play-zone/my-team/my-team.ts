import {
  ChangeDetectorRef,
  Component,
  OnInit,
  inject,
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import Keycloak from 'keycloak-js';
import { TeamGeneratorService } from '../../services/team-generator.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-my-team',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './my-team.html',
  styleUrl: './my-team.scss',
})
export class MyTeam
  implements OnInit {

  private readonly keycloak =
    inject(Keycloak);

  private readonly teamService =
    inject(TeamGeneratorService);

  private readonly cdr =
    inject(ChangeDetectorRef);

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

  selectedTeam: any | null = null;

  loading = false;

  loadError = '';

  readonly lineupPositions = [
    { key: 'GOALKEEPER', label: 'Goalkeepers', icon: '🧤' },
    { key: 'DEFENDER', label: 'Defenders', icon: '🛡️' },
    { key: 'MIDFIELDER', label: 'Midfielders', icon: '⚙️' },
    { key: 'FORWARD', label: 'Forwards', icon: '🎯' },
  ];

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
    this.loadError = '';

    this.teamService
      .getTeams(this.email)
      .subscribe({

        next: teams => {

          const response = Array.isArray(teams)
            ? teams
            : Array.isArray((teams as any)?.teams)
              ? (teams as any).teams
              : [];
          this.teams = response.map((team: any) => ({
            ...team,
            team_id: team.team_id ?? team.id,
            team_name: team.team_name ?? team.name ?? 'Untitled team',
            players: Array.isArray(team.players) ? team.players : [],
          }));
          this.loading = false;
          this.cdr.detectChanges();
        },

        error: () => {
          this.loadError = 'Could not load your teams. Please try again.';
          this.loading = false;
          this.cdr.detectChanges();
        },
      });
  }

  playersForPosition(team: any, position: string): any[] {
    return (team?.players ?? []).filter((player: any) => {
      const playerPosition = String(player.position ?? '').toUpperCase();
      return position === 'FORWARD'
        ? playerPosition === 'FORWARD' || playerPosition === 'STRIKER'
        : playerPosition === position;
    });
  }

  openTeam(team: any): void {
    this.selectedTeam = team;
  }

  closeTeam(): void {
    this.selectedTeam = null;
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
        this.cdr.detectChanges();
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
