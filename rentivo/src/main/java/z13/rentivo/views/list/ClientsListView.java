package z13.rentivo.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Car;
import z13.rentivo.entities.Client;
import z13.rentivo.entities.Segment;
import z13.rentivo.service.DataService;
import z13.rentivo.views.MainLayout;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.security.RolesAllowed;
@RolesAllowed("ROLE_ADMIN")
@PageTitle("List of all clients")
@Route(value = "/clientsList", layout = MainLayout.class)
public class ClientsListView extends VerticalLayout {
    private final DataService dataService;

    private final TextField textField;
    private ClientFilter clientFilter;

    Grid<Client> grid = new Grid<>(Client.class, false);

    @Autowired
    public ClientsListView(DataService dataService) {
        this.dataService = dataService;
        textField = new TextField();

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(dataService), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("clients-grid");
        grid.setSizeFull();


        grid.addColumn(Client::getClientId).setHeader("ID").setSortable(true);
        grid.addColumn(Client::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Client::getSurname).setHeader("Surname").setSortable(true);
        grid.addColumn(Client::getGender).setHeader("Gender").setSortable(true);
        grid.addColumn(Client::getIsVerified).setHeader("Is Verified").setSortable(true);
        // Icon
        grid.addColumn(Client::getPhoneNumber).setHeader("Phone Number").setSortable(true);
        grid.addColumn(Client-> Client.getUser().getLogin()).setHeader("Username").setSortable(true);


        List<Client> listOfClients = dataService.getAllClients();
        GridListDataView<Client> dataView = grid.setItems(listOfClients);

        clientFilter = new ClientFilter(dataView);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }


    private static class ClientFilter {
        private final GridListDataView<Client> dataView;
        private String nameInput;

        public ClientFilter(GridListDataView<Client> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);

        }
        public void setlInput(String nameInput) {
            this.nameInput = nameInput;
            this.dataView.refreshAll();
        }
        public boolean test(Client client) {
            boolean matchesName = matches(client.getName(), nameInput);
            boolean matchesSurname = matches(client.getSurname(), nameInput);
            boolean matchesLogin = matches(client.getUser().getLogin(), nameInput);
            return matchesName || matchesSurname || matchesLogin;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

    private static Component createClientFilter(String labelText,
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

        Component filterText = createClientFilter("Search the clients...", textField, clientFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }



}