package frame;

import database.Data;
import database.Datatype;

import java.util.Map;

public class RevenuePerCustomerPanel extends StatisticsPanel {
    public RevenuePerCustomerPanel() {
        super();
    }

    @Override
    protected String setFirstInformationLabelName(){ return "Customer:"; }

    @Override
    protected String setSecondInformationLabelName() {
        return "Money spent:";
    }

    @Override
    protected Map<Datatype, Integer> loadData(Map<Datatype, Integer> map) {
        Data.getData().getSales().forEach(s ->
                map.merge(s.getOrderId().getCustomer(), s.getShoe().getPrice(), Integer::sum)
        );
        return map;
    }
}
