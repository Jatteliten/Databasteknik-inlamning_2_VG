package frame;

import database.Data;
import database.Datatype;

import java.util.Map;

public class RevenuePerCityPanel extends StatisticsPanel {
    public RevenuePerCityPanel() {
        super();
    }

    @Override
    protected String setFirstInformationLabelName() {
        return "City:";
    }

    @Override
    protected String setSecondInformationLabelName() {
        return "Money spent:";
    }

    @Override
    protected Map<Datatype, Integer> loadData(Map<Datatype, Integer> map) {
        Data.getData().getSales().forEach(s -> map
                .merge(s.getOrderId().getCustomer().getCity(), s.getShoe().getPrice(), Integer::sum));
        return map;
    }
}
