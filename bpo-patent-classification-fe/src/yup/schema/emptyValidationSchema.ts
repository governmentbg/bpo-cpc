import * as yup from "yup";
import { initializeYup } from "@duosoftbg/bpo-components";

export const emptyValidationSchema = () => {
  initializeYup(yup);
  const schema = yup.object({});
  return schema;
};
