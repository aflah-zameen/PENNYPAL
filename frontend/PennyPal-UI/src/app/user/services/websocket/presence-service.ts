import { Injectable } from '@angular/core';
import { Subject, Observable, filter, debounceTime, distinctUntilChanged, bufferTime, map } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PresenceService {
  private presenceSocket: WebSocket|null = null;
  private presenceSubject = new Subject<{ userId: string; online: boolean }[]>();
  private currentUserId: string | null = null;

  connect(userId: string) {
    this.currentUserId = userId;
    this.presenceSocket = new WebSocket(`ws://localhost:8080/ws/presence?userId=${userId}`);

    this.presenceSocket.onmessage = (event) => {
      console.log(event.data);
      const data = JSON.parse(event.data);
      this.presenceSubject.next(data); // { userId, online }
    };
    this.presenceSocket.onopen = () => {
      console.log("✅ Connected to presence WebSocket");
      // you can emit an event or call a handler here
    };

    this.presenceSocket.onclose = () => {
      console.log("🔌 Disconnected from WebSocket");
    };

    this.presenceSocket.onerror = (err) => {
      console.error("🚨 WebSocket error:", err);
    };
  }

  close() {
    if (this.presenceSocket) {
      this.presenceSocket.close();
      this.presenceSocket = null;
      this.currentUserId = null;
    }
  }

  getPresenceUpdates(): Observable<{ userId: string; online: boolean }[]> {
  return this.presenceSubject.asObservable().pipe(
    map((updates) => 
      updates.filter(update => update.userId !== this.currentUserId)
    ),
    filter(updates => updates.length > 0),
    distinctUntilChanged((prev, curr) =>
      JSON.stringify(prev) === JSON.stringify(curr)
    )
  );
}


}
