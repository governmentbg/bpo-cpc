import * as React from "react";
import { BE_MODULES, BpoHeader } from "@duosoftbg/bpo-components";

const Header = () => {
  return <BpoHeader subHeaderTitle={""} showMonitoring={true} backofficeModules={[BE_MODULES.PATENT_CLASSIFICATION]} />;
};
export default Header;
