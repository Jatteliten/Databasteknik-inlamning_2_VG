package frame;

import database.Data;
import database.Datatype;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class TopShoesSoldPanel extends StatisticsPanel {
    public TopShoesSoldPanel() {
        super();
    }
    @Override
    protected String setFirstInformationLabelName() {
        return "ID : Brand";
    }

    @Override
    protected String setSecondInformationLabelName() {
        return "#Sold:";
    }

    @Override
    protected Map<Datatype, Integer> loadData(Map<Datatype, Integer> map) {
        Data.getData().getSales().forEach(s -> map.merge(s.getShoe(), 1, Integer::sum));

        return map.entrySet()
                .stream().sorted(Map.Entry.<Datatype, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
