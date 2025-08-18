export interface ChatMessageDto{
    chatId  : string;
    senderId  :string;
    receiverId : string;
    content  : string;
    sentAt : Date;
    status : string;
    isFromUser:boolean; 
}