import React, { useEffect, useState } from "react";
import IpcXmlSchemaTable from "../components/IpcXmlSchemaTable";
import { useTranslation } from "react-i18next";
import PageWrapper from "../components/common/layout/PageWrapper";
import { AsyncCallArgs, GridContainer, useAsyncCall } from "@duosoftbg/bpo-components";
import IpcOperationsTable from "../components/IpcOperationsTable";
import { getActivityToken } from "../../axios/api/services";
import LogTable from "../components/LogTable";

const IpcLoader = () => {
  const { t } = useTranslation();
  const { asyncCall } = useAsyncCall();
  const [currentProcess, setCurrentProcess] = useState(null);
  const [ipcProcessHistory, setIpcProcessHistory] = useState(null);
  const [ipcXmlSchemaFileName, setIpcXmlSchemaFileName] = useState(null);

  useEffect(() => {
    if (currentProcess !== null) {
      const intervalId = setInterval(() => {
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
      }, 1000);

      return () => clearInterval(intervalId); //This is important
    }
  }, [asyncCall, currentProcess]);

  return (
    <PageWrapper centerTitle title={t("m.ipc")} helmetTitle={t("m.ipc")}>
      <GridContainer spacing={4} mt={0}>
        <IpcXmlSchemaTable
          currentProcess={currentProcess}
          setCurrentProcess={setCurrentProcess}
          setIpcProcessHistory={setIpcProcessHistory}
          ipcXmlSchemaFileName={ipcXmlSchemaFileName}
          setIpcXmlSchemaFileName={setIpcXmlSchemaFileName}
        />
        <IpcOperationsTable
          currentProcess={currentProcess}
          setCurrentProcess={setCurrentProcess}
          setIpcProcessHistory={setIpcProcessHistory}
          ipcXmlSchemaFileName={ipcXmlSchemaFileName}
          setIpcXmlSchemaFileName={setIpcXmlSchemaFileName}
        />
        <LogTable processHistory={ipcProcessHistory} />
      </GridContainer>
    </PageWrapper>
  );
};

export default IpcLoader;
