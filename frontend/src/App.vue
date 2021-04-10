<template>
  <v-theme-provider root>
    <v-app>
      <Loader/>
      <SnackBar/>
      <router-view/>
      <div>
        <v-footer app>
          <div style="width: 100%">
            <div style="float: left">
              <span class="one--text">&copy;apsi</span>
            </div>
          </div>
        </v-footer>
      </div>
    </v-app>
  </v-theme-provider>
</template>

<script>
	import Loader from "./view/components/Loader";
	import SnackBar from "./view/components/SnackBar";

	export default {
		name: "app",
		components: {
			SnackBar,
			Loader,
		},

		beforeCreate() {
			if (!localStorage.getItem("language")) {
				this.$language.setPolish();
			}
		},
	};
</script>

<style scoped>

</style>
