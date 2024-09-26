<script setup>
defineProps({
  similar: {
    type: Array,
  },
});
</script>

<template>
  <div class="goods-hot">
    <h3>相關商品</h3>
    <RouterLink
      :to="`/detail/${item.id}`"
      class="goods-item"
      v-for="item in similar"
      :key="item.id"
    >
      <img :src="item.image_url" alt="" />
      <p class="name ellipsis">{{ item.name }}</p>
      <p class="desc ellipsis">{{ item.description }}</p>
      <p class="price">${{ item.price }}</p>
    </RouterLink>
  </div>
</template>

<style scoped lang="scss">
.goods-hot {
  display: flex;
  flex-direction: column;
  align-items: center;

  h3 {
    height: 70px;
    width: 100%;
    background: $helpColor;
    color: #fff;
    font-size: 18px;
    line-height: 70px;
    padding-left: 25px;
    margin-bottom: 10px;
    font-weight: bolder;
  }

  .goods-item {
    display: block;
    width: 100%;
    padding: 20px 30px;
    text-align: center;
    background: #fff;

    img {
      width: 160px;
      height: 160px;
    }

    p {
      padding-top: 10px;
    }

    .name {
      font-size: 16px;
    }

    .desc {
      color: #999;
      height: 29px;
    }

    .price {
      color: $priceColor;
      font-size: 20px;
    }
  }
}
</style>
