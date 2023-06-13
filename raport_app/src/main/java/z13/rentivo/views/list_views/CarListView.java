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

import z13.rentivo.entities.Car;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.MainLayout;

import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all cars")
@Route(value = "/data/cars", layout = DataSelectView.class)
public class CarListView extends VerticalLayout {
    private final DataService dataService;
    CarFilter carFilter;
    Grid<Car> grid = new Grid<>(Car.class, false);

    @Autowired
    public CarListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(carFilter), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("cars-grid");
        grid.setSizeFull();

        grid.addColumn(Car::getCarId).setHeader("ID").setSortable(true);
        grid.addColumn(Car::getBrand).setHeader("Brand").setSortable(true);
        grid.addColumn(Car::getModel).setHeader("Model").setSortable(true);
        grid.addColumn(Car::getProductionYear).setHeader("Production Year").setSortable(true);
        grid.addColumn(Car::getFuelType).setHeader("Fuel Type").setSortable(true);
        grid.addColumn(Car::getIsAvailableForRent).setHeader("Availability").setSortable(true);

        List<Car> listOfCars = dataService.getAllCars();
        GridListDataView<Car> dataView = grid.setItems(listOfCars);

        carFilter = new CarFilter(dataView);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private static Component createFilter(String labelText,
                                          Consumer<String> filterChangeConsumer) {
        TextField textField = new TextField();
        textField.setPlaceholder("Filter the results...");;
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private static class CarFilter {
        private final GridListDataView<Car> dataView;
        private String input;

        public CarFilter(GridListDataView<Car> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setlInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }
        public boolean test(Car car) {
            boolean matchesBrand = matches(car.getBrand(), input);
            boolean matchesModel = matches(car.getModel(), input);
            boolean matchesFuelType = matches(car.getFuelType(), input);
            return matchesBrand || matchesModel || matchesFuelType;
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

    private HorizontalLayout getToolbar(CarFilter carFilter) {
        Component filterText = createFilter("Filter by name...", carFilter::setlInput);

//        Button addAnimalButton = new Button("Add car");
//        addAnimalButton.addClickListener(click ->{
//            addAnimalButton.getUI().ifPresent(ui ->
//                    ui.navigate(CarFormView.class));
//
//            Notification.show("Switching tab to animal form.");
//        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText);


        toolbar.addClassName("toolbar");
        return toolbar;
    }

}