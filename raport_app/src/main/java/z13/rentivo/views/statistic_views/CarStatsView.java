package z13.rentivo.views.statistic_views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.components.ChartComponent;
import z13.rentivo.service.DataService;
import z13.rentivo.views.StatsSelectView;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PageTitle("Cars statistics")
@Route(value = "/stats/cars", layout = StatsSelectView.class)
public class CarStatsView extends VerticalLayout {
    DataService dataService;
    Map<String, Object> stats = new HashMap<>();

    ChartComponent availabilityChart = new ChartComponent();
    ChartComponent fuelTypeChart = new ChartComponent();
    ChartComponent segmentChart = new ChartComponent();
    ChartComponent productionYearChart = new ChartComponent();


    public CarStatsView(DataService dataService){
        this.dataService = dataService;
        initAvailabilityChart();
        initFuelTypeChart();
        initSegmentChart();
        initProductionYearChart();
    }

    private void initAvailabilityChart() {
        Long availableCount = dataService.countAvailableCars();
        Long notAvailableCount = dataService.countNotAvailableCars();
        availabilityChart.setTitle("Cars availability now");
        availabilityChart.setChartType(ChartComponent.ChartType.PIE);
        ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset()};

        datasets[0].data = new double[]{availableCount, notAvailableCount};
        datasets[0].background = new ChartComponent.Color(255, 99, 132, 0.2);
        availabilityChart.setDatasets(datasets);
        availabilityChart.setLabels(new String[]{"Available", "Not available"});
        add(availabilityChart);
    }

    private void initFuelTypeChart(){
        Map<String, Long> data = dataService.countCarsForFuelTypes();
        List<String> labels = data.keySet().stream().toList();
        double[] values = data.values().stream().mapToDouble(Double::valueOf).toArray();


        fuelTypeChart.setTitle("Fuel types");
        fuelTypeChart.setChartType(ChartComponent.ChartType.PIE);
        ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset()};
        datasets[0].data = values;
        fuelTypeChart.setDatasets(datasets);
        fuelTypeChart.setLabels(labels.toArray(new String[0]));
        add(fuelTypeChart);
    }

    private void initSegmentChart(){
        Map<String, Long> data = dataService.countCarsForSegments();
        List<String> labels = data.keySet().stream().toList();
        double[] values = data.values().stream().mapToDouble(Double::valueOf).toArray();
        segmentChart.setTitle("Segments");
        segmentChart.setChartType(ChartComponent.ChartType.PIE);
        ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset()};
        datasets[0].data = values;
        segmentChart.setDatasets(datasets);
        segmentChart.setLabels(labels.toArray(new String[0]));
        add(segmentChart);

    }

    private void initProductionYearChart(){
        Map<String, Long> data = dataService.countCarsForProductionYear();
        List<String> labels = data.keySet().stream().toList();
        double[] values = data.values().stream().mapToDouble(Double::valueOf).toArray();

        productionYearChart.setTitle("Production years");
        productionYearChart.setChartType(ChartComponent.ChartType.BAR);
        ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset()};
        datasets[0].data = values;
        productionYearChart.setDatasets(datasets);
        productionYearChart.setLabels(labels.toArray(new String[0]));
        productionYearChart.setMinHeight(300, Unit.PIXELS);
        add(productionYearChart);
    }
}
