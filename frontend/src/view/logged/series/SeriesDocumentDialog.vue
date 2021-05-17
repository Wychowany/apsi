<template>
  <v-dialog transition="dialog-top-transition" max-width="600" v-if="show" v-model="dialog">
    <template>
      <v-card>
        <v-toolbar color="primary" dark style="color: black">Dodawanie dokumentu do zbioru</v-toolbar>
        <v-card-text>
          <v-form class="ma-5">
            <v-layout row>
              <v-flex xs10>
                <v-autocomplete label="Dokument" v-model="documentId" :items="accessibleDocuments"
                                item-text="name" item-value="id" clearable
                                outlined class="ml-5 mr-5"></v-autocomplete>
              </v-flex>
            </v-layout>
          </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="primary" style="color: black" :disabled="!documentId" @click="submit">Zatwierdź</v-btn>
          <v-btn color="primary" style="color: black" @click="dialog = false">Anuluj</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "SeriesDocumentDialog",

  data() {
    return {
      dialog: false,
      documentId: null,
    };
  },

  watch: {
    show(val) {
      this.dialog = val;
    },

    dialog(val) {
      if (!val) {
        this.documentId = null;
        this.$emit("close");
      }
    }
  },

  methods: {
    submit() {
      this.$emit("save", this.documentId);
      this.dialog = false;
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