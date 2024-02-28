import React from "react";
import PageWrapper from "../components/common/layout/PageWrapper";
import { useTranslation } from "react-i18next";

const Home = () => {
  const { t } = useTranslation();

  return <PageWrapper centerTitle title={t("m.home")} helmetTitle={t("m.home")}></PageWrapper>;
};

export default Home;
