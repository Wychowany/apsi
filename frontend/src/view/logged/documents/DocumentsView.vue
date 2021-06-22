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
      roles:[],
      ids:[],
      elements_filled:false
    }
  },

  created() {
    api.get(this, '/documents/list', null,successResponse => {
      this.documents = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });
    api.get(this, '/documents/users-list', null,successResponse => {
          this.roles = successResponse;
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

    editAllowed(item) {
    var rola=-1;
   for (var i=0;i<this.roles.length;i++){
   if ((this.roles[i].doc_id/1)===(item.id/1)) rola=i;
   }
   if (rola <0) {

   return item.isAuthor || item.accessType === 'UPDATE';

   }
    else {
   return item.isAuthor || (item.accessType === 'UPDATE' && this.roles[rola]['accesstype']==='UPDATE');}
    },

    readAllowed(item) {
         var rola=-1;
           for (var i=0;i<this.roles.length;i++){
           if (this.roles[i].doc_id===item.id) rola=i;
           }
           if (rola <0) {
      return !item.isAuthor && item.accessType === 'READ' ;}
      else {
          return !item.isAuthor && (item.accessType === 'READ' || this.roles[rola]['accesstype']==='READ');}

      }



  }
}
</script>

<style scoped>

</style>