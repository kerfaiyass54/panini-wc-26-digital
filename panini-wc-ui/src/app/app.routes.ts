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
      },
      {
        path: '**',
        redirectTo: '',
      },
    ],
  },
];
