import { onMounted, ref } from "vue";
import { useRoute, onBeforeRouteUpdate } from "vue-router";
import { getCategoryAPI } from "@/apis/category.js";

export const useCategory = () => {
  const categoryData = ref({});

  const getCategory = async (id) => {
    const res = await getCategoryAPI(id);
    categoryData.value = res.result;
  };

  const route = useRoute();
  onMounted(() => {
    getCategory(route.params.id);
  });

  onBeforeRouteUpdate((to) => {
    getCategory(to.params.id);
  });

  return { categoryData };
};
