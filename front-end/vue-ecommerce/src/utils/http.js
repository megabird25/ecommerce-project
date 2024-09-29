import axios from "axios";
import "element-plus/theme-chalk/el-message.css";
import router from "@/router";
import { useUserStore } from "@/stores/userStore";

const httpInstance = axios.create({
  baseURL: "/api",
  timeout: 5000,
  withCredentials: true,
});

httpInstance.interceptors.request.use(
  (config) => {
    return config;
  },
  (e) => Promise.reject(e)
);

httpInstance.interceptors.response.use(
  (res) => res.data,
  (e) => {
    const userStore = useUserStore();
    if (e.response.status === 401 || e.response.status === 403) {
      userStore.clearUserInfo();
      router.push("/");
    }

    return Promise.reject(e);
  }
);

export default httpInstance;
