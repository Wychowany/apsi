<template>
  <v-app id="inspire">
    <div>
      <v-navigation-drawer app permanent color="primary">
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="title">
              Imię nazwisko
            </v-list-item-title>
            <v-list-item-subtitle>
              Rola
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
        <v-btn @click="logout" color="light" class="button-logout">Wyloguj</v-btn>
      </v-app-bar>

      <v-main>
        <router-view/>
      </v-main>
    </div>
  </v-app>
</template>


<script>

import {auth} from "@/util/Auth";

export default {
  name: "MainView",

  data() {
    return {
      drawer: true,
      links: [
        { title: 'Dokumenty', address: '/app/documents', icon: 'description' },
        { title: 'Zbiory', address: '/app/series', icon: 'folder_open' },
        { title: 'Użytkownicy', address: '/app/users', icon: 'person' }
      ],
      mini: true,
    }
  },

  methods: {
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
