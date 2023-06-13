package z13.rentivo.views.bill;

import ch.qos.logback.core.Layout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;

import java.util.HashMap;
import java.util.Map;

@PageTitle("Bills statistics")
@Route(value = "/stats/bills", layout = DataSelectView.class)
public class BillStatisticView extends VerticalLayout {
    DataService dataService;
    Map<String, Object> stats = new HashMap<>();
    public BillStatisticView(DataService dataService){
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
