<script setup>
import Login from "./components/Login.vue";
import Register from "./components/Register.vue";
import { ref } from "vue";

const isLogin = ref(true);

const currentComponent = () => {
  isLogin.value = !isLogin.value;
};
</script>

<template>
  <div>
    <header class="login-header">
      <div class="container m-top-20">
        <h1 class="logo">
          <RouterLink to="/">我的電商</RouterLink>
        </h1>
        <RouterLink class="entry" to="/">
          進入網站首頁
          <i class="fa-solid fa-angles-right"></i>
        </RouterLink>
      </div>
    </header>
    <section class="login-section">
      <Login v-if="isLogin" @currentComponent="currentComponent" />
      <Register v-else @currentComponent="currentComponent" />
    </section>
    <footer class="login-footer">
      <div class="container">
        <p>
          <a href="javascript:;">關於我們</a>
          <a href="javascript:;">幫助中心</a>
          <a href="javascript:;">售後服務</a>
          <a href="javascript:;">配送與驗收</a>
          <a href="javascript:;">商務合作</a>
          <a href="javascript:;">搜尋推薦</a>
          <a href="javascript:;">友情連結</a>
        </p>
        <p>CopyRight &copy; 我的電商</p>
      </div>
    </footer>
  </div>
</template>

<style scoped lang="scss">
.login-header {
  background: #fff;
  border-bottom: 1px solid #e4e4e4;

  .container {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
  }

  .logo {
    width: 200px;

    a {
      display: block;
      height: 132px;
      width: 100%;
      text-indent: -9999px;
      background: url("@/assets/images/logo.png") no-repeat center 18px /
        contain;
    }
  }

  .sub {
    flex: 1;
    font-size: 24px;
    font-weight: normal;
    margin-bottom: 38px;
    margin-left: 20px;
    color: #666;
  }

  .entry {
    width: 120px;
    margin-bottom: 38px;
    font-size: 16px;

    &:hover {
      color: $xtxColor;
    }

    i {
      font-size: 14px;
      color: $xtxColor;
      letter-spacing: -5px;
    }
  }
}

.login-section {
  background: url("@/assets/images/login-bg.png") no-repeat center / cover;
  height: 488px;
  position: relative;
}

.login-footer {
  padding: 30px 0 50px;
  background: #fff;

  p {
    text-align: center;
    color: #999;
    padding-top: 20px;

    a {
      line-height: 1;
      padding: 0 10px;
      color: #999;
      display: inline-block;

      ~ a {
        border-left: 1px solid #ccc;
      }

      &:hover {
        color: $xtxColor;
      }
    }
  }
}
</style>
