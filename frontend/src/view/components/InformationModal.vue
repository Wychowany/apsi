<template>
		<v-dialog
			v-model="dialog"
			max-width="600"
		>
			<v-card>
				<v-card-title class="headline grey lighten-2">
					{{title}}
				</v-card-title>

				<v-card-text>
					{{text}}
				</v-card-text>

				<v-divider></v-divider>

				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn
						color="primary"
						text
						@click="dialog = false"
					>
						OK
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
</template>

<script>
	export default {
		name: "InformationModal",

		data() {
			return {
				dialog: false,
			};
		},

		methods: {
			openModal() {
				this.dialog = true;
			},
		},

		props: {
			title: {
				type: String,
				default: "Informacja",
			},

			text: {
				type: String,
				required: true,
			},
		},
	};
</script>

<style scoped>

</style>