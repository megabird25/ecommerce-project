import httpInstance from "@/utils/http.js";

export const getUserOrderAPI = (params) => {
  return httpInstance({
    url: "/user/order",
    params,
  });
};

export const sendOrderAPI = (data) => {
  return httpInstance({
    url: "/user/order",
    method: "POST",
    data,
  });
};
