package z13.rentivo.views.statistic_views;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.entities.Client;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.StatsSelectView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@PageTitle("Clients statistics")
@Route(value = "/stats/clients", layout = StatsSelectView.class)
public class ClientStatsView extends VerticalLayout {
    DataService dataService;
    Map<String, Object> stats = new HashMap<>();

    public ClientStatsView(DataService dataService){
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
        Client bestClient = dataService.getMostActiveClient();
        Long numRentals = dataService.countClientRentals(bestClient);
        Map<String, String> clientData = new LinkedHashMap<>();

        clientData.put("Name", bestClient.getName());
        clientData.put("Surname", bestClient.getSurname());
        clientData.put("Number of rentals", numRentals.toString());
        stats.put("Best client:", clientData);


    }
}