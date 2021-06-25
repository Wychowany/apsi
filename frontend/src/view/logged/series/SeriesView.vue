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
        <v-icon small @click="editSeries(item)" v-if="editAllowed(item)">edit</v-icon>
        <v-icon small @click="editSeries(item)" v-else-if="readAllowed(item)">description</v-icon>
        <v-icon small @click="deleteSeries(item)" class="ml-3" v-if="deleteAllowed(item)">delete</v-icon>
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

    deleteSeries(item) {
      api.delete(this, "/series/", {id: item.id}, () => {
            this.series = this.series.filter(d => d.id !== item.id);
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },

    deleteAllowed(item) {
      return item.isAuthor || item.accessType === 'DELETE';
    },
    editAllowed(item) {
      return item.isAuthor || item.accessType === 'UPDATE' || item.accessType === 'DELETE';
    },
    readAllowed(item) {
      return !item.isAuthor && item.accessType === 'READ';
    }
  }
}
</script>

<style scoped>

</style>