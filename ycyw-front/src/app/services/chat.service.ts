import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Conversation } from '../interfaces/Conversation';
import { Message } from '../interfaces/Message';
import { NewMessageRequest } from '../interfaces/NewMessageRequest';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  constructor(private http: HttpClient) {}

  getConversations(): Observable<Conversation[]> {
    return this.http.get<Conversation[]>(`${environment.apiUrl}/conversations`);
  }

  getMessages(conversationId: number): Observable<Message[]> {
    return this.http.get<Message[]>(
      `${environment.apiUrl}/messages?conversationId=${conversationId}`
    );
  }

  createMessage(newMessageRequest: NewMessageRequest): Observable<Message> {
    return this.http.post<Message>(
      `${environment.apiUrl}/messages`,
      newMessageRequest
    );
  }

  createConversation(): Observable<Conversation> {
    return this.http.post<Conversation>(
      `${environment.apiUrl}/conversations`,
      {}
    );
  }
}
