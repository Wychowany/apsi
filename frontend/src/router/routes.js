import VueRouter from "vue-router";
import Vue from "vue";

import LoginView from "../view/LoginView";
import MainView from "../view/logged/main/MainView";
import ForbiddenView from "@/view/ForbiddenView";
import NotFoundView from "@/view/NotFoundView";
import DocumentsView from "@/view/logged/documents/DocumentsView";
import SeriesView from "@/view/logged/series/SeriesView";
import UsersView from "@/view/logged/users/UsersView";
import CreateUserView from "@/view/logged/users/CreateUserView";
import UsersTemplate from "@/view/logged/users/UsersTemplate";
import EditUserView from "@/view/logged/users/EditUserView";
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
				path: "users", component: UsersTemplate,
				children: [
					{
						path: "/", component: UsersView,
					},
					{
						path: "create", component: CreateUserView,
					},
					{
						path: "edit/:id", component: EditUserView,
					}
				]
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
