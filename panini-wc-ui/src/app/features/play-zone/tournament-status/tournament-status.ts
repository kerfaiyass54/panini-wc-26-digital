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
  selector: 'app-tournament-status',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tournament-status.html',
  styleUrl: './tournament-status.scss',
})
export class TournamentStatus
  implements OnInit
{
  private readonly route =
    inject(ActivatedRoute);

  private readonly tournamentService =
    inject(TournamentService);

  tournamentId = 0;

  status: any;

  loading = true;

  ngOnInit(): void {

    this.tournamentId =
      Number(
        this.route.snapshot.paramMap.get(
          'id'
        )
      );

    this.loadStatus();
  }

  loadStatus(): void {

    this.tournamentService
      .getStatus(
        this.tournamentId
      )
      .subscribe({
        next: data => {

          this.status = data;

          this.loading = false;
        },
      });
  }
}
