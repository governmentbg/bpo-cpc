import { CircularProgress, IconButton, Table, TableBody, TableContainer, TableHead } from "@mui/material";
import PlayCircleOutlineIcon from "@mui/icons-material/PlayCircleOutline";
import {
  AppSectionTitle,
  AsyncCallArgs,
  GridItem,
  isEmpty,
  OptionTableCell,
  useAsyncCall,
} from "@duosoftbg/bpo-components";
import { useTranslation } from "react-i18next";
import {
  addMissingCpcClasses,
  deleteUnusedCpcClasses,
  fillConcordanceTable,
  finalizeCpcUpdate,
  getActivityToken,
  saveNewCpcEntries,
  updateValidCpcClasses,
} from "../../axios/api/services";
import React, { useEffect, useState } from "react";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";

const CpcOperationsTable = ({
  currentProcess,
  setCurrentProcess,
  setCpcProcessHistory,
  setTitleAFileName,
  setTitleBFileName,
  setTitleCFileName,
  setTitleDFileName,
  setTitleEFileName,
  setTitleFFileName,
  setTitleGFileName,
  setTitleHFileName,
  setTitleYFileName,
  setConcordanceFileName,
  setValidityFileName,
  titleAFileName,
  titleBFileName,
  titleCFileName,
  titleDFileName,
  titleEFileName,
  titleFFileName,
  titleGFileName,
  titleHFileName,
  titleYFileName,
  concordanceFileName,
  validityFileName,
  cpcLatestVersion,
}) => {
  const { t } = useTranslation();
  const { asyncCall } = useAsyncCall();
  const [reloadActivityToken, setReloadActivityToken] = useState<Number>(0);

  useEffect(() => {
    const asyncCallArgs: AsyncCallArgs = {
      promise: getActivityToken(),
      onSuccess: (response) => {
        setCurrentProcess(response.currentProcess);
        setCpcProcessHistory(response.cpcProcessHistory);
        setTitleAFileName(response.titleAFileName);
        setTitleBFileName(response.titleBFileName);
        setTitleCFileName(response.titleCFileName);
        setTitleDFileName(response.titleDFileName);
        setTitleEFileName(response.titleEFileName);
        setTitleFFileName(response.titleFFileName);
        setTitleGFileName(response.titleGFileName);
        setTitleHFileName(response.titleHFileName);
        setTitleYFileName(response.titleYFileName);
        setConcordanceFileName(response.concordanceFileName);
        setValidityFileName(response.validityFileName);
      },
      onError: () => {},
    };
    asyncCall(asyncCallArgs);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [asyncCall, reloadActivityToken]);

  function saveNewCpcEntriesFunction() {
    setCurrentProcess("saveNewCpcEntries");
    const asyncCallArgs: AsyncCallArgs = {
      promise: saveNewCpcEntries(cpcLatestVersion),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function deleteUnusedCpcClassesFunction() {
    setCurrentProcess("deleteUnusedCpcClasses");
    const asyncCallArgs: AsyncCallArgs = {
      promise: deleteUnusedCpcClasses(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function updateValidCpcClassesFunction() {
    setCurrentProcess("updateValidCpcClasses");
    const asyncCallArgs: AsyncCallArgs = {
      promise: updateValidCpcClasses(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function addMissingCpcClassesFunction() {
    setCurrentProcess("addMissingCpcClasses");
    const asyncCallArgs: AsyncCallArgs = {
      promise: addMissingCpcClasses(),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function fillConcordanceTableFunction() {
    setCurrentProcess("fillConcordanceTable");
    const asyncCallArgs: AsyncCallArgs = {
      promise: fillConcordanceTable(cpcLatestVersion),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(asyncCallArgs);
  }

  function finalizeCpcUpdateFunction() {
    setCurrentProcess("finalizeCpcUpdate");
    const asyncCallArgs: AsyncCallArgs = {
      promise: finalizeCpcUpdate(cpcLatestVersion),
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
            <TableRow key={"save-new-cpc-entries"}>
              <TableCell>1</TableCell>
              <TableCell>{t("l.save.new.cpc.entries")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "saveNewCpcEntries" && (
                  <IconButton
                    disabled={
                      currentProcess !== null ||
                      titleAFileName === null ||
                      titleBFileName === null ||
                      titleCFileName === null ||
                      titleDFileName === null ||
                      titleEFileName === null ||
                      titleFFileName === null ||
                      titleGFileName === null ||
                      titleHFileName === null ||
                      titleYFileName === null ||
                      validityFileName === null ||
                      isEmpty(cpcLatestVersion)
                    }
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      saveNewCpcEntriesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "saveNewCpcEntries" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"delete-unused-cpc"}>
              <TableCell>2</TableCell>
              <TableCell>{t("l.delete.unused.cpc")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "deleteUnusedCpcClasses" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      deleteUnusedCpcClassesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "deleteUnusedCpcClasses" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"update-valid-cpc"}>
              <TableCell>3</TableCell>
              <TableCell>{t("l.update.valid.cpc")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "updateValidCpcClasses" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      updateValidCpcClassesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "updateValidCpcClasses" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"add-missing-cpc"}>
              <TableCell>4</TableCell>
              <TableCell>{t("l.add.missing.cpc")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "addMissingCpcClasses" && (
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      addMissingCpcClassesFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "addMissingCpcClasses" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"fill-concordance"}>
              <TableCell>5</TableCell>
              <TableCell>{t("l.fill.concordance")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "fillConcordanceTable" && (
                  <IconButton
                    disabled={currentProcess !== null || concordanceFileName === null || isEmpty(cpcLatestVersion)}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      fillConcordanceTableFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "fillConcordanceTable" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
            <TableRow key={"finalize-cpc-update"}>
              <TableCell>6</TableCell>
              <TableCell>{t("l.finalize.cpc.update")}</TableCell>
              <OptionTableCell>
                {currentProcess !== "finalizeCpcUpdate" && (
                  <IconButton
                    disabled={currentProcess !== null || isEmpty(cpcLatestVersion)}
                    color={"primary"}
                    title={t("l.start")}
                    onClick={() => {
                      finalizeCpcUpdateFunction();
                    }}
                  >
                    <PlayCircleOutlineIcon />
                  </IconButton>
                )}
                {currentProcess === "finalizeCpcUpdate" && <CircularProgress style={{ width: 30, height: 30 }} />}
              </OptionTableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    </GridItem>
  );
};

export default CpcOperationsTable;
