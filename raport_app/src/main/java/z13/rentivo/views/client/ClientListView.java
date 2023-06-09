package z13.rentivo.views.client;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Client;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.MainLayout;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@PageTitle("List of all clients")
@Route(value = "/data/clients", layout = DataSelectView.class)
public class ClientListView extends VerticalLayout {
    private final DataService dataService;
    ClientFilter clientFilter;
    Grid<Client> grid = new Grid<>(Client.class, false);

    @Autowired
    public ClientListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(clientFilter), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("client-grid");
        grid.setSizeFull();

        grid.addColumn(Client::getClientId).setHeader("ID").setSortable(true);
        grid.addColumn(Client::getBirthDate).setHeader("Birth date").setSortable(true);
        grid.addColumn(Client::getGender).setHeader("Gender").setSortable(true);
        grid.addColumn(Client::getIsVerified).setHeader("Verified?").setSortable(true);
        grid.addColumn(Client::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Client::getSurname).setHeader("Surname").setSortable(true);
        grid.addColumn(Client::getPhoneNumber).setHeader("Phone number").setSortable(true);

        List<Client> listOfClients = dataService.getAllClients();
        GridListDataView<Client> dataView = grid.setItems(listOfClients);

        clientFilter = new ClientFilter(dataView);

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

    private static class ClientFilter {
        private final GridListDataView<Client> dataView;
        private String input;

        public ClientFilter(GridListDataView<Client> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setlInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }
        public boolean test(Client client) {
            boolean matchesName = matches(client.getName(), input);
            boolean matchesSurname = matches(client.getSurname(), input);
            return matchesName || matchesSurname;
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

    private HorizontalLayout getToolbar(ClientFilter clientFilter) {
        Component filterText = createFilter("Filter by name...", clientFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

}