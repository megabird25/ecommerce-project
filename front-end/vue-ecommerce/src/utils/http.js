import axios from "axios";
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/el-message.css";
import { useUserStore } from "@/stores/userStore.js";
import router from "@/router";

const httpInstance = axios.create({
  baseURL: "http://pcapi-xiaotuxian-front-devtest.itheima.net",
  timeout: 5000,
});

httpInstance.interceptors.request.use(
  (config) => {
    const userStore = useUserStore();
    const token = userStore.userInfo.token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (e) => Promise.reject(e)
);

httpInstance.interceptors.response.use(
  (res) => res.data,
  (e) => {
    const userStore = useUserStore();
    ElMessage({
      type: "warning",
      message: e.response.data.message,
    });
    if (e.response.status === 401) {
      userStore.clearUserInfo();
      router.push("/");
    }

    return Promise.reject(e);
  }
);

export default httpInstance;
