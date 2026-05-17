import {
  Component,
  inject,
  OnInit,
  signal,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

import {
  StickerDTO,
  StickerService
} from '../services/sticker.service';

import {
  wc26Teams,
  Country
} from '../constants/codes';

@Component({
  selector: 'app-stickers-details',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './stickers-details.html',
  styleUrl: './stickers-details.scss',
})
export class StickersDetails
  implements OnInit {

  private readonly stickerService =
    inject(StickerService);

  // COUNTRIES

  countries: Country[] =
    wc26Teams;

  // SEARCH

  search = signal('');

  // DATA

  stickers =
    signal<StickerDTO[]>([]);

  currentPage =
    signal(0);

  totalPages =
    signal(0);

  pageSize = 5;

  selectedStickerId =
    signal<number | null>(null);

  selectedSticker =
    signal<StickerDTO | null>(null);

  // ADD FORM

  addForm = new FormGroup({

    name:
      new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required
        ],
      }),

    type:
      new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required
        ],
      }),

    nationality:
      new FormControl('', {
        nonNullable: true,
      }),

    number:
      new FormControl<number | null>(
        null
      ),

    place:
      new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required
        ],
      }),
  });

  // UPDATE FORM

  updateForm = new FormGroup({

    id:
      new FormControl<number>(0),

    name:
      new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required
        ],
      }),

    type:
      new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required
        ],
      }),

    nationality:
      new FormControl('', {
        nonNullable: true,
      }),

    number:
      new FormControl<number | null>(
        null
      ),

    place:
      new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required
        ],
      }),
  });

  // INIT

  ngOnInit(): void {

    this.loadStickers();
  }

  // TYPE HELPERS

  isIntro(type: string): boolean {

    return (
      type.toLowerCase() ===
      'intro'
    );
  }

  isPlayer(type: string): boolean {

    return (
      type.toLowerCase() ===
      'player'
    );
  }

  isLogo(type: string): boolean {

    return (
      type.toLowerCase() ===
      'logo'
    );
  }

  // GENERATE PLACE

  generatePlace(
    nationality: string,
    type: string,
    number: any
  ): any {

    // INTRO

    if (
      this.isIntro(type)
    ) {

      if (
        number === null ||
        number < 0 ||
        number > 8
      ) {

        return '';
      }

      return `FWC ${number}`;
    }

    // COUNTRY

    const country =
      this.countries.find(
        c =>
          c.name === nationality
      );

    if (!country) return '';

    // LOGO

    if (
      this.isLogo(type)
    ) {

      return `${country.code} 0`;
    }

    // PLAYER

    if (
      number === null ||
      number < 1 ||
      number > 12
    ) {

      return '';
    }

    return `${country.code} ${number}`;
  }

  // UPDATE ADD PLACE

  updateAddPlace(): void {

    const nationality =
      this.addForm.value
        .nationality ?? '';

    const type =
      this.addForm.value
        .type ?? '';

    const number =
      this.addForm.value
        .number ?? null;

    // RESET NATIONALITY
    // FOR INTRO

    if (
      this.isIntro(type)
    ) {

      this.addForm.patchValue({
        nationality: ''
      });
    }

    // RESET NUMBER
    // FOR LOGO

    if (
      this.isLogo(type)
    ) {

      this.addForm.patchValue({
        number: 0
      });
    }

    const place =
      this.generatePlace(
        nationality,
        type,
        this.addForm.value.number
      );

    this.addForm.patchValue({
      place
    });
  }

  // UPDATE EDIT PLACE

  updateEditPlace(): void {

    const nationality =
      this.updateForm.value
        .nationality ?? '';

    const type =
      this.updateForm.value
        .type ?? '';

    const number =
      this.updateForm.value
        .number ?? null;

    // RESET NATIONALITY
    // FOR INTRO

    if (
      this.isIntro(type)
    ) {

      this.updateForm.patchValue({
        nationality: ''
      });
    }

    // RESET NUMBER
    // FOR LOGO

    if (
      this.isLogo(type)
    ) {

      this.updateForm.patchValue({
        number: 0
      });
    }

    const place =
      this.generatePlace(
        nationality,
        type,
        this.updateForm.value.number
      );

    this.updateForm.patchValue({
      place
    });
  }

  // LOAD

  loadStickers(): void {

    this.stickerService
      .getAll(
        this.currentPage(),
        this.pageSize,
        'id'
      )
      .subscribe({

        next: (response) => {

          this.stickers.set(
            response.content
          );

          this.totalPages.set(
            response.totalPages
          );
        },

        error: (err) => {

          console.error(
            'Error loading stickers',
            err
          );
        },
      });
  }

  // SEARCH

  onSearch(
    value: string
  ): void {

    this.search.set(value);

    if (!value.trim()) {

      this.loadStickers();

      return;
    }

    this.stickerService
      .search(
        value,
        this.currentPage(),
        this.pageSize
      )
      .subscribe({

        next: (response) => {

          this.stickers.set(
            response.content
          );

          this.totalPages.set(
            response.totalPages
          );
        },

        error: (err) => {

          console.error(
            'Search error',
            err
          );
        },
      });
  }

  // OPEN MODAL

  openSticker(
    sticker: StickerDTO
  ): void {

    this.selectedStickerId.set(
      sticker.id!
    );

    this.selectedSticker.set(
      sticker
    );

    this.updateForm.patchValue({

      id:
      sticker.id,

      name:
      sticker.name,

      type:
      sticker.type,

      nationality:
        sticker.nationality ?? '',

      place:
      sticker.place,
    });
  }

  // ADD

  addSticker(): void {

    if (
      this.addForm.invalid
    ) return;

    const value =
      this.addForm.getRawValue();

    this.stickerService
      .create({

        name:
        value.name,

        type:
        value.type,

        nationality:
          value.nationality || '',

        place:
        value.place,
      })
      .subscribe({

        next: () => {

          this.loadStickers();

          this.addForm.reset();
        },

        error: (err) => {

          console.error(
            'Error creating sticker',
            err
          );
        },
      });
  }

  // UPDATE

  updateSticker(): void {

    if (
      this.updateForm.invalid
    ) return;

    const updated =
      this.updateForm
        .getRawValue();

    this.stickerService
      .update(
        updated.id!,
        {

          name:
            updated.name!,

          type:
            updated.type!,

          nationality:
            updated.nationality || '',

          place:
            updated.place!,
        }
      )
      .subscribe({

        next: () => {

          this.loadStickers();
        },

        error: (err) => {

          console.error(
            'Error updating sticker',
            err
          );
        },
      });
  }

  // DELETE

  deleteSticker(): void {

    const id =
      this.updateForm.value.id;

    if (!id) return;

    this.stickerService
      .delete(id)
      .subscribe({

        next: () => {

          this.loadStickers();
        },

        error: (err) => {

          console.error(
            'Error deleting sticker',
            err
          );
        },
      });
  }

  // PAGINATION

  nextPage(): void {

    if (
      this.currentPage() <
      this.totalPages() - 1
    ) {

      this.currentPage.update(
        v => v + 1
      );

      this.loadStickers();
    }
  }

  previousPage(): void {

    if (
      this.currentPage() > 0
    ) {

      this.currentPage.update(
        v => v - 1
      );

      this.loadStickers();
    }
  }
}
