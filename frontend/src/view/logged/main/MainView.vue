<template>
  <v-app id="inspire">
    <div>
      <v-navigation-drawer app permanent color="primary">
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="title">
              {{user.name}} {{user.surname}}
            </v-list-item-title>
            <v-list-item-subtitle>
              {{user.systemRole}}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>

        <v-list dense nav>
          <v-list-item v-for="link in links" :key="link.title" link :to="link.address">
            <v-list-item-icon>
              <v-icon>{{ link.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>{{ link.title }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>

      <v-app-bar fixed class="no-print navigation-top" id="nav-top" color="primary">
        <v-spacer></v-spacer>
        <v-btn @click="logout" color="light" style="color: black">Wyloguj</v-btn>
      </v-app-bar>

      <v-main>
        <router-view/>
      </v-main>
    </div>
  </v-app>
</template>


<script>

import {auth} from "@/util/Auth";
import {api} from "@/util/Api";

export default {
  name: "MainView",

  data() {
    return {
      drawer: true,
      user: {},
      links: [
        { title: 'Dokumenty', address: '/app/documents', icon: 'description' },
        { title: 'Moje dokumenty', address: '/app/documents/own', icon: 'description' },
        { title: 'Zbiory', address: '/app/series', icon: 'folder_open' },
        { title: 'Moje zbiory', address: '/app/series/own', icon: 'folder_open' }
      ],
    }
  },

  created() {
    api.get(this, '/users/logged', null, successResponse => {
      this.user = successResponse;
      if (this.adminLogged()) {
        this.links.push(
            { title: 'Role w dokumentach', address: '/app/document-roles', icon: 'portrait' },
            { title: 'Użytkownicy', address: '/app/users', icon: 'person' }
        )
      }
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    adminLogged() {
      return this.user.systemRole === "Administrator";
    },

    logout() {
      auth.logout(this);
    },
  },
};
</script>

<style scoped>
  .navigation-top {
    overflow-x: auto;
    overflow-y: hidden;
    position: sticky !important;
  }
</style>
