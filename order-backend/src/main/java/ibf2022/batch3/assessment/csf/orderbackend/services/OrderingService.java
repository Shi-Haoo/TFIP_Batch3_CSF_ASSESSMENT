package ibf2022.batch3.assessment.csf.orderbackend.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class OrderingService {

	@Autowired
	private OrdersRepository ordersRepo;

	@Autowired
	private PendingOrdersRepository pendingOrdersRepo;
	
	private static final String URL = "https://pizza-pricing-production.up.railway.app";

	// TODO: Task 5
	// WARNING: DO NOT CHANGE THE METHOD'S SIGNATURE
	public PizzaOrder placeOrder(PizzaOrder order) throws OrderException, IOException {

		MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

		form.add("name", order.getName());
		form.add("email", order.getEmail());
		form.add("sauce", order.getSauce());
		form.add("size", order.getSize());
		form.add("thickCrust", order.getThickCrust());
		form.add("toppings", String.join("," ,order.getToppings()));
		form.add("comments", order.getComments());

		RequestEntity<MultiValueMap<String,Object>> req = RequestEntity
			.post(URL)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(form);

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = null;

		try{
			resp = template.exchange(req, String.class);
		}catch(RestClientException e){
			throw new OrderException(e.getMessage());
		}
		
		String payload = resp.getBody();

		try (InputStream is = new ByteArrayInputStream(payload.getBytes())){
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();

			order.setOrderId(o.getString("order id"));
			order.setTotal(Float.parseFloat(o.getString("price")));
			order.setDate(new Date(Long.parseLong(o.getString("order date"))));
			
		}

		ordersRepo.add(order);
		pendingOrdersRepo.add(order);


		return order;
	}

	// For Task 6
	// WARNING: Do not change the method's signature or its implemenation
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {
		return ordersRepo.getPendingOrdersByEmail(email);
	}

	// For Task 7
	// WARNING: Do not change the method's signature or its implemenation
	public boolean markOrderDelivered(String orderId) {
		return ordersRepo.markOrderDelivered(orderId) && pendingOrdersRepo.delete(orderId);
	}


}
