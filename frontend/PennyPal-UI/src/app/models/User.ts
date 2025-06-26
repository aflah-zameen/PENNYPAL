import { Roles } from "./Roles";

export interface User{
    id : number,
    name : string,
    email : string,
    phone : number,
    roles: Set<Roles>,
    createdAt : Date,
    updatedAt : Date
    active : boolean,
    verified : boolean
}