import React, { useEffect, useState } from "react";
import PageWrapper from "../components/common/layout/PageWrapper";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import { AlertSpg, AsyncCallArgs, DividerSpg, isNotEmpty, useAsyncCall } from "@duosoftbg/bpo-components";
import { getActivityToken, getIpcView } from "../../axios/api/services";
import { Alert, Typography } from "@mui/material";
import IpcPatentsContent from "../components/ipcPatents/IpcPatentsContent";

const IpcView = () => {
  const { t } = useTranslation();
  const { asyncCall } = useAsyncCall();
  const params = useParams();

  const symbol = params.symbol;
  const edition = params.edition;

  const [currentIpc, setCurrentIpc] = useState(null);
  const [latestIpc, setLatestIpc] = useState(null);
  const [error, setError] = useState(false);
  const [currentProcess, setCurrentProcess] = useState(null);

  useEffect(() => {
    const asyncCallArgs: AsyncCallArgs = {
      promise: getActivityToken(),
      onSuccess: (response) => {
        setCurrentProcess(response.currentProcess);
      },
      onError: () => {},
    };
    asyncCall(asyncCallArgs);
  }, [asyncCall]);

  useEffect(() => {
    if (currentProcess !== null) {
      const intervalId = setInterval(() => {
        const asyncCallArgs: AsyncCallArgs = {
          promise: getActivityToken(),
          onSuccess: (response) => {
            setCurrentProcess(response.currentProcess);
          },
          onError: () => {},
        };
        asyncCall(asyncCallArgs);
      }, 1000);

      return () => clearInterval(intervalId);
    }
  }, [asyncCall, currentProcess]);

  useEffect(() => {
    const asyncCallArgs: AsyncCallArgs = {
      promise: getIpcView(symbol, edition),
      withGlobalBackdrop: true,
      onSuccess: (response) => {
        setCurrentIpc(response.currentIpc);
        setLatestIpc(response.latestIpc);
        setError(false);
      },
      onError: () => {
        setCurrentIpc(null);
        setLatestIpc(null);
        setError(true);
      },
    };
    asyncCall(asyncCallArgs);

    // eslint-disable-next-line
    }, [symbol, edition, currentProcess]);

  if (error) {
    return (
      <PageWrapper title={t("m.error")}>
        <AlertSpg severity="error">{t("m.error.serverFetchingError")}</AlertSpg>
      </PageWrapper>
    );
  }

  if (isNotEmpty(currentProcess)) {
    return (
      <PageWrapper centerTitle title={t("t.ipc.view")} helmetTitle={t("t.ipc.view")}>
        <Alert severity="info">{t("l.process.currently.running")}</Alert>
      </PageWrapper>
    );
  }

  return (
    <PageWrapper centerTitle title={t("t.ipc.view")} helmetTitle={t("t.ipc.view")}>
      <Typography variant="h4" gutterBottom>
        {t("l.currentIpc")}: ({currentIpc?.ipcEditionCode}) {currentIpc?.ipcSectionCode}
        {currentIpc?.ipcClassCode}
        {currentIpc?.ipcSubclassCode}
        {currentIpc?.ipcGroupCode}-{currentIpc?.ipcSubgroupCode} ({currentIpc?.ipcLatestVersion}) -{" "}
        {currentIpc?.ipcName}
      </Typography>
      <DividerSpg />
      {latestIpc?.ipcSectionCode && (
        <Typography variant="h4" gutterBottom>
          {t("l.latestIpc")}: ({latestIpc?.ipcEditionCode}) {latestIpc?.ipcSectionCode}
          {latestIpc?.ipcClassCode}
          {latestIpc?.ipcSubclassCode}
          {latestIpc?.ipcGroupCode}-{latestIpc?.ipcSubgroupCode} ({latestIpc?.ipcLatestVersion}) - {latestIpc?.ipcName}
        </Typography>
      )}
      {!latestIpc?.ipcSectionCode && (
        <Typography variant="h4" gutterBottom>
          {t("l.latestIpc")}: {t("l.missing")}
        </Typography>
      )}
      <IpcPatentsContent currentIpc={currentIpc} latestIpc={latestIpc} />
    </PageWrapper>
  );
};

export default IpcView;
