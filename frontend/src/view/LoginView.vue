<template>
  <v-main>
    <v-container fluid fill-height>
      <v-layout align-center justify-center>
        <v-flex xs12 sm8 md4>
          <v-card class="elevation-12" v-on:keyup.enter="login">
            <v-toolbar dark color="primary">
              <v-row>
                <v-col cols="12" style="display: block">
                  <strong class="ml-5" style="font-size: 18px">{{$t("LoginView.login")}}</strong>
                </v-col>
              </v-row>
            </v-toolbar>
            <v-card-text>
              <v-form>
                <v-text-field prepend-icon="person" name="email" label="E-mail"
                  v-model="credentials.email"
                  type="text"></v-text-field>
                <v-text-field prepend-icon="lock" name="password"
                  :label="$t('LoginView.password')"
                  v-model="credentials.password"
                  id="password"
                  autocomplete="on"
                  type="password">
                </v-text-field>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-row>

                <v-col cols="6"/>
                <v-col offset="1" cols="5">
                  <div style="float: right">
                    <v-btn color="primary" @click="login">
                      {{$t("LoginView.loginAction")}}
                    </v-btn>
                  </div>
                </v-col>
              </v-row>
            </v-card-actions>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>

	import {auth} from "../util/Auth";

	export default {
		name: "LoginView",
		data() {
			return {
				credentials: {
					email: "",
					password: "",
				},
				error_message: "",
				language: false,
			};
		},

		watch: {
			language: function (val) {
				if (val) {
					this.$language.setEnglish();
				} else {
					this.$language.setPolish();
				}
			},
		},

		methods: {

			clearMessage() {
				this.error_message = "";
			},

			async login() {
				await auth.login(this, this.credentials);
			},
		},
	};
</script>

<style scoped>

</style>
