package z13.rentivo.views;


import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H1;

import com.vaadin.flow.router.RouteAlias;
import z13.rentivo.components.ChartComponent;

@Route(value = "hello", layout = MainLayout.class)
@PageTitle("Welcome to Rentivo")
@RouteAlias(value = "", layout = MainLayout.class)
public class WelcomeView extends VerticalLayout {
    private H1 title;

    public WelcomeView() {
        setId("hello-world-view");

        H1 title = new H1("Welcome to Rentivo Admin App");

        add(title);

        ChartComponent.Dataset[] datasets = new ChartComponent.Dataset[]{new ChartComponent.Dataset(), new ChartComponent.Dataset()};
        datasets[0].label = "Chuj";
        datasets[1].label = "Dupa";
        datasets[1].data = new double[]{60, 55, 45, 25, 15, 25};
        datasets[1].background = new ChartComponent.Color(255, 99, 132, 0.2);
        datasets[1].border = new ChartComponent.Color(255, 99, 132, 1.0);

        ChartComponent chart = new ChartComponent();
        chart.setTitle("Test chart");
        chart.setDatasets(datasets);
        chart.setChartType(ChartComponent.ChartType.LINE);

        add(chart);
    }

}
