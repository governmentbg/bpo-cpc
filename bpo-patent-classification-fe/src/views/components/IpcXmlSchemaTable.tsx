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
import { deleteIpcXml, getActivityToken, uploadIpcXml } from "../../axios/api/services";
import { ApiEndpoints } from "../../axios/api/endpoints";
import React, { useEffect, useRef, useState } from "react";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import AttachmentLink from "../../components/AttachmentLink";
import UploadFileIcon from "@mui/icons-material/UploadFile";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";

const IpcXmlSchemaTable = ({
  currentProcess,
  setCurrentProcess,
  setIpcProcessHistory,
  ipcXmlSchemaFileName,
  setIpcXmlSchemaFileName,
}) => {
  const { t } = useTranslation();
  const { asyncCall } = useAsyncCall();
  const [reloadActivityToken, setReloadActivityToken] = useState<Number>(0);

  const inputFileRef = useRef(null);

  const handleCommonAttachmentDownload = () => {
    window.open(`${ProcessEnvironments.Api.Admin.PatentClassification}/api/v1${ApiEndpoints.ipc.download}`, "_blank");
  };

  useEffect(() => {
    const asyncCallArgs: AsyncCallArgs = {
      promise: getActivityToken(),
      onSuccess: (response) => {
        setIpcXmlSchemaFileName(response.ipcXmlSchemaFileName);
        setCurrentProcess(response.currentProcess);
        setIpcProcessHistory(response.ipcProcessHistory);
      },
      onError: () => {},
    };
    asyncCall(asyncCallArgs);
    // eslint-disable-next-line
  }, [asyncCall, reloadActivityToken]);

  function onFileChange(e) {
    setCurrentProcess("handleFileUpload");
    const uploadFile: AsyncCallArgs = {
      // withGlobalBackdrop: true,
      promise: uploadIpcXml(e.target.files[0]),
      onSuccess: () => {
        setReloadActivityToken(Math.random);
      },
      onError() {
        setReloadActivityToken(Math.random);
      },
    };
    asyncCall(uploadFile);
  }

  function deleteFile() {
    setCurrentProcess("deleteXmlSchema");
    const deleteFile: AsyncCallArgs = {
      promise: deleteIpcXml(),
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
            <TableRow key={"ipc-schema"}>
              <TableCell>{t("l.xml.schema")}</TableCell>
              <TableCell>
                <Link href={"https://ipcpub.wipo.int/"} title={t("l.file.link")} target={"_blank"}>
                  ipcpub.wipo.int
                </Link>
              </TableCell>
              <TableCell>
                {ipcXmlSchemaFileName && (
                  <AttachmentLink
                    onClick={() => {
                      handleCommonAttachmentDownload();
                    }}
                    target="_blank"
                  >
                    {ipcXmlSchemaFileName ? ipcXmlSchemaFileName : " - "}
                  </AttachmentLink>
                )}
                {!ipcXmlSchemaFileName && <Typography> - </Typography>}
              </TableCell>
              <OptionTableCell>
                <>
                  <input
                    ref={inputFileRef}
                    value={undefined}
                    id={"upload-ipc-xml"}
                    type="file"
                    style={{ display: "none" }}
                    onChange={onFileChange}
                    accept={"application/xml"}
                  />
                  <IconButton
                    disabled={currentProcess !== null}
                    color={"primary"}
                    title={t("l.upload.file")}
                    onClick={() => {
                      inputFileRef.current.value = "";
                      inputFileRef.current.click();
                    }}
                  >
                    <UploadFileIcon />
                  </IconButton>
                  {ipcXmlSchemaFileName && (
                    <IconButton
                      disabled={currentProcess !== null}
                      color={"error"}
                      title={t("l.delete.file")}
                      onClick={() => {
                        deleteFile();
                      }}
                    >
                      <DeleteOutlineIcon />
                    </IconButton>
                  )}
                </>
              </OptionTableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    </GridItem>
  );
};

export default IpcXmlSchemaTable;
