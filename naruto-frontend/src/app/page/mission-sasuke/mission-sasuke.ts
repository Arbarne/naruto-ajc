import { CommonModule } from '@angular/common';
import {
  AfterViewInit,
  Component,
  ElementRef,
  HostListener,
  inject,
  OnDestroy,
  signal,
  ViewChild
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MissionService } from '../../service/mission-service';
import { Router } from '@angular/router';

type Direction = 'Up' | 'Down' | 'Left' | 'Right';

@Component({
  selector: 'app-naruto',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mission-sasuke.html',
  styleUrls: ['./mission-sasuke.css']
})
export class MissionSasuke implements AfterViewInit, OnDestroy {

  @ViewChild('grass') grassRef!: ElementRef<HTMLDivElement>;
  @ViewChild('imgNaruto') imgNarutoRef!: ElementRef<HTMLImageElement>;
  @ViewChild('imgSasuke') imgSasukeRef!: ElementRef<HTMLImageElement>;
  @ViewChild('imgPoof1') imgPoof1Ref!: ElementRef<HTMLImageElement>;
  @ViewChild('imgPoof2') imgPoof2Ref!: ElementRef<HTMLImageElement>;
  @ViewChild('ost') ostRef!: ElementRef<HTMLAudioElement>;
  @ViewChild('poofSFX') poofSFXRef!: ElementRef<HTMLAudioElement>;
  @ViewChild('sasukeeee') sasukeeeeRef!: ElementRef<HTMLAudioElement>;

  private missionService: MissionService = inject(MissionService);
  private router: Router = inject(Router);

  // --- État du formulaire ---
  inputName = '';
  gameStarted = false;

  // --- État naruto ---
  posX = 0;
  posY = 0;
  mouvement = 25;
  perso = 'naruto';
  direction: Direction = 'Down';
  prenom = '';

  // --- État sasuke ---
  sasukeX = signal(300);
  sasukeY = signal(300);
  sasukeMouvement = 25;
  sasukeDirection = signal<Direction>('Down');
  private sasukeInterval: ReturnType<typeof setInterval> | null = null;

  // --- État partie ---
  gameOver = false;
  victoryText = signal('');
  showVictory = signal(false);

  // --- État poofs (positions seulement, l'animation est gérée en direct sur le DOM) ---
  poof1Top = signal(0);
  poof1Left = signal(0);
  poof2Top = signal(0);
  poof2Left = signal(0);

  private readonly CHANCE_ESQUIVE = 0.6;
  private readonly MIN_ESQUIVES = 2;
  private esquivesRestantes = this.MIN_ESQUIVES;

  private readonly OSTS = [
    'assets/audio/strong-and-strike.mp3',
    'assets/audio/turn.mp3',
    'assets/audio/beautiful-green-wild-beast.mp3',
    'assets/audio/keisei-gyakuten.mp3',
    'assets/audio/the-raising-fighting-spirit.mp3'
  ];
  private currentMusic = Math.floor(Math.random() * this.OSTS.length);

  ngAfterViewInit(): void {
    this.poofSFXRef.nativeElement.volume = 0.2;
    this.sasukeeeeRef.nativeElement.volume = 0.15;
  }

  ngOnDestroy(): void {
    if (this.sasukeInterval) {
      clearInterval(this.sasukeInterval);
    }
  }

  get narutoImgSrc(): string {
    return `assets/images/pikachu/${this.perso}${this.direction}.png`;
  }

  get sasukeImgSrc(): string {
    return `assets/images/pikachu/sasuke${this.sasukeDirection()}.png`;
  }

  checkName(): void {
    // géré directement via [disabled] dans le template
  }

  onNameKeyup(event: KeyboardEvent): void {
    if (event.key === 'Enter' && this.inputName.length > 0) {
      this.start();
    }
  }

  @HostListener('window:keydown', ['$event'])
  onKeydown(event: KeyboardEvent): void {
    if (!this.gameStarted || this.gameOver) return;

    const grass = this.grassRef.nativeElement;
    const imgNaruto = this.imgNarutoRef.nativeElement;

    const grassWidth = grass.offsetWidth;
    const narutoWidth = imgNaruto.offsetWidth;
    const grassHeight = grass.offsetHeight;
    const narutoHeight = imgNaruto.offsetHeight;

    if (event.key === 'ArrowDown' || event.key === 's') {
      if ((this.posY + this.mouvement + narutoHeight) < grassHeight) {
        this.posY += this.mouvement;
        this.direction = 'Down';
      }
    } else if (event.key === 'ArrowUp' || event.key === 'z') {
      if ((this.posY - this.mouvement) >= 0) {
        this.posY -= this.mouvement;
        this.direction = 'Up';
      }
    } else if (event.key === 'ArrowRight' || event.key === 'd') {
      if ((this.posX + this.mouvement + narutoWidth) < grassWidth) {
        this.posX += this.mouvement;
        this.direction = 'Right';
      }
    } else if (event.key === 'ArrowLeft' || event.key === 'q') {
      if ((this.posX - this.mouvement) >= 0) {
        this.posX -= this.mouvement;
        this.direction = 'Left';
      }
    }

    this.verifierCollision();
  }

