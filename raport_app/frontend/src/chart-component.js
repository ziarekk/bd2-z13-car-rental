import { html, css, LitElement } from 'lit-element';
import Chart from 'chart.js/auto';

class ChartComponent extends LitElement {
    static styles = css`
        :host {
            display: block;
            width: 100%;
            height: 300px;
        }
    `;

    static properties = {
        data: { type: Array },
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
            type: 'bar',
            data: {
                labels: ['A', 'B', 'C', 'D', 'E'],
                datasets: [{
                    label: 'Data',
                    data: this.data,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false
            }
        });

        this.chart.render();

        this.dispatchEvent(new CustomEvent('chart-rendered'));
    }

    updated(changedProperties) {
        if (changedProperties.has('data') && this.chart) {
            this.chart.data.datasets[0].data = this.data;
            this.chart.update();
        }
    }
}

customElements.define('chart-component', ChartComponent);