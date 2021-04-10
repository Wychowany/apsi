export const number = {
	formatWithDot(number) {
		let st = String(number);
		if (!st.includes(".")) {
			st = st.concat(".00");
		} else if (st.indexOf(".") === st.length - 2) {
			st = st.concat("0")
		}
		return st;
	},
};