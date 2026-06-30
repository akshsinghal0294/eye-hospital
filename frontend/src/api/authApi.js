import axiosInstance from "./axiosInstance";

const login = async (username, password) => {
  const response = await axiosInstance.post("/api/auth/login", {
    username,
    password,
  });

  return response.data;
};

const authApi = {
  login,
};

export default authApi;