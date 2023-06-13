package z13.rentivo.views.statistic_views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.StatsSelectView;

@PageTitle("Cars statistics")
@Route(value = "/stats/cars", layout = StatsSelectView.class)
public class CarStatsView extends VerticalLayout {
    DataService dataService;
    public CarStatsView(DataService dataService){
        this.dataService = dataService;
    }
}
