<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Dodawanie dokumentu</v-toolbar-title>
    </v-toolbar>
    <v-form class="ma-5">
      <v-layout>
        <v-flex xs1/>
        <v-flex xs7>
          <v-text-field label="Nazwa" type="text" class="mr-5" v-model="document.name"></v-text-field>
        </v-flex>
        <v-flex xs3>
          <v-text-field label="Wersja" type="text" class="ml-5" v-model="document.documentVersion"></v-text-field>
        </v-flex>
        <v-flex xs1/>
      </v-layout>
      <v-layout>
        <v-flex xs1/>
        <v-flex xs10>
          <v-textarea label="Opis" outlined v-model="document.description"></v-textarea>
        </v-flex>
        <v-flex xs1/>
      </v-layout>
    </v-form>
    <v-layout>
      <v-flex xs6 ml-4 mr-4>
        <v-toolbar dark color="lighter">
          <v-toolbar-title>Osoby funkcyjne</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn @click="documentUserDialog = true" color="light"
                 style="color: black" class="ma-5">Dodaj osobę</v-btn>
        </v-toolbar>
        <v-alert type="info" class="ma-5" v-if="document.documentUsers.length === 0">
          Brak dodanych osób.
        </v-alert>
        <div class="ma-5">
          <div v-for="(user, idx) in document.documentUsers" :key="'user-' + idx" class="mt-1">
            <strong class="mr-3"> {{ documentRoles.find(r => r.id === user.roleId).name }}: </strong>
            <span> {{ employees.find(e => e.id === user.userId).fullName }} </span>
            <v-icon small class="ml-4" color="red" @click="removeDocumentUser(idx)">delete</v-icon>
          </div>
        </div>
      </v-flex>
      <v-flex xs6 ml-4 mr-4>
        <v-toolbar dark color="lighter">
          <v-toolbar-title>Załączniki</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn @click="$refs.attachment.click()" color="light"
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
    <div class="mt-5">
      <v-btn @click="createDocument(false)" color="primary" style="color: black" class="ma-5">Dodaj dokument</v-btn>
      <v-btn @click="createDocument(true)" color="primary" style="color: black" class="ma-5 ml-1">Dodaj dokument i przejdź do edycji</v-btn>
      <v-btn @click="returnPage()" color="primary" style="color: black" class="ma-5 ml-1">Powrót</v-btn>
    </div>

    <input type="file" ref="attachment" v-show="false" v-on:change="handleUpload">

    <DocumentUserDialog :show="documentUserDialog" @close="documentUserDialog = false"
                        :document-roles="documentRoles" :employees="employees" @save="addDocumentUser"/>
  </div>
</template>

<script>
import {api} from "@/util/Api";
import {Files} from "@/util/Files";
import DocumentUserDialog from "@/view/logged/documents/DocumentUserDialog";

export default {
  name: "CreateDocumentView",
  components: {DocumentUserDialog},
  mixins: [Files],

  data() {
    return {
      creator: {},
      documentRoles: [],
      employees: [],
      documentUserDialog: false,
      document: {
        name: '',
        description: '',
        documentVersion: '1.0',
        documentUsers: [],
        files: [],
      }
    };
  },

  created() {
    api.get(this, '/users/logged', null, successResponse => {
      this.creator = successResponse;
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
    }, errorResponse => {
      console.log(errorResponse);
    });
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

    downloadAttachment(file) {
      this.downloadBase64File(file.encodedData, file.name);
    },

    removeAttachment(idx) {
      this.document.files.splice(idx, 1);
    },

    async handleUpload() {
      const file = this.$refs.attachment.files[0];
      let attachment = {
        id: null,
        name: file.name,
        format: file.type,
        length: file.size,
        encodedData: await this.fileToBase64(file)
      }
      this.document.files.push(attachment);
    },

    addDocumentUser(userId, roleId) {
      this.document.documentUsers.push({ userId, roleId });
      this.documentUserDialog = false;
    },

    removeDocumentUser(idx) {
      this.document.documentUsers.splice(idx, 1);
    },

    returnPage() {
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>

</style>