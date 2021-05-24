<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Dodawanie zbioru</v-toolbar-title>
    </v-toolbar>
    <v-form class="ma-5">
      <v-layout>
        <v-flex xs1/>
        <v-flex xs7>
          <v-text-field label="Nazwa" type="text" class="mr-5" v-model="series.name"></v-text-field>
        </v-flex>
        <v-flex xs3>
          <v-text-field label="Wersja" type="text" class="ml-5" v-model="series.seriesVersion"></v-text-field>
        </v-flex>
        <v-flex xs1/>
      </v-layout>
      <v-layout>
        <v-flex xs1/>
        <v-flex xs10>
          <v-textarea label="Opis" outlined v-model="series.description"></v-textarea>
        </v-flex>
        <v-flex xs1/>
      </v-layout>
    </v-form>
    <v-layout>
      <v-flex xs12 ml-4 mr-4>
        <v-toolbar dark color="lighter">
          <v-toolbar-title>Dokumenty</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn @click="seriesDocumentDialog = true" color="light"
                 style="color: black" class="ma-5">Dodaj dokument</v-btn>
        </v-toolbar>
        <v-alert type="info" class="ma-5" v-if="series.documents.length === 0">
          Brak dodanych dokumentów.
        </v-alert>
        <div class="ma-5">
          <div v-for="(document, idx) in series.documents" :key="'document-' + idx" class="mt-1 ml-5">
            <strong class="mr-2">{{ idx + 1 }}.</strong>
            <span> {{ accessibleDocuments.find(d => d.id === document.documentId).name + ", wersja: " + document.version }} </span>
            <v-icon small class="ml-4" color="red" @click="removeDocument(idx)">delete</v-icon>
          </div>
        </div>
      </v-flex>
    </v-layout>
    <div class="mt-5">
      <v-btn @click="createSeries(false)" color="primary" style="color: black" class="ma-5">Dodaj zbiór</v-btn>
      <v-btn @click="createSeries(true)" color="primary" style="color: black" class="ma-5 ml-1">Dodaj zbiór i przejdź do edycji</v-btn>
      <v-btn @click="returnPage()" color="primary" style="color: black" class="ma-5 ml-1">Powrót</v-btn>
    </div>

    <SeriesDocumentDialog :show="seriesDocumentDialog" @close="seriesDocumentDialog = false"
                        :accessibleDocuments="accessibleDocuments" @save="addDocumentToSeries"
                        :selectedDocuments="series.documents"/>
  </div>
</template>

<script>
import {api} from "@/util/Api";
import SeriesDocumentDialog from "@/view/logged/series/SeriesDocumentDialog";

export default {
  name: "CreateSeriesView",
  components: {SeriesDocumentDialog},

  data() {
    return {
      creator: {},
      accessibleDocuments: [],
      seriesDocumentDialog: false,
      series: {
        name: '',
        description: '',
        seriesVersion: '1.0',
        documents: [],
      }
    };
  },

  created() {
    api.get(this, '/users/logged', null, successResponse => {
      this.creator = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });

    api.get(this, '/documents/list', null,successResponse => {
      this.accessibleDocuments = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    createSeries(navigateToEdition) {
      api.post(this, '/series', this.series, successResponse => {
            if (navigateToEdition) {
              this.$router.push("/app/series/edit/" + successResponse.id);
            } else {
              this.$router.push("/app/series/own");
            }
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },

    addDocumentToSeries(documentDataId, documentId, version) {
      this.series.documents.push({documentDataId, documentId, version});
      this.seriesDocumentDialog = false;
    },

    removeDocument(idx) {
      this.series.documents.splice(idx, 1);
    },

    returnPage() {
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>

</style>