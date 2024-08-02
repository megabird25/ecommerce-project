import httpInstance from "@/utils/http.js";

export const getBannerAPI = () => {
  return httpInstance({
    url: "/home/banner",
  });
};

export const findNewAPI = () => {
  return httpInstance({
    url: "/home/new",
  });
};

export const findHotAPI = () => {
  return httpInstance({
    url: "/home/hot",
  });
};

export const getGoodsAPI = () => {
  return httpInstance({
    url: '/home/goods'
  })
}
