package z13.rentivo.views.car;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import z13.rentivo.entities.Car;
import z13.rentivo.service.DataService;
import z13.rentivo.views.MainLayout;

@PageTitle("Car details")
@Route(value = "/cars/:car_id", layout = MainLayout.class)
public class CarDetailView extends VerticalLayout {
    private final DataService dataService;
    private H1 title = new H1("Chuj");
    private final Grid<Car> grid = new Grid<>(Car.class);

    @Autowired
    public CarDetailView(DataService dataService) {
        this.dataService = dataService;
        add(title);
    }

}