  private deplacer(): void {
    const grass = this.grassRef.nativeElement;
    const imgSasuke = this.imgSasukeRef.nativeElement;

    const grassWidth = grass.offsetWidth;
    const grassHeight = grass.offsetHeight;
    const sasukeWidth = imgSasuke.offsetWidth;
    const sasukeHeight = imgSasuke.offsetHeight;
    const directions: string[] = [];

    if ((this.sasukeY() - this.sasukeMouvement) >= 0) directions.push('haut');
    if ((this.sasukeY() + this.sasukeMouvement + sasukeHeight) < grassHeight) directions.push('bas');
    if ((this.sasukeX() - this.sasukeMouvement) >= 0) directions.push('gauche');
    if ((this.sasukeX() + this.sasukeMouvement + sasukeWidth) < grassWidth) directions.push('droite');

    if (directions.length === 0) return;

    const choix = directions[Math.floor(Math.random() * directions.length)];

    if (choix === 'haut')    { this.sasukeY.update(y => y - this.sasukeMouvement); this.sasukeDirection.set('Up'); }
    if (choix === 'bas')     { this.sasukeY.update(y => y + this.sasukeMouvement); this.sasukeDirection.set('Down'); }
    if (choix === 'gauche')  { this.sasukeX.update(x => x - this.sasukeMouvement); this.sasukeDirection.set('Left'); }
    if (choix === 'droite')  { this.sasukeX.update(x => x + this.sasukeMouvement); this.sasukeDirection.set('Right'); }

    this.verifierCollision();
  }

  private verifierCollision(): void {
    if (this.gameOver) return;

    const narutoWidth = this.imgNarutoRef.nativeElement.offsetWidth;
    const narutoHeight = this.imgNarutoRef.nativeElement.offsetHeight;
    const sasukeWidth = this.imgSasukeRef.nativeElement.offsetWidth;
    const sasukeHeight = this.imgSasukeRef.nativeElement.offsetHeight;
    const tolerance = 30;

    const toucheX = Math.abs(this.posX - this.sasukeX()) < (narutoWidth / 2 + sasukeWidth / 2 - tolerance);
    const toucheY = Math.abs(this.posY - this.sasukeY()) < (narutoHeight / 2 + sasukeHeight / 2 - tolerance);

    if (toucheX && toucheY) {
      this.tentativeEsquive();
    }
  }

  private tentativeEsquive(): void {
    if (this.esquivesRestantes > 0) {
      this.esquivesRestantes--;
      this.esquive();
    } else {
      const random = Math.random();
      if (random < this.CHANCE_ESQUIVE) {
        this.esquive();
      } else {
        this.gagner();
      }
    }
  }

  private esquive(): void {
    const poof1 = this.imgPoof1Ref.nativeElement;
    const poof2 = this.imgPoof2Ref.nativeElement;

    this.poof1Top.set(this.sasukeY() - 35);
    this.poof1Left.set(this.sasukeX() - 35);

    poof1.classList.remove('animating');
    void poof1.offsetHeight; // reflow forcé sur le vrai DOM
    poof1.classList.add('animating');

    this.sasukeTP();

    this.poof2Top.set(this.sasukeY() - 35);
    this.poof2Left.set(this.sasukeX() - 35);

    poof2.classList.remove('animating');
    void poof2.offsetHeight;
    poof2.classList.add('animating');

    const poofSFX = this.poofSFXRef.nativeElement;
    poofSFX.pause();
    poofSFX.currentTime = 0;
    poofSFX.play();
  }

  onPoofAnimationEnd(event: AnimationEvent, poof: 1 | 2): void {
    if (event.animationName === 'poofAnim') {
      const el = poof === 1 ? this.imgPoof1Ref.nativeElement : this.imgPoof2Ref.nativeElement;
      el.classList.remove('animating');
    }
  }

  private sasukeTP(): void {
    const grass = this.grassRef.nativeElement;
    const imgSasuke = this.imgSasukeRef.nativeElement;

    const randomX = Math.random() * (grass.offsetWidth - imgSasuke.offsetWidth);
    const randomY = Math.random() * (grass.offsetHeight - imgSasuke.offsetHeight);

    this.sasukeX.set(Math.round(randomX / 25) * 25);
    this.sasukeY.set(Math.round(randomY / 25) * 25);
  }

  private gagner(): void {
    this.gameOver = true;
    if (this.sasukeInterval) {
      clearInterval(this.sasukeInterval);
      this.sasukeInterval = null;
    }

    this.victoryText.set(`${this.prenom} a gagné !!!`);
    this.showVictory.set(true);

    this.ostRef.nativeElement.volume = 0.05;
  }

  private playMusic(): void {
    const ost = this.ostRef.nativeElement;
    ost.volume = 0.15;
    if (ost.currentTime === 0 || ost.ended) {
      ost.src = this.OSTS[this.currentMusic];
      ost.play();
      let newMusic = Math.floor(Math.random() * this.OSTS.length);
      while (this.currentMusic === newMusic) {
        newMusic = Math.floor(Math.random() * this.OSTS.length);
      }
      this.currentMusic = newMusic;
    }
  }

  onOstEnded(): void {
    if (!this.gameOver) {
      this.playMusic();
    }
  }

  start(): void {
    this.prenom = this.inputName;
    this.gameStarted = true;

    this.sasukeInterval = setInterval(() => this.deplacer(), 100);

    this.playMusic();

    this.esquivesRestantes = this.MIN_ESQUIVES;
  }

  restart(): void {
    this.gameOver = false;
    this.showVictory.set(false);
    this.esquive();
    this.start();
  }

  ramenerSasukeAuVillage(): void {
    this.missionService.terminer(67).subscribe({
      next: () => {
        this.router.navigate(['/mission']);
      }
    });
  }
}
