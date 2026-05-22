import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  QueryList,
  ViewChildren,
  inject,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import { AnalyticsService }
  from '../services/analytics.service';

import {
  Chart,
  registerables
} from 'chart.js';

Chart.register(...registerables);

interface GroupResponse {

  name: string;

  total: number;
}

@Component({
  selector: 'app-stickers-stats',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl:
    './stickers-stats.html',
  styleUrl:
    './stickers-stats.scss',
})
export class StickersStats
  implements OnInit,
    AfterViewInit {

  private readonly analytics =
    inject(AnalyticsService);

  @ViewChildren('reveal')
  revealElements!:
    QueryList<ElementRef>;

  // DATA

  nations:
    Record<string, number> = {};

  continents:
    Record<string, number> = {};

  groups:
    GroupResponse[] = [];

  // TOP

  mostNation:
    [string, number] | null = null;

  mostContinent:
    [string, number] | null = null;

  mostGroup:
    GroupResponse | null = null;

  // MODAL

  selectedGroup:
    string | null = null;

  selectedGroupTotal =
    0;

  selectedGroupCountries:
    string[] = [];

  // INIT

  ngOnInit(): void {

    this.loadNations();

    this.loadContinents();

    this.loadGroups();
  }

  ngAfterViewInit(): void {

    this.observeSections();
  }

  // =========================
  // LOAD NATIONS
  // =========================

  loadNations(): void {

    this.analytics
      .getAllNations()
      .subscribe({

        next: (response) => {

          this.nations =
            response;

          this.mostNation =
            this.getBiggestNation(
              response
            );

          this.createNationsChart();
        },

        error: (err) => {

          console.error(
            'Nations error',
            err
          );
        },
      });
  }

  // =========================
  // LOAD CONTINENTS
  // =========================

  loadContinents(): void {

    this.analytics
      .getAllContinents()
      .subscribe({

        next: (response) => {

          this.continents =
            response;

          this.mostContinent =
            this.getBiggestNation(
              response
            );

          this.createContinentsChart();
        },

        error: (err) => {

          console.error(
            'Continents error',
            err
          );
        },
      });
  }

  // =========================
  // LOAD GROUPS
  // =========================

  loadGroups(): void {

    this.analytics
      .getAllGroups()
      .subscribe({

        next: (response: any) => {

          this.groups =
            response;

          this.mostGroup =
            response[0];

          this.createGroupsChart();
        },

        error: (err) => {

          console.error(
            'Groups error',
            err
          );
        },
      });
  }

  // =========================
  // BIGGEST
  // =========================

  getBiggestNation(
    data:
    Record<string, number>
  ): [string, number] {

    return Object.entries(data)
      .reduce((a, b) =>
        a[1] > b[1]
          ? a
          : b
      ) as [string, number];
  }

  // =========================
  // NATIONS CHART
  // =========================

  createNationsChart(): void {

    const existing =
      Chart.getChart(
        'nationsChart'
      );

    existing?.destroy();

    new Chart(
      'nationsChart',
      {

        type: 'bar',

        data: {

          labels:
            Object.keys(
              this.nations
            ),

          datasets: [

            {

              data:
                Object.values(
                  this.nations
                ),

              borderRadius: 12,

              backgroundColor: [
                '#00dcff',
                '#5b7cff',
                '#00f2c3',
                '#ff7b7b',
                '#ffb347',
                '#7c4dff',
              ],
            },
          ],
        },

        options: {

          responsive: true,

          maintainAspectRatio:
            false,

          plugins: {

            legend: {

              display: false,
            },
          },

          scales: {

            x: {

              ticks: {

                color:
                  'white',

                maxRotation: 90,

                minRotation: 90,
              },

              grid: {

                color:
                  'rgba(255,255,255,.05)',
              },
            },

            y: {

              beginAtZero:
                true,

              ticks: {

                color:
                  'white',
              },

              grid: {

                color:
                  'rgba(255,255,255,.05)',
              },
            },
          },
        },
      }
    );
  }

  // =========================
  // GROUPS CHART
  // =========================

  createGroupsChart(): void {

    const existing =
      Chart.getChart(
        'groupsChart'
      );

    existing?.destroy();

    new Chart(
      'groupsChart',
      {

        type: 'doughnut',

        data: {

          labels:
            this.groups.map(
              g => `Group ${g.name}`
            ),

          datasets: [

            {

              data:
                this.groups.map(
                  g => g.total
                ),

              borderWidth: 0,

              hoverOffset: 14,

              backgroundColor: [
                '#00dcff',
                '#5b7cff',
                '#7c4dff',
                '#00f2c3',
                '#ff7b7b',
                '#ffb347',
                '#ff5fd2',
                '#00ffa3',
                '#9f7fff',
                '#ffe66d',
                '#6ef3ff',
                '#7effa1',
              ],
            },
          ],
        },

        options: {

          responsive: true,

          maintainAspectRatio:
            false,

          plugins: {

            legend: {

              position:
                'bottom',

              labels: {

                color:
                  'white',

                padding: 20,
              },
            },
          },
        },
      }
    );
  }

  // =========================
  // CONTINENTS CHART
  // =========================

  createContinentsChart(): void {

    const existing =
      Chart.getChart(
        'continentsChart'
      );

    existing?.destroy();

    new Chart(
      'continentsChart',
      {

        type: 'polarArea',

        data: {

          labels:
            Object.keys(
              this.continents
            ),

          datasets: [

            {

              data:
                Object.values(
                  this.continents
                ),

              backgroundColor: [
                '#00dcff',
                '#5b7cff',
                '#00f2c3',
                '#ff7b7b',
                '#ffb347',
                '#7c4dff',
              ],
            },
          ],
        },

        options: {

          responsive: true,

          maintainAspectRatio:
            false,

          plugins: {

            legend: {

              position:
                'bottom',

              labels: {

                color:
                  'white',
              },
            },
          },
        },
      }
    );
  }

  // =========================
  // GROUP DETAILS
  // =========================

  openGroup(
    group: GroupResponse
  ): void {

    this.selectedGroup =
      group.name;

    this.selectedGroupTotal =
      group.total;

    this.selectedGroupCountries =

      Object.keys(
        this.nations
      ).slice(0, group.total);
  }

  closeModal(): void {

    this.selectedGroup =
      null;

    this.selectedGroupCountries =
      [];
  }

  // =========================
  // REVEAL
  // =========================

  observeSections(): void {

    const observer =
      new IntersectionObserver(

        entries => {

          entries.forEach(
            entry => {

              if (
                entry.isIntersecting
              ) {

                entry.target
                  .classList
                  .add(
                    'show-section'
                  );
              }
            }
          );
        },

        {
          threshold: 0.15,
        }
      );

    this.revealElements
      .forEach(el => {

        observer.observe(
          el.nativeElement
        );
      });
  }
}
