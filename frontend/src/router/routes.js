import VueRouter from "vue-router";
import Vue from "vue";

import LoginView from "../view/LoginView";
import MainView from "../view/logged/main/MainView";
import ForbiddenView from "@/view/ForbiddenView";
import NotFoundView from "@/view/NotFoundView";
Vue.use(VueRouter);


// Widoki
const routes = [
	{
		path: "/",component: LoginView
	},
	{
		path: "/forbidden", component: ForbiddenView,
	},
	{
		path: "/app/main" ,component: MainView
	},
	{
		path: "*", component: NotFoundView
	},


];

export default new VueRouter({
	mode: "history",
	routes,
});
