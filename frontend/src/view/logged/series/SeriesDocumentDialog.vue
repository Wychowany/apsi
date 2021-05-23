<template>
  <v-dialog transition="dialog-top-transition" max-width="600" v-if="show" v-model="dialog">
    <template>
      <v-card>
        <v-toolbar color="primary" dark style="color: black">Dodawanie dokumentu do zbioru</v-toolbar>
        <v-card-text>
          <v-form class="ma-5">
            <v-layout row>
              <v-flex xs8>
                <v-autocomplete label="Dokument" v-model="documentId" :items="accessibleDocuments"
                                item-text="name" item-value="id" clearable
                                outlined class="ml-5 mr-5" @change="getDocumentVersions"></v-autocomplete>
              </v-flex>
              <v-flex xs4>
                <v-select background-color="white" :items="documentVersions" label="Wersja" outlined v-model="selectedDocumentVersion"/>
              </v-flex>
            </v-layout>
          </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="primary" style="color: black" :disabled="!documentId || !selectedDocumentVersion" @click="submit">Zatwierdź</v-btn>
          <v-btn color="primary" style="color: black" @click="dialog = false">Anuluj</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "SeriesDocumentDialog",

  data() {
    return {
      dialog: false,
      documentId: null,
      documentVersions: [],
      selectedDocumentVersion: null,
      documentDataId: null
    };
  },

  watch: {
    show(val) {
      this.dialog = val;
    },

    dialog(val) {
      if (!val) {
        this.documentId = null;
        this.selectedDocumentVersion = null;
        this.documentVersions = [];
        this.$emit("close");
      }
    }
  },

  methods: {
    submit() {
      api.get(this, '/documents', {id: this.documentId, version: this.selectedDocumentVersion}, successResponse => {
        this.$emit("save", successResponse.dataId, this.documentId, this.selectedDocumentVersion);
        this.dialog = false;
      }, errorResponse => {
        console.log(errorResponse);
      });
    },

    getDocumentVersions() {
        if(this.documentId == null){
          this.documentVersions = [];
          this.selectedDocumentVersion = null;
        }else{
          api.get(this, '/documents/versions', {id: this.documentId}, successResponse => {
            this.documentVersions = successResponse.map(item => item.version);
          }, errorResponse => {
            console.log(errorResponse);
          });
      }
    }
  },

  props: {
    show: {type: Boolean, default: false},
    accessibleDocuments: {type: Array},
  }
}
</script>

<style scoped>

</style>