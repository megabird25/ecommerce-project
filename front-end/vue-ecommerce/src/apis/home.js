import httpInstance from "@/utils/http.js";

export const getBannerAPI = () => {
  return httpInstance({
    url: "/home/banner",
  });
};

export const getHeadAPI = () => {
  return httpInstance({
    url: "/home/head"
  })
}

export const getNewAPI = () => {
  return httpInstance({
    url: "/home/new",
  });
};

export const getHotAPI = () => {
  return httpInstance({
    url: "/home/hot",
  });
};

export const getGoodsAPI = () => {
  return httpInstance({
    url: "/home/goods",
  });
};
