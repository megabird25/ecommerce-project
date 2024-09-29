<script setup>
import { ElMessage } from "element-plus";
import { ref } from "vue";
import { resetPasswordAPI } from "@/apis/user";

const pwdData = ref({
  old_password: "",
  new_password: "",
  re_password: "",
});

const checkRePassword = (_rule, value, callback) => {
  if (value === "") {
    callback(new Error("請再次填入密碼"));
  } else if (value !== pwdData.value.new_password) {
    callback(new Error("輸入的密碼不一致"));
  } else {
    callback();
  }
};

const rules = {
  old_password: [
    { required: true, message: "請填入舊密碼", trigger: "blur" },
    { min: 6, max: 14, message: "需為6~14位英文或數字", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9]+$/,
      message: "需為6~14位英文或數字",
      trigger: "blur",
    },
  ],
  new_password: [
    { required: true, message: "請填入新密碼", trigger: "blur" },
    { min: 6, max: 14, message: "需為6~14位英文或數字", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9]+$/,
      message: "需為6~14位英文或數字",
      trigger: "blur",
    },
  ],
  re_password: [{ validator: checkRePassword, trigger: "blur" }],
};

const formRef = ref({});
const resetPassword = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      const res = await resetPasswordAPI(pwdData.value);
      if (res.code === 0) {
        pwdData.value.old_password = "";
        pwdData.value.new_password = "";
        pwdData.value.re_password = "";
        ElMessage.success("更改密碼成功");
      } else {
        ElMessage.error(res.message);
      }
    }
  });
};
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>更改密碼</span>
      </div>
    </template>
    <el-row justify="center">
      <el-col :span="12">
        <el-form
          :model="pwdData"
          :rules="rules"
          label-width="100px"
          size="large"
          ref="formRef"
        >
          <el-form-item label="舊密碼" prop="old_password">
            <el-input
              v-model="pwdData.old_password"
              type="password"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item label="新密碼" prop="new_password">
            <el-input
              v-model="pwdData.new_password"
              type="password"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item required label="確認新密碼" prop="re_password">
            <el-input
              v-model="pwdData.re_password"
              type="password"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="resetPassword"
              >提交修改</el-button
            >
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>
