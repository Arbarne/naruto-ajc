import { Routes } from '@angular/router';
import { AccueilPage } from './page/accueil-page/accueil-page';
import { InscriptionPage } from './page/inscription-page/inscription-page';
import { ConnexionPage } from './page/connexion-page/connexion-page';
import { MissionPage } from './page/mission-page/mission-page';
import { EquipePage } from './page/equipe-page/equipe-page';
import { HomePage } from './page/home-page/home-page';

export const routes: Routes = [
  {
    path: 'home',
    component: HomePage,
  },

  {
    path: 'accueil',
    component: AccueilPage,
  },

  {
    path: 'inscription',
    component: InscriptionPage,
  },

  {
    path: 'connexion',
    component: ConnexionPage,
  },

  {
    path: 'mission',
    component: MissionPage,
    // canActivate: [ authGuard ]
  },

  {
    path: 'equipe',
    component: EquipePage,
    // canActivate: [ authGuard ]
  },

  // Todo: redirect l'accueil vers la carte quand connecté (homeGuard ou qqch comme ca)
  { path: '', redirectTo: 'accueil', pathMatch: 'full' }
];
