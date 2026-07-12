import {
  Component,
  OnInit,
  inject,
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import Keycloak from 'keycloak-js';
import { TeamGeneratorService } from '../../services/team-generator.service';


@Component({
  selector: 'app-manual-team',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './manual-team.html',
  styleUrl: './manual-team.scss',
})
export class ManualTeam
  implements OnInit
{
  private readonly keycloak =
    inject(Keycloak);

  private readonly teamService =
    inject(TeamGeneratorService);

  teamName = '';

  ownedPlayers: any[] = [];

  selectedPlayers: any[] = [];

  canSave(): boolean {

    return (
      this.teamName.trim().length > 0 &&
      this.selectedPlayers.length === 17 &&
      this.goalkeepersCount === 2 &&
      this.defendersCount === 5 &&
      this.midfieldersCount === 5 &&
      this.forwardsCount === 5
    );
  }

  get email(): string {
    return (
      this.keycloak
        .tokenParsed?.[
        'email'
        ] as string
    ) ?? '';
  }

  ngOnInit(): void {
    this.loadPlayers();
  }

  loadPlayers(): void {
    this.teamService
      .getOwnedPlayers(
        this.email
      )
      .subscribe({
        next: response => {
          this.ownedPlayers =
            response.players ?? [];
        },
      });
  }

  get availablePlayers(): any[] {
    return this.ownedPlayers.filter(
      player =>
        !this.selectedPlayers.some(
          selected =>
            selected.id ===
            player.id
        )
    );
  }

  get goalkeepersCount(): number {
    return this.selectedPlayers.filter(
      player =>
        player.position ===
        'GOALKEEPER'
    ).length;
  }

  get defendersCount(): number {
    return this.selectedPlayers.filter(
      player =>
        player.position ===
        'DEFENDER'
    ).length;
  }

  get midfieldersCount(): number {
    return this.selectedPlayers.filter(
      player =>
        player.position ===
        'MIDFIELDER'
    ).length;
  }

  get forwardsCount(): number {
    return this.selectedPlayers.filter(
      player =>
        player.position ===
        'FORWARD'
    ).length;
  }

  addPlayer(
    player: any
  ): void {
    if (
      this.selectedPlayers.length >=
      17
    ) {
      return;
    }

    this.selectedPlayers.push(
      player
    );
  }

  removePlayer(
    player: any
  ): void {
    this.selectedPlayers =
      this.selectedPlayers.filter(
        selected =>
          selected.id !==
          player.id
      );
  }

  saveTeam(): void {
    if (!this.teamName.trim()) {
      alert(
        'Please enter a team name'
      );
      return;
    }

    if (
      this.selectedPlayers.length !==
      17
    ) {
      alert(
        'Team must contain exactly 17 players'
      );
      return;
    }

    this.teamService
      .createManualTeam({
        email: this.email,
        team_name:
        this.teamName,
        player_ids:
          this.selectedPlayers.map(
            player => player.id
          ),
      })
      .subscribe({
        next: () => {
          alert(
            'Team created successfully'
          );

          this.teamName = '';
          this.selectedPlayers =
            [];
        },
        error: error => {
          alert(
            error.error?.detail ??
            'Failed to create team'
          );
        },
      });
  }
}
