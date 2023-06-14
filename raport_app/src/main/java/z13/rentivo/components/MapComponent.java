package z13.rentivo.components;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonString;

import java.util.Locale;

@Tag("map-component")
@JsModule("./src/map-component.js")
public class MapComponent extends LitTemplate {
    @Id("mapContainer")
    private Div chartContainer;

    public static class Color {
        int r;
        int g;
        int b;
        double a;

        public Color(int r, int g, int b, double a) {
            this.r = Math.abs(r % 256);
            this.g = Math.abs(g % 256);
            this.b = Math.abs(b % 256);
            this.a = a;
        }

        @Override
        public String toString() {
            String jsonString = "rgba(%d, %d, %d, %s)";
            return String.format(jsonString, r, g, b, String.format(Locale.ROOT, "%f", a));
        }
    }

    public static class Location {
        public double x;
        public double y;

        public Location(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Dataset {
        public String label;
        public Location[] data;
        public Color border;

        public Dataset() {
            label = "";
            border = new Color(75, 192, 192, 1.0);
        }
    }

    public MapComponent() {
        setTitle("");
    }

    public void setMaxWidth(float value, Unit unit)
    {
        this.chartContainer.setMaxWidth(value, unit);
    }

    public void setMinHeight(float value, Unit unit)
    {
        this.chartContainer.setMinHeight(value, unit);
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

    public void setDatasets(Dataset[] datasets) {
        JsonArray array = Json.createArray();
        for (int i = 0; i < datasets.length; i++) {
            Dataset dataset = datasets[i];
            JsonObject jsonDataset = Json.createObject();
            jsonDataset.put("label", dataset.label);

            JsonArray value = Json.createArray();
            for(int j =0; j < dataset.data.length; j++) {
                JsonObject jsonCoord = Json.createObject();
                jsonCoord.put("x", dataset.data[j].x);
                jsonCoord.put("y", dataset.data[j].y);
                value.set(j, jsonCoord);
            }
            jsonDataset.put("data", value);

            jsonDataset.put("borderColor", dataset.border.toString());
            jsonDataset.put("showLine", true);

            if (i == 0)
                jsonDataset.put("hidden", false);
            else
                jsonDataset.put("hidden", true);

            array.set(i, jsonDataset);
        }

        getElement().setPropertyJson("datasets", array);
    }

    public void setTitle(String title) {
        JsonString value = Json.create(title);
        getElement().setPropertyJson("title", value);
    }
}