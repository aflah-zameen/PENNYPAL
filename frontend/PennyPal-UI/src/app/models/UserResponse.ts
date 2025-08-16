export interface UserResponse{
    id : string,
    name : string,
    email : string,
    phone : number,
    roles: string[],
    createdAt : Date,
    updatedAt : Date,
    verified : boolean,
    active : boolean
    profileURL:string
    coinBalance: number,
    accessToken?: string
}