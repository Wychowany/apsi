<template>
	<v-snackbar
		:color="color"
		:multi-line=true
		:timeout="timeout"
		top="top"
		v-model="enabled"
	>
		{{ message }}
		<v-btn
			@click="enabled = false"
			color="white"
			text
		>
			Zamknij
		</v-btn>
	</v-snackbar>
</template>

<script>
	export default {
		name: "SnackBar",
		
		data() {
			return {
				timeout: 1000,
				enabled: false,
				color: "",
				message: "",
			};
		},

		methods: {
			showSuccess(message) {
				this.message = message;
				this.color = "green";
				this.enabled = true;
			},

			showError(message) {
				this.message = message;
				this.color = "red";
				this.enabled = true;
			},
		},

		created() {
			this.$messagesBus.$on("success", this.showSuccess);
			this.$messagesBus.$on("error", this.showError);
		},
	};
</script>

<style scoped>

</style>