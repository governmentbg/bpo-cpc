import { AppBreadcrumbs, BreadcrumbData } from "@duosoftbg/bpo-components";

export const breadcrumbData: BreadcrumbData = [
  { path: "/", name: "m.home", isHomePage: true },
  { path: "/loader/ipc", name: "m.ipc" },
  { path: "/loader/cpc", name: "m.cpc" },
  { path: "/classifier/missing-classes", name: "t.missing.classes" },
  { path: "/classifier/missing-classes/view/:symbol", name: "t.ipc.view" },
  { path: "/classifier/old-class-versions", name: "t.old.class.versions" },
  { path: "/classifier/old-class-versions/view/:symbol", name: "t.ipc.view" },
];

const Breadcrumbs = () => {
  return <AppBreadcrumbs data={breadcrumbData} />;
};

export default Breadcrumbs;
