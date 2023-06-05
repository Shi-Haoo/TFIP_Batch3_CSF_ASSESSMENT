import { Component, OnInit, inject } from '@angular/core';
import { PizzaService } from '../pizza.service';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { PendingOrder } from '../models';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  pizzaSvc = inject(PizzaService)
  router = inject(Router)
  activatedRoute = inject(ActivatedRoute)

  orderEmail: string =""
  pendingOrders: PendingOrder[] = []
  toDeleteOrder!: PendingOrder

  ngOnInit(): void {

    this.orderEmail = this.activatedRoute.snapshot.params['email']
    console.info(">>>order email>>", this.orderEmail)

    firstValueFrom(this.pizzaSvc.getOrders(this.orderEmail))
    .then((p)=>{
      this.pendingOrders = p 
      //console.info(">>>pendingOrders in orders component>>>", this.pendingOrders)
    })
    .catch((error: HttpErrorResponse)=>alert(JSON.stringify(error)))
  }

  changeOrderStatus(i: number){

    this.toDeleteOrder = this.pendingOrders[i]

    this.pizzaSvc.orderId = this.toDeleteOrder.orderId

  
    firstValueFrom(this.pizzaSvc.delivered())
    .then((s)=>{
      alert("order status has been changed to delievered")
      //Need to use return keyword for proper chaining of .then(). Using return keyword allows the subsequent 
      //.then() to wait for the returned promise to resolve before proceeding. 
      //This allows for proper sequencing and handling of the asynchronous operations. If we don't use return keyword,
      //the subsequent .then() is not aware of the asynchronous operation inside that callback. 
      //(this.pizzaSvc.getOrders(this.orderEmail)) will not be called. The subsequent .then()  
      //will execute immediately after the callback is invoked, without waiting for any asynchronous tasks within the callback 
      //to complete. This can lead to unexpected behavior or race conditions, as the subsequent code may rely on 
      //the result of the asynchronous operation that hasn't finished yet.
       return firstValueFrom(this.pizzaSvc.getOrders(this.orderEmail))
       .then((p)=>{
         this.pendingOrders = p
       })
       .catch((error: HttpErrorResponse)=>alert(JSON.stringify(error)))
    })
    .catch((error: HttpErrorResponse)=>alert(JSON.stringify(error)))
  }

}
