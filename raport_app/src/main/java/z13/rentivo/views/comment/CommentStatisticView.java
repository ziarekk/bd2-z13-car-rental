package z13.rentivo.views.comment;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;

import java.util.HashMap;
import java.util.Map;

@PageTitle("Comments statistics")
@Route(value = "/comments/stats", layout = DataSelectView.class)
public class CommentStatisticView extends VerticalLayout {
    DataService dataService;
    Map<String, Object> stats = new HashMap<>();
    public CommentStatisticView(DataService dataService){
        this.dataService = dataService;
        getStats();
        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            HorizontalLayout pair = new HorizontalLayout();
            H3 keyLabel = new H3(entry.getKey());
            H3 valueLabel = new H3(entry.getValue().toString());
            pair.add(keyLabel, valueLabel);
            add(pair);
        }
    }

    private void getStats(){
        stats.put("Comments number:", dataService.countComments());
    }
}
