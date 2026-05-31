import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-duplicates-management',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './duplicates-management.html',
  styleUrl: './duplicates-management.scss',
})
export class DuplicatesManagement {

  duplicates = [
    {
      id: 1,
      code: 'FR',
      number: 4,
      createdAt: '2026-05-28T21:15:00'
    },
    {
      id: 2,
      code: 'BR',
      number: 2,
      createdAt: '2026-05-27T18:30:00'
    },
    {
      id: 3,
      code: 'AR',
      number: 7,
      createdAt: '2026-05-26T12:10:00'
    },
    {
      id: 4,
      code: 'PT',
      number: 3,
      createdAt: '2026-05-25T09:45:00'
    }
  ];

  reduce(
    duplicate: any
  ): void {

    if (duplicate.number > 1) {

      duplicate.number--;
    }

    if (duplicate.number <= 1) {

      this.remove(duplicate);
    }
  }

  remove(
    duplicate: any
  ): void {

    this.duplicates =
      this.duplicates.filter(
        d => d.id !== duplicate.id
      );
  }
}
