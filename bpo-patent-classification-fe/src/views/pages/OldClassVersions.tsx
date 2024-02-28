import React, { useEffect, useState } from "react";
import PageWrapper from "../components/common/layout/PageWrapper";
import { useTranslation } from "react-i18next";
import OldVersionClassesContent from "../components/oldVersionClasses/OldVersionClassesContent";
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
      <PageWrapper centerTitle title={t("t.old.class.versions")} helmetTitle={t("t.old.class.versions")}>
        <OldVersionClassesContent />
      </PageWrapper>
    );
  } else {
    return (
      <PageWrapper centerTitle title={t("t.old.class.versions")} helmetTitle={t("t.old.class.versions")}>
        <Alert severity="info">{t("l.process.currently.running")}</Alert>
      </PageWrapper>
    );
  }
};

export default MissingClasses;
