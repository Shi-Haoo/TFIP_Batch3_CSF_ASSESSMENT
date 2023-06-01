
export interface Order{
    
    name: string
    email: string
    size: string
    base: string
    sauce: string
    toppings: string[]
    comments: string

}

export interface processedOrder{
    orderId: string
    date: number
    name: string
    email: string
    total: number
    
}