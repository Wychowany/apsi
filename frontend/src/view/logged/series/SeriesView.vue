<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Zbiory</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="createSeries()" color="light" style="color: black">Dodaj zbiór</v-btn>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="series"
        :items-per-page="10"
        class="elevation-1">
      <template v-slot:item.actions="{ item }">
        <v-icon small v-if=editAllowed(item) @click="editSeries(item)">edit</v-icon>
        <v-icon small v-else-if=readAllowed(item) @click="editSeries(item)" >description</v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "SeriesView",

  data() {
    return {
      headers: [
        {text: 'Nazwa', sortable: false, value: 'name'},
        {text: 'Autor', sortable: false, value: 'author'},
        {text: 'Akcje', sortable: false, value: 'actions'},
      ],
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
      this.$router.push("/app/series/create");
    },

    editSeries(item) {
      this.$router.push("/app/series/edit/" + item.id);
    },
    editAllowed(item) {


       return item.isAuthor || item.accessType === 'UPDATE';

       },

        readAllowed(item) {

              return !item.isAuthor && (item.accessType === 'READ') ;

          },
  }
}
</script>

<style scoped>

</style>