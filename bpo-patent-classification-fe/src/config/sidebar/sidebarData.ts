import { messagesBg, messagesEn } from "../../i18n";
import { ProcessEnvironments, SecurityRole, SidebarData } from "@duosoftbg/bpo-components";

export const sidebarWidth = 258;
const BackofficeCoreModuleUrl = ProcessEnvironments.PublicUrl;

export const sidebarData: SidebarData[] = [
  {
    id: "bpo-sidebar",
    title: "",
    titleEn: "",
    pages: [
      {
        id: "loaders",
        title: "Зареждане на данни",
        titleEn: "Data loading",
        accessRoles: [SecurityRole.AdminPatentClassificationIpc, SecurityRole.AdminPatentClassificationCpc],
        children: [
          {
            id: "ipc",
            title: messagesBg.translation["t.ipc"],
            titleEn: messagesEn.translation["t.ipc"],
            accessRoles: [SecurityRole.AdminPatentClassificationIpc],
            href: `${BackofficeCoreModuleUrl}/loader/ipc`,
          },
          {
            id: "cpc",
            title: messagesBg.translation["t.cpc"],
            titleEn: messagesEn.translation["t.cpc"],
            accessRoles: [SecurityRole.AdminPatentClassificationCpc],
            href: `${BackofficeCoreModuleUrl}/loader/cpc`,
          },
        ],
      },
      {
        id: "classifier",
        title: "Прекласифициране",
        titleEn: "Classification",
        accessRoles: [
          SecurityRole.AdminPatentClassificationMissingClasses,
          SecurityRole.AdminPatentClassificationOldVersionClasses,
        ],
        children: [
          {
            id: "missing",
            title: messagesBg.translation["t.missing.classes"],
            titleEn: messagesEn.translation["t.missing.classes"],
            accessRoles: [SecurityRole.AdminPatentClassificationMissingClasses],
            href: `${BackofficeCoreModuleUrl}/classifier/missing-classes`,
          },
          {
            id: "old",
            title: messagesBg.translation["t.old.class.versions"],
            titleEn: messagesEn.translation["t.old.class.versions"],
            accessRoles: [SecurityRole.AdminPatentClassificationOldVersionClasses],
            href: `${BackofficeCoreModuleUrl}/classifier/old-class-versions`,
          },
        ],
      },
    ],
  },
];
