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

  ngOnInit(): void {

    this.orderEmail = this.activatedRoute.snapshot.params['email']
    console.info(">>>order email>>", this.orderEmail)

    firstValueFrom(this.pizzaSvc.getOrders(this.orderEmail))
    .then((p)=>{
      this.pendingOrders = p
    })
    .catch((error: HttpErrorResponse)=>alert(JSON.stringify(error)))
  }




}
