import { Routes } from '@angular/router';
import { App } from './app';
import { MyTeam } from './play-zone/my-team/my-team';
import { TeamDetails } from './play-zone/team-details/team-details';
import { ManualTeam } from './play-zone/manual-team/manual-team';
import { AutoTeam } from './play-zone/auto-team/auto-team';
import { GenerateTeam } from './play-zone/generate-team/generate-team';
import { CreateTournament } from './play-zone/create-tournament/create-tournament';
import { TournamentDashboard } from './play-zone/tournament-dashboard/tournament-dashboard';
import { TournamentStandings } from './play-zone/tournament-standings/tournament-standings';
import { TournamentMatches } from './play-zone/tournament-matches/tournament-matches';
import { TournamentStatistics } from './play-zone/tournament-statistics/tournament-statistics';
import { TournamentTopScorers } from './play-zone/tournament-top-scorers/tournament-top-scorers';
import { TournamentStatus } from './play-zone/tournament-status/tournament-status';
import { MatchDetails } from './play-zone/match-details/match-details';
import { TournamentResults } from './play-zone/tournament-results/tournament-results';
import { Champion } from './play-zone/champion/champion';
import { Profile } from './profile/profile';
import { HallOfFame } from './hall-of-fame/hall-of-fame';

export const routes: Routes = [
  {
    path: '',
    component: App,
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./welcome-page/welcome-page').then(m => m.WelcomePage),
      },
      {
        path: 'total-stats',
        loadComponent: () =>
          import('./stickers-total/stickers-total').then(m => m.StickersTotal),
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
          import('./nation-stickers/nation-stickers').then(m => m.NationStickers),
      },{
        path: 'account-details',
        loadComponent: () =>
          import('./account-details/account-details').then(m => m.AccountDetails),
      },{
        path: 'user-details/:email',
        loadComponent: () =>
          import('./user-details/user-details').then(m => m.UserDetails),
      },{
        path: 'duplicates',
        loadComponent: () =>
          import('./duplicates-management/duplicates-management').then(m => m.DuplicatesManagement),
      },{
        path: 'duplicates-compare/:email',
        loadComponent: () =>
          import('./duplicates-compare/duplicates-compare').then(m => m.DuplicatesCompare),
      },{
        path: 'swap-recommend/:email',
        loadComponent: () =>
          import('./swap-recommend/swap-recommend').then(m => m.SwapRecommend),
      },{
        path: 'swap-recommendation-public',
        loadComponent: () =>
          import('./swap-public-recommend/swap-public-recommend').then(m => m.SwapPublicRecommend),
      },{
        path: 'play-zone',
        loadComponent: () =>
          import('./play-zone/play-zone').then(m => m.PlayZone),
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
