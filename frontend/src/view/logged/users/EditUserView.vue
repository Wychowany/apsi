<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Edycja użytkownika</v-toolbar-title>
    </v-toolbar>
    <v-form class="ma-5" style="padding-left: 30px; width: 40%">
      <v-text-field label="Imię" type="text" v-model="user.name"></v-text-field>
      <v-text-field label="Nazwisko" type="text" v-model="user.surname"></v-text-field>
      <v-text-field label="E-mail" type="text" v-model="user.email"></v-text-field>
      <v-select :items="roles" item-text="name" item-value="value" label="Rola" v-model="user.systemRole"></v-select>
    </v-form>
    <v-btn @click="saveUser()" color="primary" style="color: black" class="ma-5">Zapisz użytkownika</v-btn>
    <v-btn @click="returnPage()" color="primary" style="color: black" class="ma-5 ml-1">Powrót</v-btn>
  </div>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "EditUserView",

  data() {
    return {
      roles: [
        {name: 'Administrator', value: 'ADMINISTRATOR'},
        {name: 'Pracownik', value: 'EMPLOYEE'},
        {name: 'Pracownik zewnętrzny', value: 'USER'},
        {name: 'Klient', value: 'CLIENT'},
      ],
      user: {
        id: null,
        name: '',
        surname: '',
        email: '',
        systemRole: '',
      }
    };
  },

  created() {
    api.get(this, '/users', {id: this.$route.params.id}, successResponse => {
      this.user = successResponse;
      this.user.systemRole = this.roles.find(role => role.name === successResponse.systemRole).value  ;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    saveUser() {
      api.put(this, '/users', this.user, () => {
            this.$router.go(-1);
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },

    returnPage() {
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>

</style>