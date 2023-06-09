import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Order } from '../models';
import { PizzaService } from '../pizza.service';

import { firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PIZZA_TOPPINGS: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]



@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{

  pizzaSize = SIZES[0]
  
  addedToppings: string[] = []

  fb = inject(FormBuilder)
  pizzaSvc = inject(PizzaService)
  router = inject(Router)

  form!: FormGroup

  
  constructor() { }

  ngOnInit(): void {
      this.form = this.createForm()
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  //This event will be called everytime a checkbox is checked or unchecked
  modifyToppings(topping: any){
    
    if(topping.checked){

      // for (let i = 0; i < this.addedToppings.length; i++) {
      //   if (this.addedToppings[i] === topping.value) {
      //     this.addedToppings.splice(i, 1)
      //     console.info("updatedToppings array>>>", this.addedToppings)
      //   }
      // }

      this.addedToppings.push(topping.value)
  
      console.info("addedToppings array>>>", this.addedToppings)
    }

    //if checkbox is unchecked, we remove that topping from the array using .filter().  
    //.filter() filters out element that is equal to the topping unchecked from the checkbox
    else{
      this.addedToppings = this.addedToppings.filter((top: string) => top !== topping.value)
      console.info("toppings array after unchecking>>>", this.addedToppings)
    }
    
    
  }


  postOrder(){
    const postOrder: Order = this.form.value
    postOrder.size = this.pizzaSize
    postOrder.toppings = this.addedToppings
    console.info(">>>>order>>>", postOrder)

    this.pizzaSvc.order = postOrder
    
    firstValueFrom(this.pizzaSvc.placeOrder())
    .then((po)=>{
      this.pizzaSvc.processed = po
      this.router.navigate(['/orders', postOrder.email])
    })
    .catch((error: HttpErrorResponse)=>alert(JSON.stringify(error)))
    
  }

  invalidForm(){
    return this.form.invalid || this.addedToppings.length < 1
  }

  private createForm(){
    return this.fb.group({
      
      name: this.fb.control<string>('',[Validators.required]),
      email: this.fb.control<string>('',[Validators.required, Validators.email]),
      size: this.fb.control<string>('',[Validators.required]),
      base: this.fb.control<string>('',[Validators.required]),
      sauce: this.fb.control<string>('',[Validators.required]),
      toppings: this.fb.control('',[Validators.required]),
      comments: this.fb.control<string>('')
    })
  }

}
