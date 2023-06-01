import { Injectable, inject } from "@angular/core";
import { Order, ProcessedOrder} from "./models";
import { HttpClient } from "@angular/common/http";

const URL = '/api'

@Injectable()
export class PizzaService {

  order!: Order
  processed!: ProcessedOrder
  

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
  getOrders() {
  }

  // TODO: Task 7
  // You may add any parameters and return any type from delivered() method
  // Do not change the method name
  delivered() {
  }

}
