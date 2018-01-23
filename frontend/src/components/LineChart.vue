<template lang="html">
  <div class="ct-chart ct-perfect-fourth"></div>
</template>

<script>
import Chartist from 'chartist'
import moment from 'moment'
export default {
  props: {
    temperatures: {
      type: Array,
      default() {
        return [];
      }
    }
  },
  methods: {
    renderChart() {
      new Chartist.Line('.ct-chart', {
        labels: this.temperatures.map(t => moment(t.date).fromNow()),
        series: [
           this.temperatures.map(t => t.temperature)
        ]
      }, {
        fullWidth: true,
        //high: 25,
        //low: 20,
        axisX: {
          showGrid: false,
          offset: 200
        },
        axisY: {
          offset: 80,
          labelInterpolationFnc: function(value) {
            return value + ' °C'
          }
        },
        chartPadding: {
          right: 40
        }
      });
    }
  },
  mounted() {
    this.$watch('temperatures', this.renderChart, {deep: true})
  }
}
</script>

<style lang="scss">
  @import '~chartist/dist/scss/chartist.scss';
  .ct-series-a .ct-point, .ct-series-a .ct-line, .ct-series-a .ct-bar, .ct-series-a .ct-slice-donut {
    stroke: #3f51b5 !important;
  }
</style>
