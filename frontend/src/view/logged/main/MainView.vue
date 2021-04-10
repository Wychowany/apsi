<template>
  <div>
    <h1>NASTĄPIŁO ZALOGOWANIE</h1>
    <v-btn @click="logout">wyloguj</v-btn>
  </div>
</template>


<script>

import {auth} from "@/util/Auth";

export default {
  name: "MainView",

  methods: {
    logout() {
      auth.logout(this);
    },
  },
};
</script>

<style scoped>

</style>
