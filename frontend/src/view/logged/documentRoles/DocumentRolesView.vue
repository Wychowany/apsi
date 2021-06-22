<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Role w dokumentach</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="createDocumentRoleDialog = true" color="light" style="color: black">Dodaj rolę</v-btn>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="documentRoles"
        :items-per-page="10"
        class="elevation-1">
      <template v-slot:item.actions="{ item }">
        <v-icon small @click="prepareRoleEdition(item)">edit</v-icon>
        <v-icon small class="ml-3" @click="deleteDocumentRole(item)">delete</v-icon>
      </template>
    </v-data-table>

    <EditTextDialog :header="'Dodawanie roli w dokumentach'" :show="createDocumentRoleDialog"
                    :label="'Nazwa roli'" @close="createDocumentRoleDialog = false" @save="saveDocumentRole2"/>

    <EditTextDialog :header="'Edycja roli w dokumentach'" :show="editDocumentRoleDialog"
                    :label="'Nazwa roli'" @close="editDocumentRoleDialog = false" @save="saveDocumentRole2"
                    :value="editedRole ? editedRole.name : ''" :value2 ="editedRole ? editedRole.accesstype : '' " />
  </div>
</template>

<script>
import {api} from "@/util/Api";
import EditTextDialog from "@/view/logged/dialogs/EditTextDialog";

export default {
  name: "DocumentRolesView",
  components: {EditTextDialog},

  data () {
    return {
      headers: [
        {text: 'Nazwa', sortable: false, value: 'name'},
        {text: 'Dostep', sortable: false, value: 'accesstype'},
        {text: 'Akcje', sortable: false, value: 'actions'},
      ],
      documentRoles: [],
      createDocumentRoleDialog: false,
      editDocumentRoleDialog: false,
      editedRole: null,
      editedAccess: null
    }
  },

  created() {
    api.get(this, '/document-roles/list', null,successResponse => {
      this.documentRoles = successResponse;

    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  watch: {
    editDocumentRoleDialog(val) {
      if (!val) this.editedRole = null;
    }
  },

  methods: {
    prepareRoleEdition(item) {
      this.editedRole = item;
      this.editDocumentRoleDialog = true;


    },

    saveDocumentRole(name) {
      if (this.editedRole) {
        this.editDocumentRole(name);
      } else {
        this.createDocumentRole(name);
      }
    },
    saveDocumentRole2(name,accessType) {
          if (this.editedRole) {
            this.editDocumentRole(name, accessType);
          } else {
            this.createDocumentRole(name, accessType);
          }
        },

    editDocumentRole(name,accessType) {
      let edited = this.documentRoles.find(role => role.id === this.editedRole.id);
      api.put(this, '/document-roles', {id: this.editedRole.id, name: name, accesstype: accessType}, () => {
          edited.name = name;
          edited.accesstype = accessType;
          this.editDocumentRoleDialog = false;
        },
        errorResponse => {
          console.log(errorResponse);
        });
    },

    createDocumentRole(name,accessType) {
      api.post(this, '/document-roles', {name: name + accessType, accesstype: accessType}, successResponse => {
            this.createDocumentRoleDialog = false;
            this.documentRoles.push({
              id: successResponse.id,
              name: name,
              accesstype:accessType
            });
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },

    deleteDocumentRole(item) {
      api.delete(this, "/document-roles/", {id: item.id}, () => {
            this.documentRoles = this.documentRoles.filter(u => u.id !== item.id);
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },
  }
}
</script>

<style scoped>

</style>