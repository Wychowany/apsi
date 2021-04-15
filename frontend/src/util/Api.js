import axios from "axios";

export var api = {
    async get(context, path, onSuccess, onError) {
        const successHandler = response => {
            if (typeof onSuccess === "function") {
                onSuccess(response.data);
            }
        }
        const errorHandler = response => {
            if (typeof onSuccess === "function") {
                onError(response);
            }
        }
        axios({
            method: 'get',
            url: process.env.VUE_APP_API_URL + path,
            responseType: 'json'
        })
            .then(successHandler)
            .catch(errorHandler);
    }
}