import { IconButton, Link, Table, TableBody, TableContainer, TableHead, Typography } from "@mui/material";
import {
  AppSectionTitle,
  AsyncCallArgs,
  GridItem,
  OptionTableCell,
  ProcessEnvironments,
  useAsyncCall,
} from "@duosoftbg/bpo-components";
import { useTranslation } from "react-i18next";
import { deleteCpcFile, getActivityToken, uploadCpcFile } from "../../axios/api/services";
import { ApiEndpoints } from "../../axios/api/endpoints";
import React, { useEffect, useRef, useState } from "react";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import AttachmentLink from "../../components/AttachmentLink";
import UploadFileIcon from "@mui/icons-material/UploadFile";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";

const CpcFilesTable = ({
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
}) => {
  const { t } = useTranslation();
  const { asyncCall } = useAsyncCall();
  const [reloadActivityToken, setReloadActivityToken] = useState<Number>(0);

  const inputFileRefA = useRef(null);
  const inputFileRefB = useRef(null);
  const inputFileRefC = useRef(null);
  const inputFileRefD = useRef(null);
  const inputFileRefE = useRef(null);
  const inputFileRefF = useRef(null);
  const inputFileRefG = useRef(null);
  const inputFileRefH = useRef(null);
  const inputFileRefY = useRef(null);
  const inputFileRefConcordance = useRef(null);
  const inputFileRefValidity = useRef(null);

  const handleAttachmentDownload = (fileType) => {
    window.open(
      `${ProcessEnvironments.Api.Admin.PatentClassification}/api/v1${ApiEndpoints.cpc.download}/${fileType}`,
      "_blank"
    );
  };

  const rowsArray = [
    {
      fileType: "TITLE_A",
      label: "l.title.a",
      fileName: titleAFileName,
      inputFileRef: inputFileRefA,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
    {
      fileType: "TITLE_B",
      label: "l.title.b",
      fileName: titleBFileName,
      inputFileRef: inputFileRefB,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
    {
      fileType: "TITLE_C",
      label: "l.title.c",
      fileName: titleCFileName,
      inputFileRef: inputFileRefC,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
    {
      fileType: "TITLE_D",
      label: "l.title.d",
      fileName: titleDFileName,
      inputFileRef: inputFileRefD,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
    {
      fileType: "TITLE_E",
      label: "l.title.e",
      fileName: titleEFileName,
      inputFileRef: inputFileRefE,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
    {
      fileType: "TITLE_F",
      label: "l.title.f",
      fileName: titleFFileName,
      inputFileRef: inputFileRefF,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
    {
      fileType: "TITLE_G",
      label: "l.title.g",
      fileName: titleGFileName,
      inputFileRef: inputFileRefG,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
    {
      fileType: "TITLE_H",
      label: "l.title.h",
      fileName: titleHFileName,
      inputFileRef: inputFileRefH,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
    {
      fileType: "TITLE_Y",
      label: "l.title.y",
      fileName: titleYFileName,
      inputFileRef: inputFileRefY,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
    {
      fileType: "CONCORDANCE",
      label: "l.concordance",
      fileName: concordanceFileName,
      inputFileRef: inputFileRefConcordance,
      link: "https://www.cooperativepatentclassification.org/cpcConcordances",
    },
    {
      fileType: "VALIDITY",
      label: "l.validity",
      fileName: validityFileName,
      inputFileRef: inputFileRefValidity,
      link: "https://www.cooperativepatentclassification.org/cpcSchemeAndDefinitions/bulk",
    },
  ];

  useEffect(() => {
    const asyncCallArgs: AsyncCallArgs = {
      promise: getActivityToken(),
      onSuccess: (response) => {
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
        setCurrentProcess(response.currentProcess);
        setCpcProcessHistory(response.cpcProcessHistory);
      },
      onError: () => {},
    };
    asyncCall(asyncCallArgs);
    // eslint-disable-next-line
  }, [asyncCall, reloadActivityToken]);

  function onFileChange(e, fileType) {
    setCurrentProcess("handleFileUploadCpc" + fileType);
    const uploadFile: AsyncCallArgs = {
      promise: uploadCpcFile(e.target.files[0], fileType),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(uploadFile);
  }

  function deleteFile(fileType, inputFileRef) {
    setCurrentProcess("deleteCpcFile" + fileType);
    const deleteFile: AsyncCallArgs = {
      promise: deleteCpcFile(fileType),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
        inputFileRef.current.value = "";
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(deleteFile);
  }

  return (
    <GridItem sm={12} md={12}>
      <AppSectionTitle title={"l.needed.files"} />
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>{t("l.file.type")}</TableCell>
              <TableCell>{t("l.file.link")}</TableCell>
              <TableCell>{t("l.file.name")}</TableCell>
              <TableCell style={{ width: "10%" }}></TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {rowsArray.map((row, index) => (
              <TableRow key={row.fileType}>
                <TableCell>{t(row.label)}</TableCell>
                <TableCell>
                  <Link href={row.link} title={t("l.file.link")} target={"_blank"}>
                    www.cooperativepatentclassification.org
                  </Link>
                </TableCell>
                <TableCell>
                  {row.fileName && (
                    <AttachmentLink
                      onClick={() => {
                        handleAttachmentDownload(row.fileType);
                      }}
                      target="_blank"
                    >
                      {row.fileName ? row.fileName : " - "}
                    </AttachmentLink>
                  )}
                  {!row.fileName && <Typography> - </Typography>}
                </TableCell>
                <OptionTableCell>
                  <>
                    <input
                      ref={row.inputFileRef}
                      value={undefined}
                      id={"upload-" + row.fileType}
                      type="file"
                      style={{ display: "none" }}
                      onChange={(event) => onFileChange(event, row.fileType)}
                      accept={"application/txt"}
                    />
                    <IconButton
                      disabled={currentProcess !== null}
                      color={"primary"}
                      title={t("l.upload.file")}
                      onClick={() => {
                        row.inputFileRef.current.value = "";
                        row.inputFileRef.current.click();
                      }}
                    >
                      <UploadFileIcon />
                    </IconButton>
                    {row.fileType && (
                      <IconButton
                        disabled={currentProcess !== null}
                        color={"error"}
                        title={t("l.delete.file")}
                        onClick={() => {
                          deleteFile(row.fileType, row.inputFileRef);
                        }}
                      >
                        <DeleteOutlineIcon />
                      </IconButton>
                    )}
                  </>
                </OptionTableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </GridItem>
  );
};

export default CpcFilesTable;
