import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebsocketService {
  private client: Client;
  private messageSubject = new BehaviorSubject<any>(null);
  private conversationSubject = new BehaviorSubject<any>(null);

  constructor() {
    this.client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.client.onConnect = () => {
      this.client.subscribe('/chat/messages', (wsMessage: Message) => {
        this.messageSubject.next(JSON.parse(wsMessage.body));
      });

      this.client.subscribe('/chat/conversations', (wsMessage: Message) => {
        this.conversationSubject.next(JSON.parse(wsMessage.body));
      });
    };

    this.client.activate();
  }

  getMessageObservable(): Observable<any> {
    return this.messageSubject.asObservable();
  }

  getConversationObservable(): Observable<any> {
    return this.conversationSubject.asObservable();
  }

  disconnect() {
    if (this.client) {
      this.client.deactivate();
    }
  }
}
