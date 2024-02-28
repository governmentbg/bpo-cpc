import { combineI18nMessages, defaultMessagesBG } from "@duosoftbg/bpo-components";
import validationKeysBG from "./slices/validationKey/validationKeysBG";

const messagesBg = combineI18nMessages(defaultMessagesBG, {
  translation: {
    "m.home": "Управление на патентни класове",
    "t.bpo.patent.classification": "Управление на патентни класове",
    "t.ipc": "МПК",
    "t.cpc": "КПК",
    "t.missing.classes": "Липсващи класове",
    "t.old.class.versions": "Класове със стари версии",
    "m.ipc": "Зареждане на МПК",
    "m.cpc": "Зареждане на КПК",
    "l.file.type": "Тип файл",
    "l.file.name": "Файл",
    "l.xml.schema": "XML схема",
    "l.operation": "Операция",
    "l.upload.file": "Качване на файл",
    "l.start": "Старт",
    "l.save.xml.entries":
      "Запазване на всички записи от XML файла в CF_CLASS_IPC_LATEST_SPEC (Настоящето съдържание на таблицата ще бъде презаписано)",
    "l.delete.unused.ipc": "Изтриване на неизползваните класове от CF_CLASS_IPC",
    "l.normalize.ipc":
      "Нормализиране на класовете в CF_CLASS_IPC (може да се пропусне ако не е необходимо ръчно преглеждане на данните)",
    "l.latest.version.duplicates": "Вземане на най-новата версия за дублиращи се МПК",
    "l.update.valid.ipcs": "Промяна на версиите и имената на валидните МПК от последното издание",
    "l.update.invalid.names": "Промяна на МПК с невалидни имена",
    "l.add.missing.ipcs": "Добавяне на липсващите МПК в CF_CLASS_IPC",
    "l.finalize.ipc": "Финализиране на промяната",
    "l.delete.file": "Изтриване на качения файл",
    "l.time.started": "Начало",
    "l.time.finished": "Край",
    "l.user": "Потребител",
    "l.status": "Статус",
    "l.success": "Успешен",
    "l.fail": "Грешка",
    "l.in.progress": "Изпълнява се",
    "l.load.file": "Качване на XML файл",
    "l.title.a": "Секция A",
    "l.title.b": "Секция B",
    "l.title.c": "Секция C",
    "l.title.d": "Секция D",
    "l.title.e": "Секция E",
    "l.title.f": "Секция F",
    "l.title.g": "Секция G",
    "l.title.h": "Секция H",
    "l.title.y": "Секция Y",
    "l.concordance": "Конкорданс",
    "l.validity": "Валидност",
    "l.load.cpc.file.TITLE_A": "Качване на файл - Секция A",
    "l.load.cpc.file.TITLE_B": "Качване на файл - Секция B",
    "l.load.cpc.file.TITLE_C": "Качване на файл - Секция C",
    "l.load.cpc.file.TITLE_D": "Качване на файл - Секция D",
    "l.load.cpc.file.TITLE_E": "Качване на файл - Секция E",
    "l.load.cpc.file.TITLE_F": "Качване на файл - Секция F",
    "l.load.cpc.file.TITLE_G": "Качване на файл - Секция G",
    "l.load.cpc.file.TITLE_H": "Качване на файл - Секция H",
    "l.load.cpc.file.TITLE_Y": "Качване на файл - Секция Y",
    "l.load.cpc.file.CONCORDANCE": "Качване на файл - Конкорданс",
    "l.load.cpc.file.VALIDITY": "Качване на файл - Валидност",
    "l.delete.cpc.file.TITLE_A": "Изтриване на файл - Секция A",
    "l.delete.cpc.file.TITLE_B": "Изтриване на файл - Секция B",
    "l.delete.cpc.file.TITLE_C": "Изтриване на файл - Секция C",
    "l.delete.cpc.file.TITLE_D": "Изтриване на файл - Секция D",
    "l.delete.cpc.file.TITLE_E": "Изтриване на файл - Секция E",
    "l.delete.cpc.file.TITLE_F": "Изтриване на файл - Секция F",
    "l.delete.cpc.file.TITLE_G": "Изтриване на файл - Секция G",
    "l.delete.cpc.file.TITLE_H": "Изтриване на файл - Секция H",
    "l.delete.cpc.file.TITLE_Y": "Изтриване на файл - Секция Y",
    "l.delete.cpc.file.CONCORDANCE": "Изтриване на файл - Конкорданс",
    "l.delete.cpc.file.VALIDITY": "Изтриване на файл - Валидност",
    "l.latest.version": "Версия",
    "l.save.new.cpc.entries":
      "Запазване на всички записи от файловете в CF_CLASS_CPC_LATEST_SPEC (Настоящето съдържание на таблицата ще бъде презаписано)",
    "l.delete.unused.cpc": "Изтриване на неизползваните класове от CF_CLASS_CPC",
    "l.update.valid.cpc": "Промяна на версиите и имената на валидните КПК от последното издание",
    "l.add.missing.cpc": "Добавяне на липсващите КПК в CF_CLASS_CPC",
    "l.fill.concordance": "Попълване на конкорданс таблицата",
    "l.finalize.cpc.update": "Финализиране на промяната",
    "l.operations.history": "История на операциите",
    "l.needed.files": "Необходими файлове",
    "l.operations.list": "Списък с операции",
    "l.file.link": "Линк за теглене на файл",
    "l.table.head.symbol": "Код",
    "l.table.head.ipcName": "Име",
    "l.table.head.edition": "Издание",
    "l.table.head.latestVersion": "Последна версия",
    "l.ipcLatestVersion": "Последна версия",
    "l.ipcEditionCode": "Издание",
    "l.ipcSectionCode": "Секция",
    "l.ipcClassCode": "Клас",
    "l.ipcSubclassCode": "Подклас",
    "l.ipcGroupCode": "Група",
    "l.ipcSubgroupCode": "Подгрупа",
    "l.ipcName": "Име",
    "t.ipc.view": "Преглед на клас",
    "l.currentIpc": "Настоящ клас",
    "l.latestIpc": "Актуален клас",
    "l.missing": "Липсва",
    "l.table.head.number": "№",
    "l.table.head.entryNum": "Входящ номер",
    "l.table.head.regNum": "Регистров номер",
    "l.table.head.patentTitle": "Заглавие",
    "l.fileTyp": "Тип",
    "l.fileSer": "Година",
    "l.fileNbr": "Номер",
    "l.regNum": "Регистров номер",
    "l.patentTitle": "Заглавие",
    "l.patent": "Патент",
    "l.utility.model": "Полезен модел",
    "l.eu.patent": "Европейски патент",
    "l.classify.selected": "Прекласифициране на избраните",
    "l.classify.all": "Прекласифициране на всички",
    "m.yes": "Да",
    "m.no": "Не",
    "t.classify.selected.dialog": "Прекласифициране на избраните обекти",
    "m.classify.selected.warning.message": "Потвърждавате ли прекласифицирането на избраните обекти?",
    "t.classify.all.dialog": "Прекласифициране на всички обекти",
    "m.classify.all.warning.message": "Потвърждавате ли прекласифицирането на всички обекти?",
    "m.classification.success": "Прекласифицирането приключи успешно!",
    "l.process.currently.running": "Страницата ще бъде достъпна след завършването на процесите по зареждане на данни.",
    "l.symbol": "Символ",
    "l.ipc.autocomplete": "Заместващ клас",
    ...validationKeysBG.translation,
  },
});

export default messagesBg;