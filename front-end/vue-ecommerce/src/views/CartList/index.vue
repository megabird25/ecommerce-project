<script setup>
import { useCartStore } from "@/stores/cartStore.js";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";

const cartStore = useCartStore();
const router = useRouter();

const allCheck = (selected) => {
  cartStore.allCheck(selected);
};

const goCheckout = () => {
  if (cartStore.selectedItem.length === 0) {
    ElMessage.error("請選擇需要購買的商品");
  } else {
    router.push("/checkout");
  }
};
</script>

<template>
  <div class="cart-page">
    <div class="container m-top-20">
      <div class="cart">
        <table>
          <thead>
            <tr>
              <th width="120">
                <!-- 全選框 -->
                <el-checkbox
                  :model-value="cartStore.isAll"
                  @change="allCheck"
                />
              </th>
              <th width="400">商品</th>
              <th width="220">單價</th>
              <th width="180">數量</th>
              <th width="180">總計</th>
              <th width="140">刪除</th>
            </tr>
          </thead>
          <!-- 商品列表 -->
          <tbody>
            <tr v-for="i in cartStore.cartList" :key="i.id">
              <td>
                <el-checkbox v-model="i.selected" />
              </td>
              <td>
                <div class="goods">
                  <RouterLink :to="`/detail/${i.id}`">
                    <img :src="i.image_url" alt="" />
                  </RouterLink>
                  <div>
                    <p class="name ellipsis">{{ i.name }}</p>
                  </div>
                </div>
              </td>
              <td class="tc">
                <p>${{ i.price }}</p>
              </td>
              <td class="tc">
                <el-input-number v-model="i.count" />
              </td>
              <td class="tc">
                <p class="f16 red">${{ (i.price * i.count).toFixed(2) }}</p>
              </td>
              <td class="tc">
                <a href="javascript:;">
                  <i
                    class="fa-regular fa-circle-xmark fa-xl"
                    @click="cartStore.delCart(i.id)"
                  ></i>
                </a>
              </td>
            </tr>
            <tr v-if="cartStore.cartList.length === 0">
              <td colspan="6">
                <div class="cart-none">
                  <el-empty description="你的購物車還是空的">
                    <el-button type="primary" @click="$router.push('/')">
                      隨便逛逛
                    </el-button>
                  </el-empty>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- 操作欄 -->
      <div class="action">
        <div class="batch">
          共 {{ cartStore.allCount }} 件商品，以選擇
          {{ cartStore.selectedCount }} 件，商品合計：
          <span class="red">${{ cartStore.selectedPrice }}</span>
        </div>
        <div class="total">
          <el-button size="large" type="primary" @click="goCheckout">
            下單結算
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.cart-page {
  margin-top: 20px;

  .cart {
    background: #fff;
    color: #666;

    table {
      border-spacing: 0;
      border-collapse: collapse;
      line-height: 24px;

      th,
      td {
        padding: 10px;
        border-bottom: 1px solid #f5f5f5;

        &:first-child {
          text-align: left;
          padding-left: 30px;
          color: #999;
        }
      }

      th {
        font-size: 16px;
        font-weight: normal;
        line-height: 50px;
      }
    }
  }

  .cart-none {
    text-align: center;
    padding: 120px 0;
    background: #fff;

    p {
      color: #999;
      padding: 20px 0;
    }
  }

  .tc {
    text-align: center;

    i {
      color: $xtxColor;
    }
  }

  .red {
    color: $priceColor;
  }

  .green {
    color: $xtxColor;
  }

  .f16 {
    font-size: 16px;
  }

  .goods {
    display: flex;
    align-items: center;

    img {
      width: 100px;
      height: 100px;
    }

    > div {
      width: 280px;
      font-size: 16px;
      padding-left: 10px;

      .attr {
        font-size: 14px;
        color: #999;
      }
    }
  }

  .action {
    display: flex;
    background: #fff;
    margin-top: 20px;
    height: 80px;
    align-items: center;
    font-size: 16px;
    justify-content: space-between;
    padding: 0 30px;

    .xtx-checkbox {
      color: #999;
    }

    .batch {
      a {
        margin-left: 20px;
      }
    }

    .red {
      font-size: 18px;
      margin-right: 20px;
      font-weight: bold;
    }
  }

  .tit {
    color: #666;
    font-size: 16px;
    font-weight: normal;
    line-height: 50px;
  }
}
</style>
