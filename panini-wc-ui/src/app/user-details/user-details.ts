import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';

import { ActivatedRoute } from '@angular/router';



import {
  forkJoin
} from 'rxjs';
import {StickerService } from '../services/sticker.service';

@Component({
  selector: 'app-user-details',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './user-details.html',
  styleUrl: './user-details.scss',
  changeDetection:
  ChangeDetectionStrategy.OnPush
})
export class UserDetails implements OnInit {

  private readonly route =
    inject(ActivatedRoute);

  private readonly stickerService =
    inject(StickerService);

  private readonly cdr =
    inject(ChangeDetectorRef);

  email = '';

  loading = true;

  statistics?: any;

  duplicates: any[] = [];

  finishedTeams: any[] = [];

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {

      this.email =
        params.get('email') ?? '';

      if (!this.email) {
        return;
      }

      this.loadData();
    });
  }

  private loadData(): void {

    this.loading = true;

    forkJoin({

      statistics:
        this.stickerService
          .getStatistics(this.email),

      duplicates:
        this.stickerService
          .getDuplicates(
            this.email,
            0,
            50
          ),

      finished:
        this.stickerService
          .getFinished(this.email)

    }).subscribe({

      next: ({
               statistics,
               duplicates,
               finished
             }) => {

        this.statistics =
          statistics;

        this.duplicates =
          duplicates.content;

        this.finishedTeams =
          finished;

        this.loading = false;

        this.cdr.markForCheck();
      },

      error: err => {

        console.error(err);

        this.loading = false;

        this.cdr.markForCheck();
      }
    });
  }

  trackDuplicate(
    index: number,
    item: any
  ): number {

    return item.id;
  }
}
