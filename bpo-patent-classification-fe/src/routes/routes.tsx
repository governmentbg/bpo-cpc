import React from "react";
import { Page403, Page404, SecurityGuard, SecurityRole } from "@duosoftbg/bpo-components";
import StandartLayout from "../views/layouts/StandartLayout";
import Home from "../views/pages/Home";
import CpcLoader from "../views/pages/CpcLoader";
import IpcLoader from "../views/pages/IpcLoader";
import MissingClasses from "../views/pages/MissingClasses";
import OldClassVersions from "../views/pages/OldClassVersions";
import IpcView from "../views/pages/IpcView";

const routes = [
  {
    path: "/",
    element: <StandartLayout />,
    children: [
      {
        index: true,
        element: (
          <SecurityGuard
            displayOnUnauthorized={<Page403 />}
            roleOperator={"or"}
            checkForRoles={[
              SecurityRole.AdminPatentClassificationIpc,
              SecurityRole.AdminPatentClassificationCpc,
              SecurityRole.AdminPatentClassificationMissingClasses,
              SecurityRole.AdminPatentClassificationOldVersionClasses,
            ]}
          >
            <Home />
          </SecurityGuard>
        ),
      },
      {
        path: "loader/ipc",
        element: (
          <SecurityGuard
            displayOnUnauthorized={<Page403 />}
            checkForRoles={[SecurityRole.AdminPatentClassificationIpc]}
          >
            <IpcLoader />
          </SecurityGuard>
        ),
      },
      {
        path: "loader/cpc",
        element: (
          <SecurityGuard
            displayOnUnauthorized={<Page403 />}
            checkForRoles={[SecurityRole.AdminPatentClassificationCpc]}
          >
            <CpcLoader />
          </SecurityGuard>
        ),
      },
      {
        path: "classifier/missing-classes",
        element: (
          <SecurityGuard
            displayOnUnauthorized={<Page403 />}
            checkForRoles={[SecurityRole.AdminPatentClassificationMissingClasses]}
          >
            <MissingClasses />
          </SecurityGuard>
        ),
      },
      {
        path: "classifier/old-class-versions",
        element: (
          <SecurityGuard
            displayOnUnauthorized={<Page403 />}
            checkForRoles={[SecurityRole.AdminPatentClassificationOldVersionClasses]}
          >
            <OldClassVersions />
          </SecurityGuard>
        ),
      },
      {
        path: "classifier/old-class-versions/view/:symbol/:edition",
        element: (
          <SecurityGuard
            displayOnUnauthorized={<Page403 />}
            checkForRoles={[SecurityRole.AdminPatentClassificationOldVersionClasses]}
          >
            <IpcView />
          </SecurityGuard>
        ),
      },
      {
        path: "classifier/missing-classes/view/:symbol/:edition",
        element: (
          <SecurityGuard
            displayOnUnauthorized={<Page403 />}
            checkForRoles={[SecurityRole.AdminPatentClassificationMissingClasses]}
          >
            <IpcView />
          </SecurityGuard>
        ),
      },
    ],
  },
  {
    path: "*",
    element: <StandartLayout />,
    children: [
      {
        path: "*",
        element: <Page404 />,
      },
    ],
  },
];

export default routes;
