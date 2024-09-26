<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useCartStore } from "@/stores/cartStore.js";
import { getUserAddressAPI } from "@/apis/address.js";
import { ElMessage } from "element-plus";
import { createOrderAPI } from "@/apis/order";

const showDialog = ref(false);
const payWay = ref(null);

const userAddress = ref({});
const curAddress = ref({});
const cartStore = useCartStore();
const getAddress = async () => {
  const res = await getUserAddressAPI();
  userAddress.value = res.result;

  curAddress.value = userAddress.value?.find((item) => item.isDefault === 0);
};

onMounted(() => {
  getAddress();
});

const activeAddress = ref({});
const switchAddress = (item) => {
  activeAddress.value = item;
};

const confirm = () => {
  curAddress.value = activeAddress.value;
  showDialog.value = false;
  activeAddress.value = {};
};

const router = useRouter();
const createOrder = async () => {
  if (curAddress.value && payWay.value) {
    const order = {
      receiver: curAddress.value.receiver,
      receiver_contact: curAddress.value.contact,
      receiver_address: curAddress.value.address,
      total_amount: cartStore.selectedPrice,
      total_quantity: cartStore.selectedCount,
      status: 1,
      order_details: [],
    };

    cartStore.selectedItem.forEach((item) => {
      order.order_details.push({
        id: item.id,
        quantity: item.count,
        unit_price: item.price,
      });
    });

    const res = await createOrderAPI(order);
    if (res.code === 0) {
      router.push("/member/order");
    } else {
      ElMessage.error(res.message);
    }
  } else {
    ElMessage.error("請正確填寫資料");
  }
};
</script>

