import httpInstance from "@/utils/http.js";

export const getUserAddressAPI = () => {
  return httpInstance({
    url: "/user/address",
    
  });
};

export const createAddressAPI = (data) => {
  return httpInstance({
    url: "/user/address",
    method: "POST",
    data
  });
};

export const updateAddressAPI = (id, data) => {
  return httpInstance({
    url: `/user/address/${id}`,
    method: "PUT",
    data
  });
};

export const setDefaultAddressAPI = (id) => {
  return httpInstance({
    url: `/user/address/${id}`,
    method: "PATCH",
  });
};

export const deleteAddressAPI = (id) => {
  return httpInstance({
    url: `/user/address/${id}`,

    method: "DELETE",
  });
};
