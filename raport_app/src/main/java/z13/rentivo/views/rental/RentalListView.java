package z13.rentivo.views.rental;

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

import z13.rentivo.entities.Rental;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.MainLayout;

import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all rentals")
@Route(value = "/data/rentals", layout = DataSelectView.class)
public class RentalListView extends VerticalLayout {
    private final DataService dataService;
    RentalFilter rentalFilter;
    Grid<Rental> grid = new Grid<>(Rental.class, false);

    @Autowired
    public RentalListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(rentalFilter), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("rental-grid");
        grid.setSizeFull();

        grid.addColumn(Rental::getRentalId).setHeader("ID").setSortable(true);
        grid.addColumn(rental -> rental.getRentalStart().getStartTime()).setHeader("Start Date").setSortable(true);
        grid.addColumn(rental -> rental.getRentalEnd() == null ? "" : rental.getRentalEnd().getEndTime()).setHeader("End Date").setSortable(true);
        grid.addColumn(rental -> rental.getRentalEnd() == null ? "" : rental.getRentalEnd().getEndMileage() -
                rental.getRentalStart().getStartMileage()).setHeader("Mileage").setSortable(true);
//        grid.addColumn(Rental::getCar).setHeader("Car").setSortable(true);
//        grid.addColumn(Rental::getClient).setHeader("Client").setSortable(true);

        List<Rental> listOfRentals = dataService.getAllRentals();
        GridListDataView<Rental> dataView = grid.setItems(listOfRentals);

        rentalFilter = new RentalFilter(dataView);

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

    private static class RentalFilter {
        private final GridListDataView<Rental> dataView;
        private String input;

        public RentalFilter(GridListDataView<Rental> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setlInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }
        public boolean test(Rental rental) {
            boolean matchesStart = matches(rental.getRentalStart().toString(), input);
//            boolean matchesEnd = matches(rental.getRentalEnd().toString(), input);
            return matchesStart;
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

    private HorizontalLayout getToolbar(RentalFilter rentalFilter) {
        Component filterText = createFilter("Filter by name...", rentalFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

}