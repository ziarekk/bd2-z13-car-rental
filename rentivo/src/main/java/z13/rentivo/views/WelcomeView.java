package z13.rentivo.views;

import java.util.Collection;
import java.util.List;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
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
import z13.rentivo.views.form.CarFormView;
import z13.rentivo.views.list.CarsListView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "hello", layout = MainLayout.class)
@PageTitle("Welcom to Rentivo")
@RouteAlias(value = "", layout = MainLayout.class)
public class WelcomeView extends VerticalLayout {
    private H1 title;
    private H3 description;

    public WelcomeView() {
        setId("hello-world-view");

        H1 title = new H1("Welcome to Rentivo App");
        H3 description = new H3("Check what cars we currently offer!");

        add(title, description);


    }

}
