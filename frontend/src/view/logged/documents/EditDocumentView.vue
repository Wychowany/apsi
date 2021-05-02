<template>
  <div class="ma-3" v-if="dataLoaded">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Edycja dokumentu</v-toolbar-title>
      <v-spacer/>
    </v-toolbar>
    <v-layout>
      <v-flex xs10>
        <v-form class="ma-5" style="padding-left: 30px">
          <div class="ma-2">
            <strong class="mr-2">Autor dokumentu:</strong> {{ document.creator }}
          </div>
          <div class="ma-2">
            <strong class="mr-2">Autor wersji:</strong> {{ document.author }}
          </div>
          <div class="ma-2">
            <strong class="mr-2">Nazwa:</strong> {{ document.name }}
          </div>
          <div class="ma-2">
            <strong class="mr-2">Opis:</strong> {{ document.description }}
          </div>
        </v-form>
      </v-flex>
      <v-flex xs2 mt-5 mr-5>
        <v-select background-color="white" :items="versions" label="Wersja" outlined v-model="document.documentVersion"/>
      </v-flex>
    </v-layout>

    <v-layout>
      <v-flex xs6 ml-4 mr-4>
        <v-toolbar dark color="lighter">
          <v-toolbar-title>Użytkownicy</v-toolbar-title>
        </v-toolbar>
        <v-alert type="info" class="ma-5" v-if="document.documentUsers.length === 0">
          Brak dodanych użytkowników.
        </v-alert>
        <div class="ma-5">
          <div v-for="(user, idx) in document.documentUsers" :key="'user-' + idx" class="mt-1">
            <strong class="mr-3"> {{ user.role }}: </strong>
            <span> {{ user.fullName }} </span>
          </div>
        </div>
      </v-flex>
      <v-flex xs6 ml-4 mr-4>
        <v-toolbar dark color="lighter">
          <v-toolbar-title>Załączniki</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn :disabled="false" @click="$refs.attachment.click()" color="light"
                 style="color: black" class="ma-5">Dodaj załącznik</v-btn>
        </v-toolbar>
        <v-alert type="info" class="ma-5" v-if="document.files.length === 0">
          Brak dodanych załączników.
        </v-alert>
        <div class="ma-5">
          <div v-for="(file, idx) in document.files" :key="'file-' + idx" class="mt-1">
            <strong class="mr-2">{{ idx + 1 }}.</strong> {{ file.name }}
            <v-icon small class="ml-4" color="blue" @click="downloadAttachment(file)">cloud_download</v-icon>
            <v-icon small class="ml-4" color="red" @click="removeAttachment(idx)">delete</v-icon>
          </div>
        </div>
      </v-flex>
    </v-layout>

    <v-btn @click="saveDocumentDialog = true" color="primary" style="color: black" class="ma-5">Zapisz nową wersję</v-btn>
    <v-btn @click="returnPage()" color="primary" style="color: black" class="ma-5 ml-1">Powrót</v-btn>

    <input type="file" ref="attachment" v-show="false" v-on:change="handleUpload">

    <EditTextDialog :header="'Dodanie nowej wersji dokumentu'" :show="saveDocumentDialog" :label="'Wersja'"
                            @close="saveDocumentDialog = false" @save="saveDocument"/>
  </div>
</template>

<script>
import {api} from "@/util/Api";
import {Files} from "@/util/Files";
import EditTextDialog from "@/view/logged/dialogs/EditTextDialog";


export default {
  name: "EditDocumentView",
  components: {EditTextDialog},
  mixins: [Files],

  data() {
    return {
      document: {
        name: '',
        description: '',
        creator: '',
        documentVersion: '',
        status: '',
        author: '',
        files: [],
      },
      documentRoles: [],
      employees: [],
      versions: [],
      versionsLoaded: false,
      documentLoaded: false,
      employeesLoaded: false,
      saveDocumentDialog: false,
    };
  },

  computed: {
    dataLoaded() {
      return this.versionsLoaded && this.documentLoaded && this.employeesLoaded;
    }
  },

  watch: {
    "document.documentVersion"(val) {
      api.get(this, '/documents', {id: this.$route.params.id, version: val}, successResponse => {
        this.document = successResponse;
        this.documentLoaded = true;
      }, errorResponse => {
        console.log(errorResponse);
      });
    }
  },

  created() {
    api.get(this, '/documents/versions', {id: this.$route.params.id}, successResponse => {
      this.versions = successResponse.map(item => item.version);
      this.versionsLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });

    api.get(this, '/documents', {id: this.$route.params.id}, successResponse => {
      this.document = successResponse;
      this.documentLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });

    api.get(this, '/document-roles/list', null,successResponse => {
      this.documentRoles = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });

    api.get(this, '/users/employees', null, successResponse => {
      this.employees = successResponse;
      this.employeesLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    saveDocument(version) {
      let requestData = this.prepareRequest(version);
      api.put(this, '/documents', requestData, () => {
            this.saveDocumentDialog = false;
            this.versions.push(version);
            this.document.documentVersion = version;
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },

    prepareRequest(version) {
      return {
        id: this.$route.params.id,
        documentVersion: version,
        files: this.document.files
      }
    },

    removeAttachment(idx) {
      this.document.files.splice(idx, 1);
    },

    downloadAttachment(file) {
      this.downloadBase64File(file.encodedData, file.name);
    },

    returnPage() {
      this.$router.push("/app/documents");
    },

    async handleUpload() {
      const file = this.$refs.attachment.files[0];
      let attachment = {
        id: null,
        name: file.name,
        format: file.format,
        length: file.size,
        encodedData: await this.fileToBase64(file)
      }
      this.document.files.push(attachment);
    }
  }
}
</script>

<style scoped>

</style>