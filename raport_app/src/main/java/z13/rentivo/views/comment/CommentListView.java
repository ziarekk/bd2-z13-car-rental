package z13.rentivo.views.comment;

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
import z13.rentivo.entities.Comment;

import z13.rentivo.entities.Rental;
import z13.rentivo.service.DataService;
import z13.rentivo.views.MainLayout;
import z13.rentivo.views.rental.RentalListView;

import java.util.List;
import java.util.function.Consumer;


@PageTitle("List of all comments")
@Route(value = "/comments", layout = MainLayout.class)
public class CommentListView extends VerticalLayout {
    private final DataService dataService;
    Grid<Comment> grid = new Grid<>(Comment.class, false);
    CommentFilter commentFilter;

    @Autowired
    public CommentListView(DataService dataService) {
        this.dataService = dataService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(commentFilter), grid);

    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("comment-grid");
        grid.setSizeFull();

        grid.addColumn(Comment::getCommentId).setHeader("ID").setSortable(true);
        grid.addColumn(Comment::getContent).setHeader("Content").setSortable(true);
        grid.addColumn(Comment::getRental).setHeader("Rental").setSortable(true);

        List<Comment> listOfComments = dataService.getAllComments();
        GridListDataView<Comment> dataView = grid.setItems(listOfComments);
        commentFilter = new CommentFilter(dataView);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private static class CommentFilter {
        private final GridListDataView<Comment> dataView;
        private String input;

        public CommentFilter(GridListDataView<Comment> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }
        public void setlInput(String input) {
            this.input = input;
            this.dataView.refreshAll();
        }
        public boolean test(Comment rental) {
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
    private HorizontalLayout getToolbar(CommentFilter commentFilter) {
        Component filterText = createFilter("Filter by name...", commentFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

}
