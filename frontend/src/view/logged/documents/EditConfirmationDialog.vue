<template>
  <v-dialog transition="dialog-top-transition" max-width="600" v-if="show" v-model="dialog">
    <template>
      <v-card>
        <v-toolbar color="primary" dark style="color: black">{{ header }}</v-toolbar>
        <v-card-text>
          <v-form class="ma-5">
            <v-layout>
              <v-flex xs5>
                <v-text-field label="Wersja" type="text" class="ml-5" v-model="version"></v-text-field>
              </v-flex>
            </v-layout>
          </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="primary" style="color: black" :disabled="!version" @click="submit">Zatwierdź</v-btn>
          <v-btn color="primary" style="color: black" @click="dialog = false">Anuluj</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "EditConfirmationDialog",

  watch: {
    show(val) {
      this.dialog = val;
    },

    dialog(val) {
      if (!val) {
        this.version = '';
        this.$emit("close");
      }
    }
  },

  data() {
    return {
      version: '',
      dialog: false,
    };
  },

  methods: {
    submit() {
      this.$emit("save", this.version);
      this.dialog = false;
    }
  },

  props: {
    header: String,
    message: String,
    show: {type: Boolean, default: false},
  }
}
</script>

<style scoped>

</style>