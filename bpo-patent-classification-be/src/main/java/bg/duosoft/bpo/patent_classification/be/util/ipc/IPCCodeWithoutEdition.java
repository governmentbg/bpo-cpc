package bg.duosoft.bpo.patent_classification.be.util.ipc;

public interface IPCCodeWithoutEdition {
    String getIpcSectionCode();

    String getIpcClassCode();

    String getIpcSubclassCode();

    String getIpcGroupCode();

    String getIpcSubgroupCode();
}
