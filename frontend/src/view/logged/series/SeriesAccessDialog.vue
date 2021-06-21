<template>
  <v-dialog transition="dialog-top-transition" max-width="600" v-if="show" v-model="dialog">
    <template>
      <v-card>
        <v-toolbar color="primary" dark style="color: black">Nadanie dostępu do zbioru</v-toolbar>
        <v-card-text>
          <v-form class="ma-5">
            <v-layout row>
              <v-flex xs10>
                <v-autocomplete label="Typ dostępu" v-model="accessType" :items="accessTypes"
                                item-text="name" item-value="id" clearable
                                outlined class="mt-5 ml-5 mr-5"></v-autocomplete>
              </v-flex>
            </v-layout>
            <v-layout row>
              <v-flex xs10>
                <v-autocomplete label="Użytkownik" v-model="userId" :items="users"
                                item-text="fullName" item-value="id" clearable
                                outlined class="ml-5 mr-5"></v-autocomplete>
              </v-flex>
            </v-layout>
          </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="primary" style="color: black" :disabled="!accessType || !userId" @click="submit">Zatwierdź</v-btn>
          <v-btn color="primary" style="color: black" @click="dialog = false">Anuluj</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "SeriesAccessDialog",

  data() {
    return {
      dialog: false,
      accessType: null,
      userId: null,
      accessTypes: [
        { id: "READ", name: "Podgląd" },
        { id: "UPDATE", name: "Edycja" }
      ]
    };
  },

  watch: {
    show(val) {
      this.dialog = val;
    },

    dialog(val) {
      if (!val) {
        this.userId = null;
        this.accessType = null;
        this.$emit("close");
      }
    }
  },

  methods: {
    submit() {
      this.$emit("save", this.userId, this.accessType);
      this.dialog = false;
    }
  },

  props: {
    show: {type: Boolean, default: false},
    users: {type: Array},
  }
}
</script>

<style scoped>

</style>