<script setup>
import { ref } from "vue";
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/el-message.css";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore.js";

const emit = defineEmits(["currentComponent"]);
const toggleComponent = () => {
  emit("currentComponent", "Register");
};

const form = ref({
  username: "",
  password: "",
});

const rules = {
  username: [
    { required: true, message: "請填入帳號", trigger: "blur" },
    { min: 6, max: 14, message: "需為6~14位英文或數字", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9]+$/,
      message: "需為6~14位英文或數字",
      trigger: "blur",
    },
  ],
  password: [
    { required: true, message: "請填入密碼", trigger: "blur" },
    { min: 6, max: 14, message: "需為6~14位英文或數字", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9]+$/,
      message: "需為6~14位英文或數字",
      trigger: "blur",
    },
  ],
};

const formRef = ref(false);
const userStore = useUserStore();
const router = useRouter();
const doLogin = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      const res = await userStore.getUserInfo(form.value);
      if (userStore.userInfo) {
        ElMessage({ type: "success", message: res.message });
        router.replace({ path: "/" });
      } else {
        ElMessage({ type: "error", message: res.message });
        form.value.username = "";
        form.value.password = "";
      }
    }
  });
};
</script>
<template>
  <div class="wrapper">
    <nav>
      <i>帳號登入</i>
    </nav>
    <div class="account-box">
      <div class="form">
        <el-form
          :model="form"
          :rules="rules"
          label-position="right"
          label-width="auto"
          status-icon
          ref="formRef"
        >
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="帳號" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="form.password"
              placeholder="密碼"
              type="password"
              show-password
            />
          </el-form-item>
          <el-button size="large" class="subBtn" @click="doLogin">
            點擊登入
          </el-button>
        </el-form>
        <el-link
          class="reg"
          type="info"
          :underline="false"
          @click="toggleComponent"
        >
          註冊 →
        </el-link>
      </div>
    </div>
  </div>
</template>
<style scoped lang="scss">
.wrapper {
  width: 380px;
  background: #fff;
  position: absolute;
  left: 50%;
  top: 54px;
  transform: translate3d(100px, 0, 0);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);

  nav {
    font-size: 14px;
    height: 55px;
    margin-bottom: 20px;
    border-bottom: 1px solid #f5f5f5;
    display: flex;
    padding: 0 40px;
    text-align: right;
    align-items: center;

    i {
      flex: 1;
      line-height: 1;
      display: inline-block;
      font-size: 18px;
      position: relative;
      text-align: center;
    }
  }
}

.account-box {
  .toggle {
    padding: 15px 40px;
    text-align: right;

    a {
      color: $xtxColor;

      i {
        font-size: 14px;
      }
    }
  }

  .form {
    padding: 0 20px 20px 20px;

    &-item {
      margin-bottom: 28px;

      .input {
        position: relative;
        height: 36px;

        > i {
          width: 34px;
          height: 34px;
          background: #cfcdcd;
          color: #fff;
          position: absolute;
          left: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 18px;
        }

        input {
          padding-left: 44px;
          border: 1px solid #cfcdcd;
          height: 36px;
          line-height: 36px;
          width: 100%;

          &.error {
            border-color: $priceColor;
          }

          &.active,
          &:focus {
            border-color: $xtxColor;
          }
        }

        .code {
          position: absolute;
          right: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 14px;
          background: #f5f5f5;
          color: #666;
          width: 90px;
          height: 34px;
          cursor: pointer;
        }
      }

      > .error {
        position: absolute;
        font-size: 12px;
        line-height: 28px;
        color: $priceColor;

        i {
          font-size: 14px;
          margin-right: 2px;
        }
      }
    }
  }
}

.subBtn {
  background: $xtxColor;
  width: 100%;
  color: #fff;
}

.reg {
  display: flex;
  justify-content: center;
  margin-top: 15px;
}
</style>
