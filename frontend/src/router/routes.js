import VueRouter from "vue-router";
import Vue from "vue";

import LoginView from "../view/LoginView";
import MainView from "../view/logged/main/MainView";
import ForbiddenView from "@/view/ForbiddenView";
import NotFoundView from "@/view/NotFoundView";
import DocumentsView from "@/view/logged/documents/DocumentsView";
import SeriesView from "@/view/logged/series/SeriesView";
import UsersView from "@/view/logged/users/UsersView";
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
		path: "/app", component: MainView,
		children: [
			{
				path: "documents", component: DocumentsView,
			},
			{
				path: "series", component: SeriesView,
			},
			{
				path: "users", component: UsersView,
			}
		]
	},
	{
		path: "*", component: NotFoundView
	},


];

export default new VueRouter({
	mode: "history",
	routes,
});
