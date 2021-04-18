<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Użytkownicy</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="createUser()" color="light" style="color: black">Dodaj użytkownika</v-btn>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="users"
        :items-per-page="10"
        class="elevation-1">
      <template v-slot:item.actions="{ item }">
        <v-icon small @click="editUser(item)">edit</v-icon>
        <v-icon small class="ml-3" @click="deleteUser(item)">delete</v-icon>
      </template>
    </v-data-table>
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
        {text: 'Akcje', sortable: false, value: 'actions'},
      ],
      users: [],
    }
  },

  created() {
    api.get(this, '/users/list', null,successResponse => {
      this.users = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    createUser() {
      this.$router.push("/app/users/create");
    },

    editUser(item) {
      this.$router.push("/app/users/edit/" + item.id);
    },

    deleteUser(item) {
      api.delete(this, "/users/", {id: item.id}, () => {
        this.users = this.users.filter(u => u.id !== item.id);
      },
      errorResponse => {
        console.log(errorResponse);
      });
    },
  }
}
</script>

<style scoped>

</style>