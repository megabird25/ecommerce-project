import httpInstance from "@/utils/http.js";

/**
 * @data {
 *   username: happy123,
 *   password: 123456,
 * }
 */
export const registerAPI = (data) => {
  return httpInstance({
    url: "/user/register",
    method: "POST",
    data,
  });
};

/**
 * @data {
 *   username: happy123,
 *   password: 123456,
 * }
 */
export const loginAPI = (data) => {
  return httpInstance({
    url: "/user/login",
    method: "POST",
    data,
  });
};

export const logoutAPI = () => {
  return httpInstance({
    url: "/user/logout",
    method: "POST",
  });
};
