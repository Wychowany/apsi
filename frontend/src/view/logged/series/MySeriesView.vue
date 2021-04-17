<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Moje zbiory</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="createSeries()" color="light" style="color: black">Dodaj zbiór</v-btn>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  name: "MySeriesView",

  methods: {
    createSeries() {

    }
  }
}
</script>

<style scoped>

</style>