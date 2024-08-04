import httpInstance from "@/utils/http.js";

export const getDetail = (id) => {
  return httpInstance({
    url: "/goods",
    params: {
      id,
    },
  });
};

/**
* 取得熱榜商品
 * @param {Number} id - 商品id
 * @param {Number} type - 1代表日熱銷榜 2代表週熱銷榜
 * @param {Number} limit - 取得個數
 */
export const fetchHotGoodsAPI = ({ id, type, limit = 3 }) => {
  return httpInstance({
    url:'/goods/hot',
    params:{
      id, 
      type, 
      limit
    }
  })
}
