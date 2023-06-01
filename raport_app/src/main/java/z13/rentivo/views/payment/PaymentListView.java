package z13.rentivo.views.payment;

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

import z13.rentivo.entities.Payment;
import z13.rentivo.service.DataService;
import z13.rentivo.views.MainLayout;

import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all payments")
@Route(value = "/payments", layout = MainLayout.class)
public class PaymentListView extends VerticalLayout {
    private final DataService dataService;
    PaymentFilter paymentFilter;
    Grid<Payment> grid = new Grid<>(Payment.class, false);

    @Autowired
    public PaymentListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(paymentFilter), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("client-grid");
        grid.setSizeFull();

        grid.addColumn(Payment::getPaymentId).setHeader("ID").setSortable(true);
        grid.addColumn(Payment::getStatus).setHeader("Status").setSortable(true);
        grid.addColumn(Payment::getBill).setHeader("Bill").setSortable(true);
        grid.addColumn(Payment::getPaymentType).setHeader("Type").setSortable(true);

        List<Payment> listOfPayments = dataService.getAllPayments();
        GridListDataView<Payment> dataView = grid.setItems(listOfPayments);

        paymentFilter = new PaymentFilter(dataView);

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

    private static class PaymentFilter {
        private final GridListDataView<Payment> dataView;
        private String input;

        public PaymentFilter(GridListDataView<Payment> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setlInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }
        public boolean test(Payment payment) {
            return matches(payment.getStatus(), input);
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

    private HorizontalLayout getToolbar(PaymentFilter paymentFilter) {
        Component filterText = createFilter("Filter by status...", paymentFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

}