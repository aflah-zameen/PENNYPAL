export interface NotificationMessageDto {
  id: string;
  type: string;
  message: string;
  timeStamp: string;
  actionURL ?: string;
  read: boolean;
}
