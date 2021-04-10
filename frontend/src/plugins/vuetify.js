import Vuetify from "vuetify/lib";
import "vuetify/src/styles/main.sass";
import "vuetify/src/styles/styles.sass"
import pl from "vuetify/es5/locale/pl";
import en from "vuetify/es5/locale/en";
import colors from "vuetify/es5/util/colors";

export var vuetify = new Vuetify({
	icons: {
		iconfont: "md"
	},
	lang: {
		locales: {pl, en},
		current: localStorage.getItem("language"),
	},
	theme: {
		dark: false,

		themes: {
			dark: {
				primary: colors.green,
				secondary: colors.green.darken1,
				accent: colors.shades.black,
				error: colors.red.accent3,
			},
			light: {
				primary: colors.green,
				secondary: colors.green.darken1,
				accent: colors.shades.black,
				error: colors.red.accent3,
			},

		},
	}
});

