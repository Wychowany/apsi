<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Dokumenty</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="createDocument()" color="light" style="color: black">Dodaj dokument</v-btn>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="documents"
        :items-per-page="10"
        class="elevation-1">
      <template v-slot:item.actions="{ item }">
        <v-icon small @click="editDocument(item)" v-if="editAllowed(item)">edit</v-icon>
        <v-icon small @click="editDocument(item)" v-else-if="readAllowed(item)">description</v-icon>
        <v-icon small @click="deleteDocument(item)" class="ml-3" v-if="deleteAllowed(item)">delete</v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "DocumentsView",

  data () {
    return {
      headers: [
        {text: 'Nazwa', sortable: false, value: 'name'},
        {text: 'Autor', sortable: false, value: 'author'},
        {text: 'Akcje', sortable: false, value: 'actions'},
      ],
      documents: [],
    }
  },

  created() {
    api.get(this, '/documents/list', null,successResponse => {
      this.documents = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    createDocument() {
      this.$router.push("/app/documents/create");
    },

    editDocument(item) {
      this.$router.push("/app/documents/edit/" + item.id);
    },

    deleteDocument(item) {
      api.delete(this, "/documents/", {id: item.id}, () => {
            this.documents = this.documents.filter(d => d.id !== item.id);
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },

    deleteAllowed(item) {
      return item.isAuthor || item.documentAccessType === 'DELETE' || item.documentRoleAccessType === 'DELETE';
    },

    editAllowed(item) {
      return item.isAuthor || item.documentAccessType === 'UPDATE' || item.documentRoleAccessType === 'UPDATE'
          || item.documentAccessType === 'DELETE' || item.documentRoleAccessType === 'DELETE';
    },

    readAllowed(item) {
      return !item.isAuthor && (item.documentAccessType === 'READ' || item.documentRoleAccessType === 'READ');
    }
  }
}
</script>

<style scoped>

</style>