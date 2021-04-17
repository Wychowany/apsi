<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Edycja dokumentu</v-toolbar-title>
    </v-toolbar>
    <v-form class="ma-5" style="padding-left: 30px; width: 40%">
      <div class="ma-2">
        <strong class="mr-2">Autor:</strong> {{ document.author }}
      </div>
      <div class="ma-2">
        <strong class="mr-2">Nazwa:</strong> {{ document.name }}
      </div>
      <div class="ma-2">
        <strong class="mr-2">Opis:</strong> {{ document.description }}
      </div>
    </v-form>
    <v-btn @click="saveDocument()" color="primary" style="color: black" class="ma-5">Zapisz dokument</v-btn>
    <v-btn @click="returnPage()" color="primary" style="color: black" class="ma-5 ml-1">Powrót</v-btn>
  </div>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "EditDocumentView",

  data() {
    return {
      document: {
        name: '',
        description: '',
        author: '',
      }
    };
  },

  created() {
    api.get(this, '/documents', {id: this.$route.params.id}, successResponse => {
      this.document = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    saveDocument() {
      api.put(this, '/documents', this.document, () => {
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