package bg.duosoft.bpo.patent_classification.be.util.ipc;

import java.util.ArrayList;
import java.util.List;

public enum SectionCodes {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H");

    SectionCodes(String a) {

    }

    public static List<String> sectionCodeValues() {
        ArrayList<String> codes = new ArrayList<>();
        codes.add(A.name());
        codes.add(B.name());
        codes.add(C.name());
        codes.add(D.name());
        codes.add(E.name());
        codes.add(F.name());
        codes.add(G.name());
        codes.add(H.name());
        return codes;
    }

}
