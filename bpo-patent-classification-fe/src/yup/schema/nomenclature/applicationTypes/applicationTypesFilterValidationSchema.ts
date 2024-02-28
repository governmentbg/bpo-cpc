import * as yup from "yup";
import i18n from "i18next";
// import { INPUT_LENGTH } from "@duosoftbg/bpo-components";

export const createApplicationTypesFilterValidationSchema = () => {
  yup.setLocale({
    string: {
      matches: i18n.t("validation.field.invalid"),
    },
    mixed: {
      default: i18n.t("validation.field.invalid"),
    },
  });

  return yup.object().shape({
    isActive: yup.string(),
    // name: yup.string().max(INPUT_LENGTH.MAX_INPUT_LENGTH_255, i18n.t("validation.charCount.invalid.255")),
    id: yup.string().max(4, i18n.t("validation.charCount.invalid.4")),
  });
};
