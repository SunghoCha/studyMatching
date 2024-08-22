<script>
import BaseCard from "@/components/ui/BaseCard.vue";
import TheItem from "@/components/items/TheItems.vue";

export default {
  name: "TheHome",
  components: {
    TheItem,
    BaseCard
  },
  computed: {
    filteredItems() {
      // 추후 필터링
      const items = this.$store.getters['items/items'] // [namespace/getterName]
      console.log(items);
      return items;
      }
  },

  created() {
    this.loadItems();
  },
  methods: {
    async loadItems(refresh = false) { // refresh의 default 값을 false로 설정
      this.isLoading = true;
      try {
        await this.$store.dispatch('items/loadItems', {forceRefresh: refresh}); // Refresh 버튼 눌렀을때만 true
        console.log("아이템 로딩");
      } catch (error) {
        this.error = error.message || 'Something went wrong!!'
      }
      this.isLoading = false;
    },
  }
}
</script>

<template>
  <div class="home">

    <section class="py-5 text-center container">
      <div class="row py-lg-5">
        <div class="col-lg-6 col-md-8 mx-auto">
          <h1 class="fw-light">Album example</h1>
          <p class="lead text-body-secondary">Something short and leading about the collection below—its contents, the
            creator, etc. Make it short and sweet, but not too short so folks don’t simply skip over it entirely.</p>
          <p>
            <a href="#" class="btn btn-primary my-2">Main call to action</a>
            <a href="#" class="btn btn-secondary my-2">Secondary action</a>
          </p>
        </div>
      </div>
    </section>

    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="item in filteredItems" :key="item.id">
            <base-card>
              <the-item :item="item"></the-item>
            </base-card>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>

</style>