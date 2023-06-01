package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

@Repository
public class OrdersRepository {
	@Autowired 
	MongoTemplate mongoTemplate;

	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for add()
	//db.orders.insert({
	// 	_id: 'abcd1234'
	// 	date: converted date retrieved from external REST Endpoint
	// 	total: 40.00
	// 	name: 'SH'
	// 	email: 'SH@hotmail.com'
	//	sauce: 'classic'
	//  size: 9
	// 	comments: 'time to relax!'
	//  toppings: ['chicken', 'seafood']
	//  })

	public void add(PizzaOrder order) {

		Document doc = new Document();

		doc.append("_id", order.getOrderId());
		doc.append("date", order.getDate());
		doc.append("total", order.getTotal());
		doc.append("name", order.getName());
		doc.append("email", order.getEmail());
		doc.append("sauce", order.getSauce());
		doc.append("size", order.getSize());
		doc.append("comments", order.getComments());
		doc.append("toppings", order.getToppings());

		mongoTemplate.insert(doc, "orders");

	}
	
	// TODO: Task 6
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for getPendingOrdersByEmail()
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {

		return null;
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for markOrderDelivered()
	public boolean markOrderDelivered(String orderId) {

		return false;
	}


}
