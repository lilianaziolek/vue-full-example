<template>
  <v-flex>
    <v-card v-if="loggedIn">
      <v-card-title>
        You are logged in as {{user}}
      </v-card-title>
      <v-card-actions>
        <v-btn @click="logout">Logout</v-btn>
      </v-card-actions>
    </v-card>
    <v-card v-else>
      <div id="firebaseui-auth-container"></div>
    </v-card>

    <v-card>
      <v-toolbar color="primary" dark>
        <v-toolbar-title v-if="!loggedIn">Sign in buttons should appear above</v-toolbar-title>
        <v-toolbar-title v-else>You are logged in as {{user}}</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>
      <v-expansion-panel>
        <v-expansion-panel-content>
          <div slot="header">Your welcome message (expand panel to see)</div>
          <v-card>
            <v-layout wrap>
              <v-flex xs12 class="elevation-1">
                {{welcomeMessage}}
              </v-flex>
            </v-layout>
          </v-card>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-card>
  </v-flex>
</template>

<script>

  export default {
    async fetch({store}) {
      await store.dispatch('home/fetchWelcomeMessage');
    },
    mounted() {
      if (!this.loggedIn) {
        this.$firebaseUi.start('#firebaseui-auth-container', this.$firebaseUiConfig);
      }
    },
    components: {},
    watch: {
      loggedIn: function (oldValue, newValue) {
        this.$store.dispatch('home/fetchWelcomeMessage');
      }
    },
    computed: {
      welcomeMessage() {
        return this.$store.state.home.welcomeMessage;
      },
      loggedIn() {
        return this.$store.getters['login/loggedIn']
      },
      user() {
        return this.$store.state.login.loginDetails;
      }
    },
    methods: {
      async logout() {
        await this.$firebase.auth().signOut();
        await this.$store.dispatch("login/logout");
        await this.$firebaseUi.start('#firebaseui-auth-container', this.$firebaseUiConfig);
      }
    }
  }
</script>

<style>

</style>
