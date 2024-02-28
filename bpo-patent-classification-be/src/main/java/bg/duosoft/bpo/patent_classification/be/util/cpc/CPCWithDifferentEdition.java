package bg.duosoft.bpo.patent_classification.be.util.cpc;

public interface CPCWithDifferentEdition {
    String getCpcSectionCode();

    String getCpcClassCode();

    String getCpcSubclassCode();

    String getCpcGroupCode();

    String getCpcSubgroupCode();

    String getCpcName();

    String getCpcEditionCode();

    String getNewCpcName();

    String getNewCpcEditionCode();
    String getNewCpcLatestVersion();
}
