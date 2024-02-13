package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class Repository {
    private static Repository repository;
    private static final String PROPERTIES_FILE_PATH = "src/Settings.properties";
    private static final String PROPERTIES_CONNECTION_STRING = "connectionString";
    private static final String PROPERTIES_USER_NAME = "serverUsername";
    private static final String PROPERTIES_PASSWORD = "serverPassword";
    private final Properties P = new Properties();

    private Repository() {
        try(FileInputStream f = new FileInputStream(PROPERTIES_FILE_PATH)){
            P.load(f);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public synchronized static Repository getRepository() {
        if(repository == null){
            repository = new Repository();
        }
        return repository;
    }

    protected ArrayList<City> loadCities() {
        ArrayList<City> tempList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                P.getProperty(PROPERTIES_CONNECTION_STRING),
                P.getProperty(PROPERTIES_USER_NAME),
                P.getProperty(PROPERTIES_PASSWORD));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, name from cities")) {

            while(rs.next()){
                tempList.add(new City(rs.getInt("id"), rs.getString("name")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tempList;
    }

    protected ArrayList<Customer> loadCustomers(ArrayList<City> cities) {
        ArrayList<Customer> tempList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                P.getProperty(PROPERTIES_CONNECTION_STRING),
                P.getProperty(PROPERTIES_USER_NAME),
                P.getProperty(PROPERTIES_PASSWORD));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, name, cityId, adress from customers")) {

            while(rs.next()){
                int cityIdCheck = rs.getInt("cityID");
                Customer newCustomer = new Customer(rs.getInt("id"), rs.getString("name"),
                        rs.getString("adress"));

                cities.stream().filter(c -> c.id() == cityIdCheck).findFirst().ifPresent(newCustomer::setCity);
                tempList.add(newCustomer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tempList;
    }

    protected ArrayList<Colour> loadColours() {
        ArrayList<Colour> tempList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                P.getProperty(PROPERTIES_CONNECTION_STRING),
                P.getProperty(PROPERTIES_USER_NAME),
                P.getProperty(PROPERTIES_PASSWORD));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, name from colours")) {

            while(rs.next()){
                tempList.add(new Colour(rs.getInt("id"), rs.getString("name")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tempList;
    }

    protected ArrayList<Category> loadCategories() {
        ArrayList<Category> tempList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                P.getProperty(PROPERTIES_CONNECTION_STRING),
                P.getProperty(PROPERTIES_USER_NAME),
                P.getProperty(PROPERTIES_PASSWORD));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, name from categories")) {

            while(rs.next()){
                tempList.add(new Category(rs.getInt("id"), rs.getString("name")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tempList;
    }

    protected ArrayList<Shoe> loadShoes(ArrayList<Colour> colours) {
        ArrayList<Shoe> tempList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                P.getProperty(PROPERTIES_CONNECTION_STRING),
                P.getProperty(PROPERTIES_USER_NAME),
                P.getProperty(PROPERTIES_PASSWORD));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, brand, price, colourID, size, stock from shoes")) {

            while(rs.next()){
                int colourIdCheck = rs.getInt("colourID");
                Shoe newShoe = new Shoe(rs.getInt("id"), rs.getString("brand"),
                        rs.getInt("price"), rs.getInt("size"), rs.getInt("stock"));

                colours.stream().filter(c -> c.id() == colourIdCheck).findFirst().ifPresent(newShoe::setColour);
                tempList.add(newShoe);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tempList;
    }

    protected void assignShoeCategories(ArrayList<Shoe> shoes, ArrayList<Category> categories) {
        try (Connection con = DriverManager.getConnection(
                P.getProperty(PROPERTIES_CONNECTION_STRING),
                P.getProperty(PROPERTIES_USER_NAME),
                P.getProperty(PROPERTIES_PASSWORD));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select shoeID, categoryID from shoecategory")) {

            while(rs.next()){
                int shoeIdCheck = rs.getInt("shoeID");
                int categoryIdCheck = rs.getInt("categoryID");

                shoes.stream().filter(s -> s.getId() == shoeIdCheck).findFirst()
                        .ifPresent(s -> categories.stream().filter(c -> c.id() == categoryIdCheck)
                                .findFirst().ifPresent(s::addToCategories));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ArrayList<Order> loadOrders(ArrayList<Customer> customers){
        ArrayList<Order> orders = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                P.getProperty(PROPERTIES_CONNECTION_STRING),
                P.getProperty(PROPERTIES_USER_NAME),
                P.getProperty(PROPERTIES_PASSWORD));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, date, customerId from orders")) {

            while(rs.next()){
                int checkCustomerId = rs.getInt("customerId");
                Order tempOrder = new Order(rs.getInt("id"), rs.getDate("date"));
                customers.stream().filter(c -> c.getId() == checkCustomerId)
                        .findFirst().ifPresent(tempOrder::setCustomer);
                orders.add(tempOrder);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    protected ArrayList<Sale> loadSoldShoes(ArrayList<Shoe> shoes, ArrayList<Order> orders){
        ArrayList<Sale> sales = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                P.getProperty(PROPERTIES_CONNECTION_STRING),
                P.getProperty(PROPERTIES_USER_NAME),
                P.getProperty(PROPERTIES_PASSWORD));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, shoeID, orderID from soldshoes")) {

            while(rs.next()){
                int checkShoeId = rs.getInt("shoeID");
                int checkOrderId = rs.getInt("orderID");
                Sale tempSale = new Sale(rs.getInt("id"));
                shoes.stream().filter(s -> s.getId() == checkShoeId)
                        .findFirst().ifPresent(tempSale::setShoe);
                orders.stream().filter(o -> o.getId() == checkOrderId)
                        .findFirst().ifPresent(tempSale::setOrder);
                sales.add(tempSale);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sales;
    }

}