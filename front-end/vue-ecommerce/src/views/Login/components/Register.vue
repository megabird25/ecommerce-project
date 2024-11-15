<script setup>
import { ref } from "vue";
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/el-message.css";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore.js";

const emit = defineEmits(["currentComponent"]);
const toggleComponent = () => {
  emit("currentComponent", "Login");
};

const form = ref({
  username: "",
  password: "",
  rePassword: "",
  agree: false,
});

const checkRePassword = (_rule, value, callback) => {
  if (value === "") {
    callback(new Error("請再次填入密碼"));
  } else if (value !== form.value.password) {
    callback(new Error("輸入的密碼不一致"));
  } else {
    callback();
  }
};

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
  rePassword: [{ validator: checkRePassword, trigger: "blur" }],
};

const formRef = ref({});
const userStore = useUserStore();
const router = useRouter();
const doLogin = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      const res = await userStore.registerUser(form.value);
      if (res.code === 0) {
        ElMessage({ type: "success", message: res.message });
        router.replace({ path: "/" });
      } else {
        ElMessage({ type: "error", message: res.message });
        form.value.username = "";
        form.value.password = "";
        form.value.rePassword = "";
      }
    }
  });
};
</script>
<template>
  <div class="wrapper">
    <nav>
      <i>用戶註冊</i>
    </nav>
    <div class="account-box">
      <div class="form">
        <el-form
          :model="form"
          :rules="rules"
          label-position="right"
          label-width="auto"
          ref="formRef"
        >
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="請輸入帳號" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="form.password"
              placeholder="請輸入密碼"
              type="password"
              show-password
            />
          </el-form-item>
          <el-form-item prop="rePassword">
            <el-input
              v-model="form.rePassword"
              placeholder="再次輸入密碼"
              type="password"
              show-password
            />
          </el-form-item>
          <el-button size="large" class="subBtn" @click="doLogin">
            註冊
          </el-button>
        </el-form>
        <el-link
          class="reg"
          type="info"
          :underline="false"
          @click="toggleComponent"
        >
          ← 登入
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
