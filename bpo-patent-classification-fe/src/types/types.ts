export interface IpcFilterDetails {
  ipcEditionCode: string;
  ipcSectionCode: string;
  ipcClassCode: string;
  ipcSubclassCode: string;
  ipcGroupCode: string;
  ipcSubgroupCode: string;
  ipcName: string;
  latestVersion: string;
  page: number;
  pageSize: number;
  order: string;
  orderBy: string;
}

export interface IpcPatentsFilterDetails {
  symbol: string;
  ipcEditionCode: string;
  fileTyp: string;
  fileSer: string;
  fileNbr: string;
  regNum: string;
  patentTitle: string;
  page: number;
  pageSize: number;
  order: string;
  orderBy: string;
}
