import { messagesBg, messagesEn } from "../i18n";

export const i18nKeyByCode = (code: string, baseKey: string): string => {
  const key = `${baseKey}.${code}`;
  if (messagesBg.translation[key] && messagesEn.translation[key]) {
    return key;
  }
  return baseKey;
};
