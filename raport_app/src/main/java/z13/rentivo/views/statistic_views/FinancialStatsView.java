package z13.rentivo.views.statistic_views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.StatsSelectView;

import java.util.HashMap;
import java.util.Map;

@PageTitle("Finances")
@Route(value = "/stats/finances", layout = StatsSelectView.class)
public class FinancialStatsView extends VerticalLayout {
    DataService dataService;
    Map<String, Object> stats = new HashMap<>();
    public FinancialStatsView(DataService dataService){
        this.dataService = dataService;
        getStats();
        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            HorizontalLayout pair = new HorizontalLayout();
            H3 keyLabel = new H3(entry.getKey());
            H3 valueLabel = new H3(entry.getValue().toString());
            pair.add(keyLabel, valueLabel);
            add(pair);
        }
    }

    private void getStats(){
        stats.put("Average amount:", dataService.getAverageBillAmount());
        stats.put("Paid bills:", dataService.getPaidBillsCount());
        stats.put("Not paid bills:", dataService.getNotPaidBillsCount());
    }
}
