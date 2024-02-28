package bg.duosoft.bpo.patent_classification.be.util.cpc;

import java.util.Arrays;
import java.util.List;

public enum CpcFilesEnum {
    TITLE_A("titleA.txt", "A"),
    TITLE_B("titleB.txt", "B"),
    TITLE_C("titleC.txt", "C"),
    TITLE_D("titleD.txt", "D"),
    TITLE_E("titleE.txt", "E"),
    TITLE_F("titleF.txt", "F"),
    TITLE_G("titleG.txt", "G"),
    TITLE_H("titleH.txt", "H"),
    TITLE_Y("titleY.txt", "Y"),
    CONCORDANCE("concordance.txt", "Concordance"),
    VALIDITY("validity.txt", "Validity");

    private String value;
    private String code;

    CpcFilesEnum(String value, String code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public static List<CpcFilesEnum> getAllTitleTxts() {
        return Arrays.asList(TITLE_A, TITLE_B, TITLE_C, TITLE_D, TITLE_E, TITLE_F, TITLE_G, TITLE_H, TITLE_Y);
    }
}
