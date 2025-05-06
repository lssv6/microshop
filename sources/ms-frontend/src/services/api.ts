import axios, { Axios } from "axios";
const api: Axios = axios.create({
  baseURL: "http://localhost:8080/",
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
    "Access-Control-Allow-Methods": "*",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Headers": "*",
  },
});

export default api;
