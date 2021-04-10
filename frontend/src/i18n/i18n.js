import Vue from "vue";
import VueI18n from "vue-i18n";

Vue.use(VueI18n);

import {PL} from "./i18n-pl";
import {EN} from "./i18n-en";

const messages = {
	pl: PL,
	en: EN
};

export const i18n = new VueI18n({
	locale: localStorage.getItem("language"), // set locale
	messages, // set locale messages
});
