import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Contact} from '../../../models/money-flow.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PresenceService } from '../../../services/websocket/presence-service';

@Component({
  selector: 'app-contact-card',
  imports: [FormsModule,CommonModule],
  templateUrl: './contact-card.component.html',
  styleUrl: './contact-card.component.css'
})
export class ContactCardComponent {
  @Input() contact!: Contact
  @Output() sendMoney = new EventEmitter<Contact>()
  @Output() message = new EventEmitter<Contact>()

  onSendMoney(contact: Contact) {
    this.sendMoney.emit(contact)
  }

  onMessage(contact: Contact) {
    this.message.emit(contact)
  }
}
