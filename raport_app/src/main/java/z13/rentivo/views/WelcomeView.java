package z13.rentivo.views;


import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.RouteAlias;

@Route(value = "hello", layout = MainLayout.class)
@PageTitle("Welcome to Rentivo")
@RouteAlias(value = "", layout = MainLayout.class)
public class WelcomeView extends VerticalLayout {

    public WelcomeView() {
        setId("hello-world-view");

//        H1 title = new H1("Welcome to Rentivo Admin App");
//
//        add(title);

        ChartComponent chart = new ChartComponent();
        chart.setData(new double[]{10, 20, 30, 40, 50});

        add(chart);
    }

}
