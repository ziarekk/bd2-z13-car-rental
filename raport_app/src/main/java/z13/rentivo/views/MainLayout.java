package z13.rentivo.views;


import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

import z13.rentivo.service.DataService;
import z13.rentivo.views.car.CarAddView;
import z13.rentivo.views.car.CarListView;
import z13.rentivo.views.payment.PaymentListView;
import z13.rentivo.views.client.ClientListView;
import z13.rentivo.views.rental.RentalListView;
import z13.rentivo.views.discount.DiscountListView;
import z13.rentivo.views.penalty.PenaltyListView;
import z13.rentivo.views.location.LocationListView;

public class MainLayout extends AppLayout{
    private DataService dataService;

    private Tabs getLinkTabs() {
        Tabs tabs = new Tabs();

        tabs.add(
                createTab(VaadinIcon.MONEY, "Payments", PaymentListView.class),
                createTab(VaadinIcon.CAR, "Cars", CarListView.class),
                createTab(VaadinIcon.USER, "Clients", ClientListView.class),
                createTab(VaadinIcon.EXCHANGE, "Rental", RentalListView.class),
                createTab(VaadinIcon.TICKET, "Discounts", DiscountListView.class),
                createTab(VaadinIcon.BAN, "Penalty", PenaltyListView.class),
                createTab(VaadinIcon.LOCATION_ARROW_CIRCLE, "Location", LocationListView.class)
        );

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName, Class<? extends Component> link) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink Rlink = new RouterLink(link);
        Rlink.add(icon, new Span(viewName));
        Rlink.setTabIndex(-1);
        return new Tab(Rlink);
    }

    public MainLayout(@Autowired DataService dataService) {
        this.dataService = dataService;

        H1 title = new H1("Rentivo - raport app");
        title.addClickListener(click ->{
            title.getUI().ifPresent(ui ->
                    ui.navigate(WelcomeView.class));
        });
        DrawerToggle linksDT = new DrawerToggle();
        linksDT.addClassNames("drawer-toggle");

        HorizontalLayout header = new HorizontalLayout(linksDT, title);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();

        addToNavbar(header);

        createNavigationDrawer();

    }

    private void createNavigationDrawer() {
        addToDrawer(getLinkTabs());
    }

}
