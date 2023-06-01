package z13.rentivo.views.penalty;

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
import z13.rentivo.entities.Penalty;
import z13.rentivo.service.DataService;
import z13.rentivo.views.MainLayout;

import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all penalties")
@Route(value = "/penalties", layout = MainLayout.class)
public class PenaltyListView extends VerticalLayout {
    private final DataService dataService;
    PenaltyFilter penaltyFilter;
    Grid<Penalty> grid = new Grid<>(Penalty.class, false);

    @Autowired
    public PenaltyListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(penaltyFilter), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("penalty-grid");
        grid.setSizeFull();

        grid.addColumn(Penalty::getPenaltyId).setHeader("ID").setSortable(true);
        grid.addColumn(Penalty::getDescription).setHeader("Description").setSortable(true);
        grid.addColumn(Penalty::getAmount).setHeader("Amount").setSortable(true);
        grid.addColumn(Penalty::getRental).setHeader("Rental").setSortable(true);
        grid.addColumn(Penalty::getClass).setHeader("Class").setSortable(true);

        List<Penalty> listOfPenalties = dataService.getAllPenalties();
        GridListDataView<Penalty> dataView = grid.setItems(listOfPenalties);

        penaltyFilter = new PenaltyFilter(dataView);

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

    private static class PenaltyFilter {
        private final GridListDataView<Penalty> dataView;
        private String input;

        public PenaltyFilter(GridListDataView<Penalty> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setlInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }
        public boolean test(Penalty penalty) {
            return matches(penalty.getDescription(), input);
        }
        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

    private HorizontalLayout getToolbar(PenaltyFilter penaltyFilter) {
        Component filterText = createFilter("Filter by desc...", penaltyFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

}