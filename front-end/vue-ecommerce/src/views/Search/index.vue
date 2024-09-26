<script setup>
import { ref, onMounted } from "vue";
import { useRoute, onBeforeRouteUpdate } from "vue-router";
import { searchProductsAPI } from "@/apis/product.js";

const route = useRoute();

const goodList = ref([]);
const title = ref("");
const reqData = ref({
  keyword: route.query.keyword,
  page: 1,
  pageSize: 20,
  sortField: "publishTime",
});
const getProducts = async () => {
  const res = await searchProductsAPI(reqData.value);
  title.value = res.result.title;
  goodList.value = res.result.data;
};

onMounted(() => {
  getProducts();
});

onBeforeRouteUpdate((to) => {
  reqData.value.keyword = to.query.keyword;
  reqData.value.page = 1;
  getProducts();
});

const tabChange = () => {
  reqData.value.page = 1;
  getProducts();
};

const disabled = ref(false);
const load = async () => {
  reqData.value.page++;
  const res = await searchProductsAPI(reqData.value);
  goodList.value = [...goodList.value, ...res.result.data];

  if (reqData.value.page === res.result.total_pages) {
    disabled.value = true;
  }
};
</script>

<template>
  <div class="container">
    <div class="bread-container">
      <i>{{ title }}</i>
    </div>
    <div class="sub-container">
      <el-tabs v-model="reqData.sortField" @tab-change="tabChange">
        <el-tab-pane label="最新商品" name="publishTime"></el-tab-pane>
        <el-tab-pane label="最高人氣" name="orderNum"></el-tab-pane>
        <el-tab-pane label="評論最多" name="evaluateNum"></el-tab-pane>
      </el-tabs>
      <div
        class="body"
        v-infinite-scroll="load"
        :infinite-scroll-disabled="disabled"
      >
        <GoodsItem v-for="good in goodList" :good="good" :key="good.id" />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.bread-container {
  padding: 25px 0;
  color: #666;

  i {
    padding-left: 15px;
    font-size: 25px;
  }
}

.sub-container {
  padding: 20px 10px;
  background-color: #fff;

  .body {
    display: flex;
    flex-wrap: wrap;
    padding: 0 10px;
  }

  .goods-item {
    display: block;
    width: 220px;
    margin-right: 20px;
    padding: 20px 30px;
    text-align: center;

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

  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }
}
</style>
