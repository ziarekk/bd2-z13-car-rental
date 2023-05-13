package z13.rentivo.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Car;
import z13.rentivo.service.DataService;
import z13.rentivo.views.MainLayout;


import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all cars")
@Route(value = "/carsList", layout = MainLayout.class)
public class CarsListView extends VerticalLayout {
    private final DataService dataService;
    Grid<Car> grid = new Grid<>(Car.class, false);


    @Autowired
    public CarsListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("animals-grid");
        grid.setSizeFull();

        grid.addColumn(Car::getCarId).setHeader("ID").setSortable(true);
        grid.addColumn(Car::getBrand).setHeader("Brand").setSortable(true);
        grid.addColumn(Car::getModel).setHeader("Model").setSortable(true);
        grid.addColumn(Car::getProductionYear).setHeader("Production Year").setSortable(true);
        grid.addColumn(Car::getFuelType).setHeader("Fuel Type").setSortable(true);
        grid.addColumn(Car::getIsAvailableForRent).setHeader("Availability").setSortable(true);

        List<Car> listOfCars = dataService.getAllCars();
        GridListDataView<Car> dataView = grid.setItems(listOfCars);

        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(car -> {
                    String searchTerm = searchField.getValue().trim();

                    if (searchTerm.isEmpty())
                        return true;

                    boolean matchesBrand = matchesTerm(car.getBrand(),
                            searchTerm);
                    boolean matchesModel = matchesTerm(car.getModel(), searchTerm);

                    return matchesBrand || matchesModel;
                });
        
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

}