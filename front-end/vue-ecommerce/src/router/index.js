import { createRouter, createWebHistory } from "vue-router";
// import { useUserStore } from "@/stores/userStore";

import Login from "@/views/Login/index.vue";
import Layout from "@/views/Layout/index.vue";
import Home from "@/views/Home/index.vue";
import Category from "@/views/Category/index.vue";
import SubCategory from "@/views/SubCategory/index.vue";
import Search from "@/views/Search/index.vue";
import Detail from "@/views/Detail/index.vue";
import CartList from "@/views/CartList/index.vue";
import Checkout from "@/views/Checkout/index.vue";
import Member from "@/views/Member/index.vue";
import UserInfo from "@/views/Member/components/UserInfo.vue";
import UserOrder from "@/views/Member/components/UserOrder.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      component: Layout,
      children: [
        { path: "", component: Home },
        { path: "category/:id", component: Category },
        { path: "category/sub/:id", component: SubCategory },
        { path: "search", component: Search },
        { path: "detail/:id", component: Detail },
        { path: "cartlist", component: CartList },
        { path: "checkout", component: Checkout },
        {
          path: "member",
          component: Member,
          meta: { requiresAuth: true },
          children: [
            { path: "user", component: UserInfo },
            { path: "order", component: UserOrder },
          ],
        },
      ],
    },
    { path: "/login", component: Login },
  ],
  scrollBehavior() {
    return {
      top: 0,
    };
  },
});

// router.beforeEach((to, _, next) => {
//   if (to.meta.requiresAuth && !useUserStore().isAuthenticated()) {
//     next('/login');
//   } else {
//     next();
//   }
// });

export default router;
