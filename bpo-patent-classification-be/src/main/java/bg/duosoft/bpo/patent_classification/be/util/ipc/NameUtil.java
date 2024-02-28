package bg.duosoft.bpo.patent_classification.be.util.ipc;

import bg.duosoft.bpo.patent_classification.be.jaxb.*;
import org.springframework.util.StringUtils;

import jakarta.xml.bind.JAXBElement;
import java.util.List;
import java.util.stream.Collectors;

public class NameUtil {

    public static boolean ipcNameEquals(String name1, String name2) {
        name1 = name1.toLowerCase();
        name2 = name2.toLowerCase();
        name1 = name1.replaceAll("\u00a0", "");
        name2 = name2.replaceAll("\u00a0", "");
        name1 = name1.replaceAll("[,;. ]", "");
        name2 = name2.replaceAll("[,;. ]", "");
        name1 = name1.replaceAll("\\(.*?\\)", "");
        name2 = name2.replaceAll("\\(.*?\\)", "");
        name1 = name1.replaceAll("\\[.*?\\]", "");
        name2 = name2.replaceAll("\\[.*?\\]", "");
        name1 = name1.replaceAll("\\{.*?\\}", "");
        name2 = name2.replaceAll("\\{.*?\\}", "");
        return name1.equals(name2);
    }

    public static boolean ipcNameEquals2(String name1, String name2) {
        name1 = name1.toLowerCase();
        name2 = name2.toLowerCase();
        name1 = name1.replaceAll("\u00a0", "");
        name2 = name2.replaceAll("\u00a0", "");
        name1 = name1.replaceAll("[,;.# ]", "");
        name2 = name2.replaceAll("[,;.# ]", "");
        name1 = name1.replaceAll("\\[.*?\\]", "");
        name2 = name2.replaceAll("\\[.*?\\]", "");
        return name1.equals(name2);
    }

    public static String clearIpcName(String name) {
        String s = name
                .toLowerCase()
                .replaceAll("\u00a0", "")
                .replaceAll("[,;.# ]", "")
//                .replaceAll("\\(.*?\\)", "")
                .replaceAll("\\[.*?\\]", "")
                .replaceAll("\\{.*?\\}", "");
        return s;
    }

    public static String stringifyJaxbElement(JAXBElement element) {

        if (element.getDeclaredType().equals(Sref.class)) {
            Sref sref = (Sref) element.getValue();
            String symbol = sref.getRef();
            if (StringUtils.hasText(symbol) && (symbol.length() == 3 || symbol.length() == 4 || symbol.length() == 14)) {
                String result = getShortSymbol(symbol);
                return result;
            } else {
                throw new RuntimeException("Unrecognized format for Sref class: " + symbol);
            }
        } else if (element.getDeclaredType().equals(Mref.class)) {
            Mref mref = (Mref) element.getValue();
            String symbol = mref.getRef();
            String endSymbol = mref.getEndRef();

            String shortSymbol;
            String shortEndSymbol;

            if (StringUtils.hasText(symbol) && (symbol.length() == 3 || symbol.length() == 4 || symbol.length() == 14)) {
                shortSymbol = getShortSymbol(symbol);
            } else {
                throw new RuntimeException("Unrecognized format for Mref class: " + symbol);
            }

            if (StringUtils.hasText(endSymbol) && (endSymbol.length() == 3 || endSymbol.length() == 4 || endSymbol.length() == 14)) {
                shortEndSymbol = getShortSymbol(endSymbol);
            } else {
                throw new RuntimeException("Unrecognized format for Sref class: " + symbol);
            }
            String result = shortSymbol + " to " + shortEndSymbol;
            return result;
        } else if (element.getDeclaredType().equals(Sub.class) || element.getDeclaredType().equals(Sup.class) || element.getDeclaredType().equals(Img.class)) {
            return element.getValue().toString();
        } else if (element.getDeclaredType().equals(Underline.class)) {
            Underline value = (Underline) element.getValue();
            List<String> content = value.getContent().stream().map(Object::toString).collect(Collectors.toList());
            String result = String.join(", ", content);
            if (result.equals("per se")){
                result += " ";
            }
            return result;
        } else {
            throw new RuntimeException("Unrecognized JAXB Element in name conversion: " + element.getDeclaredType());
        }
    }

    private static String getShortSymbol(String symbol) {
        String ipcSectionCode = symbol.substring(0, 1);
        String ipcClassCode = symbol.substring(1, 3);
        if (symbol.length() == 14) {
            String ipcSubclassCode = symbol.substring(3, 4);
            String ipcGroupCode = symbol.substring(4, 8).replaceFirst("^0+(?!$)", "");
            String ipcSubgroupCode = symbol.substring(8, 14);
            while (ipcSubgroupCode.endsWith("0") && ipcSubgroupCode.length() > 2) {
                ipcSubgroupCode = ipcSubgroupCode.substring(0, ipcSubgroupCode.length() - 1);
            }

            return ipcSectionCode + ipcClassCode + ipcSubclassCode + " " + ipcGroupCode + "/" + ipcSubgroupCode;
        } else if (symbol.length() == 4) {
            String ipcSubclassCode = symbol.substring(3, 4);
            return ipcSectionCode + ipcClassCode + ipcSubclassCode;
        } else {
            return ipcSectionCode + ipcClassCode;
        }
    }

}
