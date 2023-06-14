package z13.rentivo.views.statistic_views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.components.ChartComponent;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.StatsSelectView;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        {
            ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset()};;
            datasets[0].data = new double[]{
                    dataService.getPaidPaymentCount().doubleValue(),
                    dataService.getPendingCount().doubleValue(),
                    dataService.getDeclinedCount().doubleValue()
            };

            ChartComponent countPieChart = new ChartComponent();
            countPieChart.setChartType(ChartComponent.ChartType.PIE);
            countPieChart.setDatasets(datasets);
            countPieChart.setTitle("Payment Status");
            countPieChart.setLabels(new String[] {"Paid", "Pending", "Declined"});

            add(countPieChart);
        }

        {
            ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset()};;
            datasets[0].data = new double[]{
                    dataService.getPaidPaymentAmount().doubleValue(),
                    dataService.getPendingPaymentAmount().doubleValue(),
                    dataService.getDeclinedPaymentAmount().doubleValue()
            };

            ChartComponent amountPieChart = new ChartComponent();
            amountPieChart.setChartType(ChartComponent.ChartType.PIE);
            amountPieChart.setDatasets(datasets);
            amountPieChart.setTitle("Payment Amount");
            amountPieChart.setLabels(new String[] {"Paid", "Pending", "Declined"});

            add(amountPieChart);
        }

        {
            double[] paid = new double[12];
            double[] pending = new double[12];
            double[] declined = new double[12];
            String[] labels = generateMonthList();

            for(int i=0; i<12; i++) {
                paid[i] = dataService.getPaymentCountByMonth(i, "przyjeta").doubleValue();
                pending[i] = dataService.getPaymentCountByMonth(i, "oczekujaca").doubleValue();
                declined[i] = dataService.getPaymentCountByMonth(i, "odrzucona").doubleValue();
            }

            ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset(), new ChartComponent.Dataset(), new ChartComponent.Dataset()};;
            datasets[0].data = paid;
            datasets[0].label = "Paid";
            datasets[0].background = new ChartComponent.Color(255, 0, 0, 0.2);
            datasets[0].border = new ChartComponent.Color(255, 0, 0, 1.0);
            datasets[1].data = pending;
            datasets[1].label = "Pending";
            datasets[1].background = new ChartComponent.Color(0, 255, 0, 0.2);
            datasets[1].border = new ChartComponent.Color(0, 255, 0, 1.0);
            datasets[2].data = declined;
            datasets[2].label = "Declined";
            datasets[2].background = new ChartComponent.Color(0, 0, 255, 0.2);
            datasets[2].border = new ChartComponent.Color(0, 0, 255, 1.0);

            ChartComponent countBarChart = new ChartComponent();
            countBarChart.setChartType(ChartComponent.ChartType.BAR);
            countBarChart.setDatasets(datasets);
            countBarChart.setTitle("Payment Status By Month");
            countBarChart.setLabels(labels);

            add(countBarChart);
        }

        {
            double[] paid = new double[12];
            double[] pending = new double[12];
            double[] declined = new double[12];
            String[] labels = generateMonthList();

            for(int i=0; i<12; i++) {
                paid[i] = dataService.getPaymentAmountByMonth(i, "przyjeta").doubleValue();
                pending[i] = dataService.getPaymentAmountByMonth(i, "oczekujaca").doubleValue();
                declined[i] = dataService.getPaymentAmountByMonth(i, "odrzucona").doubleValue();
            }

            ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset(), new ChartComponent.Dataset(), new ChartComponent.Dataset()};;
            datasets[0].data = paid;
            datasets[0].label = "Paid";
            datasets[0].background = new ChartComponent.Color(255, 0, 0, 0.2);
            datasets[0].border = new ChartComponent.Color(255, 0, 0, 1.0);
            datasets[1].data = pending;
            datasets[1].label = "Pending";
            datasets[1].background = new ChartComponent.Color(0, 255, 0, 0.2);
            datasets[1].border = new ChartComponent.Color(0, 255, 0, 1.0);
            datasets[2].data = declined;
            datasets[2].label = "Declined";
            datasets[2].background = new ChartComponent.Color(0, 0, 255, 0.2);
            datasets[2].border = new ChartComponent.Color(0, 0, 255, 1.0);

            ChartComponent amountLineChart = new ChartComponent();
            amountLineChart.setChartType(ChartComponent.ChartType.LINE);
            amountLineChart.setDatasets(datasets);
            amountLineChart.setTitle("Payment Amount By Month");
            amountLineChart.setLabels(labels);

            add(amountLineChart);
        }
    }

    private void getStats(){
        stats.put("Sum amount:", dataService.getSumBillAmount());
        stats.put("Average amount:", dataService.getAverageBillAmount());
    }

    public static String[] generateMonthList() {
        String[] months = new String[12];

        Month currentMonth = Month.JANUARY;

        for (int i = 0; i < 12; i++) {
            months[i] = currentMonth.toString();
            currentMonth = currentMonth.plus(1);
        }

        return months;
    }
}
