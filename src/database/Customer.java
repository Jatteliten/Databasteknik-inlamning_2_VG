package database;

public class Customer implements Datatype {
    private final int id;
    private final String name;
    private final String adress;
    private City city;

    public Customer(int id, String name, String adress) {
        this.id = id;
        this.name = name;
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
    public String getAdress() { return adress; }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
