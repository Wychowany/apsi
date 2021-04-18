export const Files = {

	methods: {
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
		},

		async fileToBase64(file) {
			return new Promise((resolve, reject) => {
				const reader = new FileReader();
				reader.readAsDataURL(file);
				reader.onload = () => resolve(reader.result);
				reader.onerror = error => reject(error);
			});
		},

		async downloadBase64File(encodedData, fileName) {
			const downloadLink = document.createElement("a");
			downloadLink.href = encodedData;
			downloadLink.download = fileName;
			downloadLink.click();
		}
	}
};