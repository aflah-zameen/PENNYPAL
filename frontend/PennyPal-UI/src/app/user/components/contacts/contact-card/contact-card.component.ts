import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Contact} from '../../../models/money-flow.model';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PresenceService } from '../../../services/websocket/presence-service';
import { ModalOverlayComponent } from "../../../modals/modal-overlay/modal-overlay.component";
import { SentLentRequest } from '../../../models/lend.model';

@Component({
  selector: 'app-contact-card',
  imports: [FormsModule, CommonModule, ModalOverlayComponent],
  templateUrl: './contact-card.component.html',
  styleUrl: './contact-card.component.css'
})
export class ContactCardComponent implements OnInit{
  @Input() contact!: Contact
  @Output() sendMoney = new EventEmitter<Contact>()
  @Output() message = new EventEmitter<Contact>()
  @Output() request = new EventEmitter<SentLentRequest>()

  isRequestModalOpen = false;
  requestAmount: number | null = null;
  requestMessage = '';
  requestDeadline = '' 
  minDate = ''

  transactionsExpanded = false;

  ngOnInit(): void {
  const today = new Date();
  this.minDate = today.toISOString().split('T')[0]; 
  }
  toggleTransactions(): void {
    this.transactionsExpanded = !this.transactionsExpanded;
  }

  onSendMoney(contact: Contact) {
    this.sendMoney.emit(contact)
  }

  onMessage(contact: Contact) {
    this.message.emit(contact)
  }

  submitRequest(form : NgForm){
    if(form.invalid)
      return;

    const formData : SentLentRequest ={
      amount : this.requestAmount!,
      message : this.requestMessage,
      proposedDeadline : new Date(this.requestDeadline).toISOString(),
      requestedTo : this.contact.id
    }
    this.request.emit(formData);
    this.requestAmount = null;
    this.requestMessage = '';
    this.requestDeadline = '' 
    this.minDate = ''
    this.isRequestModalOpen = false;
  }
}
