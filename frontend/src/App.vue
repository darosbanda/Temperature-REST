<template>
  <div id="app">
    <div>
    <select class="" name="" v-model="selectedSensor">
      <option :value="sensor.id" v-for="sensor in sensors"> {{sensor.name}} </option>
    </select>
    <datetime v-model="date"
              type="datetime"
              input-format="YYYY-MM-DD HH:mm"
              placeholder="Select Date and Time"
              monday-first
    ></datetime>
    </div>
    <a @click="sensorForm.show = !sensorForm.show" class="button is-small is-info">Add sensor</a>
    <div v-show="sensorForm.show">
      <input v-model="sensorForm.name" class="input is-small" style="width: 100px" placeholder="Name...">
      <a @click="addSensor" class="button is-small is-success">Add</a>
    </div>
    <div class="mainLineChart">
      <LineChart
      :temperatures="temperatures"/>
    </div>
  </div>
</template>

<script>
import LineChart from './components/LineChart'
import { Datetime } from 'vue-datetime';
import moment from 'moment'
import axios from 'axios'
window.api = axios.create({
  baseURL: 'http://localhost:8080/'
})
export default {
  methods: {
    getSensors(){
      api.get('sensors/temperature').then(response => {
        this.sensors = response.data
        if (this.sensors.length > 0) {
          this.selectedSensor = this.sensors[0].id
        }
      })
    },
    getTemps(sensorID, from) {
      const fromDate = from ? '&from=' + from : ''
      api.get('sensors/temperatures/' + sensorID + '/' + fromDate).then(response => {
        console.log(response)
        this.temperatures = response.data
      })
    },
    addSensor() {
      api.post('sensors/temperature', {name: this.sensorForm.name}).then(response => {
        console.log(response)
      })
    }
  },
  computed: {
    formattedDate() {
      if (!this.date) {
        return ''
      }
      return moment(this.date).format('YYYY-MM-DD HH:mm')
    }
  },
  watch: {
    date(newdate) {
      this.getTemps(this.selectedSensor, this.formattedDate)
    },
    selectedSensor(sensorID) {
      this.getTemps(sensorID)
    }
  },
  created(){
    this.getSensors()
  },
  data(){
    return {
      date: '',
      selectedSensor: '',
      temperatures: [],
      sensors: [],
      sensorForm: {
        show: false,
        name: ''
      }
    }
  },
  name: 'app',
  components: {
    Datetime,
    LineChart
  }
}
</script>

<style lang="scss">
@import '~bulma/bulma.sass';
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.mainLineChart {
  width: 50vw;
  height: 50vh;
  margin: 0 auto;
}

.vdatetime-popup__month-selector {
  box-sizing: border-box;
}

</style>
