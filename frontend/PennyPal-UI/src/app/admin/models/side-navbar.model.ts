
export interface MenuItem {
  text: string
  icon: string
  route: string
  isActive?: boolean
  badge?: string
  subItems?: MenuItem[]
}