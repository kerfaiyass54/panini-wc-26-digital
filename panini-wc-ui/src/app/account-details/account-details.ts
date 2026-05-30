import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';

import Keycloak from 'keycloak-js';

import { UserRelations } from '../services/user-relations';
import { RouterLink } from '@angular/router';

interface Invitation {
  id: number;
  sender: string;
  receiver: string;
  status: string;
}

@Component({
  selector: 'app-account-details',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './account-details.html',
  styleUrl: './account-details.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AccountDetails implements OnInit {

  private readonly relationService =
    inject(UserRelations);

  private readonly keycloak =
    inject(Keycloak);

  private readonly cdr =
    inject(ChangeDetectorRef);

  relations: string[] = [];

  receivedInvitations: Invitation[] = [];

  sentInvitations: Invitation[] = [];

  availableUsers: string[] = [];

  sendingTo = '';

  loading = true;

  get email(): string {

    return (
      this.keycloak
        .tokenParsed?.[
        'email'
        ] as string
    ) ?? '';
  }

  ngOnInit(): void {

    this.refresh();
  }

  refresh(): void {

    this.loadRelations();

    this.loadReceivedInvitations();

    this.loadSentInvitations();

    this.loadAvailableUsers();
  }

  loadRelations(): void {

    this.relationService
      .getRelations(this.email)
      .subscribe({

        next: data => {

          this.relations = data;

          this.cdr.markForCheck();
        }
      });
  }

  loadReceivedInvitations(): void {

    this.relationService
      .getPendingInvitations(this.email)
      .subscribe({

        next: data => {

          this.receivedInvitations = data;

          this.cdr.markForCheck();
        }
      });
  }

  loadSentInvitations(): void {

    this.relationService
      .getSentPendingInvitations(this.email)
      .subscribe({

        next: data => {

          this.sentInvitations = data;

          this.cdr.markForCheck();
        }
      });
  }

  loadAvailableUsers(): void {

    this.relationService
      .getNotConnectedUsers(this.email)
      .subscribe({

        next: data => {

          this.availableUsers = data;

          this.loading = false;

          this.cdr.markForCheck();
        }
      });
  }

  sendInvitation(
    receiver: string
  ): void {

    this.sendingTo = receiver;

    this.cdr.markForCheck();

    this.relationService
      .sendInvite(
        this.email,
        receiver
      )
      .subscribe({

        next: () => {

          this.sendingTo = '';

          this.refresh();

          this.cdr.markForCheck();
        },

        error: () => {

          this.sendingTo = '';

          this.cdr.markForCheck();
        }
      });
  }

  acceptInvitation(
    invitation: Invitation
  ): void {

    this.relationService
      .changeStatus(
        invitation.id,
        'ACCEPTED'
      )
      .subscribe(() => {

        this.refresh();

        this.cdr.markForCheck();
      });
  }

  refuseInvitation(
    invitation: Invitation
  ): void {

    this.relationService
      .changeStatus(
        invitation.id,
        'REFUSED'
      )
      .subscribe(() => {

        this.refresh();

        this.cdr.markForCheck();
      });
  }
}
