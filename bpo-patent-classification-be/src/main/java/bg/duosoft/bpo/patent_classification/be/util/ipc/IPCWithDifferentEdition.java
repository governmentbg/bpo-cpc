package bg.duosoft.bpo.patent_classification.be.util.ipc;

public interface IPCWithDifferentEdition {
    String getIpcSectionCode();

    String getIpcClassCode();

    String getIpcSubclassCode();

    String getIpcGroupCode();

    String getIpcSubgroupCode();

    String getIpcName();

    String getIpcEditionCode();

    String getNewIpcName();

    String getNewIpcEditionCode();
    String getNewIpcLatestVersion();
}
