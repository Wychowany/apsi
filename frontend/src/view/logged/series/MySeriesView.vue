 <template>
  <div class="ma-3">
    <v-toolbar dark color="lighter">
      <v-toolbar-title>Moje zbiory</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="createSeries()" color="light" style="color: black">Dodaj zbiór</v-btn>
    </v-toolbar>
    <v-data-table
        :headers="headers"
        :items="series"
        :items-per-page="10"
        class="elevation-1">
      <template v-slot:item.actions="{ item }">
        <v-icon small @click="editSeries(item)">edit</v-icon>
        <v-icon small class="ml-3" @click="manageAccess(item)">person</v-icon>
        <v-icon small class="ml-3" @click="deleteSeries(item)">delete</v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import {api} from "@/util/Api";

export default {
  name: "MySeriesView",

  data() {
    return {
      headers: [
        {text: 'Nazwa', sortable: false, value: 'name'},
        {text: 'Akcje', sortable: false, value: 'actions'},
      ],
      series: [],
    };
  },

  created() {
    api.get(this, '/series/my-list', null,successResponse => {
      this.series = successResponse;
    }, errorResponse => {
      console.log(errorResponse);
    });
  },

  methods: {
    createSeries() {
      this.$router.push("/app/series/create");
    },

    editSeries(item) {
      this.$router.push("/app/series/edit/" + item.id);
    },
     manageAccess(item) {
          this.$router.push("/app/series/access/" + item.id);
        },

    deleteSeries(item) {
      api.delete(this, "/series/", {id: item.id}, () => {
            this.series = this.series.filter(d => d.id !== item.id);
          },
          errorResponse => {
            console.log(errorResponse);
          });
    },
  }
}
</script>

<style scoped>

</style>