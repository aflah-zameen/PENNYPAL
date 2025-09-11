import { Injectable } from "@angular/core"
import { BehaviorSubject, type Observable, of, delay } from "rxjs"
import type { AdminProfile, ProfileUpdateRequest } from "../models/admin.model"
import { HttpClient } from "@angular/common/http"
import { environment } from "../../../environments/environment"
import { User } from "../../models/User"

@Injectable({
  providedIn: "root",
})
export class AdminProfileService {
  private profileSubject = new BehaviorSubject<AdminProfile | null>(null)
  apiURL = environment.apiBaseUrl+"/api/private/admin"

  profile$ = this.profileSubject.asObservable()

  constructor(private http :HttpClient) {
  }


  updateProfile(updatedData : ProfileUpdateRequest):Observable<User>{
    const formData = new FormData();
    if(updatedData.name)
      formData.append("name",updatedData.name);
    if(updatedData.avatar)
      formData.append("profile",updatedData.avatar)
    if(updatedData.phoneNumber)
      formData.append("phone",updatedData.phoneNumber)

    return this.http.put<User>(this.apiURL+"/update",formData,{withCredentials:true});
  }


formatDateTime(date: Date | string | null | undefined): string {
  if (!date) return "—"; // graceful fallback

  const parsedDate = typeof date === "string" ? new Date(date) : date;

  if (isNaN(parsedDate.getTime())) return "Invalid date";

  return new Intl.DateTimeFormat("en-US", {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
    hour12: true
  }).format(parsedDate);
}



}
