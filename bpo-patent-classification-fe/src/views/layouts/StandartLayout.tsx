import { Box } from "@mui/material";
import { Fragment } from "react";
import { Outlet } from "react-router-dom";
import Header from "../components/common/layout/Header";
import Footer from "../components/common/layout/Footer";
import { AppModulesPageBlockerDialog, BpoSidebar, GlobalBackdrop, WithChildren } from "@duosoftbg/bpo-components";
import { sidebarData } from "../../config/sidebar/sidebarData";

const StandartLayout = (props: WithChildren) => {
  const { children } = props;
  return (
    <Fragment>
      <Header />
      <BpoSidebar sidebarData={sidebarData} />
      <Box>
        {children}
        <Outlet />
      </Box>
      <Footer />
      <GlobalBackdrop />
      <AppModulesPageBlockerDialog />
    </Fragment>
  );
};

export default StandartLayout;
