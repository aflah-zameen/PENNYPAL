import { Roles } from "./Roles";

export interface User{
    id : string,
    userName : string,
    email : string,
    phone : number,
    roles: Set<Roles>,
    createdAt : Date,
    updatedAt : Date
    active : boolean,
    verified : boolean
    profileURL : string
    coinBalance : number
}