package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ibf2022.batch3.assessment.csf.orderbackend.models.Order;
import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderException;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderingService;
import jakarta.json.Json;

@Controller
public class OrderController {

	@Autowired
	OrderingService orderSvc;

	// TODO: Task 3 - POST /api/order
	@PostMapping(path="/api/order")
	@ResponseBody
	public ResponseEntity<String> postOrder(@RequestBody Order order){
		System.out.println(">>>>post order>>>"+order.toString());
		PizzaOrder po = new PizzaOrder();

		if(order.getBase().equalsIgnoreCase("thin")){
			po.setThickCrust(false);
		}
		else
		po.setThickCrust(true);

		Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(order.getSize());
        
        if (matcher.find()) {
            String number = matcher.group();
			po.setSize(Integer.parseInt(number));
            System.out.println(number); 
        }

		po.setName(order.getName());
		po.setEmail(order.getEmail());
		po.setSauce(order.getSauce());
		po.setToppings(order.getToppings());
		po.setComments(order.getComments());

		try{
			PizzaOrder processedOrder = orderSvc.placeOrder(po);

			return ResponseEntity
					.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Json.createObjectBuilder()
							.add("orderId", processedOrder.getOrderId())
							.add("date", processedOrder.getDate().getTime())
							.add("name", processedOrder.getName())
							.add("email", processedOrder.getEmail())
							.add("total", processedOrder.getTotal())
							.build().toString());

		}catch(OrderException ex){

			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(Json.createObjectBuilder()
							add("error", ex.getMessage())
							.build().toString());
		}
		
		
	}

	// TODO: Task 6 - GET /api/orders/<email>


	// TODO: Task 7 - DELETE /api/order/<orderId>

}
