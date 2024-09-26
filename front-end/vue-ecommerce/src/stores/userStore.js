import { defineStore } from "pinia";
import { ref } from "vue";
import { useCartStore } from "./cartStore.js";
import { loginAPI, logoutAPI } from "@/apis/user.js";

export const useUserStore = defineStore(
  "user",
  () => {
    const cartStore = useCartStore();

    const userInfo = ref({});

    const getUserInfo = async (data) => {
      const res = await loginAPI(data);
      userInfo.value = res.result;
      return res;
    };

    const clearUserInfo = () => {
      userInfo.value = {};
      cartStore.clearCart();
    };

    const logoutUser = async () => {
      const res = await logoutAPI();
      userInfo.value = {};
      cartStore.clearCart();
      return res;
    };

    const isAuthenticated = () => {
      return !!userInfo.value.username;
    };

    return {
      userInfo,
      getUserInfo,
      clearUserInfo,
      logoutUser,
      isAuthenticated,
    };
  },
  { persist: true }
);
