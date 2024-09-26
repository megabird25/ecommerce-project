import httpInstance from "@/utils/http.js";

export const getCategoriesAPI = () => {
  return httpInstance({
    url: "/category",
  });
};

export const getCategoryAPI = (id) => {
  return httpInstance({
    url: `/category/${id}`,
  });
};

/**
 * @data {
 *   page: 1,
 *   pageSize: 20,
 *   sortField: 'publishTime' | 'orderNum' | 'evaluateNum'
 * }
 */
export const getSubCategoryProductsAPI = (id, data) => {
  return httpInstance({
    url: `/category/sub/${id}`,
    params: data,
  });
};
