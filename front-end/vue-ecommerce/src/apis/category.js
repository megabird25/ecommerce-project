import httpInstance from "@/utils/http.js";

export const getCategoryAPI = (id) => {
  return httpInstance({
    url: "/category",
    params: {
      id,
    },
  });
};
