package bg.duosoft.bpo.patent_classification.be.util.ipc;

import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCLatestEntity;
import bg.duosoft.bpo.patent_classification.be.jaxb.IPCScheme;
import bg.duosoft.bpo.patent_classification.be.jaxb.IpcEntry;
import bg.duosoft.bpo.patent_classification.be.jaxb.SchemeEntryReference;
import bg.duosoft.bpo.patent_classification.be.jaxb.SchemeTitlePart;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import jakarta.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JAXBtoEntityConverter {

    private static String ipcEditionCode = "";
    private static String ipcSectionCode = "";
    private static String ipcClassCode = "";
    private static String ipcSubclassCode = "";
    private static String ipcGroupCode = "";
    private static String ipcSubgroupCode = "";
    private static String ipcLatestVersion = "";
    private static StringBuilder ipcName = new StringBuilder();

    public static List<IPCLatestEntity> convertToEntity(JAXBElement<IPCScheme> ipcEntryAttributes) {

        IPCScheme ipcScheme = ipcEntryAttributes.getValue();

        ipcLatestVersion = ipcScheme.getEdition();

        List<IpcEntry> ipcEntryMainList = ipcScheme.getIpcEntry();

        List<IPCLatestEntity> entities = new ArrayList<>();


        while (!ipcEntryMainList.isEmpty()) {
            getUsefulIpc(ipcEntryMainList, ipcEntryMainList.get(0), entities);
        }

        return entities;
    }

    private static void getUsefulIpc(List<IpcEntry> remainingIpcEntries, IpcEntry ipcEntry, List<IPCLatestEntity> entitiesToSave) {

        if (ipcEntry.getIpcEntry().isEmpty()) {
            String symbol = ipcEntry.getSymbol();

//            //use these to debug by symbol
//            if (symbol.equals("G06E0003000000"))
//            if (symbol.equals("A01G0013000000"))
//                System.out.println(1);

//        A 01 B 0001 040000
            if (StringUtils.hasText(symbol) &&
                    Objects.nonNull(ipcEntry.getTextBody()) &&
                    Objects.nonNull(ipcEntry.getTextBody().getTitle()) &&
                    (symbol.length() == 14 || (symbol.length() == 4 && ipcEntry.getKind().equals("u"))) &&
                    !ipcEntry.getKind().equals("t") && !ipcEntry.getKind().equals("g") && !ipcEntry.getKind().equals("n") && !ipcEntry.getKind().equals("i")) {

                if (symbol.length() == 14) {
                    try {
                        int kind = Integer.parseInt(ipcEntry.getKind());
                        for (int i = 0; i < kind; i++) {
                            ipcName.append(". ");
                        }
                    } catch (Exception ignored) {

                    }
                }

                if (Objects.nonNull(ipcEntry.getTextBody()) && Objects.nonNull(ipcEntry.getTextBody().getTitle())
                        && !ipcEntry.getTextBody().getTitle().getTitlePart().isEmpty()) {
                    for (SchemeTitlePart schemeTitlePart : ipcEntry.getTextBody().getTitle().getTitlePart()) {
                        for (Serializable content : schemeTitlePart.getText().getContent()) {
                            if (content instanceof JAXBElement) {
                                ipcName.append(NameUtil.stringifyJaxbElement((JAXBElement) content));
                            } else if (content instanceof String) {
                                ipcName.append(content);
                            } else {
                                throw new RuntimeException("Unrecognized content type for ipc name: " + content.getClass().getSimpleName());
                            }
                        }

                        if (!CollectionUtils.isEmpty(schemeTitlePart.getEntryReference())) {
                            ipcName.append(" (");
                            for (SchemeEntryReference entryReference : schemeTitlePart.getEntryReference()) {
                                for (Serializable content : entryReference.getContent()) {
                                    if (content instanceof JAXBElement) {
                                        ipcName.append(NameUtil.stringifyJaxbElement((JAXBElement) content));
                                    } else if (content instanceof String) {
                                        ipcName.append(content);
                                    } else {
                                        throw new RuntimeException("Unrecognized entry reference content type for ipc name: " + content.getClass().getSimpleName());
                                    }
                                }
                                ipcName.append("; ");
                            }
                            ipcName.delete(ipcName.length() - 2, ipcName.length()).append(")");
                        }
                        ipcName.append("; ");
                    }
                    ipcName.delete(ipcName.length() - 2, ipcName.length());
                    if (ipcName.toString().contains("javax.xml") || ipcName.toString().contains("jakarta.") || ipcName.toString().contains("bg.duosoft"))
                        throw new RuntimeException("IPC name contains invalid symbols");
                }

//                    FOR NOTE
//                if (Objects.nonNull(ipcEntry.getTextBody()) && Objects.nonNull(ipcEntry.getTextBody().getNote())
//                        && !ipcEntry.getTextBody().getNote().getNoteParagraph().isEmpty()) {
//                    for (SchemeNoteParagraph schemeNoteParagraph : ipcEntry.getTextBody().getNote().getNoteParagraph()) {
//                        ipcName.append(schemeNoteParagraph.toString());
//                    }
//                    ipcName.append(ipcEntry.getTextBody().getNote().getType());
//                }

                //TODO if needed inner schemeIndexEntries
                //FOR INDEX
//                if (Objects.nonNull(ipcEntry.getTextBody()) && Objects.nonNull(ipcEntry.getTextBody().getIndex())
//                        && !ipcEntry.getTextBody().getIndex().getIndexEntry().isEmpty()) {
//                    for (SchemeIndexEntry schemeIndexEntry : ipcEntry.getTextBody().getIndex().getIndexEntry()) {
//                        ipcName.append(schemeIndexEntry.getText().toString());
//                    }
//                }

                ipcSectionCode = symbol.substring(0, 1);
                ipcClassCode = symbol.substring(1, 3);
                ipcSubclassCode = symbol.substring(3, 4);
                if (symbol.length() == 14) {
                    ipcGroupCode = symbol.substring(4, 8).replaceFirst("^0+(?!$)", "");
                    ipcSubgroupCode = symbol.substring(8, 14);
                    while (ipcSubgroupCode.endsWith("0") && ipcSubgroupCode.length() > 2) {
                        ipcSubgroupCode = ipcSubgroupCode.substring(0, ipcSubgroupCode.length() - 1);
                    }
                }
                if (symbol.length() == 4) {
                    ipcGroupCode = "0";
                    ipcSubgroupCode = "0";
                }

                IPCLatestEntity ipcLatestEntity = new IPCLatestEntity();
                ipcLatestEntity.setIpcEntityPK(new IPCEntityPK());
                if (StringUtils.hasText(ipcEntry.getEdition())) {
                    String[] versions = ipcEntry.getEdition().split(",");
                    ipcLatestEntity.getIpcEntityPK().setIpcEditionCode(versions[versions.length - 1]);
                } else {
                    ipcLatestEntity.getIpcEntityPK().setIpcEditionCode(ipcLatestVersion);
                }
                ipcLatestEntity.getIpcEntityPK().setIpcSectionCode(ipcSectionCode);
                ipcLatestEntity.getIpcEntityPK().setIpcClassCode(ipcClassCode);
                ipcLatestEntity.getIpcEntityPK().setIpcSubclassCode(ipcSubclassCode);
                ipcLatestEntity.getIpcEntityPK().setIpcGroupCode(ipcGroupCode);
                ipcLatestEntity.getIpcEntityPK().setIpcSubgroupCode(ipcSubgroupCode);
                String ipcNameFormatted = ipcName.toString().trim();
//                if (ipcNameFormatted.endsWith(";"))
//                    ipcNameFormatted = ipcNameFormatted.substring(0, ipcNameFormatted.length() - 1);
                ipcNameFormatted = ipcNameFormatted.replace("\u00a0", " "); //replace &nbsp with space
                ipcLatestEntity.setIpcName(ipcNameFormatted);
                ipcLatestEntity.setIpcLatestVersion(ipcLatestVersion);
                ipcLatestEntity.setXmlDesigner(null);
                ipcLatestEntity.setRowVersion(1);
                ipcLatestEntity.setSymbol(symbol);

                //use these to debug by name
//                if (ipcNameFormatted.contains("Housing animals"))
//                    System.out.println(1);

                entitiesToSave.add(ipcLatestEntity);
                ipcName = new StringBuilder();
            }
            remainingIpcEntries.remove(ipcEntry);
        } else {
            getUsefulIpc(ipcEntry.getIpcEntry(), ipcEntry.getIpcEntry().get(0), entitiesToSave);
        }

    }

}
