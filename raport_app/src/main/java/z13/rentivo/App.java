package z13.rentivo;

import java.lang.String;

import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

@SpringBootApplication
//@Theme(value = "my_theme", variant = Lumo.DARK)
@PWA(name = "Rentivo - rent yourself a car", shortName = "Rentivo", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class App implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
