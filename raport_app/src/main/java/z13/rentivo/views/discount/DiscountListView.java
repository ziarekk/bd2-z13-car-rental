package z13.rentivo.views.discount;

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

import z13.rentivo.entities.Discount;
import z13.rentivo.service.DataService;
import z13.rentivo.views.MainLayout;

import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all discounts")
@Route(value = "/discounts", layout = MainLayout.class)
public class DiscountListView extends VerticalLayout {
    private final DataService dataService;
    DiscountFilter discountFilter;
    Grid<Discount> grid = new Grid<>(Discount.class, false);

    @Autowired
    public DiscountListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(discountFilter), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("discount-grid");
        grid.setSizeFull();

        grid.addColumn(Discount::getDiscountId).setHeader("ID").setSortable(true);
        grid.addColumn(Discount::getDescription).setHeader("Description").setSortable(true);
        grid.addColumn(Discount::getPercent).setHeader("Percent").setSortable(true);
        grid.addColumn(Discount::getRental).setHeader("Rental").setSortable(true);
        grid.addColumn(Discount::getClass).setHeader("Class").setSortable(true);

        List<Discount> listOfDiscounts = dataService.getAllDiscounts();
        GridListDataView<Discount> dataView = grid.setItems(listOfDiscounts);

        discountFilter = new DiscountFilter(dataView);

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

    private static class DiscountFilter {
        private final GridListDataView<Discount> dataView;
        private String input;

        public DiscountFilter(GridListDataView<Discount> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setlInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }
        public boolean test(Discount discount) {
            return matches(discount.getDescription(), input);
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

    private HorizontalLayout getToolbar(DiscountFilter discountFilter) {
        Component filterText = createFilter("Filter by desc...", discountFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

}