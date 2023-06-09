package z13.rentivo.views;

import java.util.Collection;
import java.util.List;

import javax.annotation.security.PermitAll;

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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import z13.rentivo.entities.Client;
import z13.rentivo.entities.User;
import z13.rentivo.security.SecurityService;
import z13.rentivo.service.DataService;
import z13.rentivo.views.form.CarFormView;
import z13.rentivo.views.list.CarsListView;

@Route(value = "")
@PermitAll
public class MainLayout extends AppLayout{
    private final DataService dataService;
    private final SecurityService securityService;

    private Tabs getLinkTabs() {
        Tabs tabs = new Tabs();

        tabs.add(
                createTab(VaadinIcon.CAR, "Our cars", CarsListView.class)

        );

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equals("ROLE_ADMIN")) {
                tabs.add(
                        createTab(VaadinIcon.PLUS_CIRCLE, "Add car", CarFormView.class));
            }
        }

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

    public MainLayout(
        @Autowired SecurityService securityService, 
        @Autowired DataService dataService) {

        this.securityService = securityService;
        this.dataService = dataService;

        H1 title = new H1("Rentivo");
        title.addClickListener(click ->{
            title.getUI().ifPresent(ui ->
                    ui.navigate(CarsListView.class));
        });
        DrawerToggle linksDT = new DrawerToggle();
        linksDT.addClassNames("drawer-toggle");

        Button logout = new Button("Log out", e -> securityService.logout());
        logout.addClassNames("logout-button");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<User> users = dataService.getUserByLogin(authentication.getName());
        User user = users.get(0);
        HorizontalLayout userInfo = createUserCardComponent(user);


        HorizontalLayout header = new HorizontalLayout(linksDT, title, userInfo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();

        addToNavbar(header);

        createNavigationDrawer();

    }

    private void createNavigationDrawer() {
        addToDrawer(getLinkTabs());
    }

    private HorizontalLayout createUserCardComponent(User user)
    {
        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);

        Avatar avatar = new Avatar(user.getLogin());
       // avatar.setImage(employee.getPictureRoute());

        avatar.setHeight("64px");
        avatar.setWidth("64px");

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);
        infoLayout.getElement().appendChild(
                ElementFactory.createStrong(user.getLogin()));
        infoLayout.add(new Div(new Text(user.getUserRole().getName())));


        VerticalLayout contactLayout = new VerticalLayout();
        contactLayout.setSpacing(false);
        contactLayout.setPadding(false);

        cardLayout.add(avatar, infoLayout);
        return cardLayout;
    }

}
