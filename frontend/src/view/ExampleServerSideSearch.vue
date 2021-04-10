<template>
	<div>
		<v-autocomplete
			ref="beerSearch"
			v-model="beerId"
			:items="beers"
			:search-input.sync="search"
			item-text="name"
			no-filter
			hide-no-data
			item-value="id"
			label="beer"
			placeholder="Start typing to Search"
		></v-autocomplete>
	</div>
</template>

<script>
	import debounce from "debounce";

	export default {

		name: "ExampleServerSideSearch",

		data() {
			return {
				search: "",
				previousSearch: "",
				beerId: null,
				beers: [],
				alreadyLoading: false,
			};
		},
		methods: {

			makeSearch: async (value, ctx) => {
				// Handle empty value
				if (!value) {
					ctx.beers = [];
					ctx.beerId = null;
				}

				// Items have already been requested
				if (ctx.alreadyLoading || ctx.previousSearch === ctx.search) {
					return;
				}

				ctx.alreadyLoading = true;

				// YOUR AJAX Methods go here
				// if you prefer not to use vue-api-query
				ctx.previousSearch = ctx.search;
				ctx.$api.get("/beers/landing-search?search=".concat(ctx.search).concat("&cityId=1"))
					.then(response => {
						ctx.beers = response.data;
					}).catch(() => {
					ctx.error = "some error occured";
				}).finally(() => {
					ctx.alreadyLoading = false;
					ctx.$refs.beerSearch.focus();
				});
			},
		},
		watch: {
			search(value) {
				if (!value) {
					return;
				}
				// Debounce the input and wait for a pause of at
				// least 200 milliseconds. This can be changed to
				// suit your needs.
				debounce(this.makeSearch, 500)(value, this);
			},
		},

	};
</script>

<style scoped>

</style>