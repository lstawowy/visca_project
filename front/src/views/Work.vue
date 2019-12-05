<template>
  <div class="work">
    <div id="connection" class="box">
      <div style="display: flex">
        <multiselect v-model="port" :options="ports" style="width: 66%; margin: 2%;"></multiselect>
        <button
          class="button is-primary"
          @click="connect"
          style="width: 26%; margin: 2%;"
        >Connect to device</button>
      </div>
      <div style="display: flex">
        <input
          v-model="address"
          type="text"
          placeholder="Address"
          class="form-control"
          style="width: 66%; margin: 2%;"
        />
        <button
          class="button is-primary"
          @click="switchAddress"
          style="width: 26%; margin: 2%;"
        >Switch to address</button>
      </div>
    </div>

    <div id="movement" class="box">
      <div id="toggle">
        <br />
        <button class="button is-primary is-rounded" @click="tiltUp">Up</button>
        <br />
        <button
          class="button is-primary is-rounded"
          @click="tiltLeft"
          style="margin: 5px 10px 5px 10px"
        >Left</button>
        <button
          class="button is-primary is-rounded"
          @click="tiltRight"
          style="margin: 5px 10px 5px 10px"
        >Right</button>
        <br />
        <button class="button is-primary is-rounded" @click="tiltDown">Down</button>
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
          @click="zoomTele"
          style="margin: 0 10px 0 10px"
        >Zoom in</button>
        <button
          class="button is-info is-rounded"
          @click="zoomWide"
          style="margin: 0 10px 0 10px"
        >Zoom out</button>
        <button
          class="button is-danger is-rounded"
          @click="clear"
          style="margin: 0 10px 0 10px"
        >Clear</button>
      </div>
      <br />
      <div id="response" class="box">{{posts}}</div>
      <br />
    </div>

    <div id="groups" class="box">
      <div class="row">
        <div class="column">
          <div id="select">
            <multiselect v-model="action" :options="actions" @select="selectAction"></multiselect>
          </div>
          <div class="table-container">
            <table class="table table-hover" style="width: 100%">
              <tbody>
                <tr v-for="(selected, index) in selected_actions" v-bind:key="index">
                  <th>{{ selected }}</th>
                </tr>
              </tbody>
            </table>
          </div>

          <div style="display: flex">
            <input
              v-model="group_name"
              type="text"
              placeholder="Group name"
              class="form-control"
              style="width: 66%; margin: 2%;"
            />
            <button
              class="button is-info is-rounded"
              @click="submitGroup"
              style="width: 26%; margin: 2%;"
            >Submit</button>
          </div>
        </div>
        <div class="column">
          <div class="table-container">
            <table class="table table-hover" style="width: 100%">
              <thead>
                <tr>
                  <th scope="col">Name</th>
                  <th scope="col">Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="group in groups" v-bind:key="group">
                  <td>{{ group.name }}</td>
                  <td>{{ group.actions }}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div style="display: flex">
            <input
              v-model="execute_name"
              type="text"
              placeholder="Execute group name"
              class="form-control"
              style="width: 36%; margin: 2%;"
            />
            <input
              v-model="delay"
              type="text"
              placeholder="Delay"
              class="form-control"
              style="width: 36%; margin: 2%;"
            />
            <button
              class="button is-rounded is-danger"
              @click="executeGroup"
              style="width: 26%; margin: 2%;"
            >Execute</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Multiselect } from "vue-multiselect";

export default {
  name: "ButtonPage",
  components: {
    Multiselect
  },
  data() {
    return {
      port: null,
      ports: [],

      value: null,
      options: [],

      address: null,

      action: null,
      actions: [
        "tilt_up",
        "tilt_down",
        "tilt_left",
        "tilt_right",
        "zoom_tele",
        "zoom_wide"
      ],

      selected_action: null,
      selected_actions: [],

      group_name: null,
      groups: [],

      execute_name: null,
      delay: 1,

      posts: null,
      speed: null
    };
  },
  mounted() {
    this.$http.get("http://localhost:8080/ports").then(response => {
      this.ports = response.data;
    });
    this.$http.get("http://localhost:8080/groups").then(response => {
      this.groups = response.data;
    });
  },
  methods: {
    selectAction(actionName) {
      this.selected_actions.push(actionName);
    },
    executeGroup() {
      this.$http
        .post(
          "http://localhost:8080/groups/execute/" +
            this.execute_name +
            "?delay=" +
            this.delay
        )
        .then(response => {
          this.posts = response.data;
        });
    },
    submitGroup() {
      this.$http
        .post(
          "http://localhost:8080/groups/" + this.group_name,
          this.selected_actions
        )
        .then(response => {
          this.posts = response.data;
          this.$http.get("http://localhost:8080/groups").then(response => {
            this.groups = response.data;
          });
        });
    },
    connect() {
      this.$http
        .post("http://localhost:8080/connect?port=" + this.port)
        .then(response => {
          this.posts = response.data;
        });
    },
    switchAddress() {
      this.$http
        .post("http://localhost:8080/switch_address?address=" + this.address)
        .then(response => {
          this.posts = response.data;
        });
    },
    tiltUp() {
      this.$http.post("http://localhost:8080/tilt_up").then(response => {
        this.posts = response.data;
      });
    },
    tiltDown() {
      this.$http.post("http://localhost:8080/tilt_down").then(response => {
        this.posts = response.data;
      });
    },
    tiltLeft() {
      this.$http
        .post("http://localhost:8080/tilt_left?speed=" + this.speed)
        .then(response => {
          this.posts = response.data;
        });
    },
    tiltRight() {
      this.$http
        .post("http://localhost:8080/tilt_right?speed=" + this.speed)
        .then(response => {
          this.posts = response.data;
        });
    },
    zoomTele() {
      this.$http.post("http://localhost:8080/zoom_tele").then(response => {
        this.posts = response.data;
      });
    },
    zoomWide() {
      this.$http.post("http://localhost:8080/zoom_wide").then(response => {
        this.posts = response.data;
      });
    },
    clear() {
      this.$http.post("http://localhost:8080/clear").then(response => {
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

#response {
  background-color: rgb(230, 189, 189);
}

#movement {
  background-color: rgb(228, 215, 157);
}

#connection {
  background-color: rgb(116, 216, 255);
}

#groups {
  background-color: rgb(195, 221, 231);
}

.row {
  display: flex;
}

.column {
  flex: 50%;
}

body {
  background-color: rgb(186, 252, 230);
}
</style>
