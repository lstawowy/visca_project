<template>
  <div class="work">
    <h1>This is controlling page</h1>

    <div id="connection">
      <input v-model="port" type="text" placeholder="Port" class="form-control" />
      <mdb-btn large color="primary" @click.native="switchToPort" :active="active6">Switch to port</mdb-btn>
      <br />
      <mdb-btn large color="primary" @click.native="connect" :active="active0">Connect to device</mdb-btn>
      <br />
    </div>

    <div id="movement">
      <br />
      <mdb-btn large color="primary" @click.native="toggleUp" :active="active1">Up</mdb-btn>
      <br />
      <mdb-btn large color="primary" @click.native="toggleLeft" :active="active2">Left</mdb-btn>
      <mdb-btn large color="primary" @click.native="toggleRight" :active="active3">Right</mdb-btn>
      <br />
      <mdb-btn large color="primary" @click.native="toggleDown" :active="active4">Down</mdb-btn>
      <br />
      <br />
      <mdb-btn large color="primary" @click.native="zoomIn" :active="active5">Zoom in</mdb-btn>
      <mdb-btn large color="primary" @click.native="zoomOut" :active="active6">Zoom out</mdb-btn>
      <br />
      <input v-model="speed" type="text" placeholder="Speed" class="form-control" />
    </div>

    <div id="groups">
      <div id="select">
        <multiselect v-model="value" :options="options"></multiselect>
      </div>

      <div id="saved">
        <b-table striped hover :items="items"></b-table>
      </div>
    </div>
  </div>
</template>

<script>
import { mdbBtn } from "mdbvue";
import { Multiselect } from "vue-multiselect";

export default {
  name: "ButtonPage",
  components: {
    mdbBtn,
    Multiselect
  },
  data() {
    return {
      value: null,
      options: ["list", "of", "options"],
      items: [
        { age: 40, first_name: "Dickerson", last_name: "Macdonald" },
        { age: 21, first_name: "Larsen", last_name: "Shaw" },
        { age: 89, first_name: "Geneva", last_name: "Wilson" },
        { age: 38, first_name: "Jami", last_name: "Carney" }
      ],
      port: 1,
      speed: 1
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
      axios.post("https://localhost:8080/connect").then(response => {
        this.posts = response.data;
      });
    },
    toggleUp() {
      this.active1 = true;
      axios
        .post("https://localhost:8080/up/?speed=" + this.speed)
        .then(response => {
          this.posts = response.data;
        });
    },
    toggleLeft() {
      this.active2 = true;
      axios
        .post("https://localhost:8080/down/?speed=" + this.speed)
        .then(response => {
          this.posts = response.data;
        });
    },
    toggleRight() {
      this.active3 = true;
      axios
        .post("https://localhost:8080/right/?speed=" + this.speed)
        .then(response => {
          this.posts = response.data;
        });
    },
    toggleDown() {
      this.active4 = true;
      axios
        .post("https://localhost:8080/left/?speed=" + this.speed)
        .then(response => {
          this.posts = response.data;
        });
    },
    zoomIn() {
      this.active5 = true;
      axios
        .post("https://localhost:8080/zoom_in/?speed=" + this.speed)
        .then(response => {
          this.posts = response.data;
        });
    },
    zoomOut() {
      this.active6 = true;
      axios
        .post("https://localhost:8080/zoom_out/?speed=" + this.speed)
        .then(response => {
          this.posts = response.data;
        });
    },
    rotate() {
      this.active7 = true;
      axios.post("https://localhost:8080/clear_all").then(response => {
        this.posts = response.data;
      });
    },
    switchToPort() {
      this.active8 = true;
      axios
        .post("https://localhost:8080/switch_port/?port=" + value)
        .then(response => {
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
</style>