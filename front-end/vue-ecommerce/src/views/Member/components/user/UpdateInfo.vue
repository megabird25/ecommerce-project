<script setup>
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
import { useUserStore } from "@/stores/userStore";
import { updateInfoAPI, uploadAvatarAPI } from "@/apis/user";

const userStore = useUserStore();
const userInfo = ref({ ...userStore.userInfo });
const rules = {
  email: [{ type: "email", message: "格式不正確", trigger: "blur" }],
  mobile: [{ pattern: /^09\d{8}$/, message: "格式不正確", trigger: "blur" }],
  nickname: [
    {
      pattern: /^\S{2,10}$/,
      message: "暱稱必須是2~10位非空字符",
      trigger: "blur",
    },
  ],
};

const formRef = ref(false);
const updateUserInfo = async () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      console.log(userInfo.value);
      const res = await updateInfoAPI(userInfo.value);
      userStore.userInfo = res.result;
      ElMessage.success("修改成功");
    }
  });
};

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== "image/jpeg" && rawFile.type !== "image/png") {
    ElMessage.error("檔案類型只能是JPEG或PNG");
    return false;
  } else if (rawFile.size / 1024 / 1024 > 1) {
    ElMessage.error("檔案大小不能大於1MB");
    return false;
  }
  return true;
};

const uploadAvatar = async ({ file }) => {
  const res = await uploadAvatarAPI(file);
  userStore.userInfo.image_url = res.result;
  userInfo.value.image_url = res.result;
};

const handleAvatarSuccess = () => (response, uploadFile) => {
  ElMessage.success("修改成功");
  console.log(response);
  console.log(uploadFile);
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>會員資料</span>
      </div>
    </template>
    <el-row justify="space-between">
      <el-col :span="12">
        <el-form
          :model="userInfo"
          :rules="rules"
          label-width="100px"
          size="large"
          ref="formRef"
        >
          <el-form-item label="電子信箱" prop="email">
            <el-input v-model="userInfo.email"></el-input>
          </el-form-item>
          <el-form-item label="手機號碼" prop="mobile">
            <el-input v-model="userInfo.mobile"></el-input>
          </el-form-item>
          <el-form-item label="暱稱" prop="nickname">
            <el-input v-model="userInfo.nickname"></el-input>
          </el-form-item>
          <el-form-item label="性別" prop="gender">
            <el-radio-group v-model="userInfo.gender">
              <el-radio value="1">男</el-radio>
              <el-radio value="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="生日" prop="birthday">
            <el-date-picker
              v-model="userInfo.birthday"
              type="date"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateUserInfo"
              >提交修改</el-button
            >
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="8">
        <el-upload
          class="avatar-uploader"
          :http-request="uploadAvatar"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="userInfo.image_url" :src="image_url" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
        <div class="warn">
          <p>檔案大小： 最大1MB</p>
          <p>檔案限制： .JPEG, .PNG</p>
        </div>
      </el-col>
    </el-row>
  </el-card>
</template>
<style scoped lang="scss">
.avatar-uploader ::v-deep() {
  padding-top: 30px;

  .avatar {
    width: 150px;
    height: 150px;
    display: block;
  }

  .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }

  .el-upload:hover {
    border-color: var(--el-color-primary);
  }

  .el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    text-align: center;
  }
}

.warn {
  padding-top: 30px;
}
</style>
