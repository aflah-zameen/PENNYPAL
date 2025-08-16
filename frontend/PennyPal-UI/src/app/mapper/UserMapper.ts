import { Injectable } from "@angular/core";
import { Roles } from "../models/Roles";
import { User } from "../models/User";
import { UserResponse } from "../models/UserResponse";

@Injectable({
  providedIn : 'root'
})
export class UserMapper{
    toEntity(user : UserResponse):User{
        return {
                  id : user.id,
                  userName : user.name,
                  email : user.email,
                  phone : user.phone,
                  roles : new Set(
                              user.roles.map(role => Roles[role as keyof typeof Roles])
                            ),
                  createdAt : user.createdAt,
                  updatedAt : user.updatedAt,
                  verified : user.verified,
                  active : user.active,
                  profileURL : user.profileURL,
                  coinBalance : user.coinBalance
                  };
    }
} 