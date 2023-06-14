package z13.rentivo.views.statistic_views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.components.ChartComponent;
import z13.rentivo.entities.Client;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.StatsSelectView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@PageTitle("Clients statistics")
@Route(value = "/stats/clients", layout = StatsSelectView.class)
public class ClientStatsView extends VerticalLayout {
    DataService dataService;
    ChartComponent mostRentalsChart = new ChartComponent();
    ChartComponent highestPenaltySumChart = new ChartComponent();
    ChartComponent clientsGenderChart = new ChartComponent();

    public ClientStatsView(DataService dataService){
        this.dataService = dataService;
        initClientsGenderChart();
        initMostRentalsChart();
        initHighestPenaltySumChart();
    }

    private void initClientsGenderChart() {
        Map<String, Long> data = dataService.countClientsByGenders();
        List<String> labels = data.keySet().stream().toList();
        double[] values = data.values().stream().mapToDouble(Double::valueOf).toArray();
        clientsGenderChart.setTitle("Gender structure");
        clientsGenderChart.setChartType(ChartComponent.ChartType.PIE);
        ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset()};
        datasets[0].data = values;
        clientsGenderChart.setDatasets(datasets);
        clientsGenderChart.setLabels(labels.toArray(new String[0]));
        clientsGenderChart.setMinHeight(300, Unit.PIXELS);
        add(clientsGenderChart);
    }

    private void initMostRentalsChart(){
        Map<String, Long> data = dataService.getTenClientsWithMostRentals();
        List<String> labels = data.keySet().stream().toList();
        double[] values = data.values().stream().mapToDouble(Double::valueOf).toArray();
        mostRentalsChart.setTitle("The most active clients");
        mostRentalsChart.setChartType(ChartComponent.ChartType.BAR);
        ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset()};
        datasets[0].data = values;
        mostRentalsChart.setDatasets(datasets);
        mostRentalsChart.setLabels(labels.toArray(new String[0]));
        mostRentalsChart.setMinHeight(300, Unit.PIXELS);
        add(mostRentalsChart);
    }

    private void initHighestPenaltySumChart(){
        Map<String, Float> data = dataService.getFiveClientsWithHighestPenaltySum();
        List<String> labels = data.keySet().stream().toList();
        double[] values = data.values().stream().mapToDouble(Double::valueOf).toArray();
        highestPenaltySumChart.setTitle("Clients black list (penalty sum)");
        highestPenaltySumChart.setChartType(ChartComponent.ChartType.BAR);
        ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset()};
        datasets[0].data = values;
        highestPenaltySumChart.setDatasets(datasets);
        highestPenaltySumChart.setLabels(labels.toArray(new String[0]));
        highestPenaltySumChart.setMinHeight(300, Unit.PIXELS);
        add(highestPenaltySumChart);
    }
}
