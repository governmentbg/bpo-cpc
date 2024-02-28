import { FilterGroup } from "../../../types/filters";
import { ipcFilterInitialValues } from "../../../init/ipcFilterInitialValues";
import { ipcPatentsFilterInitialValues } from "../../../init/ipcPatentsFilterInitialValues";

export const SEARCH_FILTERS_GROUP: FilterGroup = {
  MISSING_CLASSES: "sf_missing_classes",
  OLD_VERSION_CLASSES: "sf_old_version_classes",
  IPC_PATENTS: "sf_ipc_patents",
};

export const SEARCH_TABLE_CONFIG = {
  [SEARCH_FILTERS_GROUP.MISSING_CLASSES]: {
    initValue: ipcFilterInitialValues,
  },
  [SEARCH_FILTERS_GROUP.OLD_VERSION_CLASSES]: {
    initValue: ipcFilterInitialValues,
  },
  [SEARCH_FILTERS_GROUP.IPC_PATENTS]: {
    initValue: ipcPatentsFilterInitialValues,
  },
};
