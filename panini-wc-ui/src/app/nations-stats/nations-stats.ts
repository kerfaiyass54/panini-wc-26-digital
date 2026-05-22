import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule }
  from '@angular/common';

import { StickerService }
  from '../services/sticker.service';

import { AnalyticsService }
  from '../services/analytics.service';

@Component({
  selector: 'app-nations-stats',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './nations-stats.html',
  styleUrl: './nations-stats.scss',
})
export class NationsStats
  implements OnInit {

  private readonly stickerService =
    inject(StickerService);

  private readonly analyticsService =
    inject(AnalyticsService);

  private readonly cdr =
    inject(ChangeDetectorRef);

  logos: string[] = [];

  nations: {
    name: string;
    total: number;
    progress: number;
    hasLogo: boolean;
  }[] = [];

  emptyNations: string[] = [];

  wc26Teams: string[] = [

    "algeria",
    "argentina",
    "australia",
    "austria",
    "belgium",
    "bosnia",
    "brazil",
    "canada",
    "cape verde",
    "colombia",
    "congo dr",
    "croatia",
    "curaçao",
    "czechia",
    "ecuador",
    "egypt",
    "england",
    "france",
    "germany",
    "ghana",
    "haiti",
    "iran",
    "iraq",
    "ivory coast",
    "japan",
    "jordan",
    "mexico",
    "morocco",
    "netherlands",
    "new zealand",
    "norway",
    "panama",
    "paraguay",
    "portugal",
    "qatar",
    "saudi arabia",
    "scotland",
    "senegal",
    "south africa",
    "south korea",
    "spain",
    "sweden",
    "switzerland",
    "tunisia",
    "turkey",
    "uruguay",
    "united states",
    "uzbekistan"
  ];

  loading = true;

  ngOnInit(): void {

    this.loadAll();
  }

  loadAll(): void {

    this.loading = true;

    this.stickerService
      .logos()
      .subscribe({

        next: (logosData) => {

          this.logos = logosData;

          this.analyticsService
            .getAllNations()
            .subscribe({

              next: (stats) => {

                this.nations =
                  Object.entries(stats)
                    .map(([name, total]) => ({

                      name,

                      total:
                        Number(total),

                      progress:
                        (Number(total) / 12) * 100,

                      hasLogo:
                        logosData.includes(name)
                    }))
                    .sort(
                      (a, b) =>
                        b.total - a.total
                    );
                const existing =
                  this.nations.map(
                    n => n.name.toLowerCase()
                  );

                this.emptyNations =
                  this.wc26Teams.filter(
                    team => !existing.includes(team)
                  );
                this.loading = false;

                this.cdr.detectChanges();
              },

              error: () => {

                this.loading = false;

                this.cdr.detectChanges();
              }
            });
        },

        error: () => {

          this.loading = false;

          this.cdr.detectChanges();
        }
      });
  }
}
