import {
  Component,
  OnInit,
  inject,
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import Keycloak from 'keycloak-js';
import { TeamGeneratorService } from '../../services/team-generator.service';

type PositionKey = 'GOALKEEPER' | 'DEFENDER' | 'MIDFIELDER' | 'STRIKER';

interface PositionOption {
  key: PositionKey;
  label: string;
  required: number;
  icon: string;
}

@Component({
  selector: 'app-manual-team',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
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

  readonly positions: PositionOption[] = [
    { key: 'GOALKEEPER', label: 'Goalkeepers', required: 2, icon: '🧤' },
    { key: 'DEFENDER', label: 'Defenders', required: 5, icon: '🛡️' },
    { key: 'MIDFIELDER', label: 'Midfielders', required: 5, icon: '⚙️' },
    { key: 'STRIKER', label: 'Strikers', required: 5, icon: '🎯' },
  ];

  teamName = '';

  playerSearch = '';

  activePosition: PositionKey = 'GOALKEEPER';

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
        next: players => {
          this.ownedPlayers =
            players ?? [];
        },
      });
  }

  get activePositionOption(): PositionOption {
    return this.positions.find(
      position => position.key === this.activePosition
    )!;
  }

  get activePositionPlayers(): any[] {
    const query = this.playerSearch.trim().toLocaleLowerCase();

    return this.ownedPlayers.filter(player => {
      const playerPosition = this.normalizePosition(player.position);
      const isActivePosition = playerPosition === this.activePosition;
      const notSelected = !this.selectedPlayers.some(
        selected => selected.id === player.id
      );
      const matchesSearch = !query || [
        player.name,
        player.position,
        player.nationality,
      ].some(value => value?.toLocaleLowerCase().includes(query));

      return isActivePosition && notSelected && matchesSearch;
    });
  }

  positionCount(position: PositionKey): number {
    return this.selectedPlayers.filter(
      player => this.normalizePosition(player.position) === position
    ).length;
  }

  selectedForPosition(position: PositionKey): any[] {
    return this.selectedPlayers.filter(
      player => this.normalizePosition(player.position) === position
    );
  }

  isPositionFull(position: PositionKey): boolean {
    const option = this.positions.find(item => item.key === position);
    return !option || this.positionCount(position) >= option.required;
  }

  selectPosition(position: PositionKey): void {
    this.activePosition = position;
    this.playerSearch = '';
  }

  private normalizePosition(position: string | null | undefined): PositionKey | null {
    const normalized = position?.trim().toUpperCase();
    if (normalized === 'FORWARD') {
      return 'STRIKER';
    }
    return this.positions.some(item => item.key === normalized)
      ? normalized as PositionKey
      : null;
  }

  get goalkeepersCount(): number {
    return this.positionCount('GOALKEEPER');
  }

  get defendersCount(): number {
    return this.positionCount('DEFENDER');
  }

  get midfieldersCount(): number {
    return this.positionCount('MIDFIELDER');
  }

  get forwardsCount(): number {
    return this.positionCount('STRIKER');
  }

  addPlayer(player: any): void {
    if (this.selectedPlayers.length >= 17 || this.isPositionFull(this.activePosition)) {
      return;
    }
    this.selectedPlayers.push(
      player
    );
  }

  removePlayer(player: any): void {
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
