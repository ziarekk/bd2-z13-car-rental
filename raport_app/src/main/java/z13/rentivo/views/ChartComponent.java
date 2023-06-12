package z13.rentivo.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import elemental.json.Json;
import elemental.json.JsonArray;

@Tag("chart-component")
@JsModule("./src/chart-component.js")
public class ChartComponent extends LitTemplate {
    @Id("chartContainer")
    private Div chartContainer;

    public ChartComponent() {
    }

    @ClientCallable
    private void chartRendered() {
        // Wywoływane, gdy wykres jest gotowy do renderowania
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        getElement().callJsFunction("render");
    }

    public void setData(double[] data) {
        JsonArray value = Json.createArray();
        for(int i =0; i < data.length; i++)
            value.set(i, data[i]);

        // Przekazanie danych do wykresu
        getElement().setPropertyJson("data", value);
    }

    public interface ChartComponentModel extends TemplateModel {
        // Pusty interfejs modelu
    }
}