import {
  Component,
  computed,
  signal,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

interface Sticker {

  id: number;

  name: string;

  type: string;

  nationality: string;

  place: string;
}

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
export class StickersDetails {

  search = signal('');

  filteredStickers = computed(() => {

    const query = this.search()
      .toLowerCase()
      .trim();

    if (!query) {

      return this.stickers();
    }

    return this.stickers().filter(sticker =>

      sticker.name
        .toLowerCase()
        .includes(query)

      ||

      sticker.type
        .toLowerCase()
        .includes(query)

      ||

      sticker.nationality
        .toLowerCase()
        .includes(query)

      ||

      sticker.place
        .toLowerCase()
        .includes(query)
    );
  });

  // SIGNALS

  stickers = signal<Sticker[]>([
    {
      id: 1,
      name: 'Lionel Messi',
      type: 'Legendary',
      nationality: 'Argentina',
      place: 'Forward',
    },
    {
      id: 2,
      name: 'Cristiano Ronaldo',
      type: 'Rare',
      nationality: 'Portugal',
      place: 'Forward',
    },
    {
      id: 3,
      name: 'Kylian Mbappé',
      type: 'Epic',
      nationality: 'France',
      place: 'Forward',
    },
    {
      id: 4,
      name: 'Neymar Jr',
      type: 'Ultra Rare',
      nationality: 'Brazil',
      place: 'Forward',
    },
    {
      id: 5,
      name: 'Luka Modrić',
      type: 'Golden',
      nationality: 'Croatia',
      place: 'Midfielder',
    },
  ]);

  currentPage = signal(0);

  totalPages = signal(8);

  selectedStickerId = signal<number | null>(null);

  selectedSticker = computed(() =>
    this.stickers().find(
      s => s.id === this.selectedStickerId()
    )
  );

  // UPDATE FORM

  updateForm = new FormGroup({

    id: new FormControl<number>(0),

    name: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required],
    }),

    type: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required],
    }),

    nationality: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required],
    }),

    place: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required],
    }),
  });

  // ADD FORM

  addForm = new FormGroup({

    name: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required],
    }),

    type: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required],
    }),

    nationality: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required],
    }),

    place: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required],
    }),
  });

  // OPEN MODAL

  openSticker(sticker: Sticker): void {

    this.selectedStickerId.set(sticker.id);

    this.updateForm.patchValue({

      id: sticker.id,

      name: sticker.name,

      type: sticker.type,

      nationality: sticker.nationality,

      place: sticker.place,
    });
  }

  // ADD

  addSticker(): void {

    if (this.addForm.invalid) return;

    const value = this.addForm.getRawValue();

    const sticker: Sticker = {

      id: Date.now(),

      name: value.name,

      type: value.type,

      nationality: value.nationality,

      place: value.place,
    };

    this.stickers.update(list => [
      sticker,
      ...list,
    ]);

    this.addForm.reset();
  }

  // UPDATE

  updateSticker(): void {

    if (this.updateForm.invalid) return;

    const updated = this.updateForm.getRawValue();

    this.stickers.update(list =>
      list.map(sticker =>
        sticker.id === updated.id
          ? {
            id: updated.id ?? 0,
            name: updated.name ?? '',
            type: updated.type ?? '',
            nationality: updated.nationality ?? '',
            place: updated.place ?? '',
          }
          : sticker
      )
    );
  }

  // DELETE

  deleteSticker(): void {

    const id = this.updateForm.value.id;

    this.stickers.update(list =>
      list.filter(s => s.id !== id)
    );
  }

  // PAGINATION

  nextPage(): void {

    if (
      this.currentPage() <
      this.totalPages() - 1
    ) {

      this.currentPage.update(v => v + 1);
    }
  }

  previousPage(): void {

    if (this.currentPage() > 0) {

      this.currentPage.update(v => v - 1);
    }
  }
}
