import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { useUserStore } from "./userStore.js";
import { insertCartAPI, fetchCartListAPI, delCartAPI } from "@/apis/cart.js";

export const useCartStore = defineStore(
  "cart",
  () => {
    const userStore = useUserStore();
    const isLogin = computed(() => userStore.userInfo.token);

    // data
    const cartList = ref([]);

    // action
    const addCart = async (goods) => {
      if (isLogin.value) {
        const { skuId, count } = goods;
        await insertCartAPI({ skuId, count });
        updateList();
      } else {
        const item = cartList.value.find((item) => goods.skuId === item.skuId);
        if (item) {
          item.count += goods.count;
        } else {
          cartList.value.push(goods);
        }
      }
    };
    const delCart = async (skuId) => {
      if (isLogin.value) {
        await delCartAPI([skuId]);
        updateList();
      } else {
        const index = cartList.value.findIndex((item) => {
          skuId === item.skuId;
        });
        cartList.value.splice(index, 1);
      }
    };
    const clearCart = () => {
      cartList.value = [];
    };
    const allCheck = (selected) => {
      cartList.value.forEach((item) => (item.selected = selected));
    };

    const updateList = async () => {
      const res = await fetchCartListAPI();
      cartList.value = res.result;
    };

    // getter
    const allCount = computed(() =>
      cartList.value.reduce((a, c) => a + c.count, 0)
    );
    const allPrice = computed(() =>
      cartList.value.reduce((a, c) => a + c.count * c.price, 0)
    );
    const selectedCount = computed(() =>
      cartList.value
        .filter((item) => item.selected)
        .reduce((a, c) => a + c.count, 0)
    );
    const selectedPrice = computed(() =>
      cartList.value
        .filter((item) => item.selected)
        .reduce((a, c) => a + c.count * c.price, 0)
    );

    const isAll = computed(() => cartList.value.every((item) => item.selected));

    return {
      cartList,
      allCount,
      allPrice,
      selectedCount,
      selectedPrice,
      isAll,
      addCart,
      delCart,
      clearCart,
      allCheck,
      updateList,
    };
  },
  { persist: true }
);
