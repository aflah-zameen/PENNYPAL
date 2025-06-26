import { Component,EventEmitter,Output } from '@angular/core';

@Component({
  selector: 'app-logout-button',
  imports: [],
  templateUrl: './logout-button.component.html',
  styleUrl: './logout-button.component.css'
})
export class LogoutButtonComponent {
  @Output() logout = new EventEmitter<void>();

  logoutIconSvg = `<svg id="159:830" layer-name="logout" width="32" height="33" viewBox="0 0 32 33" fill="none" xmlns="http://www.w3.org/2000/svg" class="logout-icon" style="width: 32px; height: 32px; z-index: 1">
        <path d="M3.58333 32.25C2.59792 32.25 1.75434 31.8991 1.0526 31.1974C0.350868 30.4957 0 29.6521 0 28.6667V3.58333C0 2.59792 0.350868 1.75434 1.0526 1.0526C1.75434 0.350868 2.59792 0 3.58333 0H16.125V3.58333H3.58333V28.6667H16.125V32.25H3.58333ZM23.2917 25.0833L20.8281 22.4854L25.3969 17.9167H10.75V14.3333H25.3969L20.8281 9.76458L23.2917 7.16667L32.25 16.125L23.2917 25.0833Z" fill="#FF0000"></path>
        </svg>`;

  onLogout(): void {    
    this.logout.emit();
  }
}
