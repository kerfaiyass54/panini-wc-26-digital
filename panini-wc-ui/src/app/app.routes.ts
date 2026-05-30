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
      },
      {
        path: '**',
        redirectTo: '',
      },
    ],
  },
];
