package z13.rentivo.views.statistic_views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import z13.rentivo.components.MapComponent;
import z13.rentivo.entities.Car;
import z13.rentivo.entities.LocationHistory;
import z13.rentivo.service.DataService;
import z13.rentivo.views.DataSelectView;
import z13.rentivo.views.StatsSelectView;

import java.util.List;

@PageTitle("Map")
@Route(value = "/stats/map", layout = StatsSelectView.class)
public class MapStatsView extends VerticalLayout {
    DataService dataService;

    public MapStatsView(DataService dataService){
        this.dataService = dataService;

        List<Car> cars = dataService.getAllCars();

        MapComponent.Dataset[] datasets = cars.stream().map(car -> {
            List<LocationHistory> locations = dataService.getLocationHistoriesByCarID(car.getCarId());

            MapComponent.Location[] data = locations.stream().map(
                    l -> new MapComponent.Location(l.getLatitude(), l.getLongitude())
            ).toArray(MapComponent.Location[]::new);

            MapComponent.Dataset dataset = new MapComponent.Dataset();

            dataset.data = data;
            dataset.label = car.getCarId().toString();
            dataset.border = getColor();
            return dataset;
        }).filter(dataset -> dataset.data.length > 0).toArray(MapComponent.Dataset[]::new);

        MapComponent map = new MapComponent();
        map.setDatasets(datasets);
        map.setTitle("Location History");
        map.setMinHeight(600, Unit.PIXELS);

        add(map);
    }

    int i = -1;
    MapComponent.Color getColor() {
        i++;
        MapComponent.Color[] colors = new MapComponent.Color[]{
                new MapComponent.Color(255, 128, 128, 1.0),
                new MapComponent.Color(128, 255, 128, 1.0),
                new MapComponent.Color(128, 128, 255, 1.0),
                new MapComponent.Color(255, 255, 128, 1.0),
                new MapComponent.Color(255, 128, 255, 1.0),
                new MapComponent.Color(128, 255, 255, 1.0),
        };

        return colors[i % colors.length];
    }
}
