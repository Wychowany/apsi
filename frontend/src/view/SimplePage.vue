<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <div>
        <v-btn to="/login" style="margin-right: 10px;">Logowanie</v-btn>
        <v-btn to="/register" style="margin-left: 10px;">Rejestracja</v-btn>
      </div>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "SimplePage",

  methods: {
    goToLoginPage() {
      this.$router.push("/login");
    }
  }
};
</script>

<style scoped>

</style>
