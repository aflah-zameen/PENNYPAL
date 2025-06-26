import { EventEmitter } from "@angular/core";

export interface InputFieldProps {
  label: string;
  placeholder: string;
  type?: string;
  required?: boolean;
  value?: string;
  onChange: EventEmitter<string>;
}

export interface PhoneInputProps {
  value?: string;
  onChange: EventEmitter<string>;
  required?: boolean;
}

export interface FormButtonProps {
  text: string;
  type?: 'button' | 'submit';
  variant?: 'primary' | 'secondary';
  onClick?: () => void;
}

export interface OtpInputProps {
  value: string;
  onChange: (value: string) => void;
  onComplete?: () => void;
  length?: number;
}

export interface TimerProps {
  initialSeconds: number;
  onExpire?: () => void;
}
