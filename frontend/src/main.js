import Vue from "vue";
import "./plugins/vuetify";
import App from "./App.vue";
import router from "./router/routes.js";
import store from "./store/store.js";
import VeeValidate, {Validator} from "vee-validate";
import axios from "axios";
import {i18n} from "./i18n/i18n";
import pl from "vuetify/es5/locale/pl";
import {role} from "./util/Role";
import {rule} from "./util/Validation";
import Vuetify from "vuetify";
import {vuetify} from "./plugins/vuetify";
import VuetifyConfirm from "vuetify-confirm";
import "material-design-icons-iconfont/dist/material-design-icons.css";
import {number} from "./util/Number";
import VueMask from "v-mask";
import VueRx from "vue-rx";

import VuejsClipper from "vuejs-clipper/dist/vuejs-clipper.umd";
import "vuejs-clipper/dist/vuejs-clipper.css";

Vue.use(VueRx);
Vue.use(VuejsClipper);

Vue.use(VueMask);
Vue.use(VuetifyConfirm, {vuetify});

Vue.use(Vuetify);

Vue.use(VuejsClipper, {
	components: {
		clipperBasic: true,
		clipperPreview: true
	}
})

Validator.localize("pld", pl);
Vue.config.productionTip = false;

Vue.use(VeeValidate, {
	locale: "pl",
	fieldsBagName: "inputs ", errorBagName: "vErrors",
});

// Autoryzacja widoków po stronie frontedu
router.beforeEach((to, from, next) => {
	if (to.matched.some(record => record.meta.requiresAuth) && localStorage.getItem("access_token") == null) {
		next({
			path: "/login",
			query: {next: to.fullPath},
		});
	} else if (to.matched.some(record => record.meta.requiresAuth &&
		record.meta.roles !== undefined && record.meta.roles.indexOf(localStorage.getItem("role")) < 0)) {
		next({
			path: "/forbidden",
		});
	} else {
		next();
	}
});


let api = axios.create({
	baseURL: process.env.VUE_APP_API_URL,
});

api.interceptors.request.use((config) => {
	if (localStorage.getItem("access_token") !== null) {
		config.headers.Authorization = "Bearer " + localStorage.getItem("access_token");
	}
	store.commit("loader", true);
	return config;
}, (error) => {
	store.commit("loader", false);
	return Promise.reject(error);
});


api.interceptors.response.use((config) => {
	store.commit("loader", false);
	return config;
}, (error) => {
	store.commit("loader", false);
	if (error.response.status === 401) {
		router.push("/forbidden");
	}
	return Promise.reject(error);
});


// obiekt role jest dostępny we wszystkich komponentach Vue jako read only
// służy do autoryzacji poszczególnych zasobów w konkretnym widoku
Vue.mixin({
	data: function () {
		return {
			get number() {
				return number;
			},
			get roles() {
				return role;
			},
			get rule() {
				return rule;
			},
		};
	},
});


export const language = {
	setPolish() {
		let pl = "pl";
		localStorage.setItem("language", pl);
		i18n.locale = pl;
	},
	setEnglish() {
		let en = "en";
		localStorage.setItem("language", en);
		i18n.locale = en;
	},
};

Vue.prototype.$messagesBus = new Vue();
Vue.prototype.$language = language;
Vue.prototype.$api = api;

new Vue({
	router,
	store,
	vuetify: vuetify,
	api,
	i18n,
	render: h => h(App),
}).$mount("#app");
