export type PagedResultDTO<T> = {
    content : T[],
    pageNumber : number,
    pageSize : number,
    totalElements : number,
    totalPages : number
}