import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const useCartStore = defineStore(
  "cart",
  () => {
    // data
    const cartList = ref([]);

    // action
    const addCart = (goods) => {
      const item = cartList.value.find((item) => goods.id === item.id);
      if (item) {
        item.count += goods.count;
      } else {
        cartList.value.push(goods);
      }
    };

    const delCart = (id) => {
      const index = cartList.value.findIndex((item) => {
        id === item.id;
      });
      cartList.value.splice(index, 1);
    };

    const clearCart = () => {
      cartList.value = [];
    };

    const allCheck = (selected) => {
      cartList.value.forEach((item) => (item.selected = selected));
    };

    // getter
    const allCount = computed(() =>
      cartList.value.reduce((a, c) => a + c.count, 0)
    );

    const allPrice = computed(() =>
      cartList.value.reduce((a, c) => a + c.count * c.price, 0)
    );

    const selectedItem = computed(() =>
      cartList.value.filter((item) => item.selected)
    );

    const selectedCount = computed(() =>
      selectedItem.value.reduce((a, c) => a + c.count, 0)
    );

    const selectedPrice = computed(() =>
      selectedItem.value.reduce((a, c) => a + c.count * c.price, 0)
    );

    const isAll = computed(() => cartList.value.every((item) => item.selected));

    return {
      cartList,
      allCount,
      allPrice,
      selectedItem,
      selectedCount,
      selectedPrice,
      isAll,
      addCart,
      delCart,
      clearCart,
      allCheck,
    };
  },
  { persist: true }
);
