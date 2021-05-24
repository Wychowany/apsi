<template>
  <div class="ma-3" v-if="dataLoaded">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Edycja zbioru</v-toolbar-title>
      <v-spacer/>
    </v-toolbar>
    <v-layout>
      <v-flex xs10>
        <v-form class="ma-5" style="padding-left: 30px">
          <div class="ma-2">
            <strong class="mr-2">Autor zbioru:</strong> {{ series.creator }}
          </div>
          <div class="ma-2">
            <strong class="mr-2">Autor wersji:</strong> {{ series.author }}
          </div>
          <div class="ma-2">
            <strong class="mr-2">Nazwa:</strong> {{ series.name }}
          </div>
          <div class="ma-2">
            <strong class="mr-2">Opis:</strong> {{ series.description }}
          </div>
        </v-form>
      </v-flex>
      <v-flex xs2 mt-5 mr-5>
        <v-select background-color="white" :items="versions" label="Wersja" outlined v-model="series.seriesVersion"/>
      </v-flex>
    </v-layout>

    <v-layout>
      <v-flex xs12 ml-4 mr-4>
        <v-toolbar dark color="lighter">
          <v-toolbar-title>Dokumenty</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn @click="seriesDocumentDialog = true" color="light"
                 style="color: black" class="ma-5">Dodaj dokument</v-btn>
        </v-toolbar>
        <v-alert type="info" class="ma-5" v-if="series.seriesDocuments.length === 0">
          Brak dodanych dokumentów.
        </v-alert>
        <div class="ma-5">
          <div v-for="(document, idx) in series.seriesDocuments" :key="'document-' + idx" class="mt-1 ml-5">
            <strong class="mr-2">{{ idx + 1 }}.</strong>
            <span> {{ accessibleDocuments.find(d => d.id === document.documentId).name + ", wersja: " + document.version }} </span>
            <v-icon small class="ml-4" color="blue" @click="openDocument(document)">description</v-icon>
            <v-icon small class="ml-4" color="red" @click="removeDocument(idx)">delete</v-icon>
          </div>
        </div>
      </v-flex>
    </v-layout>

    <v-btn @click="saveSeriesDialog = true" color="primary" style="color: black" class="ma-5">Zapisz nową wersję</v-btn>
    <v-btn @click="returnPage()" color="primary" style="color: black" class="ma-5 ml-1">Powrót</v-btn>

    <EditTextDialog :header="'Dodanie nowej wersji zbioru'" :show="saveSeriesDialog" :label="'Wersja'"
                    @close="saveSeriesDialog = false" @save="saveSeries"/>

    <SeriesDocumentDialog :show="seriesDocumentDialog" @close="seriesDocumentDialog = false"
                          :accessibleDocuments="accessibleDocuments" @save="addDocumentToSeries"
                          :selectedDocuments="series.seriesDocuments"/>
  </div>
</template>

<script>
import {api} from "@/util/Api";
import EditTextDialog from "@/view/logged/dialogs/EditTextDialog";
import SeriesDocumentDialog from "@/view/logged/series/SeriesDocumentDialog";

export default {
  name: "EditSeriesView",
  components: {EditTextDialog, SeriesDocumentDialog},

  data() {
    return {
      series: {
        name: '',
        description: '',
        creator: '',
        seriesVersion: '',
        author: '',
        seriesDocuments: [],
      },
      versions: [],
      versionsLoaded: false,
      seriesLoaded: false,
      accessibleDocumentsLoaded: false,
      saveSeriesDialog: false,
      seriesDocumentDialog: false,
      accessibleDocuments: [],
    };
  },
  computed: {
    dataLoaded() {
      return this.versionsLoaded && this.seriesLoaded && this.accessibleDocumentsLoaded;
    }
  },

  watch: {
    "series.seriesVersion"(val) {
      api.get(this, '/series', {id: this.$route.params.id, version: val}, successResponse => {
        this.series = successResponse;
        this.seriesLoaded = true;
      }, errorResponse => {
        console.log(errorResponse);
      });
    }
  },

  created() {

    api.get(this, '/documents/list', null,successResponse => {
      this.accessibleDocuments = successResponse;
      this.accessibleDocumentsLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });

    api.get(this, '/series/versions', {id: this.$route.params.id}, successResponse => {
      this.versions = successResponse.map(item => item.version);
      this.versionsLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });

    api.get(this, '/series', {id: this.$route.params.id}, successResponse => {
      this.series = successResponse;
      this.seriesLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    saveSeries(version) {
      let requestData = this.prepareRequest(version);
      api.put(this, '/series', requestData, () => {
            this.saveSeriesDialog = false;
            this.versions.push(version);
            this.series.seriesVersion = version;
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },

    prepareRequest(version) {
      return {
        id: this.$route.params.id,
        seriesVersion: version,
        documents: this.series.seriesDocuments
      }
    },

    addDocumentToSeries(documentDataId, documentId, version) {
      this.series.seriesDocuments.push({documentDataId, documentId, version});
      this.seriesDocumentDialog = false;
    },

    openDocument(document) {
      let routeData = this.$router.resolve("/app/documents/edit/" + document.documentId);
      window.open(routeData.href, '_blank');
    },

    removeDocument(idx) {
      this.series.seriesDocuments.splice(idx, 1);
    },

    returnPage() {
      this.$router.push("/app/series");
    },
  }


}
</script>

<style scoped>

</style>