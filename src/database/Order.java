package database;

import java.util.Date;

public class Order {
    private final int id;
    private final Date date;
    private Customer customer;

    public Order(int id, Date date){
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
