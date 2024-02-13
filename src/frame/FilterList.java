package frame;

import database.Sale;

import java.util.ArrayList;

public interface FilterList {
    ArrayList<Sale> apply(ArrayList<Sale> sales);
}
