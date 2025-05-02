import axios, { Axios } from "axios";
const api: Axios = axios.create({
  baseURL: "localhost:8080/",
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

export default api;
