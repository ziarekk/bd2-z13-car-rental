package z13.rentivo.views.car;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import z13.rentivo.entities.*;
import z13.rentivo.service.DataService;
import z13.rentivo.views.MainLayout;

@PageTitle("Edit existing car")
@Route(value = "/cars/:car_id/edit", layout = MainLayout.class)
public class CarEditView extends VerticalLayout{
    private final DataService dataService;

    private final ComboBox<Segment> segmentCB;
    private final ComboBox<Integer> seatsCB;
    private final ComboBox<String> transmissionCB;



    private final TextField brandTF;
    private final TextField modelTF;
    private final TextField fuelCapacityTF;
    private final TextField fuelTypeTF;
    private final TextField latitudeTF;
    private final TextField longitudeTF;
    private final TextField mileageTF;
    private final TextField registrationNoTF;
    private final TextField ProdYearTF;


    HorizontalLayout buttons;
    @Autowired
    public CarEditView(DataService dataService) {
        this.dataService = dataService;

        FormLayout carForm = new FormLayout();

        brandTF             = new TextField("Brand name:");
        modelTF             = new TextField("Model name:");
        fuelCapacityTF      = new TextField("Fuel capacity:");
        fuelTypeTF          = new TextField("Fuel type:");
        latitudeTF          = new TextField("Latitude:");
        longitudeTF         = new TextField("Longitude:");
        mileageTF           = new TextField("Mileage:");
        registrationNoTF    = new TextField("Registration number:");
        ProdYearTF          = new TextField("Year of production:");
        segmentCB           = prepareSegmentComboBox();
        seatsCB             = prepareSeatsComboBox();
        transmissionCB      = prepareTransmissionComboBox();
        buttons             = prepareButtons();

        carForm.add(brandTF, brandTF, modelTF, fuelCapacityTF, fuelTypeTF, latitudeTF, longitudeTF,
                mileageTF, registrationNoTF, ProdYearTF, segmentCB, seatsCB, transmissionCB, buttons);


        H1 title = new H1("Add Car");
        add(title, carForm);
        setWidth("auto");
        setMargin(true);
        setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        setAlignItems(FlexComponent.Alignment.STRETCH);
    }

    private HorizontalLayout prepareButtons() {
        Button addButton = new Button("Add");
        Button BClear = new Button("Clear");
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(addButton, BClear);

        addButton.addClickListener(click -> {
            String brand                =  brandTF.getValue();
            String model                =  modelTF.getValue();
            Float fuelCapacity          =  Float.parseFloat(fuelCapacityTF.getValue());
            String fuelType             =  fuelTypeTF.getValue();
            Float latitude              =  Float.parseFloat(latitudeTF.getValue());
            Float longitude             =  Float.parseFloat(longitudeTF.getValue());
            Integer mileage             =  Integer.parseInt(mileageTF.getValue());
            String registrationNumber   =  registrationNoTF.getValue();
            Integer productionYear      =  Integer.parseInt(ProdYearTF.getValue());
            Long segmentId             =  segmentCB.getValue().getSegmentId();
            Integer seats               =  seatsCB.getValue();
            String transmission         =  transmissionCB.getValue();
            Float fuelLevel             = 0.0f;
            Boolean isAvailableForRent  = true;

            dataService.addCar(mileage, registrationNumber, productionYear, longitude, latitude, fuelLevel,
                    isAvailableForRent, fuelType, fuelCapacity, model, brand, seats, transmission, segmentId
            );


            Notification.show("Successfully added new Animal to database!");
        });

        BClear.addClickListener(click -> {
            brandTF.clear();
            modelTF.clear();
            fuelCapacityTF.clear();
            fuelTypeTF.clear();
            latitudeTF.clear();
            longitudeTF.clear();
            mileageTF.clear();
            registrationNoTF.clear();
            ProdYearTF.clear();
            segmentCB.clear();
            seatsCB.clear();
            transmissionCB.clear();

            Notification.show("All info cleared.");
        });
        return buttons;
    }




    private ComboBox<Segment> prepareSegmentComboBox() {
        ComboBox<Segment> segmentCB = new ComboBox<>("Car Segment");
        segmentCB.setItems(dataService.getAllSegments());
        segmentCB.setItemLabelGenerator(Segment::getName);
        return segmentCB;
    }

    private ComboBox<Integer> prepareSeatsComboBox() {
        ComboBox<Integer> CBseats = new ComboBox<>("Seats in the car");
        Integer[] seats = new Integer[]{2, 3, 4, 5, 6, 7, 8, 9};
        CBseats.setItems(seats);
        return CBseats;
    }

    private ComboBox<String> prepareTransmissionComboBox() {
        ComboBox<String> CBtransmission = new ComboBox<>("Type of transmission:");
        String[] transmissions = new String[]{"Manual", "Automatic"};
        CBtransmission.setItems(transmissions);
        return CBtransmission;
    }


}

