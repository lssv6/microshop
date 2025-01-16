import axios, { type AxiosInstance } from "axios";

const defaultHeaders = {
	Accept: "application/json",
	"Content-Type": "application/json",
	"Access-Control-Allow-Methods": "*",
	"Access-Control-Allow-Origin": "*",
	"Access-Control-Allow-Headers": "*",
};

const api: AxiosInstance = axios.create({
	headers: defaultHeaders,
	timeout: 30 * 1000,
});

export default api;
