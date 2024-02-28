import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE, DESC_ORDER } from "@duosoftbg/bpo-components";
import { IpcPatentsFilterDetails } from "../types/types";

export const ipcPatentsFilterInitialValues: IpcPatentsFilterDetails = {
  fileNbr: "",
  fileSer: "",
  fileTyp: "",
  patentTitle: "",
  regNum: "",
  ipcEditionCode: "",
  symbol: "",
  page: DEFAULT_PAGE,
  pageSize: DEFAULT_PAGE_SIZE,
  order: DESC_ORDER,
  orderBy: "todo",
};
