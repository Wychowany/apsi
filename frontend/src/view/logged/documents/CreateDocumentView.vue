<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Dodawanie dokumentu</v-toolbar-title>
    </v-toolbar>
    <v-form class="ma-5" style="padding-left: 30px; width: 40%">
      <v-text-field label="Nazwa" type="text" v-model="document.name"></v-text-field>
      <v-textarea label="Opis" outlined v-model="document.description"></v-textarea>
    </v-form>
    <v-btn @click="createDocument(false)" color="primary" style="color: black" class="ma-5">Dodaj dokument</v-btn>
    <v-btn @click="createDocument(true)" color="primary" style="color: black" class="ma-5 ml-1">Dodaj dokument i przejdź do edycji</v-btn>
    <v-btn @click="returnPage()" color="primary" style="color: black" class="ma-5 ml-1">Powrót</v-btn>
  </div>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "CreateDocumentView",

  data() {
    return {
      document: {
        name: '',
        description: '',
      }
    };
  },

  methods: {
    createDocument(navigateToEdition) {
      api.post(this, '/documents', this.document, successResponse => {
            if (navigateToEdition) {
              this.$router.push("/app/documents/edit/" + successResponse.id);
            } else {
              this.$router.push("/app/documents/own");
            }
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