import axiosInstance from "./axiosInstance";

const processReceptionFlow = async (mobile, data) => {
  const response = await axiosInstance.post(
    `/api/reception/visits/process/${mobile}`,
    data
  );

  return response.data;
};

const extendVisit = async (visitId) => {
  const response = await axiosInstance.put(
    `/api/reception/visits/extend/${visitId}`
  );

  return response.data;
};

const getVisitByOpdNumber = async (opdNumber) => {
  const response = await axiosInstance.get(
    `/api/reception/visits/opd/${opdNumber}`
  );

  return response.data;
};

const getVisitsByPatient = async (patientId) => {
  const response = await axiosInstance.get(
    `/api/reception/visits/patient/${patientId}`
  );

  return response.data;
};

const visitApi = {
  processReceptionFlow,
  extendVisit,
  getVisitByOpdNumber,
  getVisitsByPatient,
};


export default visitApi;