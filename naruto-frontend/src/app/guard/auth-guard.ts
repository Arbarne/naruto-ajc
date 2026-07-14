import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth-service';

export const authGuard: CanActivateFn = (route, state) => {
  const router: Router = inject(Router);
  const authService: AuthService = inject(AuthService);

  if (authService.isLogged()) {
    return true;
  }

  // On va demander à rediriger vers la page login
  return router.createUrlTree([ 'connexion' ]);
};
