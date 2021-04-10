import {i18n} from "../i18n/i18n";

export const rule = {
	required: value => !!value || i18n.t("validation.required"),
	email: value => {
		const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return pattern.test(value) || "Invalid e-mail.";
	},
};