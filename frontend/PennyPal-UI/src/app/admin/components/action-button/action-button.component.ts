import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-action-button',
  imports: [],
  templateUrl: './action-button.component.html',
  styleUrl: './action-button.component.css'
})
export class ActionButtonComponent {
  @Input() text: string = '';
  @Input() variant: 'danger' | 'primary' | 'secondary' = 'primary';
  @Input() onClick?: () => void;

  get buttonClasses(): string {
    const baseClasses = 'gap-2.5 p-2.5 h-11 text-xl tracking-wide max-sm:p-1.5 max-sm:text-base max-sm:h-[35px] max-sm:w-[60px]';

    switch (this.variant) {
      case 'danger':
        return `${baseClasses} text-white bg-red-600`;
      case 'primary':
        return `${baseClasses} text-white bg-blue-600`;
      case 'secondary':
        return `${baseClasses} text-gray-700 bg-gray-200`;
      default:
        return `${baseClasses} text-white bg-blue-600`;
    }
  }

  handleClick(): void {
    if (this.onClick) {
      this.onClick();
    }
  }
}
