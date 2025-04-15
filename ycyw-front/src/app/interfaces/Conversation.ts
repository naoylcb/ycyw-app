export interface Conversation {
  id: number;
  customer: {
    id: number;
    name: string;
  };
  support: {
    id: number;
    name: string;
  };
}
