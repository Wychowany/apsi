<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Zarządzanie dostępem do dokumentu</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="documentAccessDialog = true" color="light" style="color: black">Dodaj użytkownika</v-btn>
    </v-toolbar>
    <v-layout row mt-5>
      <v-flex xs3/>
      <v-flex xs6>
        <v-alert type="info" class="ma-5" v-if="accessListLoaded && accessList.length === 0">
          Brak dodanych użytkowników.
        </v-alert>
        <v-list>
          <v-list-item v-for="access in accessList" :key="access.userId" two-line>
            <v-list-item-content>
              <v-list-item-title v-text="access.fullName"></v-list-item-title>
              <v-list-item-subtitle v-html="'Typ dostępu: ' + getAccessName(access.accessType)"></v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-icon>
              <v-icon small color="red" class="mr-4" @click="removeUser(access.userId)">delete</v-icon>
            </v-list-item-icon>
          </v-list-item>
        </v-list>
      </v-flex>
      <v-flex xs3/>
    </v-layout>

    <DocumentAccessDialog :show="documentAccessDialog" @close="documentAccessDialog = false"
                          :users="usersToAdd" @save="addUser"/>
  </div>
</template>

<script>
import {api} from "@/util/Api";
import DocumentAccessDialog from "@/view/logged/documents/DocumentAccessDialog";

export default {
  name: "DocumentAccessView",
  components: {DocumentAccessDialog},

  data() {
    return {
      users: [],
      usersLoaded: false,
      accessList: [],
      accessListLoaded: false,
      documentAccessDialog: false,
    };
  },

  computed: {
    usersToAdd() {
      if (!this.usersLoaded || !this.accessListLoaded) return [];
      let addedUsersIds = this.accessList.map(access => access.userId);
      return this.users.filter(user => !addedUsersIds.includes(user.id));
    }
  },

  created() {
    api.get(this, '/users/employees', null, successResponse => {
      this.users = successResponse;
      this.usersLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });

    api.get(this, '/document-access', {id: this.$route.params.id}, successResponse => {
      this.accessList = successResponse;
      this.accessListLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    addUser(userId, accessType) {
      api.post(this, '/document-access', {
            documentId: this.$route.params.id,
            userId: userId,
            accessType: accessType
          }, () => {
            this.documentAccessDialog = false;
            this.accessList.push({
              userId: userId,
              fullName: this.users.find(user => user.id === userId).fullName,
              accessType: accessType
            });
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },

    removeUser(userId) {
      api.delete(this, "/document-access", {userId: userId, documentId: this.$route.params.id}, () => {
            this.accessList = this.accessList.filter(access => access.userId !== userId);
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },

    getAccessName(accessType) {
      return accessType === 'READ' ? 'Podgląd' : 'Edycja';
    }
  }
}
</script>

<style scoped>

</style>