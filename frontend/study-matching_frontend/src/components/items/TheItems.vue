<template>
  <img :src="item.imgPath" width="100%" height="225"/>
  <div class="card-body">
    <p class="card-text">{{ item.name }}
      <span class="discount badge bg-danger">
        {{ formatPrice(item.discountPer) }}%
      </span>
    </p>
    <div class="d-flex justify-content-between align-items-center">
      <div class="btn-group">
        <button type="button" class="btn btn-sm btn-outline-secondary">View</button>
        <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
      </div>
      <span class="price text-body-secondary">
        ${{ formatPrice(item.price) }}
      </span>
      <span class="real text-body-secondary">
        ${{ formatPrice(discount) }}
      </span>
    </div>
  </div>
</template>

<script>
import lib from "@/script/lib";

export default {
  name: "TheItem",
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  computed: {
    discount() {
      return this.item.price - (this.item.price * this.item.discountPer / 100)
    }
  },
  methods: {
    formatPrice(val) {
      console.log('item.price:', this.item.price, 'Type:', typeof this.item.price);
      return lib.getNumberFormatted(val);
    }
  }
}
</script>

<style scoped>
.card-body .price {
  text-decoration: line-through;
}
</style>