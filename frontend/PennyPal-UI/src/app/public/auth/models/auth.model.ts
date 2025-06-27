import { Roles } from "../../../models/Roles";

// src/public/models/auth.model.ts
export interface SignupRequest {
  name : string,
  phone : number,  
  email: string;
  password: string;
  role: string;
  profilePicture : File
}

export interface SignupResponse {
  success: boolean;
  message: string;
  user?: {
    id: number;
    username: string;
    roles: string[];
  };
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  success: boolean;
  message: string;
}

export interface ForgotPasswordRequest {
  email: string;
}

export interface ForgotPasswordResponse {
  success: boolean;
  message: string;
}

export interface OtpVerificationRequest {
  email: string;
  otp: string;
}

export interface OtpVerificationResponse {
  success: boolean;
  message: string;
}