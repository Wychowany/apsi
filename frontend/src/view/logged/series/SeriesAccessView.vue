<template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Zarządzanie dostępem do zbioru</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="seriesAccessDialog = true" color="light" style="color: black">Dodaj użytkownika</v-btn>
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

    <SeriesAccessDialog :show="seriesAccessDialog" @close="seriesAccessDialog = false"
                          :users="usersToAdd" @save="addUser"/>
  </div>
</template>

<script>
import {api} from "@/util/Api";
import SeriesAccessDialog from "@/view/logged/series/SeriesAccessDialog";

export default {
  name: "SeriesAccessView",
  components: {SeriesAccessDialog},

  data() {
    return {
      users: [],
      usersLoaded: false,
      accessList: [],
      accessListLoaded: false,
      seriesAccessDialog: false,
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
    api.get(this, '/users/names', null, successResponse => {
      this.users = successResponse;
      this.usersLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });

    api.get(this, '/series-access', {id: this.$route.params.id}, successResponse => {
      this.accessList = successResponse;
      this.accessListLoaded = true;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    addUser(userId, accessType) {
      api.post(this, '/series-access', {
            seriesId: this.$route.params.id,
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
      api.delete(this, "/series-access", {userId: userId, seriesId: this.$route.params.id}, () => {
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