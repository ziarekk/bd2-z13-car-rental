package z13.rentivo.views.statistic_views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.StatsSelectView;

@PageTitle("Map")
@Route(value = "/stats/map", layout = StatsSelectView.class)
public class MapStatsView extends VerticalLayout {
    DataService dataService;

    public MapStatsView(DataService dataService){
        this.dataService = dataService;
    }
}
