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

public class MainLayout extends AppLayout{
    private DataService dataService;

    private Tabs getLinkTabs() {
        Tabs tabs = new Tabs();

        tabs.add(
                createTab(VaadinIcon.DATABASE, "Data View", DataSelectView.class),
                createTab(VaadinIcon.CHART_GRID, "Statistic View", StatsSelectView.class)
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