package bg.duosoft.bpo.patent_classification.be.util.cpc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidityPair {
    private String validFrom;
    private String validTo;
}
