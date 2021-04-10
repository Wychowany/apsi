export const files = {
	downloadBlob(blob, filename) {
		let link = document.createElement("a");
		if (!!window.MSInputMethodContext && !!document.documentMode) {
			window.navigator.msSaveOrOpenBlob(blob, filename);
		} else {
			var binaryData = [];
			binaryData.push(blob);
			link.href = window.URL.createObjectURL(new Blob(binaryData, {type: "application/vnd.ms-excel;charset=UTF-8"}));
			link.download = filename;
			link.click();
		}
	}
};