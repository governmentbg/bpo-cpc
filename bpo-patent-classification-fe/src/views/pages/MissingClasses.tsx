import React, { useEffect, useState } from "react";
import PageWrapper from "../components/common/layout/PageWrapper";
import { useTranslation } from "react-i18next";
import MissingClassesContent from "../components/missingClasses/MissingClassesContent";
import { AsyncCallArgs, isEmpty, useAsyncCall } from "@duosoftbg/bpo-components";
import { getActivityToken } from "../../axios/api/services";
import { Alert } from "@mui/material";

const MissingClasses = () => {
  const { t } = useTranslation();
  const { asyncCall } = useAsyncCall();
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

  if (isEmpty(currentProcess)) {
    return (
      <PageWrapper centerTitle title={t("t.missing.classes")} helmetTitle={t("t.missing.classes")}>
        <MissingClassesContent />
      </PageWrapper>
    );
  } else {
    return (
      <PageWrapper centerTitle title={t("t.missing.classes")} helmetTitle={t("t.missing.classes")}>
        <Alert severity="info">{t("l.process.currently.running")}</Alert>
      </PageWrapper>
    );
  }
};

export default MissingClasses;
