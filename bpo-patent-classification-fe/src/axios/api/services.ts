import { axiosClient } from "./axiosClient";
import { ApiEndpoints } from "./endpoints";
import qs from "qs";

//COMMON
export const getActivityToken = () => async () => {
  const response = await axiosClient.get(`${ApiEndpoints.common.activityToken}`);
  return response.data;
};

const getAutocompleteResponse = async (url, params) => {
  return await axiosClient.get(url, {
    params,
    paramsSerializer: (params) => {
      return qs.stringify(params);
    },
  });
};

//IPC
export const uploadIpcXml = (file) => async () => {
  let formData = new FormData();
  formData.append("file", file);
  const response = await axiosClient.post(`${ApiEndpoints.ipc.upload}`, formData);
  return response.data;
};

export const deleteIpcXml = () => async () => {
  const response = await axiosClient.delete(`${ApiEndpoints.ipc.deleteSchemaFile}`);
  return response.data;
};

export const saveAllXmlEntries = () => async () => {
  await axiosClient.post(`${ApiEndpoints.ipc.saveAllXmlEntries}`);
};

export const deleteUnusedIpcClasses = () => async () => {
  await axiosClient.post(`${ApiEndpoints.ipc.deleteUnusedIpcClasses}`);
};

export const normalizeIpcClasses = () => async () => {
  await axiosClient.post(`${ApiEndpoints.ipc.normalizeIpcClasses}`);
};

export const takeLatestForDuplicateIpcs = () => async () => {
  await axiosClient.post(`${ApiEndpoints.ipc.takeLatestForDuplicateIpcs}`);
};

export const updateEditionOfValidIpcClasses = () => async () => {
  await axiosClient.post(`${ApiEndpoints.ipc.updateEditionOfValidIpcClasses}`);
};

export const fixInvalidNames = () => async () => {
  await axiosClient.post(`${ApiEndpoints.ipc.fixInvalidNames}`);
};

export const addMissingIpcClasses = () => async () => {
  await axiosClient.post(`${ApiEndpoints.ipc.addMissingIpcClasses}`);
};

export const finalizeIpcUpdate = () => async () => {
  await axiosClient.post(`${ApiEndpoints.ipc.finalizeIpcUpdate}`);
};

// CPC
export const uploadCpcFile = (file, fileType) => async () => {
  let formData = new FormData();
  formData.append("file", file);
  formData.append("fileType", fileType);
  const response = await axiosClient.post(`${ApiEndpoints.cpc.upload}`, formData);
  return response.data;
};

export const deleteCpcFile = (fileType) => async () => {
  const response = await axiosClient.delete(`${ApiEndpoints.cpc.deleteFile}/${fileType}`);
  return response.data;
};

export const saveNewCpcEntries = (latestVersion) => async () => {
  let formData = new FormData();
  formData.append("latestVersion", latestVersion);
  await axiosClient.post(`${ApiEndpoints.cpc.saveNewCpcEntries}`, formData);
};

export const deleteUnusedCpcClasses = () => async () => {
  await axiosClient.post(`${ApiEndpoints.cpc.deleteUnusedCpcClasses}`);
};

export const updateValidCpcClasses = () => async () => {
  await axiosClient.post(`${ApiEndpoints.cpc.updateValidCpcClasses}`);
};

export const addMissingCpcClasses = () => async () => {
  await axiosClient.post(`${ApiEndpoints.cpc.addMissingCpcClasses}`);
};

export const fillConcordanceTable = (latestVersion) => async () => {
  let formData = new FormData();
  formData.append("latestVersion", latestVersion);
  await axiosClient.post(`${ApiEndpoints.cpc.fillConcordanceTable}`, formData);
};

export const finalizeCpcUpdate = (latestVersion) => async () => {
  let formData = new FormData();
  formData.append("latestVersion", latestVersion);
  await axiosClient.post(`${ApiEndpoints.cpc.finalizeCpcUpdate}`, formData);
};

export const missingClassesSearch = (params) => async () => {
  const response = await axiosClient.get(`${ApiEndpoints.missingClasses.search}`, {
    params,
  });
  return response.data;
};

export const oldVersionClassesSearch = (params) => async () => {
  const response = await axiosClient.get(`${ApiEndpoints.oldVersionClasses.search}`, {
    params,
  });
  return response.data;
};

export const getIpcView = (symbol, edition) => async () => {
  const response = await axiosClient.get(ApiEndpoints.common.ipcView, {
    params: { symbol: symbol, edition: edition },
    paramsSerializer: (params) => {
      return qs.stringify(params);
    },
  });
  return response.data;
};

//IPC-PATENTS
export const ipcPatentsSearch = (params) => async () => {
  const response = await axiosClient.get(`${ApiEndpoints.ipcPatents.search}`, {
    params,
  });
  return response.data;
};

export const classifyMultiple = (ids, symbol, oldEdition, newEdition) => async () => {
  let formData = new FormData();
  formData.append("ids", ids);
  formData.append("symbol", symbol);
  formData.append("oldEdition", oldEdition);
  formData.append("newEdition", newEdition);
  await axiosClient.post(`${ApiEndpoints.ipcPatents.classifyMultiple}`, formData);
};

export const classifyAll = (symbol, oldEdition, newEdition) => async () => {
  let formData = new FormData();
  formData.append("symbol", symbol);
  formData.append("oldEdition", oldEdition);
  formData.append("newEdition", newEdition);
  await axiosClient.post(`${ApiEndpoints.ipcPatents.classifyAll}`, formData);
};

export const classifyAllMissing = (oldEdition, oldSymbol, newEditionAndSymbol) => async () => {
  let formData = new FormData();
  formData.append("oldEdition", oldEdition);
  formData.append("oldSymbol", oldSymbol);
  formData.append("newEditionAndSymbol", newEditionAndSymbol);
  await axiosClient.post(`${ApiEndpoints.ipcPatents.classifyAllMissing}`, formData);
};

export const getIpcAutocomplete = (inputValue, page, resultsLimit) => async () => {
  const response = await getAutocompleteResponse(ApiEndpoints.common.ipcAutocomplete, {
    name: inputValue,
    page: page,
    pageSize: resultsLimit,
  });

  return response.data.map(function (type) {
    return { id: type, name: type };
  });
};
