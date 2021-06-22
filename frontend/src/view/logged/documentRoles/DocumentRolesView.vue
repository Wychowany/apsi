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

    <EditRoleDialog :header="'Dodawanie roli w dokumentach'" :show="createDocumentRoleDialog"
                    :label="'Nazwa roli'" @close="createDocumentRoleDialog = false" @save="saveDocumentRole"/>

    <EditRoleDialog :header="'Edycja roli w dokumentach'" :show="editDocumentRoleDialog"
                    :label="'Nazwa roli'" @close="editDocumentRoleDialog = false" @save="saveDocumentRole"
                    :name="editedRole ? editedRole.name : ''"
                    :access-type ="editedRole ? editedRole.accessType : '' " />
  </div>
</template>

<script>
import {api} from "@/util/Api";
import EditRoleDialog from "@/view/logged/documentRoles/EditRoleDialog";

export default {
  name: "DocumentRolesView",
  components: {EditRoleDialog},

  data () {
    return {
      headers: [
        {text: 'Nazwa', sortable: false, value: 'name'},
        {text: 'Dostęp', sortable: false, value: 'accessTypeName'},
        {text: 'Akcje', sortable: false, value: 'actions'},
      ],
      documentRoles: [],
      createDocumentRoleDialog: false,
      editDocumentRoleDialog: false,
      editedRole: null,
    }
  },

  created() {
    api.get(this, '/document-roles/list', null,successResponse => {
      this.documentRoles = successResponse;
      this.documentRoles.map(role => {
        let accessTypeName = role.accessType === "READ" ? "Podgląd" : role.accessType === "UPDATE" ? "Edycja" : "Usuwanie";
        this.$set(role, "accessTypeName", accessTypeName);
      });
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

    saveDocumentRole(name, accessType) {
        if (this.editedRole) {
          this.editDocumentRole(name, accessType);
        } else {
          this.createDocumentRole(name, accessType);
        }
      },

    editDocumentRole(name, accessType) {
      let edited = this.documentRoles.find(role => role.id === this.editedRole.id);
      api.put(this, '/document-roles', {id: this.editedRole.id, name: name, accessType: accessType}, () => {
          edited.name = name;
          edited.accessType = accessType;
          edited.accessTypeName = accessType === "READ" ? "Podgląd" : accessType === "UPDATE" ? "Edycja" : "Usuwanie";
          this.editDocumentRoleDialog = false;
        },
        errorResponse => {
          console.log(errorResponse);
        });
    },

    createDocumentRole(name, accessType) {
      api.post(this, '/document-roles', {name: name, accessType: accessType}, successResponse => {
            this.createDocumentRoleDialog = false;
            this.documentRoles.push({
              id: successResponse.id,
              name: name,
              accessType: accessType,
              accessTypeName: accessType === "READ" ? "Podgląd" : accessType === "UPDATE" ? "Edycja" : "Usuwanie"
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