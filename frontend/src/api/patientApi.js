import axiosInstance from "./axiosInstance";

const registerPatient = async (data) => {
  const response = await axiosInstance.post(
    "/api/reception/patients",
    data
  );

  return response.data;
};

const findByMobile = async (mobile) => {
  const response = await axiosInstance.get(
    `/api/reception/patients/mobile/${mobile}`
  );

  return response.data;
};

const getPatientById = async (id) => {
  const response = await axiosInstance.get(
    `/api/reception/patients/${id}`
  );

  return response.data;
};

const getAllPatients = async () => {
  const response = await axiosInstance.get(
    "/api/reception/patients"
  );

  return response.data;
};

const updatePatient = async (id, data) => {
  const response = await axiosInstance.put(
    `/api/reception/patients/${id}`,
    data
  );

  return response.data;
};

const patientApi = {
  registerPatient,
  findByMobile,
  getPatientById,
  getAllPatients,
  updatePatient,
};

export default patientApi;