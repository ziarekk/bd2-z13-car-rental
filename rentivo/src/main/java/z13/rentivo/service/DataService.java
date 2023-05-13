package z13.rentivo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import z13.rentivo.entities.Car;
import z13.rentivo.entities.Segment;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
/*
    private final CarRespoitory carRespoitory;
    private final ClientRepository clientRepository;\

    @Autowired
    public DataService(CarRespoitory carRespoitory,
                       ClientRepository clientRepository){
            this.carRepository = carRespoitory;
            this.clientRepository = clientRepository;
    }
*/

    @Autowired
    public DataService(){}

    public List<Car> getAllCars() {

        List<Car> carsList = new ArrayList<>();

        Car car1 = new Car(1l, 100, "WSI ŻABA", 2002, 20.21f, 21.37f, 0.9f , true, "petrol", 90f, "Yaris", "Toyota", 4, "Automatic", new Segment(1l,"D", 21f, 2f, 3f));
        Car car2 = new Car(2l, 2300, "WS 2137", 2008, 21.34f, 31.69f, 0.4f , false, "diesel", 65f, "Ford", "Focus", 4, "Manual", new Segment(1l,"D", 21f, 2f, 3f));

        carsList.add(car1);
        carsList.add(car2);

        return carsList;
    }

}
