import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Contact } from '../../../models/money-flow.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-recent-contacts',
  imports: [CommonModule],
  templateUrl: './recent-contacts.component.html',
  styleUrl: './recent-contacts.component.css'
})
export class RecentContactsComponent {
  @Input() recentContacts: Contact[] = []
  @Output() sendMoney = new EventEmitter<Contact>()
  @Output() message = new EventEmitter<Contact>()

  onSendMoney(contact: Contact) {
    this.sendMoney.emit(contact)
  }

  onMessage(contact: Contact) {
    this.message.emit(contact)
  }
}
