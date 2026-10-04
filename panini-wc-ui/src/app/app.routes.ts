import { Routes } from '@angular/router';
import { App } from './app';
import { MyTeam } from './features/play-zone/my-team/my-team';
import { TeamDetails } from './features/play-zone/team-details/team-details';
import { ManualTeam } from './features/play-zone/manual-team/manual-team';
import { AutoTeam } from './features/play-zone/auto-team/auto-team';
import { GenerateTeam } from './features/play-zone/generate-team/generate-team';
import { CreateTournament } from './features/play-zone/create-tournament/create-tournament';
import { TournamentDashboard } from './features/play-zone/tournament-dashboard/tournament-dashboard';
import { TournamentStandings } from './features/play-zone/tournament-standings/tournament-standings';
import { TournamentMatches } from './features/play-zone/tournament-matches/tournament-matches';
import { TournamentStatistics } from './features/play-zone/tournament-statistics/tournament-statistics';
import { TournamentTopScorers } from './features/play-zone/tournament-top-scorers/tournament-top-scorers';
import { TournamentStatus } from './features/play-zone/tournament-status/tournament-status';
import { MatchDetails } from './features/play-zone/match-details/match-details';
import { TournamentResults } from './features/play-zone/tournament-results/tournament-results';
import { Champion } from './features/play-zone/champion/champion';
import { Profile } from './features/profile/profile/profile';
import { HallOfFame } from './features/hall-of-fame/hall-of-fame';

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
          import('./features/stickers-total/stickers-total').then(m => m.StickersTotal),
      },
      {
        path: 'stickers-details',
        loadComponent: () =>
          import('./stickers-details/stickers-details').then(m => m.StickersDetails),
      },{
        path: 'stickers-stats',
        loadComponent: () =>
          import('./stickers-stats/stickers-stats').then(m => m.StickersStats),
      },{
        path: 'nation-stickers/:nationality',
        loadComponent: () =>
          import('./features/nation-stickers/nation-stickers').then(m => m.NationStickers),
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
          import('./features/duplicates/duplicates-management/duplicates-management').then(m => m.DuplicatesManagement),
      },{
        path: 'duplicates-compare/:email',
        loadComponent: () =>
          import('./features/duplicates/duplicates-compare/duplicates-compare').then(m => m.DuplicatesCompare),
      },{
        path: 'swap-recommend/:email',
        loadComponent: () =>
          import('./features/swap/swap-recommend/swap-recommend').then(m => m.SwapRecommend),
      },{
        path: 'swap-recommendation-public',
        loadComponent: () =>
          import('./features/swap/swap-public-recommend/swap-public-recommend').then(m => m.SwapPublicRecommend),
      },{
        path: 'play-zone',
        loadComponent: () =>
          import('./features/play-zone/play-zone').then(m => m.PlayZone),
      },
      {
        path: 'my-teams',
        component: MyTeam,
      },{
        path: 'generate-team',
        component: GenerateTeam
      },
      {
        path: 'manual-team',
        component: ManualTeam
      },
      {
        path: 'team-details/:id',
        component: TeamDetails,
      },{
        path: 'create-tournament',
        component: CreateTournament
      },{
        path: 'tournament/:id',
        component: TournamentDashboard
      },{
        path: 'tournament/:id/results',
        component: TournamentResults
      },{
        path: 'tournament/:id/champion',
        component: Champion
      },{
        path: 'profile',
        component: Profile
      },{
        path: 'hall-of-fame',
        component: HallOfFame
      },{
        path:
          'tournament/:id/status',
        component:
        TournamentStatus
      },{
        path: 'match/:id',
        component: MatchDetails
      },{
        path: 'tournament/:id/standings',
        component: TournamentStandings
      },{
        path:
          'tournament/:id/top-scorers',
        component:
        TournamentTopScorers
      },{
        path: 'tournament/:id/matches',
        component: TournamentMatches
      },{
        path: 'tournament/:id/statistics',
        component: TournamentStatistics
      },
      {
        path: '**',
        redirectTo: '',
      },
    ],
  },
];
