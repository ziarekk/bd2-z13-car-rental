package z13.rentivo.views;

import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.router.RouterLink;

import z13.rentivo.service.DataService;
import z13.rentivo.views.list.CarsListView;


public class MainLayout extends AppLayout{
    private DataService dataService;

    public MainLayout(@Autowired DataService dataService) {
        this.dataService = dataService;


        H1 title = new H1("CarRental");
        title.addClickListener(click ->{
            title.getUI().ifPresent(ui ->
                    ui.navigate(CarsListView.class));
        });
        DrawerToggle linksDT = new DrawerToggle();
        linksDT.addClassNames("drawer-toggle");

        HorizontalLayout header = new HorizontalLayout(linksDT, title);


        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();

        addToNavbar(header);



    }
}
