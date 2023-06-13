import { html, css, LitElement } from 'lit-element';
import Chart from 'chart.js/auto';

class ChartComponent extends LitElement {
    static styles = css`
        :host {
            display: block;
            width: 100%;
            height: 600px;
        }
    `;

    static properties = {
        datasets: { type: Array },
        labels: { type: Array },
        title: { type: String },
        xAxisLabel: { type: String },
        yAxisLabel: { type: String },
        chartType: { type: String },
        chart: { type: Object },
    };

    render() {
        return html`
            <div id="chartContainer">
                <canvas id="chartCanvas"></canvas>
            </div>
        `;
    }

    firstUpdated() {
        const canvas = this.shadowRoot.getElementById('chartCanvas');
        const ctx = canvas.getContext('2d');

        this.chart = new Chart(ctx, {
            type: this.chartType,
            data: {
                labels: this.labels,
                datasets: this.datasets
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: this.xAxisLabel
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: this.yAxisLabel
                        }
                    }
                },
                plugins: {
                    title: {
                        display: true,
                        text: this.title
                    }
                }
            }
        });

        this.chart.render();

        this.dispatchEvent(new CustomEvent('chart-rendered'));
    }

    updated(changedProperties) {
        if (changedProperties.has('datasets') && this.chart) {
            this.chart.data.datasets = this.datasets;
            this.chart.update();
        }
        if (changedProperties.has('labels') && this.chart) {
            this.chart.data.labels = this.labels;
            this.chart.update();
        }
        if (changedProperties.has('title') && this.chart) {
            this.chart.options.plugins.title.text = this.title;
            this.chart.update();
        }
        if (changedProperties.has('xAxisLabel') && this.chart) {
            this.chart.options.scales.x.title.text = this.xAxisLabel;
            this.chart.update();
        }
        if (changedProperties.has('yAxisLabel') && this.chart) {
            this.chart.options.scales.y.title.text = this.yAxisLabel;
            this.chart.update();
        }
        if (changedProperties.has('chartType') && this.chart) {
            this.chart.config.type = this.chartType;
            this.chart.update();
        }
    }
}

customElements.define('chart-component', ChartComponent);