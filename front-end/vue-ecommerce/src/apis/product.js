import httpInstance from "@/utils/http";

export const searchProductsAPI = (data) => {
  return httpInstance({
    url: "/product/search",
    method: "POST",
    params: data,
  });
};

export const getProductDetailAPI = (id) => {
  return httpInstance({
    url: `/product/${id}`,
  });
};
