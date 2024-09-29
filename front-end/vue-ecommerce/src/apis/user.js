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

export const updateInfoAPI = (data) => {
  return httpInstance({
    url: "/user/update",
    method: "PUT",
    data,
  });
};

export const resetPasswordAPI = (data) => {
  return httpInstance({
    url: "user/password",
    method: "PATCH",
    data,
  });
};

export const uploadAvatarAPI = (upload) => {
  return httpInstance({
    url: "user/avatar",
    method: "PATCH",
    data: upload,
  });
};
