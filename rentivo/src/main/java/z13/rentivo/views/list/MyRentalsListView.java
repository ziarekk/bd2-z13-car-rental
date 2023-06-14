package z13.rentivo.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import z13.rentivo.entities.Car;
import z13.rentivo.entities.Client;
import z13.rentivo.entities.Rental;
import z13.rentivo.entities.User;
import z13.rentivo.service.DataService;
import z13.rentivo.service.ReturnService;
import z13.rentivo.views.MainLayout;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@PermitAll
@PageTitle("List of your rentals")
@Route(value = "/myRentalsList", layout = MainLayout.class)
public class MyRentalsListView extends VerticalLayout {
    private final DataService dataService;
    private final ReturnService returnService;

    private final TextField textField;
    private RentalFilter rentalFilter;

    Dialog detailsDialog = new Dialog();
    Grid<Rental> grid = new Grid<>(Rental.class, false);

    @Autowired
    public MyRentalsListView(DataService dataService, ReturnService returnService) {
        this.dataService = dataService;
        this.returnService = returnService;
        textField = new TextField();

        addClassName("list-view");
        setSizeFull();
        configureDialog();
        configureGrid();

        add(getToolbar(dataService), grid);
    }

    @Transactional
    void configureGrid() {
        grid.addClassNames("my-rentals-grid");
        grid.setSizeFull();



        grid.addColumn(Rental -> Rental.getCar().getBrand()).setHeader("Car Brand").setSortable(true);
        grid.addColumn(Rental -> Rental.getCar().getModel()).setHeader("Car Model").setSortable(true);
        grid.addColumn(Rental -> Rental.getCar().getCarId()).setHeader("Car ID").setSortable(true);
        grid.addColumn(this::getStartTime).setHeader("Start Time").setSortable(true);
        grid.addColumn(this::getEndTime).setHeader("End Time").setSortable(true);
        grid.addColumn(new ComponentRenderer<>(rental -> {
            return new Button("Show details", e -> {
                updateDetailsDialog(rental);
                detailsDialog.open();
            } );

        })).setHeader("Show details");
        grid.addComponentColumn(Rental -> createStatusIcon(getPaymentStatus(Rental)))
                .setTooltipGenerator(this::getPaymentStatus)
                .setHeader("Payment Status").setTextAlign(ColumnTextAlign.CENTER);;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Rental> listOfRentals = dataService.getRentalsByUser(authentication.getName());

        GridListDataView<Rental> dataView = grid.setItems(listOfRentals);

        rentalFilter = new RentalFilter(dataView);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }


    private static class RentalFilter {
        private final GridListDataView<Rental> dataView;
        private String nameInput;

