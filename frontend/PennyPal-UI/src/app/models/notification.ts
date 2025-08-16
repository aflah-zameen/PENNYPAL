export interface NotificationMessageDto {
  id: string;
  type: string;
  message: string;
  timestamp: string;
  actionURL ?: string;
  read: boolean;
}
