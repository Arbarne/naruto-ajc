import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth-service';

@Component({
  selector: 'app-home-page',
  imports: [RouterLink],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {
  protected authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  public deconnexion() {
    this.authService.resetAuth();
    this.router.navigate(['/connexion']);
  }
}
