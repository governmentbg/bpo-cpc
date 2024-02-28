import React from "react";
import { BaseInputFieldFilter, FilterLabelProps } from "@duosoftbg/bpo-components";

const BgName = ({ label = "l.searchFilter.name", ...others }: FilterLabelProps) => {
  return <BaseInputFieldFilter fieldName={"bgName"} label={label} {...others} />;
};

export default BgName;
