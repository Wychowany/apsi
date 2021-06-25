<template>
  <v-dialog transition="dialog-top-transition" max-width="600" v-if="show" v-model="dialog">
    <template>
      <v-card>
        <v-toolbar color="primary" dark style="color: black">{{ header }}</v-toolbar>
        <v-card-text>
          <v-form class="ma-5">
            <v-layout row mt-5>
              <v-flex xs10>
                <v-text-field :label="label" type="text" class="ml-5" v-model="internalNameValue"></v-text-field>
              </v-flex>
            </v-layout>
            <v-layout row>
              <v-flex xs10>
                <v-autocomplete label="Typ dostępu" v-model="internalTypeValue" :items="accessTypes" item-text="name" item-value="id" clearable
                                class="mt-5 ml-5 mr-5"></v-autocomplete>
              </v-flex>
            </v-layout>
          </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="primary" style="color: black" :disabled="!internalNameValue || !internalTypeValue" @click="submit">Zatwierdź</v-btn>
          <v-btn color="primary" style="color: black" @click="dialog = false">Anuluj</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "EditRoleDialog",

  watch: {
    show(val) {
      this.dialog = val;
    },

    name(val) {
      this.internalNameValue = val;
    },

    accessType(val) {
      this.internalTypeValue = this.accessTypes.find(type => type.id === val);
    },

    dialog(val) {
      if (!val) {
        this.internalNameValue = '';
        this.internalTypeValue = '';
        this.$emit("close");
      }
    }
  },

  data() {
    return {
      internalNameValue: '',
      internalTypeValue: '',
      dialog: false,
      accessTypes: [
        { id: "READ", name: "Podgląd" },
        { id: "UPDATE", name: "Edycja" },
        { id: "DELETE", name: "Usuwanie" }
      ]
    };
  },

  methods: {
    submit() {
      this.$emit("save", this.internalNameValue, this.internalTypeValue);
      this.dialog = false;
    }
  },

  props: {
    header: String,
    label: String,
    name: {type: String, default: ''},
    accessType: {type: String, default: ''},
    show: {type: Boolean, default: false},
  }
}
</script>

<style scoped>

</style>