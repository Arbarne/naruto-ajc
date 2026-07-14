import { Routes } from '@angular/router';
import { AccueilPage } from './page/accueil-page/accueil-page';
import { InscriptionPage } from './page/inscription-page/inscription-page';
import { ConnexionPage } from './page/connexion-page/connexion-page';
import { MissionPage } from './page/mission-page/mission-page';
import { EquipePage } from './page/equipe-page/equipe-page';
import { HomePage } from './page/home-page/home-page';
import { ProfilPage } from './page/profil-page/profil-page';
import { authGuard } from './guard/auth-guard';
import { MissionSasuke } from './page/mission-sasuke/mission-sasuke';

const appName = 'Projet Naruto'
const formatTitle = (pageName: string) => {
  return pageName + " - " + appName
}

export const routes: Routes = [
  {
    path: 'home',
    component: HomePage,
    title: formatTitle('Village'),
    canActivate: [ authGuard ]
  },

  {
    path: 'inscription',
    component: InscriptionPage,
    title: formatTitle('Inscription')
  },

  {
    path: 'connexion',
    component: ConnexionPage,
    title: formatTitle('Connexion'),
  },

  {
    path: 'mission',
    component: MissionPage,
    title: formatTitle('Archives des missions'),
    canActivate: [ authGuard ]
  },

  {
    path: 'equipe',
    component: EquipePage,
    title: formatTitle('Caserne des ninjas'),
    canActivate: [ authGuard ]
  },

  {
    path: 'profil',
    component: ProfilPage,
    title: formatTitle('Bureau du Hokage'),
    canActivate: [ authGuard ]
  },

  {
    path: 'mission-sasuke',
    component: MissionSasuke,
    title: formatTitle('Mission : Capturer Sasuke'),
    canActivate: [ authGuard ]
  },

  // Todo: redirect l'accueil vers la carte quand connecté
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];
