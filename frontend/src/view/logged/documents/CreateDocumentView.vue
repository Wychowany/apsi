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
          <v-toolbar-title>Użytkownicy</v-toolbar-title>
        </v-toolbar>
        <div class="ma-4">
          <strong>Kontroler:</strong>
          <v-autocomplete label="Kontroler" v-model="document.controllerId" :items="employees"
                          item-text="fullName" item-value="id" clearable
                          outlined class="mt-5 ml-5 mr-5"></v-autocomplete>
        </div>
        <div class="ma-4">
          <strong>Recenzent:</strong>
          <v-autocomplete label="Recenzent" v-model="document.reviewerId" :items="employees"
                          item-text="fullName" item-value="id" clearable
                          outlined class="mt-5 ml-5 mr-5"></v-autocomplete>
        </div>
        <div class="ma-4">
          <strong>Osoba zatwierdzająca:</strong>
          <v-autocomplete label="Osoba zatwierdzająca" v-model="document.approverId" :items="employees"
                          item-text="fullName" item-value="id" clearable
                          outlined class="mt-5 ml-5 mr-5"></v-autocomplete>
        </div>
        <div class="ma-4">
          <strong>Osoba odbierająca:</strong>
          <v-autocomplete label="Osoba odbierająca" v-model="document.receiverId" :items="employees"
                          item-text="fullName" item-value="id" clearable
                          outlined class="mt-5 ml-5 mr-5"></v-autocomplete>
        </div>
      </v-flex>
      <v-flex xs6 ml-4 mr-4>
        <v-toolbar dark color="lighter">
          <v-toolbar-title>Załączniki</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn :disabled="false" @click="$refs.attachment.click()" color="light"
                 style="color: black" class="ma-5">Dodaj załącznik</v-btn>
        </v-toolbar>
        <div class="ma-5">
          <div v-for="(file, idx) in document.files" :key="'file-' + idx">
            <strong class="mr-2">{{ idx + 1 }}.</strong> {{ file.name }}
            <v-icon small class="ml-4" color="red" @click="removeAttachment(idx)">delete</v-icon>
          </div>
        </div>
      </v-flex>
    </v-layout>
    <v-btn @click="createDocument(false)" color="primary" style="color: black" class="ma-5">Dodaj dokument</v-btn>
    <v-btn @click="createDocument(true)" color="primary" style="color: black" class="ma-5 ml-1">Dodaj dokument i przejdź do edycji</v-btn>
    <v-btn @click="returnPage()" color="primary" style="color: black" class="ma-5 ml-1">Powrót</v-btn>

    <input type="file" ref="attachment" v-show="false" v-on:change="handleUpload">
  </div>
</template>

<script>
import {api} from "@/util/Api";
import {Files} from "@/util/Files";

export default {
  name: "CreateDocumentView",

  mixins: [Files],

  data() {
    return {
      creator: {},
      employees: [],
      document: {
        name: '',
        description: '',
        documentVersion: '1.0',
        controllerId: null,
        reviewerId: null,
        approverId: null,
        receiverId: null,
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

    returnPage() {
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>

</style>