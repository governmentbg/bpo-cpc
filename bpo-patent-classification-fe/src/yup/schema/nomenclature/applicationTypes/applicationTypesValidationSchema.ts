import * as yup from "yup";
import i18n from "i18next";

export const createApplicationTypesValidationSchema = () => {
  yup.setLocale({
    string: {
      matches: i18n.t("validation.field.invalid"),
    },
    mixed: {
      default: i18n.t("validation.field.invalid"),
      required: i18n.t("validation.field.required"),
    },
  });

  return yup.object({
    // id: yup.string().required().max(4, i18n.t("validation.charCount.invalid.4")),
    // name: yup.string().required().max(INPUT_LENGTH.MAX_INPUT_LENGTH_255, i18n.t("validation.charCount.invalid.255")),
    // isActive: yup.boolean().required(),
  });
};
