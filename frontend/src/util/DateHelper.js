export const Dates = {
	getDayByIndex(dayIndex) {
		return weekDay [dayIndex];
	},
	getIndexByDay(day) {
		for (let i= 0; i< weekDay.length ; i++) {
			if (day === weekDay[i]) {
				return i;
			}
		}
	}
};
let weekDay = [
	"SUNDAY",
	"MONDAY",
	"TUESDAY",
	"WEDNESDAY",
	"THURSDAY",
	"FRIDAY",
	"SATURDAY"
];
