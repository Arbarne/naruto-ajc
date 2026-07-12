import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from "@angular/router";
import { AuthService } from '../../service/auth-service';

@Component({
  selector: 'app-navigation',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navigation.html',
  styleUrl: './navigation.css',
})
export class Navigation {
  private router: Router = inject(Router);
  protected authService: AuthService = inject(AuthService);

  public deconnexion() {
    this.authService.resetAuth();
    this.router.navigate(['/connexion']);
  }
}
