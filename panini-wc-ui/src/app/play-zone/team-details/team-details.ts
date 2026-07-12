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
import { TeamGeneratorService } from '../../services/team-generator.service';
import { TeamService } from '../../services/team.service';



@Component({
  selector: 'app-team-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './team-details.html',
  styleUrl: './team-details.scss',
})
export class TeamDetails
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly teamService =
    inject(
      TeamService
    );

  loading = true;

  team: any;

  teamId = 0;

  ngOnInit(): void {

    this.teamId = Number(
      this.route.snapshot.paramMap.get(
        'id'
      )
    );

    this.loadTeam();
  }

  loadTeam(): void {

    this.teamService
      .getTeamById(
        this.teamId
      )
      .subscribe({
        next: team => {

          this.team = team;

          this.loading =
            false;
        },
      });
  }

  get averageAbility(): number {

    if (
      !this.team ||
      !this.team.players?.length
    ) {
      return 0;
    }

    const total =
      this.team.players.reduce(
        (
          sum: number,
          player: any
        ) =>
          sum +
          (player.ability || 0),
        0
      );

    return Math.round(
      total /
      this.team.players.length
    );
  }

  getPlayersByPosition(
    position: string
  ) {

    return this.team.players.filter(
      (p: any) =>
        p.position ===
        position
    );
  }
}
