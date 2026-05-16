import {
  Component,
  HostListener,
} from '@angular/core';

@Component({
  selector: 'app-welcome-page',
  standalone: true,
  imports: [],
  templateUrl: './welcome-page.html',
  styleUrl: './welcome-page.scss',
})
export class WelcomePage {

  scrollY = 0;

  @HostListener('window:scroll')
  onScroll(): void {

    this.scrollY = window.scrollY;
  }

  get heroScale(): number {

    return 1 - Math.min(this.scrollY / 2500, .15);
  }

  get heroOpacity(): number {

    return 1 - Math.min(this.scrollY / 700, 1);
  }

  get cardsVisible(): boolean {

    return this.scrollY > 250;
  }
}
