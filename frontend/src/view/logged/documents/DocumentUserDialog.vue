<template>
  <v-dialog transition="dialog-top-transition" max-width="600" v-if="show" v-model="dialog">
    <template>
      <v-card>
        <v-toolbar color="primary" dark style="color: black">Dodawanie użytkownika dokumentu</v-toolbar>
        <v-card-text>
          <v-form class="ma-5">
            <v-layout row>
              <v-flex xs10>
                <v-autocomplete label="Rola" v-model="roleId" :items="documentRoles"
                                item-text="name" item-value="id" clearable
                                outlined class="mt-5 ml-5 mr-5"></v-autocomplete>
              </v-flex>
            </v-layout>
            <v-layout row>
              <v-flex xs10>
                <v-autocomplete label="Użytkownik" v-model="userId" :items="employees"
                                item-text="fullName" item-value="id" clearable
                                outlined class="ml-5 mr-5"></v-autocomplete>
              </v-flex>
            </v-layout>
          </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="primary" style="color: black" :disabled="!roleId || !userId" @click="submit">Zatwierdź</v-btn>
          <v-btn color="primary" style="color: black" @click="dialog = false">Anuluj</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "DocumentUserDialog",

  data() {
    return {
      dialog: false,
      roleId: null,
      userId: null,
    };
  },

  watch: {
    show(val) {
      this.dialog = val;
    },

    dialog(val) {
      if (!val) {
        this.userId = null;
        this.roleId = null;
        this.$emit("close");
      }
    }
  },

  methods: {
    submit() {
      this.$emit("save", this.userId, this.roleId);
      this.dialog = false;
    }
  },

  props: {
    show: {type: Boolean, default: false},
    documentRoles: {type: Array},
    employees: {type: Array},
  }
}
</script>

<style scoped>

</style>