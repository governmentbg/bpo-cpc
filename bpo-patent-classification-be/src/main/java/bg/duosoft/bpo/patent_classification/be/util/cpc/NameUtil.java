package bg.duosoft.bpo.patent_classification.be.util.cpc;

public class NameUtil {

    public static boolean cpcNameEquals(String name1, String name2) {
        name1 = name1.toLowerCase();
        name2 = name2.toLowerCase();
        name1 = name1.replaceAll("\u00a0", "");
        name2 = name2.replaceAll("\u00a0", "");
        name1 = name1.replaceAll("[,;.#{}() ]", "");
        name2 = name2.replaceAll("[,;.#{}() ]", "");
        return name1.equals(name2);
    }

//    public static String clearCpcName(String name) {
//        String s = name
//                .toLowerCase()
//                .replaceAll("\u00a0", "")
//                .replaceAll("[,;.# ]", "")
////                .replaceAll("\\(.*?\\)", "")
//                .replaceAll("\\[.*?\\]", "")
//                .replaceAll("\\{.*?\\}", "");
//        return s;
//    }
//
//    private static String getShortSymbol(String symbol) {
//        String cpcSectionCode = symbol.substring(0, 1);
//        String cpcClassCode = symbol.substring(1, 3);
//        if (symbol.length() == 14) {
//            String cpcSubclassCode = symbol.substring(3, 4);
//            String cpcGroupCode = symbol.substring(4, 8).replaceFirst("^0+(?!$)", "");
//            String cpcSubgroupCode = symbol.substring(8, 14);
//            while (cpcSubgroupCode.endsWith("0") && cpcSubgroupCode.length() > 2) {
//                cpcSubgroupCode = cpcSubgroupCode.substring(0, cpcSubgroupCode.length() - 1);
//            }
//
//            return cpcSectionCode + cpcClassCode + cpcSubclassCode + " " + cpcGroupCode + "/" + cpcSubgroupCode;
//        } else if (symbol.length() == 4) {
//            String cpcSubclassCode = symbol.substring(3, 4);
//            return cpcSectionCode + cpcClassCode + cpcSubclassCode;
//        } else {
//            return cpcSectionCode + cpcClassCode;
//        }
//    }

}
