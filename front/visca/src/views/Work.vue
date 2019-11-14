<template>
    <div class="work">
        <h1>This is controlling page</h1>
        <mdb-btn large color="primary" @click.native="toggleUp" :active="active1">Up</mdb-btn><p>     </p>
        <mdb-btn large color="primary" @click.native="zoomIn" :active="active5">Zoom in</mdb-btn>
        <mdb-btn large color="primary" @click.native="zoomOut" :active="active6">Zoom out</mdb-btn>
        <br>
        <mdb-btn large color="primary" @click.native="toggleLeft" :active="active2">Left</mdb-btn>
        <mdb-btn large color="primary" @click.native="toggleRight" :active="active3">Right</mdb-btn>
        <br>
        <mdb-btn large color="primary" @click.native="toggleDown" :active="active4">Down</mdb-btn>



    </div>
</template>

<script>
    import { mdbBtn } from 'mdbvue';
    export default {
        name: 'ButtonPage',
        components: {
            mdbBtn
        },
        methods: {
            toggleUp() {
                this.active1 = true;
                axios.get("https://jsonplaceholder.typicode.com/posts")
                    .then(response => {this.posts = response.data})
            },
            toggleLeft() {
                this.active2 = true;
                axios.get("https://jsonplaceholder.typicode.com/posts")
                    .then(response => {this.posts = response.data})
            },
            toggleRight() {
                this.active3 = true;
                axios.get("https://jsonplaceholder.typicode.com/posts")
                    .then(response => {this.posts = response.data})
            },
            toggleDown() {
                this.active4 = true;
                axios.get("https://jsonplaceholder.typicode.com/posts")
                    .then(response => {this.posts = response.data})
            },
            zoomIn() {
                this.active1 = true;
                axios.get("https://jsonplaceholder.typicode.com/posts")
                    .then(response => {this.posts = response.data})
            },
            zoomOut() {
                this.active1 = true;
                axios.get("https://jsonplaceholder.typicode.com/posts")
                    .then(response => {this.posts = response.data})
            }
        }
    }
</script>