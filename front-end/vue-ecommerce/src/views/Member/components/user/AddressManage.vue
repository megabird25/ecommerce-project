<script setup>
import { onMounted, ref, watch } from "vue";
import {
  createAddressAPI,
  deleteAddressAPI,
  getUserAddressAPI,
  setDefaultAddressAPI,
  updateAddressAPI,
} from "@/apis/address";

const userAddress = ref({});
const addressData = ref({});
const showDialog = ref(false);
const dialogTitle = ref("");
const getAddress = async () => {
  const res = await getUserAddressAPI();
  userAddress.value = res.result;
};

onMounted(() => {
  getAddress();
});

const rules = {
  receiver: [
    { required: true, message: "請填入收貨人", trigger: "blur" },
    {
      pattern: /^\S{2,10}$/,
      message: "格式不正確",
      trigger: "blur",
    },
  ],
  contact: [
    { required: true, message: "請填入聯絡方式", trigger: "blur" },
    { pattern: /^09\d{8}$/, message: "格式不正確", trigger: "blur" },
  ],
  address: [{ required: true, message: "請填入收穫地址", trigger: "blur" }],
};

const formRef = ref({});
const addAddress = () => {
  dialogTitle.value = "添加地址";
  addressData.value = {};
  showDialog.value = true;
};

const editAddress = (item) => {
  dialogTitle.value = "編輯地址";
  addressData.value = { ...item };
  showDialog.value = true;
};

const deleteAddress = async (item) => {
  await deleteAddressAPI(item.id);
  getAddress();
};

const setDefault = async (item) => {
  await setDefaultAddressAPI(item.id);
  getAddress();
};

const confirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      if (dialogTitle.value === "添加地址") {
        await createAddressAPI(addressData.value);
      } else {
        await updateAddressAPI(addressData.value.id, addressData.value);
      }
      getAddress();
      showDialog.value = false;
    }
  });
};

watch(showDialog, (newVal) => {
  if (!newVal) {
    formRef.value.clearValidate();
  }
});
</script>
<template class="page-container">
  <el-card>
    <template #header>
      <div class="header">
        <span>地址管理</span>
      </div>
    </template>
    <div class="action">
      <el-button size="large" @click="addAddress">添加地址</el-button>
    </div>
    <el-scrollbar height="500px">
      <div class="addressWrapper">
        <div class="text item" v-for="item in userAddress" :key="item.id">
          <ul>
            <li><span>收貨人：</span>{{ item.receiver }}</li>
            <li><span>聯絡方式：</span>{{ item.contact }}</li>
            <li><span>收貨地址：</span>{{ item.address }}</li>
          </ul>
          <div class="editor">
            <el-button @click="editAddress(item)" type="primary"
              >編輯</el-button
            >
            <el-button @click="deleteAddress(item)" type="danger"
              >刪除</el-button
            >
            <br />
            <el-button
              @click="setDefault(item)"
              :disabled="item.is_default === 1"
              style="margin-top: 20px"
              >設為預設</el-button
            >
          </div>
        </div>
      </div>
    </el-scrollbar>
  </el-card>
  <!-- 添加、編輯地址 -->
  <el-dialog v-model="showDialog" :title="dialogTitle" width="35%" center>
    <el-form
      :model="addressData"
      :rules="rules"
      label-width="80px"
      size="large"
      ref="formRef"
    >
      <el-form-item label="收貨人" prop="receiver">
        <el-input v-model="addressData.receiver"></el-input>
      </el-form-item>
      <el-form-item label="聯絡方式" prop="contact">
        <el-input v-model="addressData.contact"></el-input>
      </el-form-item>
      <el-form-item label="收貨地址" prop="address">
        <el-input v-model="addressData.address"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="confirm">{{
          dialogTitle === "添加地址" ? "添加" : "編輯"
        }}</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<style scoped lang="scss">
.action {
  margin-bottom: 15px;
}

.text {
  flex: 1;
  min-height: 90px;
  display: flex;
  align-items: center;

  &.item {
    border: 1px solid #f5f5f5;
    margin-bottom: 10px;
    display: flex;
    justify-content: space-between;

    > ul {
      padding: 10px;
      font-size: 14px;
      line-height: 30px;
    }

    .editor {
      margin-right: 10px;
    }
  }
}
</style>
