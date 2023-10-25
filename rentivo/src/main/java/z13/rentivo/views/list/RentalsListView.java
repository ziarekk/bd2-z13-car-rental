package z13.rentivo.views.list;

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
import z13.rentivo.views.MainLayout;


import java.util.List;
import java.util.function.Consumer;

import javax.annotation.security.RolesAllowed;
@RolesAllowed("ROLE_ADMIN")
@PageTitle("List of all rentals")
@Route(value = "/rentalsList", layout = MainLayout.class)
public class RentalsListView extends VerticalLayout {
    private final DataService dataService;

    private final TextField textField;
    private RentalFilter rentalFilter;

    Grid<Rental> grid = new Grid<>(Rental.class, false);

    @Autowired
    public RentalsListView(DataService dataService) {
        this.dataService = dataService;
        textField = new TextField();

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(dataService), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("rentals-grid");
        grid.setSizeFull();


        grid.addColumn(Rental::getRentalId).setHeader("Rental ID").setSortable(true);
        grid.addColumn(Rental -> Rental.getCar().getBrand()).setHeader("Car Brand").setSortable(true);
        grid.addColumn(Rental -> Rental.getCar().getModel()).setHeader("Car Model").setSortable(true);
        grid.addColumn(Rental -> Rental.getCar().getCarId()).setHeader("Car ID").setSortable(true);
        grid.addColumn(Rental -> Rental.getClient().getName()).setHeader("Client Name").setSortable(true);
        grid.addColumn(Rental -> Rental.getClient().getSurname()).setHeader("Client Surname").setSortable(true);
        grid.addColumn(Rental -> Rental.getClient().getClientId()).setHeader("Client ID").setSortable(true);
        grid.addColumn(Rental -> Rental.getRentalStart().getStartTime()).setHeader("Start Time").setSortable(true);
        grid.addColumn(Rental -> Rental.getRentalEnd().getEndTime()).setHeader("End Time").setSortable(true);


        List<Rental> listOfRentals = dataService.getAllRentals();
        GridListDataView<Rental> dataView = grid.setItems(listOfRentals);

        rentalFilter = new RentalFilter(dataView);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }


    private static class RentalFilter {
        private final GridListDataView<Rental> dataView;
        private String nameInput;

        public RentalFilter(GridListDataView<Rental> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);

        }
        public void setlInput(String nameInput) {
            this.nameInput = nameInput;
            this.dataView.refreshAll();
        }
        public boolean test(Rental rental) {
            boolean matchesModel = matches(rental.getCar().getModel(), nameInput);
            boolean matchesBrand = matches(rental.getCar().getBrand(), nameInput);
            boolean matchesName = matches(rental.getClient().getName(), nameInput);
            boolean matchesSurname = matches(rental.getClient().getSurname(), nameInput);
            return matchesName || matchesSurname || matchesModel || matchesBrand;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

    private static Component createRentalFilter(String labelText,
                                                TextField textField,
                                                Consumer<String> filterChangeConsumer) {

        textField.setPlaceholder(labelText);
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

    private HorizontalLayout getToolbar(DataService dataService) {

        Component filterText = createRentalFilter("Search the rentals...", textField, rentalFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }



}