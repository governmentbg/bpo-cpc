import React, { useEffect, useState } from "react";
import PageWrapper from "../components/common/layout/PageWrapper";
import { useTranslation } from "react-i18next";
import { AsyncCallArgs, GridContainer, GridItem, useAsyncCall } from "@duosoftbg/bpo-components";
import { getActivityToken } from "../../axios/api/services";
import LogTable from "../components/LogTable";
import CpcFilesTable from "../components/CpcFilesTable";
import CpcOperationsTable from "../components/CpcOperationsTable";
import { TextField } from "@mui/material";

const CpcLoader = () => {
  const { t } = useTranslation();
  const { asyncCall } = useAsyncCall();
  const [currentProcess, setCurrentProcess] = useState(null);
  const [cpcProcessHistory, setCpcProcessHistory] = useState(null);
  const [titleAFileName, setTitleAFileName] = useState(null);
  const [titleBFileName, setTitleBFileName] = useState(null);
  const [titleCFileName, setTitleCFileName] = useState(null);
  const [titleDFileName, setTitleDFileName] = useState(null);
  const [titleEFileName, setTitleEFileName] = useState(null);
  const [titleFFileName, setTitleFFileName] = useState(null);
  const [titleGFileName, setTitleGFileName] = useState(null);
  const [titleHFileName, setTitleHFileName] = useState(null);
  const [titleYFileName, setTitleYFileName] = useState(null);
  const [concordanceFileName, setConcordanceFileName] = useState(null);
  const [validityFileName, setValidityFileName] = useState(null);
  const [cpcLatestVersion, setCpcLatestVersion] = useState(null);

  useEffect(() => {
    if (currentProcess !== null) {
      const intervalId = setInterval(() => {
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
            setCpcLatestVersion(response.cpcLatestVersion);
          },
          onError: () => {},
        };
        asyncCall(asyncCallArgs);
      }, 1000);

      return () => clearInterval(intervalId); //This is important
    }
  }, [asyncCall, currentProcess]);

  useEffect(() => {
    const asyncCallArgs: AsyncCallArgs = {
      promise: getActivityToken(),
      onSuccess: (response) => {
        setCpcLatestVersion(response.cpcLatestVersion);
      },
      onError: () => {},
    };
    asyncCall(asyncCallArgs);
    // eslint-disable-next-line
  }, []);

  return (
    <PageWrapper centerTitle title={t("m.cpc")} helmetTitle={t("m.cpc")}>
      <GridContainer spacing={4} mt={0}>
        <CpcFilesTable
          currentProcess={currentProcess}
          setCurrentProcess={setCurrentProcess}
          setCpcProcessHistory={setCpcProcessHistory}
          setTitleAFileName={setTitleAFileName}
          setTitleBFileName={setTitleBFileName}
          setTitleCFileName={setTitleCFileName}
          setTitleDFileName={setTitleDFileName}
          setTitleEFileName={setTitleEFileName}
          setTitleFFileName={setTitleFFileName}
          setTitleGFileName={setTitleGFileName}
          setTitleHFileName={setTitleHFileName}
          setTitleYFileName={setTitleYFileName}
          setConcordanceFileName={setConcordanceFileName}
          setValidityFileName={setValidityFileName}
          titleAFileName={titleAFileName}
          titleBFileName={titleBFileName}
          titleCFileName={titleCFileName}
          titleDFileName={titleDFileName}
          titleEFileName={titleEFileName}
          titleFFileName={titleFFileName}
          titleGFileName={titleGFileName}
          titleHFileName={titleHFileName}
          titleYFileName={titleYFileName}
          concordanceFileName={concordanceFileName}
          validityFileName={validityFileName}
        />
        <GridItem sm={12} md={12}>
          <TextField
            value={cpcLatestVersion ? cpcLatestVersion : ""}
            required
            id="latest-version"
            label={t("l.latest.version")}
            size={"small"}
            style={{ width: "100%" }}
            onChange={(e) => {
              setCpcLatestVersion(e.target.value);
            }}
          />
        </GridItem>
        <CpcOperationsTable
          currentProcess={currentProcess}
          setCurrentProcess={setCurrentProcess}
          setCpcProcessHistory={setCpcProcessHistory}
          setTitleAFileName={setTitleAFileName}
          setTitleBFileName={setTitleBFileName}
          setTitleCFileName={setTitleCFileName}
          setTitleDFileName={setTitleDFileName}
          setTitleEFileName={setTitleEFileName}
          setTitleFFileName={setTitleFFileName}
          setTitleGFileName={setTitleGFileName}
          setTitleHFileName={setTitleHFileName}
          setTitleYFileName={setTitleYFileName}
          setConcordanceFileName={setConcordanceFileName}
          setValidityFileName={setValidityFileName}
          titleAFileName={titleAFileName}
          titleBFileName={titleBFileName}
          titleCFileName={titleCFileName}
          titleDFileName={titleDFileName}
          titleEFileName={titleEFileName}
          titleFFileName={titleFFileName}
          titleGFileName={titleGFileName}
          titleHFileName={titleHFileName}
          titleYFileName={titleYFileName}
          concordanceFileName={concordanceFileName}
          validityFileName={validityFileName}
          cpcLatestVersion={cpcLatestVersion}
        />
        <LogTable processHistory={cpcProcessHistory} />
      </GridContainer>
    </PageWrapper>
  );
};

export default CpcLoader;
