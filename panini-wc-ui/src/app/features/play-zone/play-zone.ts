import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import Keycloak from 'keycloak-js';
import { TournamentService } from '../../shared/services/services/tournament';
import { TeamGeneratorService } from '../../shared/services/services/team-generator.service';

@Component({
  selector: 'app-play-zone',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './play-zone.html',
  styleUrl: './play-zone.scss',
})
export class PlayZone implements OnInit {
  private readonly keycloak = inject(Keycloak);
  private readonly tournamentService = inject(TournamentService);
  private readonly teamService = inject(TeamGeneratorService);
  private readonly router = inject(Router);

  teams: any[] = [];
  tournaments: any[] = [];
  teamsLoading = false;
  tournamentsLoading = false;
  teamsError = '';
  tournamentsError = '';

  private get email(): string {
    return (this.keycloak.tokenParsed?.['email'] as string) ?? '';
  }

  ngOnInit(): void {
    this.loadTeams();
    this.loadTournaments();
  }

  loadTeams(): void {
    this.teamsLoading = true;
    this.teamsError = '';
    this.teamService.getTeams(this.email).pipe(
      finalize(() => this.teamsLoading = false),
    ).subscribe({
      next: response => {
        const teams = Array.isArray(response)
          ? response
          : Array.isArray((response as any)?.teams) ? (response as any).teams : [];
        this.teams = teams.map((team: any) => ({
          ...team,
          team_id: team.team_id ?? team.id ?? team.team_name,
          team_name: team.team_name ?? team.name ?? 'Untitled team',
          players: Array.isArray(team.players) ? team.players : [],
        }));
      },
      error: () => this.teamsError = 'Check your connection and try again.',
    });
  }

  loadTournaments(): void {
    this.tournamentsLoading = true;
    this.tournamentsError = '';
    this.tournamentService.getByEmail(this.email).pipe(
      finalize(() => this.tournamentsLoading = false),
    ).subscribe({
      next: response => {
        this.tournaments = Array.isArray(response)
          ? response
          : Array.isArray((response as any)?.tournaments) ? (response as any).tournaments : [];
      },
      error: () => this.tournamentsError = 'Check your connection and try again.',
    });
  }

  openTournament(tournament: any): void {
    void this.router.navigate(['/tournament', tournament.id]);
  }

  createTournament(): void {
    void this.router.navigate(['/create-tournament']);
  }

  createTeam(): void {
    void this.router.navigate(['/my-teams']);
  }
}
