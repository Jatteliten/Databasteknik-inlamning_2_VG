package database;

public class Sale {
    private final int id;
    private Shoe shoe;
    private Order order;

    public Sale(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrderId() {
        return order;
    }
}
