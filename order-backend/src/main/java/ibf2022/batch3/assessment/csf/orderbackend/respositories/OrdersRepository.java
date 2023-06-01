package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

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
		
		// Query q =new Query();
		// q.addCriteria(Criteria.where("email").is(email));

		// return mongoTemplate.find(q, Document.class, "orders").stream()
        //                                                  .map(d -> convertFromDocument(d))
        //                                                  .toList();

		MatchOperation mOp = Aggregation.match(Criteria.where("email").is(email));

		ProjectionOperation pop = Aggregation.project("_id", "total", "date");

		SortOperation sop = Aggregation.sort(Sort.by(Direction.DESC, "date"));

		Aggregation pipeline = Aggregation.newAggregation(mOp, pop, sop);

		AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "orders", Document.class);

		List<Document> rs = results.getMappedResults();

		return rs.stream().map(d -> convertFromDocument(d)).toList();

	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for markOrderDelivered()
	public boolean markOrderDelivered(String orderId) {

		Query q = new Query();
		q.addCriteria(Criteria.where("_id").is(orderId));
		Update updateOps= new Update()
								.set("delivered", "true");

		UpdateResult result = mongoTemplate.updateMulti(q, updateOps, Document.class, "orders");
		
		if(result.wasAcknowledged()){
			return true;
		}
		return false;
	}

	public PizzaOrder convertFromDocument(Document d){
		PizzaOrder po = new PizzaOrder();

		po.setOrderId(d.getString("_id"));
		po.setDate(d.getDate("date"));
		po.setTotal((float)d.getLong("total"));
		// po.setName(d.getString("name"));
		// po.setEmail(d.getString("email"));
		// po.setSauce(d.getString("sauce"));
		// po.setSize(d.getInteger("size"));
		// po.setComments(d.getString("comments"));
		// po.setToppings(d.getList("toppings", String.class));

		return po;
	}

}
