import { AppPageWrapper, WithChildren } from "@duosoftbg/bpo-components";
import Breadcrumbs from "./Breadcrumbs";
import React from "react";

type PageWrapperProps = WithChildren<{
  title: string | React.ReactNode;
  centerTitle?: boolean;
  helmetTitle?: string;
}>;

const PageWrapper = (props: PageWrapperProps) => {
  const { title, centerTitle, children, helmetTitle } = props;

  return (
    <AppPageWrapper
      children={children}
      title={title}
      helmetTitle={helmetTitle}
      centerTitle={centerTitle}
      breadcrumbs={<Breadcrumbs />}
    />
  );
};

export default PageWrapper;
