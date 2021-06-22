import VueRouter from "vue-router";
import Vue from "vue";

import LoginView from "../view/LoginView";
import MainView from "../view/logged/main/MainView";
import ForbiddenView from "@/view/ForbiddenView";
import NotFoundView from "@/view/NotFoundView";
import DocumentsView from "@/view/logged/documents/DocumentsView";
import SeriesView from "@/view/logged/series/SeriesView";
import MySeriesView from "@/view/logged/series/MySeriesView";
import UsersView from "@/view/logged/users/UsersView";
import CreateUserView from "@/view/logged/users/CreateUserView";
import UsersTemplate from "@/view/logged/users/UsersTemplate";
import EditUserView from "@/view/logged/users/EditUserView";
import DocumentsTemplate from "@/view/logged/documents/DocumentsTemplate";
import CreateDocumentView from "@/view/logged/documents/CreateDocumentView";
import EditDocumentView from "@/view/logged/documents/EditDocumentView";
import MyDocumentsView from "@/view/logged/documents/MyDocumentsView";
import SeriesTemplate from "@/view/logged/series/SeriesTemplate";
import CreateSeriesView from "@/view/logged/series/CreateSeriesView";
import EditSeriesView from "@/view/logged/series/EditSeriesView";
import DocumentAccessView from "@/view/logged/documents/DocumentAccessView";
import DocumentRolesView from "@/view/logged/documentRoles/DocumentRolesView";
import SeriesAccessView from "@/view/logged/series/SeriesAccessView";
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
				path: "documents", component: DocumentsTemplate,
				children: [
					{
						path: "/", component: DocumentsView,
					},
					{
						path: "own", component: MyDocumentsView,
					},
					{
						path: "create", component: CreateDocumentView,
					},
					{
						path: "edit/:id", component: EditDocumentView,
					},
					{
						path: "access/:id", component: DocumentAccessView,
					}
				]
			},
			{
				path: "series", component: SeriesTemplate,
				children: [
					{
						path: "/", component: SeriesView,
					},
					{
						path: "own", component: MySeriesView,
					},
					{
						path: "create", component: CreateSeriesView,
					},
					{
						path: "edit/:id", component: EditSeriesView,
					},
					{
						path: "access/:id", component: SeriesAccessView,
					}
				]
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
			},
			{
				path: "document-roles", component: DocumentRolesView,
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
