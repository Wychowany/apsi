import axios from "axios";
import {encoder} from "./encoder";


export var auth = {
	async login(context, credentials) {
		localStorage.removeItem('access_token');
		axios({
			method: "post",
			url: process.env.VUE_APP_API_URL + "/oauth/token?grant_type=password" +
				"&username=" + encoder.encodeEmail(credentials.email) + // plus jest kodowany jako spacja
				"&password=" + credentials.password,
			auth: {username: "my-trusted-client", password: "secret"},
			headers: {"Content-type": "application/x-www-form-urlencoded"},
		}).then(async response => {
			localStorage.setItem("access_token", response.data.access_token);
			context.$router.push("/app")

		}).catch(error => {
			context.error_message = "";
			let error_data = error.response.data
			if (String(error.message).includes("Network Error")) {
				context.error_message = context.$t('common.serverError');
			}

			if ((error_data.error_description && error_data.error_description.includes("Niepoprawne")) || error_data.error === "unauthorized") {
				context.error_message = context.$t('common.loginError');
			}

			if (error_data.error_description && error_data.error_description.includes("zablokowane")) {
				context.error_message = context.$t('common.notConfirmed')
			}

			if (error_data.error_description && error_data.error_description.includes("wyłączone")) {
				context.error_message = context.$t('common.notAccepted');
			}
		});
	},

	logout(context) {
		localStorage.removeItem("access_token");
		localStorage.removeItem("role");
		context.$router.push("/");
	},

};

