import { Routes } from '@angular/router';
import { App } from './app';

export const routes: Routes = [
  {
    path: '',
    component: App,
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./features/home/welcome-page/welcome-page').then(m => m.WelcomePage),
      },
      {
        path: 'total-stats',
        loadComponent: () =>
          import('./features/stickers/stickers-total/stickers-total').then(m => m.StickersTotal),
      },
      {
        path: 'stickers-details',
        loadComponent: () =>
          import('./features/stickers/stickers-details/stickers-details').then(m => m.StickersDetails),
      },{
        path: 'stickers-stats',
        loadComponent: () =>
          import('./features/stickers/stickers-stats/stickers-stats').then(m => m.StickersStats),
      },{
        path: 'nation-stickers/:nationality',
        loadComponent: () =>
          import('./features/stickers/nation-stickers/nation-stickers').then(m => m.NationStickers),
      },{
        path: 'account-details',
        loadComponent: () =>
          import('./features/profile/account-details/account-details').then(m => m.AccountDetails),
      },{
        path: 'user-details/:email',
        loadComponent: () =>
          import('./features/profile/user-details/user-details').then(m => m.UserDetails),
      },{
        path: 'duplicates',
        loadComponent: () =>
          import('./features/trade/duplicates/duplicates-management/duplicates-management').then(m => m.DuplicatesManagement),
      },{
        path: 'duplicates-compare/:email',
        loadComponent: () =>
          import('./features/trade/duplicates/duplicates-compare/duplicates-compare').then(m => m.DuplicatesCompare),
      },{
        path: 'swap-recommend/:email',
        loadComponent: () =>
          import('./features/trade/swap/swap-recommend/swap-recommend').then(m => m.SwapRecommend),
      },{
        path: 'swap-recommendation-public',
        loadComponent: () =>
          import('./features/trade/swap/swap-public-recommend/swap-public-recommend').then(m => m.SwapPublicRecommend),
      },{
        path: 'play-zone',
        loadComponent: () =>
          import('./features/play-zone/play-zone').then(m => m.PlayZone),
      },
      {
        path: 'my-teams',
        loadComponent: () =>
          import('./features/play-zone/my-team/my-team').then(m => m.MyTeam),
      },{
        path: 'generate-team',
        loadComponent: () =>
          import('./features/play-zone/generate-team/generate-team').then(m => m.GenerateTeam),
      },
      {
        path: 'manual-team',
        loadComponent: () =>
          import('./features/play-zone/manual-team/manual-team').then(m => m.ManualTeam),
      },
      {
        path: 'team-details/:id',
        loadComponent: () =>
          import('./features/play-zone/team-details/team-details').then(m => m.TeamDetails),
      },{
        path: 'create-tournament',
        loadComponent: () =>
          import('./features/play-zone/create-tournament/create-tournament').then(m => m.CreateTournament),
      },{
        path: 'tournament/:id',
        loadComponent: () =>
          import('./features/play-zone/tournament-dashboard/tournament-dashboard').then(m => m.TournamentDashboard),
      },{
        path: 'tournament/:id/results',
        loadComponent: () =>
          import('./features/play-zone/tournament-results/tournament-results').then(m => m.TournamentResults),
      },{
        path: 'tournament/:id/champion',
        loadComponent: () =>
          import('./features/play-zone/champion/champion').then(m => m.Champion),
      },{
        path: 'profile',
        loadComponent: () =>
          import('./features/profile/profile/profile').then(m => m.Profile),
      },{
        path: 'hall-of-fame',
        loadComponent: () =>
          import('./features/play-zone/hall-of-fame/hall-of-fame').then(m => m.HallOfFame),
      },{
        path:
          'tournament/:id/status',
        loadComponent: () =>
          import('./features/play-zone/tournament-status/tournament-status').then(m => m.TournamentStatus),
      },{
        path: 'match/:id',
        loadComponent: () =>
          import('./features/play-zone/match-details/match-details').then(m => m.MatchDetails),
      },{
        path: 'tournament/:id/standings',
        loadComponent: () =>
          import('./features/play-zone/tournament-standings/tournament-standings').then(m => m.TournamentStandings),
      },{
        path:
          'tournament/:id/top-scorers',
        loadComponent: () =>
          import('./features/play-zone/tournament-top-scorers/tournament-top-scorers').then(m => m.TournamentTopScorers),
      },{
        path: 'tournament/:id/matches',
        loadComponent: () =>
          import('./features/play-zone/tournament-matches/tournament-matches').then(m => m.TournamentMatches),
      },{
        path: 'tournament/:id/statistics',
        loadComponent: () =>
          import('./features/play-zone/tournament-statistics/tournament-statistics').then(m => m.TournamentStatistics),
      },
      {
        path: '**',
        redirectTo: '',
      },
    ],
  },
];
