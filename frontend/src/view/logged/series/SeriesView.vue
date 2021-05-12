<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Zbiory</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="createSeries()" color="light" style="color: black">Dodaj zbiór</v-btn>
    </v-toolbar>
  </div>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "SeriesView",

  data() {
    return {
      series: [],
    };
  },

  created() {
    api.get(this, '/series/list', null,successResponse => {
      this.series = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    createSeries() {

    }
  }
}
</script>

<style scoped>

</style>