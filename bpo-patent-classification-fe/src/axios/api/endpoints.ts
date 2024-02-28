const ApiEndpoints = {
  common: {
    activityToken: "/common/get-activity-token",
    ipcView: "/common/get-ipc",
    ipcAutocomplete: "/common/ipc-autocomplete",
  },
  ipc: {
    download: "/ipc/download",
    upload: "/ipc/upload",
    deleteSchemaFile: "/ipc/delete-schema-file",
    saveAllXmlEntries: "/ipc/save-all-xml-entries",
    deleteUnusedIpcClasses: "/ipc/delete-unused-ipc-classes",
    normalizeIpcClasses: "/ipc/normalize-ipc-classes",
    takeLatestForDuplicateIpcs: "/ipc/take-latest-for-duplicate-ipcs",
    updateEditionOfValidIpcClasses: "/ipc/update-edition-of-valid-ipc-classes",
    fixInvalidNames: "/ipc/fix-invalid-names",
    addMissingIpcClasses: "/ipc/add-missing-ipc-classes",
    finalizeIpcUpdate: "/ipc/finalize-ipc-update",
  },
  cpc: {
    download: "/cpc/download",
    upload: "/cpc/upload",
    deleteFile: "/cpc/delete-file",
    saveNewCpcEntries: "/cpc/save-new-cpc-entries",
    deleteUnusedCpcClasses: "/cpc/delete-unused-cpc-classes",
    updateValidCpcClasses: "/cpc/update-valid-cpc-classes",
    addMissingCpcClasses: "/cpc/add-missing-cpc-classes",
    fillConcordanceTable: "/cpc/fill-concordance-table",
    finalizeCpcUpdate: "/cpc/finalize-cpc-update",
  },
  missingClasses: { search: "/missing-classes/search" },
  oldVersionClasses: { search: "/old-version-classes/search" },
  ipcPatents: {
    search: "/ipc-patents/search",
    classifyMultiple: "/ipc-patents/classify-multiple",
    classifyAll: "/ipc-patents/classify-all",
    classifyAllMissing: "/ipc-patents/classify-all-missing",
  },
};

export { ApiEndpoints };
