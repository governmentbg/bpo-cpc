import { CircularProgress, IconButton, Table, TableBody, TableContainer, TableHead } from "@mui/material";
import PlayCircleOutlineIcon from "@mui/icons-material/PlayCircleOutline";
import { AppSectionTitle, AsyncCallArgs, GridItem, OptionTableCell, useAsyncCall } from "@duosoftbg/bpo-components";
import { useTranslation } from "react-i18next";
import {
  addMissingIpcClasses,
  deleteUnusedIpcClasses,
  finalizeIpcUpdate,
  fixInvalidNames,
  getActivityToken,
  normalizeIpcClasses,
  saveAllXmlEntries,
  takeLatestForDuplicateIpcs,
  updateEditionOfValidIpcClasses,
} from "../../axios/api/services";
import React, { useEffect, useState } from "react";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";

const IpcOperationsTable = ({
  currentProcess,
  setCurrentProcess,
  setIpcProcessHistory,
  ipcXmlSchemaFileName,
  setIpcXmlSchemaFileName,
}) => {
  const { t } = useTranslation();
  const { asyncCall } = useAsyncCall();
  const [reloadActivityToken, setReloadActivityToken] = useState<Number>(0);

  useEffect(() => {
    const asyncCallArgs: AsyncCallArgs = {
      promise: getActivityToken(),
      onSuccess: (response) => {
        setCurrentProcess(response.currentProcess);
        setIpcProcessHistory(response.ipcProcessHistory);
        setIpcXmlSchemaFileName(response.ipcXmlSchemaFileName);
      },
      onError: () => {},
    };
    asyncCall(asyncCallArgs);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [asyncCall, reloadActivityToken]);

  function saveAllXmlEntriesFunction() {
    setCurrentProcess("saveAllXmlEntries");
    const asyncCallArgs: AsyncCallArgs = {
      promise: saveAllXmlEntries(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function deleteUnusedIpcClassesFunction() {
    setCurrentProcess("deleteUnusedIpcClasses");
    const asyncCallArgs: AsyncCallArgs = {
      promise: deleteUnusedIpcClasses(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function normalizeIpcClassesFunction() {
    setCurrentProcess("normalizeIpcClasses");
    const asyncCallArgs: AsyncCallArgs = {
      promise: normalizeIpcClasses(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function takeLatestForDuplicateIpcsFunction() {
    setCurrentProcess("takeLatestForDuplicateIpcs");
    const asyncCallArgs: AsyncCallArgs = {
      promise: takeLatestForDuplicateIpcs(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function updateEditionOfValidIpcClassesFunction() {
    setCurrentProcess("updateEditionOfValidIpcClasses");
    const asyncCallArgs: AsyncCallArgs = {
      promise: updateEditionOfValidIpcClasses(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function fixInvalidNamesFunction() {
    setCurrentProcess("fixInvalidNames");
    const asyncCallArgs: AsyncCallArgs = {
      promise: fixInvalidNames(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function addMissingIpcClassesFunction() {
    setCurrentProcess("addMissingIpcClasses");
    const asyncCallArgs: AsyncCallArgs = {
      promise: addMissingIpcClasses(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function finalizeIpcUpdateFunction() {
    setCurrentProcess("finalizeIpcUpdate");
    const asyncCallArgs: AsyncCallArgs = {
      promise: finalizeIpcUpdate(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  return (
    <GridItem sm={12} md={12}>
      <AppSectionTitle title={"l.operations.list"} />
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ width: "3%" }}>№</TableCell>
              <TableCell>{t("l.operation")}</TableCell>
              <TableCell style={{ width: "10%" }}></TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            <TableRow key={"save-xml-entries"}>
              <TableCell>1</TableCell>
              <TableCell>{t("l.save.xml.entries")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "saveAllXmlEntries" && (
                  <IconButton
                    disabled={currentProcess !== null || ipcXmlSchemaFileName === null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      saveAllXmlEntriesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "saveAllXmlEntries" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"delete-unused-ipc"}>
              <TableCell>2</TableCell>
              <TableCell>{t("l.delete.unused.ipc")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "deleteUnusedIpcClasses" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      deleteUnusedIpcClassesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "deleteUnusedIpcClasses" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"normalize-ipc"}>
              <TableCell>3</TableCell>
              <TableCell>{t("l.normalize.ipc")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "normalizeIpcClasses" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      normalizeIpcClassesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "normalizeIpcClasses" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"latest-version-duplicates"}>
              <TableCell>4</TableCell>
              <TableCell>{t("l.latest.version.duplicates")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "takeLatestForDuplicateIpcs" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      takeLatestForDuplicateIpcsFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "takeLatestForDuplicateIpcs" && (
                  <CircularProgress style={{ width: 30, height: 30 }} />
                )}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"update-valid-ipcs"}>
              <TableCell>5</TableCell>
              <TableCell>{t("l.update.valid.ipcs")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "updateEditionOfValidIpcClasses" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      updateEditionOfValidIpcClassesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "updateEditionOfValidIpcClasses" && (
                  <CircularProgress style={{ width: 30, height: 30 }} />
                )}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"update-invalid-names"}>
              <TableCell>6</TableCell>
              <TableCell>{t("l.update.invalid.names")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "fixInvalidNames" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      fixInvalidNamesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "fixInvalidNames" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"add-missing-ipcs"}>
              <TableCell>7</TableCell>
              <TableCell>{t("l.add.missing.ipcs")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "addMissingIpcClasses" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      addMissingIpcClassesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "addMissingIpcClasses" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"finalize-ipc"}>
              <TableCell>8</TableCell>
              <TableCell>{t("l.finalize.ipc")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "finalizeIpcUpdate" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      finalizeIpcUpdateFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "finalizeIpcUpdate" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    </GridItem>
  );
};

export default IpcOperationsTable;
