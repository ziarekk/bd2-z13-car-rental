package z13.rentivo.views.licences;

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
import z13.rentivo.entities.DriverLicence;

import z13.rentivo.entities.Rental;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.MainLayout;
import z13.rentivo.views.rental.RentalListView;

import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all driverLicences")
@Route(value = "/data/driverLicences", layout = DataSelectView.class)
public class DriverLicenceListView extends VerticalLayout {
    private final DataService dataService;
    Grid<DriverLicence> grid = new Grid<>(DriverLicence.class, false);
    DriverLicenceFilter driverLicenceFilter;

    @Autowired
    public DriverLicenceListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(driverLicenceFilter), grid);

    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("driverLicence-grid");
        grid.setSizeFull();

        grid.addColumn(DriverLicence::getLicenceId).setHeader("ID").setSortable(true);
        grid.addColumn(DriverLicence::getCategory).setHeader("Category").setSortable(true);
        grid.addColumn(DriverLicence::getNumber).setHeader("Number").setSortable(true);
        grid.addColumn(DriverLicence::getDateObtained).setHeader("Date obtained").setSortable(true);
        grid.addColumn(DriverLicence::getExpirationDate).setHeader("Expiration date").setSortable(true);
        grid.addColumn(DriverLicence::getClient).setHeader("Client").setSortable(true);




        List<DriverLicence> listOfDriverLicences = dataService.getAllDriverLicences();
        GridListDataView<DriverLicence> dataView = grid.setItems(listOfDriverLicences);
        driverLicenceFilter = new DriverLicenceFilter(dataView);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private static class DriverLicenceFilter {
        private final GridListDataView<DriverLicence> dataView;
        private String input;

        public DriverLicenceFilter(GridListDataView<DriverLicence> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setlInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }
        public boolean test(DriverLicence rental) {
            return true;
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
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
    private HorizontalLayout getToolbar(DriverLicenceFilter driverLicenceFilter) {
        Component filterText = createFilter("Filter by name...", driverLicenceFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

}
