import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        token: localStorage.getItem('access_token') || '',
        loader: false,
    },

    mutations: {
        loader(state, payload) {
            state.loader = payload;
        }
    }
});