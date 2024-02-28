package bg.duosoft.bpo.patent_classification.be.util;

import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import io.micrometer.common.util.StringUtils;

public class Utils {
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static IPCEntityPK symbolToPk(String symbol) throws Exception {
        if (!org.springframework.util.StringUtils.hasText(symbol) || !symbol.matches("^[A-Z]\\d{2}[A-Z]\\d{1,10}-\\d{1,10}$")) {
            throw new Exception("symbol has wrong format or is empty! " + symbol);
        }
        IPCEntityPK pk = new IPCEntityPK();
        pk.setIpcSectionCode(symbol.substring(0, 1));
        pk.setIpcClassCode(symbol.substring(1, 3));
        pk.setIpcSubclassCode(symbol.substring(3, 4));
        String[] split = symbol.substring(4).split("-");
        pk.setIpcGroupCode(split[0]);
        pk.setIpcSubgroupCode(split[1]);
        return pk;
    }

    public static IPCEntityPK symbolAndEditionToPk(String symbol, String edition) throws Exception {
        IPCEntityPK pk = symbolToPk(symbol);
        if (!org.springframework.util.StringUtils.hasText(edition) || edition.length() > 20) {
            throw new Exception("edition has wrong format or is empty! " + edition);
        }
        pk.setIpcEditionCode(edition);
        return pk;
    }
}
