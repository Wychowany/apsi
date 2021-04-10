<template>
    <v-app id="404">
        <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <div class="text-md-center">
                    <h1>404</h1>
                    <h2 class="my-3 headline ">Przepraszamy, strona nie została odnaleziona</h2>
                    <div>
                        <v-btn color="primary" to="/">Strona główna</v-btn>
                    </div>
                </div>
            </v-layout>
        </v-container>
    </v-app>
</template>

<script>
    export default {
        name: "ErrorView"
    }
</script>

<style scoped>

</style>