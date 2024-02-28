import { useTranslation } from "react-i18next";
import { Box } from "@mui/material";
import React from "react";

export const YesMessage = () => {
  const { t } = useTranslation();
  return <Box style={{ color: "green" }}>{t("m.yes")}</Box>;
};

export const NoMessage = () => {
  const { t } = useTranslation();
  return <Box style={{ color: "red" }}>{t("m.no")}</Box>;
};

export enum AbdocsDocActionExpectedResult {
  WithoutResult = "WithoutResult",
  Registration = "Registration",
  Sign = "Sign",
  ExecutionTask = "ExecutionTask",
  Coordination = "Coordination",
}
