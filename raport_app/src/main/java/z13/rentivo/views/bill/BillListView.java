package z13.rentivo.views.bill;

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
import z13.rentivo.entities.Bill;
import z13.rentivo.entities.Rental;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.MainLayout;
import z13.rentivo.views.rental.RentalListView;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Consumer;

@PageTitle("List of all bills")
@Route(value = "/bills", layout = DataSelectView.class)
public class BillListView extends VerticalLayout {
    private final DataService dataService;
    BillFilter billFilter;
    Grid<Bill> grid = new Grid<>(Bill.class, false);

    @Autowired
    public BillListView(DataService dataService) {
        this.dataService = dataService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(billFilter), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("bill-grid");
        grid.setSizeFull();

        grid.addColumn(Bill::getBillId).setHeader("ID").setSortable(true);
        grid.addColumn(Bill::getAmount).setHeader("Amount").setSortable(true);
        grid.addColumn(Bill::getDateDue).setHeader("DateDue").setSortable(true);

        List<Bill> listOfBills = dataService.getAllBills();
        GridListDataView<Bill> dataView = grid.setItems(listOfBills);
        billFilter = new BillFilter(dataView);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
    private static class BillFilter{
        private final GridListDataView<Bill> dataView;
        private String input;

        public BillFilter(GridListDataView<Bill> dataView){
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }

        public boolean test(Bill bill) {
            boolean matchesDateDue = matches(bill.getDateDue().toString(), input);
            boolean matchesAmount = matches(bill.getAmount().toString(), input);
            return matchesDateDue || matchesAmount;
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
    private HorizontalLayout getToolbar(BillListView.BillFilter billFilter) {
        Component filterText = createFilter("Filter by name...", billFilter::setInput);
        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }
}

