export interface Message {
  id: number;
  conversation: number;
  author: number;
  content: string;
  createdAt: Date | null;
}
