package ibf2022.batch3.assessment.csf.orderbackend.models;

import java.util.List;

public class Order {
    
    private String name;
	private String email;
	private String size;
    private String base;
    private String sauce;
    private List<String> toppings;
    private String comments = "";


    public Order() {
    }

    public Order(String name, String email, String size, String base, String sauce, List<String> toppings,
            String comments) {
        this.name = name;
        this.email = email;
        this.size = size;
        this.base = base;
        this.sauce = sauce;
        this.toppings = toppings;
        this.comments = comments;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }
    public String getSauce() {
        return sauce;
    }
    public void setSauce(String sauce) {
        this.sauce = sauce;
    }
    public List<String> getToppings() {
        return toppings;
    }
    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Order [name=" + name + ", email=" + email + ", size=" + size + ", base=" + base + ", sauce=" + sauce
                + ", toppings=" + toppings + ", comments=" + comments + "]";
    }
    
    
    
}
