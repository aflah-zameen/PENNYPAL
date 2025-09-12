import { Injectable, OnInit } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject } from "rxjs";
import { Message } from "../models/message.model";

@Injectable({
  providedIn: "root",
})
export class MessageService {
 private readonly apiURL = `${environment.apiBaseUrl}/api/private/user/messages`;
 private messageSubject = new BehaviorSubject<Message[]>([]);
 messages$ = this.messageSubject.asObservable();
  constructor(private http: HttpClient) {}

    getAllMessages(){
      this.http.get<Message[]>(this.apiURL,{withCredentials: true}).subscribe({
        next: (messages) => this.messageSubject.next(messages),
        error: (error) => console.error("Error fetching messages:", error),
      });
    }
}
