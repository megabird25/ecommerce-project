import { defineStore } from "pinia";
import { ref } from "vue";
import { useCartStore } from "./cartStore.js";
import { loginAPI, logoutAPI, registerAPI } from "@/apis/user.js";

export const useUserStore = defineStore(
  "user",
  () => {
    const cartStore = useCartStore();

    const userInfo = ref({});

    const registerUser = async (data) => {
      const res = await registerAPI(data);
      userInfo.value = res.result;
      return res;
    };

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
      registerUser,
      getUserInfo,
      clearUserInfo,
      logoutUser,
      isAuthenticated,
    };
  },
  { persist: true }
);
