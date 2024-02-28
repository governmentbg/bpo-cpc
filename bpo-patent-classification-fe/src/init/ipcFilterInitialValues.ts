import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE, DESC_ORDER } from "@duosoftbg/bpo-components";
import { IpcFilterDetails } from "../types/types";

export const ipcFilterInitialValues: IpcFilterDetails = {
  ipcEditionCode: "",
  ipcSectionCode: "",
  ipcClassCode: "",
  ipcSubclassCode: "",
  ipcGroupCode: "",
  ipcSubgroupCode: "",
  ipcName: "",
  latestVersion: "",
  page: DEFAULT_PAGE,
  pageSize: DEFAULT_PAGE_SIZE,
  order: DESC_ORDER,
  orderBy: "ipcSectionCode",
};
