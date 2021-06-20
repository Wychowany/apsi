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






  },


  methods: {
    createDocument() {
      this.$router.push("/app/documents/create");
    },

    editDocument(item) {
      this.$router.push("/app/documents/edit/" + item.id);
    },

    editAllowed(item) {
    console.log(this.documents.length)

   if (!this.elements_filled){
    if (this.documents.length>0){
              for (var i=0; i<this.documents.length;i++){
                  this.ids.push(this.documents[i].id);
                  api.get(this, '/documents/users-list', {id:this.documents[i].id},successResponse => {
                              this.roles.push(successResponse);

                              }, errorResponse => {
                                console.log(errorResponse);
                              });
              }
              }
              this.elements_filled=true;
   }
     var index=  this.ids.indexOf(item.id);
   if (this.roles.length ===0 || this.roles[0].length===0) {console.log("tu jestem");
   return item.isAuthor || item.accessType === 'UPDATE';

   }
    else {
    console.log(this.roles[index][0].accesstype);
    return item.isAuthor || item.accessType === 'UPDATE' || this.roles[index][0].accesstype=='UPDATE';}
    },

    readAllowed(item) {
      return !item.isAuthor && item.accessType === 'READ';
    }
  }
}
</script>

<style scoped>

</style>