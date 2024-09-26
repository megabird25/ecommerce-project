import { ref } from "vue";
import { defineStore } from "pinia";
import { getCategoriesAPI } from "@/apis/category.js";

export const useCategoryStore = defineStore("category", () => {
  const categoryList = ref([]);

  const getCategory = async () => {
    const res = await getCategoriesAPI();
    categoryList.value = res.result;
  };

  return { categoryList, getCategory };
});
