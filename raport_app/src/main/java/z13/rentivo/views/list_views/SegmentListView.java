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
import z13.rentivo.entities.Segment;

import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.MainLayout;

import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all segments")
@Route(value = "/data/segments", layout = DataSelectView.class)
public class SegmentListView extends VerticalLayout {
    private final DataService dataService;
    Grid<Segment> grid = new Grid<>(Segment.class, false);
    SegmentFilter segmentFilter;

    @Autowired
    public SegmentListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(segmentFilter), grid);

    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("segment-grid");
        grid.setSizeFull();

        grid.addColumn(Segment::getSegmentId).setHeader("ID").setSortable(true);
        grid.addColumn(Segment::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Segment::getHourRate).setHeader("Hour rate").setSortable(true);
        grid.addColumn(Segment::getKmRate).setHeader("KM rate").setSortable(true);
        grid.addColumn(Segment::getRentalFee).setHeader("Rental fee").setSortable(true);

        List<Segment> listOfSegments = dataService.getAllSegments();
        GridListDataView<Segment> dataView = grid.setItems(listOfSegments);
        segmentFilter = new SegmentFilter(dataView);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private static class SegmentFilter {
        private final GridListDataView<Segment> dataView;
        private String input;

        public SegmentFilter(GridListDataView<Segment> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setlInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }
        public boolean test(Segment segment) {
            boolean matchesName = matches(segment.getName(), input);
            return matchesName;
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
    private HorizontalLayout getToolbar(SegmentFilter segmentFilter) {
        Component filterText = createFilter("Filter by name...", segmentFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

}
