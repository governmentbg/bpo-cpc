package bg.duosoft.bpo.patent_classification.be.service.cpc;

import java.io.FileNotFoundException;

public interface CPCLatestService {

    void saveNewCpcEntries(String latestVersion) throws FileNotFoundException;

}
