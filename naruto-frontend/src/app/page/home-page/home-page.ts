import { Component, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth-service';

interface Position {
  left: number;
  top: number;
}

// Dans la rue, un peu devant la porte du village (pas dessus)
const positionInitiale: Position = { left: 50, top: 76.5 };

const CIBLES: Record<string, Position> = {
  '/equipe': { left: 18, top: 60 },
  '/profil': { left: 50, top: 47.5 },
  '/mission': { left: 81, top: 58 },
};

const dureePoofDepart = 700;
const dureePoofArrivee = 900;

@Component({
  selector: 'app-home-page',
  imports: [],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {
  protected authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  protected enDeplacement = signal(false);
  protected spriteVisible = signal(true);
  protected spritePos = signal<Position>({ ...positionInitiale });
  protected poofDepartVisible = signal(false);
  protected poofArriveeVisible = signal(false);
  protected poofPos = signal<Position>({ ...positionInitiale });

  protected get sprite(): string {
    switch (this.authService.role) {
      case 'LEADER':
        return '/assets/images/sasuke-up.png';
      case 'ADMIN':
        return '/assets/images/tsunadeUp.png';
      default:
        return '/assets/images/naruto-up.png';
    }
  }

  public teleporter(destination: string) {
    if (this.enDeplacement()) {
      return;
    }

    this.enDeplacement.set(true);

    // Depart : puff a l'endroit ou se trouve le personnage, qui disparait aussitot
    this.poofPos.set(this.spritePos());
    this.poofDepartVisible.set(true);
    this.spriteVisible.set(false);

    setTimeout(() => {
      this.poofDepartVisible.set(false);

      // Arrivee : reapparait directement devant la cible, avec un puff
      const cible = CIBLES[destination] ?? positionInitiale;
      this.spritePos.set(cible);
      this.poofPos.set(cible);
      this.spriteVisible.set(true);
      this.poofArriveeVisible.set(true);

      setTimeout(() => {
        this.router.navigate([destination]);
      }, dureePoofArrivee);
    }, dureePoofDepart);
  }

  public deconnexion() {
    this.authService.resetAuth();
    this.router.navigate(['/connexion']);
  }
}
