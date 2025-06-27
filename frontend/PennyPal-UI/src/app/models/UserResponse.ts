export interface UserResponse{
    id : number,
    name : string,
    email : string,
    phone : number,
    roles: string[],
    createdAt : Date,
    updatedAt : Date,
    verified : boolean,
    active : boolean
    profileURL:string
}