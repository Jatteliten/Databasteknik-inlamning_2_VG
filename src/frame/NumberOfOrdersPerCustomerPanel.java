package frame;

import database.Data;
import database.Datatype;

import java.util.Map;

public class NumberOfOrdersPerCustomerPanel extends StatisticsPanel {
    public NumberOfOrdersPerCustomerPanel() {
        super();
    }

    @Override
    protected String setFirstInformationLabelName(){ return "Customer:"; }

    @Override
    protected String setSecondInformationLabelName() { return "#Orders:"; }

    @Override
    protected Map<Datatype, Integer> loadData(Map<Datatype, Integer> map) {
        Data.getData().getOrders().forEach(o -> map.merge(o.getCustomer(), 1, Integer::sum));
        return map;
    }
}
