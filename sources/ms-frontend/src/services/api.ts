import axios, { Axios } from "axios";
const api: Axios = axios.create({
  baseURL: "localhost:8080/",
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

// Auto gather data from json response
api.interceptors.response.use(
  function onFullfield(response) {
    return response.data;
  },
  function onRejected(error) {
    return Promise.reject(error);
  },
);

export default api;
