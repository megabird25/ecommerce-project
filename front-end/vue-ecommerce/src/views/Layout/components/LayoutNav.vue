<script setup>
import { RouterLink } from "vue-router";
import { useUserStore } from "@/stores/userStore.js";
import { ElMessage } from "element-plus";

const userStore = useUserStore();
const confirm = async () => {
  const res = await userStore.logoutUser();
  ElMessage({ type: "success", message: res.message });
};
</script>

<template>
  <nav class="app-topnav">
    <div class="container">
      <ul>
        <template v-if="userStore.userInfo.username">
          <li>
            <p>
              <i class="fa-regular fa-user"></i>
              {{ userStore.userInfo.username }}
            </p>
          </li>
          <li><RouterLink to="/member/user">會員中心</RouterLink></li>
          <li><RouterLink to="/member/order">我的訂單</RouterLink></li>
          <li>
            <el-popconfirm
              @confirm="confirm"
              title="確認退出嗎?"
              confirm-button-text="確認"
              cancel-button-text="取消"
            >
              <template #reference>
                <a href="javascript:;">登出</a>
              </template>
            </el-popconfirm>
          </li>
        </template>
        <template v-else>
          <li>
            <a href="javascript:;" @click="$router.push('/login')">登入</a>
          </li>
          <li><a href="javascript:;">幫助中心</a></li>
          <li><a href="javascript:;">關於我們</a></li>
        </template>
      </ul>
    </div>
  </nav>
</template>

<style scoped lang="scss">
.app-topnav {
  background: #333;
  ul {
    display: flex;
    height: 53px;
    justify-content: flex-end;
    align-items: center;
    li {
      p, a {
        padding: 0 15px;
        color: #cdcdcd;
        line-height: 1;
        display: inline-block;

        i {
          font-size: 14px;
          margin-right: 2px;
        }

        &:hover {
          color: $xtxColor;
        }
      }

      ~ li {
        a {
          border-left: 2px solid #666;
        }
      }
    }
  }
}
</style>
