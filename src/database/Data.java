package database;

import java.util.ArrayList;

public class Data {
    private static Data data;
    private final ArrayList<City> cities;
    private final ArrayList<Customer> customers;
    private final ArrayList<Colour> colours;
    private final ArrayList<Category> categories;
    private final ArrayList<Shoe> shoes;
    private ArrayList<Order> orders;
    private ArrayList<Sale> sales;

    private Data() {
        cities = Repository.getRepository().loadCities();
        customers =  Repository.getRepository().loadCustomers(cities);
        colours = Repository.getRepository().loadColours();
        categories = Repository.getRepository().loadCategories();
        shoes = Repository.getRepository().loadShoes(colours);
        orders = Repository.getRepository().loadOrders(customers);
        sales = Repository.getRepository().loadSoldShoes(shoes, orders);
        Repository.getRepository().assignShoeCategories(shoes, categories);
    }

    public static Data getData() {
        if(data == null){
            data = new Data();
        }
        return data;
    }

    public ArrayList<City> getCities() { return cities; }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Colour> getColours() {
        return colours;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Shoe> getShoes() {
        return shoes;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public ArrayList<Sale> getSales() { return sales; }

    public void reloadOrders(){
        orders.clear();
        orders = Repository.getRepository().loadOrders(customers);
    }

    public void reloadSales(){
        reloadOrders();
        sales.clear();
        sales = Repository.getRepository().loadSoldShoes(shoes, orders);
    }

}