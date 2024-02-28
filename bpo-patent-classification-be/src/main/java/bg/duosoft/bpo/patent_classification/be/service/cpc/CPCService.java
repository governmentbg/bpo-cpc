package bg.duosoft.bpo.patent_classification.be.service.cpc;

public interface CPCService {

    void deleteUnusedCpcClasses();

    void updateValidCpcClasses();

    void addMissingCpcClasses();

    void finalizeCpcUpdate(String latestVersion);

}
