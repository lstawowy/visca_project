<template>
  <div class="work">
    <div id="connection" class="box">
      <div style="display: flex">
        <multiselect
          v-model="value"
          :options="options"
          style="width: 66%; margin: 2%;"
        ></multiselect>
        <button
          class="button is-primary"
          @click.native="connect"
          :active="active0"
          style="width: 26%; margin: 2%;"
        >
          Connect to device
        </button>
      </div>
      <div style="display: flex">
        <input
          v-model="port"
          type="text"
          placeholder="Port"
          class="form-control"
          style="width: 66%; margin: 2%;"
        />
        <button
          class="button is-primary"
          @click.native="switchToPort"
          :active="active6"
          style="width: 26%; margin: 2%;"
        >
          Switch to port
        </button>
      </div>
    </div>

    <div id="movement" class="box">
      <div id="toggle">
        <br />
        <button class="button is-primary is-rounded" @click.native="toggleUp">Up</button>
        <br />
        <button
          class="button is-primary is-rounded"
          @click.native="toggleLeft"
          style="margin: 0 10px 0 10px"
        >
          Left
        </button>
        <button
          class="button is-primary is-rounded"
          @click.native="toggleRight"
          style="margin: 0 10px 0 10px"
        >
          Right
        </button>
        <br />
        <button class="button is-primary is-rounded" @click.native="toggleDown">Down</button>
      </div>
      <br />
      <input
        v-model="speed"
        type="text"
        placeholder="Speed"
        class="form-control"
        style="width: 10%"
      />

      <div id="zoom">
        <br />
        <button
          class="button is-info is-rounded"
          @click.native="zoomIn"
          style="margin: 0 10px 0 10px"
        >
          Zoom in
        </button>
        <button
          class="button is-info is-rounded"
          @click.native="zoomOut"
          style="margin: 0 10px 0 10px"
        >
          Zoom out
        </button>
        <button
          class="button is-danger is-rounded"
          @click.native="zoomOut"
          style="margin: 0 10px 0 10px"
        >
          Clear
        </button>
      </div>
      <br />
    </div>

    <div id="groups" class="box">
      <div class="row">
        <div class="column">
          <div id="select">
            <multiselect v-model="action" :options="actions"></multiselect>
          </div>
          <table class="table table-hover" style="width: 100%">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">First</th>
                <th scope="col">Last</th>
                <th scope="col">Handle</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th scope="row">1</th>
                <td>Mark</td>
                <td>Otto</td>
                <td>@mdo</td>
              </tr>
              <tr>
                <th scope="row">2</th>
                <td>Jacob</td>
                <td>Thornton</td>
                <td>@fat</td>
              </tr>
              <tr>
                <th scope="row">3</th>
                <td colspan="2">Larry the Bird</td>
                <td>@twitter</td>
              </tr>
            </tbody>
          </table>
          <button
            class="button is-success is-rounded"
            @click.native="submit"
            style="margin: 0 10px 0 10px"
          >
            Submit
          </button>
        </div>
        <div class="column">
          <table class="table table-hover" style="width: 100%">
            <thead>
              <tr>
                <th scope="col">Name</th>
                <th scope="col">Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th scope="row">1</th>
                <td>@mdo</td>
              </tr>
              <tr>
                <th scope="row">2</th>
                <td>@fat</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Multiselect} from "vue-multiselect";

export default {
  name: "ButtonPage",
  components: {
    Multiselect
  },
  data() {
    return {
      value: null,
      options: [],
      items: [
        {age: 40, first_name: "Dickerson", last_name: "Macdonald"},
        {age: 21, first_name: "Larsen", last_name: "Shaw"},
        {age: 89, first_name: "Geneva", last_name: "Wilson"},
        {age: 38, first_name: "Jami", last_name: "Carney"}
      ],
      actions: ["up", "down", "left", "right", "zoom_in", "zoom_out"],
      port: 1,
      speed: null
    };
  },
  methods: {
    select() {
      this.selected = [];
      if (!this.selectAll) {
        for (let i in this.items) {
          this.selected.push(this.items[i].id);
        }
      }
    },
    connect() {
      this.active0 = true;
      this.$http.post("http://localhost:8080/connect").then(response => {
        this.posts = response.data;
      });
    },
    toggleUp() {
      this.active1 = true;
      this.$http.post("http://localhost:8080/up?speed=" + speed).then(response => {
        this.posts = response.data;
      });
    },
    toggleLeft() {
      this.active2 = true;
      this.$http.post("http://localhost:8080/down?speed=" + speed).then(response => {
        this.posts = response.data;
      });
    },
    toggleRight() {
      this.active3 = true;
      this.$http.post("http://localhost:8080/right?speed=" + speed).then(response => {
        this.posts = response.data;
      });
    },
    toggleDown() {
      this.active4 = true;
      this.$http.post("http://localhost:8080/left?speed=" + speed).then(response => {
        this.posts = response.data;
      });
    },
    zoomIn() {
      this.active5 = true;
      this.$http.post("http://localhost:8080/zoom_in?speed=" + speed).then(response => {
        this.posts = response.data;
      });
    },
    zoomOut() {
      this.active6 = true;
      this.$http.post("http://localhost:8080/zoom_out?speed=" + speed).then(response => {
        this.posts = response.data;
      });
    },
    rotate() {
      this.active7 = true;
      this.$http.post("http://localhost:8080/clear_all").then(response => {
        this.posts = response.data;
      });
    },
    switchToPort() {
      this.active8 = true;
      this.$http.post("http://localhost:8080/switch_port?port=" + value).then(response => {
        this.posts = response.data;
      });
    }
  }
};
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
<style>
body {
  padding: 50px;
}

.row {
  display: flex;
}

.column {
  flex: 50%;
}
</style>