        public RentalFilter(GridListDataView<Rental> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);

        }
        public void setlInput(String nameInput) {
            this.nameInput = nameInput;
            this.dataView.refreshAll();
        }
        public boolean test(Rental rental) {
            boolean matchesModel = matches(rental.getCar().getModel(), nameInput);
            boolean matchesBrand = matches(rental.getCar().getBrand(), nameInput);
            boolean matchesName = matches(rental.getClient().getName(), nameInput);
            boolean matchesSurname = matches(rental.getClient().getSurname(), nameInput);
            return matchesName || matchesSurname || matchesModel || matchesBrand;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

    private static Component createRentalFilter(String labelText,
                                                TextField textField,
                                                Consumer<String> filterChangeConsumer) {

        textField.setPlaceholder(labelText);
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

    private HorizontalLayout getToolbar(DataService dataService) {

        Component filterText = createRentalFilter("Search the rentals...", textField, rentalFilter::setlInput);

        HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private String getEndTime(Rental rental){

        if(rental.getRentalEnd() == null){
            return "Not ended";
        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(rental.getRentalEnd().getEndTime().getTime());
            return  strDate;
        }
    }

    private String getStartTime(Rental rental){

        if(rental.getRentalStart() == null){
            return "Not started";
        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(rental.getRentalStart().getStartTime());
            return  strDate;
        }
    }

    void configureDialog() {
        detailsDialog.setHeaderTitle("Rent details");

        detailsDialog.setMinWidth("680px");

        detailsDialog.getFooter().add(new Button("Close", e -> detailsDialog.close()));
    }

    private void updateDetailsDialog(Rental rental) {
        detailsDialog.removeAll();
        detailsDialog.getFooter().removeAll();

        detailsDialog.setHeaderTitle("Details of the rental: " + rental.getRentalId());
        detailsDialog.add(getDetailsLayout(rental));

        if (rental.getRentalEnd() == null ){
            Button endButton = getEndButton(rental);
            detailsDialog.getFooter().add(endButton);
        } else if (!getPaymentStatus(rental).equals("przyjeta")){
            Button payButton = getPayButton(rental);
            detailsDialog.getFooter().add(payButton);
        }

        detailsDialog.getFooter().add(new Button("Close", e -> detailsDialog.close()));

    }

    private VerticalLayout getDetailsLayout(Rental rental) {
        VerticalLayout verticalDetailsLayout = new VerticalLayout();

        Accordion accordion = new Accordion();
        Car car = rental.getCar();
        Span brand = new Span(car.getBrand() + " " + car.getModel());
        Span yearOP = new Span("Year of production: " + car.getProductionYear());
        Span trans = new Span("Transmission: " + car.getTransmission());
        Span fuelCap= new Span("Fuel Type: "+ car.getFuelType() + ",  Fuel Capacity: " + car.getFuelCapacity());
        Span seats = new Span("Seats: " + car.getSeats());
        Span mileage = new Span("Mileage: " + car.getMileage());
        Span regNumber = new Span("Registration number: " + car.getRegistrationNumber());

        VerticalLayout carInfo = new VerticalLayout(brand,
                yearOP, trans, regNumber);
        carInfo.setSpacing(false);
        carInfo.setPadding(false);
        accordion.add("Car information", carInfo);

        Span status = new Span(getStatus(rental));
        Span timeS = new Span("Time started: " + getStartTime(rental));
        Span timeE = new Span("Time ended: " + getEndTime(rental));

        VerticalLayout paymentInfo = new VerticalLayout();
        paymentInfo.setSpacing(false);
        paymentInfo.setPadding(false);

        paymentInfo.add(status, timeS, timeE);

        if (rental.getRentalEnd() != null){
        Span amount = new Span("Amount to pay: $" + dataService.getPaymentAmount(rental));
        Span bill = new Span("Payment status: " + dataService.getPaymentStatus(rental) );
        paymentInfo.add(amount, bill);
        }

        accordion.add("Rental information", paymentInfo);

        verticalDetailsLayout.add(accordion);

        return verticalDetailsLayout;
    }

    private Button getEndButton(Rental rental){

        Button endRentalButton = new Button("End the rental");
        endRentalButton.addClickListener(
                e -> {
                    boolean result = returnService.returnCar(rental.getClient().getClientId().intValue(), rental.getCar().getCarId().intValue());
                    detailsDialog.close();
                    if (result){
                        showMessage("Your rent is ended." ,
                                "You just finished a rent of  " + rental.getCar().getBrand() + " " + rental.getCar().getModel()+ ". Thank you!", 2);
                    } else{
                        showMessage("Whoops, something went wrong.." ,
                                "There was an error while closing your rent. Please, try again later.", 1);
                    }
                }
        );
        return endRentalButton;
    }

    private Button getPayButton(Rental rental){

        Button payRentalButton = new Button("Pay for the rental");
        payRentalButton.addClickListener(
                e -> {
                    boolean result = dataService.payForRental(rental);
                    detailsDialog.close();
                    if (result){
                        showMessage("Payment was succesful." ,
                                "You just payed $" + dataService.getPaymentAmount(rental) + " for the rental. Thank you!", 1);
                    } else{
                        showMessage("Whoops, something went wrong.." ,
                                "There was an error while processing your payment. Please, try again later.", 1);
                    }
                }
        );
        return payRentalButton;
    }
    private void showMessage( String title, String message, Integer int1){
        Dialog messageBox = new Dialog();
        messageBox.setHeaderTitle(title);

        messageBox.setMinWidth("680px");
        H2 msgH2 = new H2(message);

        VerticalLayout layoutMSG = new VerticalLayout(msgH2);

        messageBox.add(layoutMSG);
        if (int1 == 2){
            messageBox.getFooter().add(new Button("Close", e -> {messageBox.close(); UI.getCurrent().getPage().reload();}));
        } else{
            messageBox.getFooter().add(new Button("Close", e -> messageBox.close()));
        }

        messageBox.open();
        textField.clear();
        rentalFilter.setlInput("");
    }

    private String getStatus(Rental rental){
        if (rental.getRentalEnd() == null){
            return "Rental is still active.";
        } else{
            return "Rental is over.";
        }
    }
    private String getPaymentStatus(Rental rental){
        if (rental.getRentalEnd() == null){
            return "Rental is still active";
        } else {
            return dataService.getPaymentStatus(rental);
        }
    }

    private Icon createStatusIcon(String status) {
        Icon icon;
        if (status.equals("przyjeta")) {
            icon = VaadinIcon.CHECK.create();
            icon.getElement().getThemeList().add("badge success");
        } else if (status.equals("odrzucona")) {
            icon = VaadinIcon.CLOSE_SMALL.create();
            icon.getElement().getThemeList().add("badge error");
        } else if (status.equals("oczekujaca")){
            icon = VaadinIcon.CLOCK.create();
            icon.getElement().getThemeList().add("badge");
        } else {
            icon = VaadinIcon.INFO_CIRCLE_O.create();
            icon.getElement().getThemeList().add("badge contrast");
        }

        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }
}
