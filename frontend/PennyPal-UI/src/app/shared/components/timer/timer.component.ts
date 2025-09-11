import { AsyncPipe } from '@angular/common';
import { Component, EventEmitter, Output, Input, OnInit, OptionalDecorator, OnDestroy } from '@angular/core';
import { interval, map, Observable, of, startWith, Subscribable, Subscription, switchMap, takeWhile } from 'rxjs';

@Component({
  selector: 'app-timer',
  imports: [AsyncPipe],
  templateUrl: './timer.component.html',
  styleUrl: './timer.component.css'
})
export class TimerComponent implements OnInit, OnDestroy{
  @Input() expiresAt$: Observable<Date | null> = new Observable();
  @Output() expire = new EventEmitter<void>();

  fomrattedTime$ : Observable<string> = of('--:--');
  private sub?: Subscription;

  remainingSeconds: number = 0;

  ngOnInit() {
    this.fomrattedTime$ = this.expiresAt$.pipe(
      switchMap((expiresAt) => {
        console.log(expiresAt+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
        if(!expiresAt) return of('--:--');
        return interval(1000).pipe(
          startWith(0),
          map(()=>{
            const diffMs =expiresAt.getTime() - Date.now();
            const remainingSec = Math.max(Math.floor(diffMs/1000),0);

            if(remainingSec === 0){
              this.expire.emit();
            }
            const mins = Math.floor(remainingSec/60).toString().padStart(2,'0');
            const sec = (remainingSec % 60).toString().padStart(2,'0');
            return `${mins}:${sec} sec`;
          }),
          takeWhile((time) => time !== '00:00 sec',true)
        );
      })
    );

    this.fomrattedTime$.subscribe(formatted => {
      console.log('Formatted time:', formatted);
    });

  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }
}
