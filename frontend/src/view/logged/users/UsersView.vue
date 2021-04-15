<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Użytkownicy</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn
          icon
          large
          target="_blank"
          slot="activator"
      >
      </v-btn>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="users"
        :items-per-page="10"
        class="elevation-1"
    ></v-data-table>
  </div>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "UsersView",

  data () {
    return {
      headers: [
        {text: 'Imię', sortable: false, value: 'name'},
        {text: 'Nazwisko', sortable: false, value: 'surname'},
        {text: 'E-mail', sortable: false, value: 'email'},
        {text: 'Rola', sortable: false, value: 'systemRole'},
        {text: 'Akcje', sortable: false},
      ],
      users: [],
    }
  },

  created() {
    api.get(this, '/users', successResponse => {
      this.users = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });
  }
}
</script>

<style scoped>

</style>