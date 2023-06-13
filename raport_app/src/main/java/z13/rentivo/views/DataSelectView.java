package z13.rentivo.views;


import com.vaadin.flow.router.Route;
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
import z13.rentivo.views.list_views.BillListView;
import z13.rentivo.views.list_views.CarListView;
import z13.rentivo.views.list_views.CommentListView;
import z13.rentivo.views.list_views.DriverLicenceListView;
import z13.rentivo.views.list_views.PaymentListView;
import z13.rentivo.views.list_views.ClientListView;
import z13.rentivo.views.list_views.RentalListView;
import z13.rentivo.views.list_views.DiscountListView;
import z13.rentivo.views.list_views.PenaltyListView;
import z13.rentivo.views.list_views.LocationListView;
import z13.rentivo.views.list_views.SegmentListView;

@Route(value = "/data")
public class DataSelectView extends AppLayout{
    private DataService dataService;

    private Tabs getLinkTabs() {
        Tabs tabs = new Tabs();

        tabs.add(
                createTab(VaadinIcon.CASH, "Bills", BillListView.class),
                createTab(VaadinIcon.CAR, "Cars", CarListView.class),
                createTab(VaadinIcon.USER, "Clients", ClientListView.class),
                createTab(VaadinIcon.COMMENT, "Comments", CommentListView.class),
                createTab(VaadinIcon.TICKET, "Discounts", DiscountListView.class),
                createTab(VaadinIcon.USER_CARD, "Licences", DriverLicenceListView.class),
                createTab(VaadinIcon.LOCATION_ARROW_CIRCLE, "Location", LocationListView.class),
                createTab(VaadinIcon.MONEY, "Payments", PaymentListView.class),
                createTab(VaadinIcon.BAN, "Penalty", PenaltyListView.class),
                createTab(VaadinIcon.EXCHANGE, "Rental", RentalListView.class),
                createTab(VaadinIcon.PYRAMID_CHART, "Segments", SegmentListView.class)
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

    public DataSelectView(@Autowired DataService dataService) {
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
