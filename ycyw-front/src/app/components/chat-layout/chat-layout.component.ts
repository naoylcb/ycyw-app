import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { WebsocketService } from '../../services/websocket.service';
import { Subscription } from 'rxjs';
import { Conversation } from '../../interfaces/Conversation';
import { Message } from '../../interfaces/Message';
import { ChatService } from '../../services/chat.service';
import { NewMessageRequest } from '../../interfaces/NewMessageRequest';

@Component({
  selector: 'app-chat-layout',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './chat-layout.component.html',
  styleUrl: './chat-layout.component.css',
})
export class ChatLayoutComponent implements OnInit, OnDestroy {
  conversations: Conversation[] = [];
  selectedConversation: Conversation | null = null;
  messages: Message[] = [];
  newMessage: string = '';
  conversationsLoading: boolean = true;
  conversationsError: string | null = null;
  private messagesSubscription: Subscription | null = null;
  private conversationsSubscription: Subscription | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private websocketService: WebsocketService,
    private chatService: ChatService
  ) {}

  ngOnInit() {
    if (this.authService.isAuthenticated()) {
      this.fetchConversations();
      this.setupWebSocketSubscription();
    }
  }

  ngOnDestroy() {
    if (this.messagesSubscription) {
      this.messagesSubscription.unsubscribe();
    }

    if (this.conversationsSubscription) {
      this.conversationsSubscription.unsubscribe();
    }
  }

  private scrollConversation() {
    setTimeout(() => {
      const messagesContainer = document.querySelector('.messages');
      if (messagesContainer) {
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
      }
    });
  }

  private setupWebSocketSubscription() {
    this.messagesSubscription = this.websocketService
      .getMessageObservable()
      .subscribe((message: Message) => {
        if (
          this.selectedConversation &&
          message.conversation === this.selectedConversation.id
        ) {
          this.messages.push(message);
          this.scrollConversation();
        }
      });

    this.conversationsSubscription = this.websocketService
      .getConversationObservable()
      .subscribe((conversation: Conversation) => {
        this.conversations.push(conversation);
      });
  }

  fetchConversations(): void {
    this.conversationsLoading = true;
    this.chatService.getConversations().subscribe({
      next: (conversations) => {
        this.conversations = conversations;
        this.conversationsLoading = false;
      },
      error: () => {
        this.conversationsError = 'Erreur lors du chargement des conversations';
        this.conversationsLoading = false;
      },
    });
  }

  getSortedMessages(): Message[] {
    return this.messages.sort((a, b) => {
      if (a.createdAt === null && b.createdAt === null) return 0;
      if (a.createdAt === null) return 1;
      if (b.createdAt === null) return -1;
      return new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime();
    });
  }

  formatMessageTimestamp(date: Date | null): string {
    if (!date) return '';

    return new Date(date).toLocaleTimeString('fr-FR', {
      year: '2-digit',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  isCurrentUser(authorId: number): boolean {
    return authorId === this.authService.getUserId();
  }

  selectConversation(conversation: Conversation) {
    this.selectedConversation = conversation;

    this.chatService.getMessages(conversation.id).subscribe({
      next: (messages) => {
        this.messages = messages;
      },
      error: () => {
        console.log('Erreur lors du chargement des messages');
      },
    });
  }

  sendMessage() {
    if (this.newMessage && this.selectedConversation) {
      const newMessageRequest: NewMessageRequest = {
        conversationId: this.selectedConversation.id,
        content: this.newMessage,
      };

      this.chatService.createMessage(newMessageRequest).subscribe({
        next: () => {
          this.newMessage = '';
          this.scrollConversation();
        },
        error: () => {
          console.log("Erreur lors de l'envoi du message");
        },
      });
    }
  }

  newConversation() {
    this.chatService.createConversation().subscribe({
      next: () => {
        this.fetchConversations();
      },
      error: () => {
        console.log('Erreur lors de la création de la conversation');
      },
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
