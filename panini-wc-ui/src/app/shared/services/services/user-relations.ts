import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserRelations {

  private readonly API = 'http://localhost:9090/api/relations';

  constructor(private http: HttpClient) {}

  sendInvite(sender: string, receiver: string): Observable<any> {

    const params = new HttpParams()
      .set('sender', sender)
      .set('receiver', receiver);

    return this.http.post(`${this.API}/invite`, null, { params });
  }

  changeStatus(
    invitationId: number,
    status: 'ACCEPTED' | 'REFUSED'
  ): Observable<any> {

    const params = new HttpParams()
      .set('status', status);

    return this.http.put(
      `${this.API}/invite/${invitationId}/status`,
      null,
      { params }
    );
  }

  getRelations(email: string): Observable<string[]> {

    return this.http.get<string[]>(
      `${this.API}/${encodeURIComponent(email)}`
    );
  }

  getNotConnectedUsers(email: string): Observable<string[]> {

    return this.http.get<string[]>(
      `${this.API}/${encodeURIComponent(email)}/available`
    );
  }

  getPendingInvitations(receiver: string): Observable<any[]> {

    return this.http.get<any[]>(
      `${this.API}/invitations/pending/${encodeURIComponent(receiver)}`
    );
  }

  getSentPendingInvitations(
    sender: string
  ): Observable<any[]> {

    return this.http.get<any[]>(
      `${this.API}/invitations/sent/pending/${encodeURIComponent(sender)}`
    );
  }
}
