package z13.rentivo.views.list_views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.LocationHistory;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.MainLayout;

import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all locations")
@Route(value = "/data/locations", layout = DataSelectView.class)
public class LocationListView extends VerticalLayout {
    private final DataService dataService;
    //LocationFilter locationFilter;
    Grid<LocationHistory> grid = new Grid<>(LocationHistory.class, false);

    @Autowired
    public LocationListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("location-grid");
        grid.setSizeFull();

        grid.addColumn(LocationHistory::getLocationId).setHeader("ID").setSortable(true);
        grid.addColumn(locationHistory -> locationHistory.getCar().getCarId()).setHeader("Car ID").setSortable(true);
        grid.addColumn(LocationHistory::getLatitude).setHeader("Latitude").setSortable(true);
        grid.addColumn(LocationHistory::getLongitude).setHeader("Longitude").setSortable(true);
        grid.addColumn(LocationHistory::getRegistrationTimestamp).setHeader("Registration Timestamp").setSortable(true);

        List<LocationHistory> listOfLocations = dataService.getAllLocationHistories();
        grid.setItems(listOfLocations);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addClassName("toolbar");
        return toolbar;
    }

}