package database;

public record City(int id, String name) implements Datatype{

    @Override
    public String getName() {
        return name;
    }
}
