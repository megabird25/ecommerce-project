<script setup>
import HomePanel from "./HomePanel.vue";
import { getHotAPI } from "@/apis/home.js";
import { onMounted, ref } from "vue";

const hotList = ref([]);

const getHotList = async () => {
  const res = await getHotAPI();
  hotList.value = res.result;
};

onMounted(() => {
  getHotList();
});
</script>

<template>
  <HomePanel title="人氣推薦" sub-title="爆款熱賣 口碑佳選">
    <ul class="goods-list">
      <li v-for="item in hotList" :key="item.id">
        <RouterLink to="/">
          <img v-img-lazy="item.image_url" alt="" />
          <p class="title">{{ item.name }}</p>
          <p class="desc">${{ item.price }}</p>
        </RouterLink>
      </li>
    </ul>
  </HomePanel>
</template>

<style scoped lang="scss">
.goods-list {
  display: flex;
  justify-content: space-between;
  height: 406px;

  li {
    width: 306px;
    height: 406px;

    background: #f0f9f4;
    transition: all 0.5s;

    &:hover {
      transform: translate3d(0, -3px, 0);
      box-shadow: 0 3px 8px rgb(0 0 0 / 20%);
    }

    img {
      width: 306px;
      height: 306px;
    }

    p {
      font-size: 22px;
      padding-top: 12px;
      text-align: center;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
    }

    .desc {
      color: $priceColor;
    }
  }
}
</style>
