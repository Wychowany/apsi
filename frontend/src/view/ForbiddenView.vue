<template>
    <v-container fluid fill-height id="403">
        <v-layout align-center justify-center>
            <div class="text-md-center">
                <h1>403</h1>
                <h2 class="my-3 headline ">Brak dostępu</h2>
                <div>
                    <v-btn @click="goToLoginPage" color="primary">logowanie</v-btn>
                </div>
            </div>
        </v-layout>
    </v-container>
</template>

<script>
    export default {
        name: "ForbiddenView",

        methods: {
            goToLoginPage() {
                this.$router.push("/login");
            }
        }
    }
</script>

<style scoped>

</style>