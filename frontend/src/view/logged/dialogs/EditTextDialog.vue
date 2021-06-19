<template>
  <v-dialog transition="dialog-top-transition" max-width="600" v-if="show" v-model="dialog">
    <template>
      <v-card>
        <v-toolbar color="primary" dark style="color: black">{{ header }}</v-toolbar>
        <v-card-text>
          <v-form class="ma-5">
            <v-layout>
              <v-flex xs10>
                <v-text-field :label="label" type="text" class="ml-5" v-model="internalValue"></v-text-field>
                <v-autocomplete v-if= "header == 'Dodawanie roli w dokumentach'" label="Typ dostępu" v-model="accessType" :items="accessTypes"
                                              item-text="name" item-value="id" clearable
                                              outlined class="mt-5 ml-5 mr-5"></v-autocomplete>
              </v-flex>
            </v-layout>
          </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="primary" style="color: black" :disabled="!internalValue" @click="submit(header)">Zatwierdź</v-btn>
          <v-btn color="primary" style="color: black" @click="dialog = false">Anuluj</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "EditTextDialog",

  watch: {
    show(val) {
      this.dialog = val;
    },

    value(val) {
      this.internalValue = val;
    },

    dialog(val) {
      if (!val) {
        this.internalValue = '';
        this.$emit("close");
      }
    }
  },

  data() {
    return {
      internalValue: '',
      dialog: false,
      accessType: null,
        accessTypes: [
                { id: "READ", name: "Podgląd" },
                { id: "UPDATE", name: "Edycja" }
              ]
    };
  },

  methods: {
    submit(header) {
    if (header == 'Dodawanie roli w dokumentach'){
      this.$emit("save", this.internalValue,this.accessType);}
      else{
      this.$emit("save", this.internalValue);
      }
      this.dialog = false;
    }
  },

  props: {
    header: String,
    label: String,
    value: {type: String, default: ''},
    show: {type: Boolean, default: false},
  }
}
</script>

<style scoped>

</style>