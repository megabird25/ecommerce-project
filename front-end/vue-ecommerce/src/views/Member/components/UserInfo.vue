<script setup>
import { useUserStore } from '@/stores/userStore.js';
const userStore = useUserStore();

</script>

<template>
  <div class="home-overview">
    <div class="user-meta">
      <div class="avatar">
        <img :src="userStore.userInfo?.avatar" />
      </div>
      <h4>{{ userStore.userInfo.username }}</h4>
    </div>
    <div class="item">
      <router-link to="/member/user/info">
        <span class="iconfont fa-solid fa-user-group"></span>
        <p>會員資料</p>
      </router-link>
      <router-link to="/member/user/password">
        <span class="iconfont fa-solid fa-key"></span>
        <p>更改密碼</p>
      </router-link>
      <router-link to="/member/user/address">
        <span class="iconfont fa-solid fa-location-dot"></span>
        <p>地址管理</p>
      </router-link>
    </div>
  </div>
  <div>
    <RouterView/>
  </div>
  <!-- <div class="like-container">
    <div class="home-panel">
      <div class="header">
        <h4 data-v-bcb266e0="">猜你喜欢</h4>
      </div>
      <div class="goods-list">
        <GoodsItem v-for="good in likeList" :key="good.id" :good="good" />
      </div>
    </div>
  </div> -->
</template>

<style scoped lang="scss">
.home-overview {
  height: 132px;
  background: url(@/assets/images/center-bg.png) no-repeat center / cover;
  display: flex;

  .user-meta {
    flex: 1;
    display: flex;
    align-items: center;

    .avatar {
      width: 85px;
      height: 85px;
      border-radius: 50%;
      overflow: hidden;
      margin-left: 60px;

      img {
        width: 100%;
        height: 100%;
      }
    }

    h4 {
      padding-left: 26px;
      font-size: 18px;
      font-weight: normal;
      color: white;
    }
  }

  .item {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-around;

    &:first-child {
      border-right: 1px solid #f4f4f4;
    }

    a {
      color: white;
      font-size: 16px;
      text-align: center;

      .iconfont {
        font-size: 32px;
      }

      p {
        line-height: 32px;
      }
    }
  }
}

.like-container {
  margin-top: 20px;
  border-radius: 4px;
  background-color: #fff;
}

.home-panel {
  background-color: #fff;
  padding: 0 20px;
  margin-top: 20px;
  height: 400px;

  .header {
    height: 66px;
    border-bottom: 1px solid #f5f5f5;
    padding: 18px 0;
    display: flex;
    justify-content: space-between;
    align-items: baseline;

    h4 {
      font-size: 22px;
      font-weight: 400;
    }
  }

  .goods-list {
    display: flex;
    justify-content: space-around;
  }
}
</style>
