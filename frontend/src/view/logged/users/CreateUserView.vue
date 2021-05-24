<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Dodawanie użytkownika</v-toolbar-title>
    </v-toolbar>
    <v-form class="ma-5" style="padding-left: 30px; width: 40%">
      <v-text-field label="Imię" type="text" v-model="user.name"></v-text-field>
      <v-text-field label="Nazwisko" type="text" v-model="user.surname"></v-text-field>
      <v-text-field label="E-mail" type="text" v-model="user.email"></v-text-field>
      <v-text-field label="Hasło" type="password" v-model="user.password"></v-text-field>
      <v-text-field label="Potwierdź hasło" type="password" v-model="user.confirmPassword"></v-text-field>
      <v-select :items="roles" item-text="name" item-value="value" label="Rola" v-model="user.systemRole"></v-select>
    </v-form>
    <v-btn @click="createUser()" color="primary" style="color: black" class="ma-5">Dodaj użytkownika</v-btn>
    <v-btn @click="returnPage()" color="primary" style="color: black" class="ma-5 ml-1">Powrót</v-btn>
  </div>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "CreateUserView",

  data() {
    return {
      roles: [
        {name: 'Administrator', value: 'ADMINISTRATOR'},
        {name: 'Pracownik', value: 'EMPLOYEE'},
        {name: 'Pracownik zewnętrzny', value: 'USER'},
      ],
      user: {
        name: '',
        surname: '',
        email: '',
        password: '',
        confirmPassword: '',
        systemRole: '',
      }
    };
  },

  methods: {
    createUser() {
      api.post(this, '/users', this.user, () => {
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