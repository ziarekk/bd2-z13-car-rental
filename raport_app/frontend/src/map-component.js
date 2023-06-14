import { html, css, LitElement } from 'lit-element';
import Chart from 'chart.js/auto';

class MapComponent extends LitElement {
    static styles = css`
        :host {
            display: block;
            width: 100%;
            height: 100%;
        }
    `;

    static properties = {
        datasets: { type: Array },
        title: { type: String },
        chart: { type: Object },
    };

    render() {
        return html`
            <div id="mapContainer">
                <canvas id="mapCanvas"></canvas>
            </div>
        `;
    }

    firstUpdated() {
        const canvas = this.shadowRoot.getElementById('mapCanvas');
        const ctx = canvas.getContext('2d');

        this.chart = new Chart(ctx, {
            type: 'scatter',
            data: {
                datasets: this.datasets
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true,
                        text: this.title
                    }
                }
            }
        });

        this.chart.render();

        this.dispatchEvent(new CustomEvent('map-rendered'));
    }

    updated(changedProperties) {
        if (changedProperties.has('datasets') && this.chart) {
            this.chart.data.datasets = this.datasets;
            this.chart.update();
        }
        if (changedProperties.has('title') && this.chart) {
            this.chart.options.plugins.title.text = this.title;
            this.chart.update();
        }
    }
}

customElements.define('map-component', MapComponent);