export type NomenclatureGroupData = Array<{ name: string; href: string; accessRoles: Array<string> }>;
export type NomenclatureConfigArray = Array<{ groupName: string | null; nomenclatures: NomenclatureGroupData }>;
