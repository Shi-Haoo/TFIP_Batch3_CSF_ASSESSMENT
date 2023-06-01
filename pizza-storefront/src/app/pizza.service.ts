import { Injectable, inject } from "@angular/core";
import { Order, PendingOrder, ProcessedOrder} from "./models";
import { HttpClient } from "@angular/common/http";

const URL = '/api'

@Injectable()
export class PizzaService {

  order!: Order
  processed!: ProcessedOrder
  orderId!: string
  

  httpClient = inject(HttpClient)

  // TODO: Task 3
  // You may add any parameters and return any type from placeOrder() method
  // Do not change the method name
  placeOrder() {
    console.info("service order>>>", this.order)
    return this.httpClient.post<any>(`${URL}/order`, this.order)
  }

  // TODO: Task 5
  // You may add any parameters and return any type from getOrders() method
  // Do not change the method name
  getOrders(orderEmail: string) {
    console.info(">>>order email in service>>", orderEmail)
    
    return this.httpClient.get<PendingOrder[]>(`${URL}/orders/${orderEmail}`)
  }

  // TODO: Task 7
  // You may add any parameters and return any type from delivered() method
  // Do not change the method name
  delivered() {

    return this.httpClient.delete(`${URL}/order/${this.orderId}`)
  }

}
