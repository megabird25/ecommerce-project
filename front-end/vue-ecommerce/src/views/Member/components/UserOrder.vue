<script setup>
import { getUserOrderAPI } from "@/apis/order.js";
import { ref, onMounted } from "vue";

const tabTypes = [
  { name: "all", label: "全部訂單" },
  { name: "unpay", label: "待付款" },
  { name: "deliver", label: "待出貨" },
  { name: "receive", label: "待收貨" },
  { name: "complete", label: "已完成" },
  { name: "cancel", label: "取消" },
];

const orderList = ref([]);
const total = ref(0);
const params = ref({
  status: 0,
  page: 1,
  pageSize: 2,
});
const getOrderList = async () => {
  const res = await getUserOrderAPI(params.value);
  orderList.value = res.result.data;
  console.log(orderList.value);
  total.value = res.result.total;
};

onMounted(() => {
  getOrderList();
});

const tabChange = (type) => {
  params.value.status = type;
  getOrderList();
};

// const fomartPayState = (payState) => {
//   const stateMap = {
//     1: "待付款",
//     2: "待出貨",
//     3: "待收貨",
//     4: "已完成",
//     5: "已取消",
//   };
//   return stateMap[payState];
// };
</script>

<template>
  <div class="order-container">
    <el-tabs @tab-change="tabChange">
      <el-tab-pane
        v-for="item in tabTypes"
        :key="item.name"
        :label="item.label"
      />
      <div class="main-container">
        <div class="holder-container" v-if="orderList.length === 0">
          <el-empty description="暫無訂單數據" />
        </div>
        <div v-else>
          <!-- 訂單列表 -->
          <div class="order-item" v-for="order in orderList" :key="order.id">
            <div class="head">
              <span>下單時間：{{ order.order_date }}</span>
              <span>訂單編號：{{ order.id }}</span>
              <!-- <span class="down-time" v-if="order.orderState === 1">
                <i class="iconfont icon-down-time"></i>
                <b>付款截止： {{ order.countdown }}</b>
              </span> -->
            </div>
            <div class="body">
              <div class="column goods">
                <ul>
                  <li v-for="item in order.order_details" :key="item.id">
                    <RouterLink
                      class="image"
                      :to="`/detail/${item.product.id}`"
                    >
                      <img :src="item.product.image_url" alt="" />
                    </RouterLink>
                    <div class="info">{{ item.product.name }}</div>
                    <div class="price">${{ item.unit_price }}</div>
                    <div class="count">x{{ item.quantity }}</div>
                  </li>
                </ul>
              </div>
              <!-- 呼叫函數適配顯示 -->
              <!-- <div class="column state">
                <p>{{ fomartPayState(order.orderState) }}</p>
                <p v-if="order.orderState === 3">
                  <a href="javascript:;" class="green">查看物流</a>
                </p>
                <p v-if="order.orderState === 4">
                  <a href="javascript:;" class="green">評價商品</a>
                </p>
                <p v-if="order.orderState === 5">
                  <a href="javascript:;" class="green">查看評價</a>
                </p>
              </div> -->
              <div class="column amount">
                <p>訂單金額：</p>
                <p class="red">${{ order.total_amount }}</p>
              </div>
              <div class="column action">
                <el-button v-if="order.status == 1" type="primary" size="small">
                  立即付款
                </el-button>
                <el-button v-if="order.status == 3" type="primary" size="small">
                  確認收貨
                </el-button>
                <p><a href="javascript:;">查看詳情</a></p>
                <p v-if="[4, 5].includes(order.status)">
                  <a href="javascript:;">再次訂購</a>
                </p>
                <p v-if="[4, 5].includes(order.status)">
                  <a href="javascript:;">申請售後</a>
                </p>
                <p v-if="order.status == 1">
                  <a href="javascript:;">取消訂單</a>
                </p>
              </div>
            </div>
          </div>
          <!-- 分頁 -->
          <div class="pagination-container">
            <el-pagination
              :total="total"
              @current-change="pageChange"
              :page-size="params.pageSize"
              background
              layout="prev, pager, next"
            />
          </div>
        </div>
      </div>
    </el-tabs>
  </div>
</template>

<style scoped lang="scss">
.order-container {
  padding: 10px 20px;

  .pagination-container {
    display: flex;
    justify-content: center;
  }

  .main-container {
    min-height: 500px;

    .holder-container {
      min-height: 500px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}

.order-item {
  margin-bottom: 20px;
  border: 1px solid #f5f5f5;

  .head {
    height: 50px;
    line-height: 50px;
    background: #f5f5f5;
    padding: 0 20px;
    overflow: hidden;

    span {
      margin-right: 20px;

      &.down-time {
        margin-right: 0;
        float: right;

        i {
          vertical-align: middle;
          margin-right: 3px;
        }

        b {
          vertical-align: middle;
          font-weight: normal;
        }
      }
    }

    .del {
      margin-right: 0;
      float: right;
      color: #999;
    }
  }

  .body {
    display: flex;
    align-items: stretch;

    .column {
      border-left: 1px solid #f5f5f5;
      text-align: center;
      padding: 20px;

      > p {
        padding-top: 10px;
      }

      &:first-child {
        border-left: none;
      }

      &.goods {
        flex: 1;
        padding: 0;
        align-self: center;

        ul {
          li {
            border-bottom: 1px solid #f5f5f5;
            padding: 10px;
            display: flex;

            &:last-child {
              border-bottom: none;
            }

            .image {
              width: 70px;
              height: 70px;
              border: 1px solid #f5f5f5;
            }

            div {
              display: flex;
              align-items: center;
              padding: 0 10px;

              &.info {
                width: 220px;
                font-size: 18px;
              }

              &.price {
                width: 100px;
              }

              &.count {
                width: 80px;
              }
            }
          }
        }
      }

      &.state {
        width: 120px;

        .green {
          color: $xtxColor;
        }
      }

      &.amount {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 200px;

        .red {
          font-size: 25px;
          color: $priceColor;
        }
      }

      &.action {
        width: 140px;

        a {
          display: block;

          &:hover {
            color: $xtxColor;
          }
        }
      }
    }
  }
}
</style>
