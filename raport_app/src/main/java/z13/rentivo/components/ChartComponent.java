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

import java.util.Arrays;
import java.util.Locale;

@Tag("chart-component")
@JsModule("./src/chart-component.js")
public class ChartComponent extends LitTemplate {
    @Id("chartContainer")
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

    public static class Dataset {
        public String label;
        public double[] data;
        public Color background;
        public Color border;

        public Dataset() {
            label = "";
            data = new double[]{10, 20, 30, 40, 50, 60};
            background = new Color(75, 192, 192, 0.2);
            border = new Color(75, 192, 192, 1.0);
        }

        @Override
        public String toString() {
            String jsonString = "{ label: '%s', data: %s, backgroundColor: '%s', borderColor: '%s', borderWidth: 1 }";

            return String.format(jsonString, label,
                    Arrays.toString(data),
                    background.toString(),
                    border.toString());
        }
    }

    public enum ChartType {
        SCATTER("scatter"),
        LINE("line"),
        BAR("bar"),
        PIE("pie"),
        DOUGHNUT("doughnut"),
        BUBBLE("bubble"),
        AREA("polarArea"),
        RADAR("radar");

        public final String label;

        private ChartType(String label) {
            this.label = label;
        }
    }

    public ChartComponent() {
        setDatasets(new Dataset[]{ new Dataset() });
        setLabels(new String[]{"N", "O", "D", "A", "T", "A"});
        setTitle("");
        setXAxisLabel("");
        setYAxisLabel("");
        setChartType(ChartType.BAR);
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
            for(int j =0; j < dataset.data.length; j++)
                value.set(j, dataset.data[j]);
            jsonDataset.put("data", value);

            jsonDataset.put("backgroundColor", dataset.background.toString());
            jsonDataset.put("borderColor", dataset.border.toString());
            jsonDataset.put("borderWidth", 1);
            array.set(i, jsonDataset);
        }

        getElement().setPropertyJson("datasets", array);
    }

    public void setLabels(String[] labels) {
        JsonArray value = Json.createArray();
        for(int i =0; i < labels.length; i++)
            value.set(i, labels[i]);

        getElement().setPropertyJson("labels", value);
    }

    public void setLabels(double[] labels) {
        JsonArray value = Json.createArray();
        for(int i =0; i < labels.length; i++)
            value.set(i, labels[i]);

        getElement().setPropertyJson("labels", value);
    }

    public void setTitle(String title) {
        JsonString value = Json.create(title);
        getElement().setPropertyJson("title", value);
    }

    public void setXAxisLabel(String xAxisLabel) {
        JsonString value = Json.create(xAxisLabel);
        getElement().setPropertyJson("xAxisLabel", value);
    }

    public void setYAxisLabel(String yAxisLabel) {
        JsonString value = Json.create(yAxisLabel);
        getElement().setPropertyJson("yAxisLabel", value);
    }

    public void setChartType(ChartType chartType) {
        JsonString value = Json.create(chartType.label);
        getElement().setPropertyJson("chartType", value);
    }
}