<template>
  <div class="pay-checkout-page">
    <div class="container">
      <div class="wrapper">
        <!-- 商品資訊 -->
        <h3 class="box-title">商品資訊</h3>
        <div class="box-body">
          <table class="goods">
            <thead>
              <tr>
                <th width="520">商品</th>
                <th width="170">單價</th>
                <th width="170">數量</th>
                <th width="170">小計</th>
                <th width="170">實付</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="i in cartStore.cartList" :key="i.id">
                <td>
                  <a href="javascript:;" class="info">
                    <img :src="i.image_url" alt="" />
                    <div class="right">
                      <p>{{ i.name }}</p>
                    </div>
                  </a>
                </td>
                <td>${{ i.price }}</td>
                <td>{{ i.count }}</td>
                <td>${{ i.price * i.count }}</td>
                <td>${{ i.price * i.count }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- 收穫地址 -->
        <h3 class="box-title">收貨地址</h3>
        <div class="box-body">
          <div class="address">
            <div class="text">
              <div class="none" v-if="!curAddress">
                您需要先新增收貨地址才可提交訂單。
              </div>
              <ul v-else>
                <li>
                  <span>收<i></i>貨<i></i>人：</span>{{ curAddress.receiver }}
                </li>
                <li><span>聯絡方式：</span>{{ curAddress.contact }}</li>
                <li>
                  <span>收貨地址：</span>
                  {{ curAddress.address }}
                </li>
              </ul>
            </div>
            <div class="action">
              <el-button size="large" @click="showDialog = true"
                >切換地址</el-button
              >
              <el-button size="large" @click="addFlag = true"
                >添加地址</el-button
              >
            </div>
          </div>
        </div>
        <!-- 付款方式 -->
        <h3 class="box-title">付款方式</h3>
        <div class="box-body">
          <a
            class="my-btn"
            :class="{ active: payWay === 0 }"
            @click="payWay = 0"
            href="javascript:;"
            >線上付款</a
          >
          <a
            class="my-btn"
            :class="{ active: payWay === 1 }"
            @click="payWay = 1"
            href="javascript:;"
            >貨到付款</a
          >
        </div>
        <!-- 金額明細 -->
        <h3 class="box-title">金額明細</h3>
        <div class="box-body">
          <div class="total">
            <dl>
              <dt>商品件數：</dt>
              <dd>{{ cartStore.selectedCount }}件</dd>
            </dl>
            <dl>
              <dt>商品總價：</dt>
              <dd>${{ cartStore.selectedPrice }}</dd>
            </dl>
            <dl>
              <dt>運<i></i>費：</dt>
              <dd>${{ 0 }}</dd>
            </dl>
            <dl>
              <dt>應付總額：</dt>
              <dd class="price">${{ cartStore.selectedPrice }}</dd>
            </dl>
          </div>
        </div>
        <!-- 提交訂單 -->
        <div class="submit">
          <el-button @click="createOrder" type="primary" size="large">
            提交訂單
          </el-button>
        </div>
      </div>
    </div>
  </div>
  <!-- 切換地址 -->
  <el-dialog v-model="showDialog" title="切換收貨地址" width="30%" center>
    <el-scrollbar height="500px">
      <div class="addressWrapper">
        <div
          class="text item"
          v-for="item in userAddress"
          :key="item.id"
          @click="switchAddress(item)"
          :class="{ active: activeAddress.id === item.id }"
        >
          <ul>
            <li>
              <span>收<i></i>貨<i></i>人：</span>{{ item.receiver }}
            </li>
            <li><span>聯絡方式：</span>{{ item.contact }}</li>
            <li><span>收貨地址：</span>{{ item.address }}</li>
          </ul>
        </div>
      </div>
    </el-scrollbar>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="confirm">確定</el-button>
      </span>
    </template>
  </el-dialog>
  <!-- 添加地址 -->
</template>

<style scoped lang="scss">
.pay-checkout-page {
  margin-top: 20px;

  .wrapper {
    background: #fff;
    padding: 0 20px;

    .box-title {
      font-size: 16px;
      font-weight: normal;
      padding-left: 10px;
      line-height: 70px;
      border-bottom: 1px solid #f5f5f5;
    }

    .box-body {
      padding: 20px 0;
    }
  }
}

.address {
  border: 1px solid #f5f5f5;
  display: flex;
  align-items: center;

  .text {
    flex: 1;
    min-height: 90px;
    display: flex;
    align-items: center;

    .none {
      line-height: 90px;
      color: #999;
      text-align: center;
      width: 100%;
    }

    > ul {
      flex: 1;
      padding: 20px;

      li {
        line-height: 30px;

        span {
          color: #999;
          margin-right: 5px;

          > i {
            width: 0.5em;
            display: inline-block;
          }
        }
      }
    }

    > a {
      color: $xtxColor;
      width: 160px;
      text-align: center;
      height: 90px;
      line-height: 90px;
      border-right: 1px solid #f5f5f5;
    }
  }

  .action {
    width: 420px;
    text-align: center;

    .btn {
      width: 140px;
      height: 46px;
      line-height: 44px;
      font-size: 14px;

      &:first-child {
        margin-right: 10px;
      }
    }
  }
}

.goods {
  width: 100%;
  border-collapse: collapse;
  border-spacing: 0;

  .info {
    display: flex;
    text-align: left;

    img {
      width: 70px;
      height: 70px;
      margin-right: 40px;
    }

    .right {
      display: flex;
      align-items: center;
      font-size: 20px;
    }
  }

  tr {
    th {
      background: #f5f5f5;
      font-weight: normal;
    }

    td,
    th {
      text-align: center;
      padding: 20px;
      border-bottom: 1px solid #f5f5f5;

      &:first-child {
        border-left: 1px solid #f5f5f5;
      }

      &:last-child {
        border-right: 1px solid #f5f5f5;
      }
    }
  }
}

.my-btn {
  width: 228px;
  height: 50px;
  border: 1px solid #e4e4e4;
  text-align: center;
  line-height: 48px;
  margin-right: 25px;
  color: #666666;
  display: inline-block;

  &.active,
  &:hover {
    border-color: $xtxColor;
  }
}

.total {
  dl {
    display: flex;
    justify-content: flex-end;
    line-height: 50px;

    dt {
      i {
        display: inline-block;
        width: 2em;
      }
    }

    dd {
      width: 240px;
      text-align: right;
      padding-right: 70px;

      &.price {
        font-size: 20px;
        color: $priceColor;
      }
    }
  }
}

.submit {
  text-align: right;
  padding: 60px;
  border-top: 1px solid #f5f5f5;
}

.addressWrapper {
  max-height: 500px;
}

.text {
  flex: 1;
  min-height: 90px;
  display: flex;
  align-items: center;

  &.item {
    border: 1px solid #f5f5f5;
    margin-bottom: 10px;
    cursor: pointer;

    &.active,
    &:hover {
      border-color: $xtxColor;
      background: lighten($xtxColor, 50%);
    }

    > ul {
      padding: 10px;
      font-size: 14px;
      line-height: 30px;
    }
  }
}
</style>
