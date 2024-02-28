package bg.duosoft.bpo.patent_classification.be.repository.patent_ipc;

import bg.duosoft.bpo.patent_classification.be.domain.dto.filter.IpcPatentsFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.patent_ipc.PatentIpcEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PatentIPCRepositoryCustom {

    private final EntityManager em;

    public int updateEditionOfUsedIpc(String ipcSectionCode, String ipcClassCode, String ipcSubclassCode, String ipcGroupCode,
                                      String ipcSubgroupCode, String oldIpcEditionCode, String newIpcEditionCode) {
        Query nativeQuery = em.createNativeQuery("update IPASPROD.IP_PATENT_IPC_CLASSES set IPC_EDITION_CODE = :newIpcEditionCode " +
                "where IPC_EDITION_CODE = :oldIpcEditionCode and IPC_SECTION_CODE = :ipcSectionCode and IPC_CLASS_CODE = :ipcClassCode " +
                "and IPC_SUBCLASS_CODE = :ipcSubclassCode " +
                "and IPC_GROUP_CODE = :ipcGroupCode and IPC_SUBGROUP_CODE = :ipcSubgroupCode");
        nativeQuery.setParameter("newIpcEditionCode", newIpcEditionCode);
        nativeQuery.setParameter("oldIpcEditionCode", oldIpcEditionCode);
        nativeQuery.setParameter("ipcSectionCode", ipcSectionCode);
        nativeQuery.setParameter("ipcClassCode", ipcClassCode);
        nativeQuery.setParameter("ipcSubclassCode", ipcSubclassCode);
        nativeQuery.setParameter("ipcGroupCode", ipcGroupCode);
        nativeQuery.setParameter("ipcSubgroupCode", ipcSubgroupCode);
        return nativeQuery.executeUpdate();
    }

    public List<PatentIpcEntity> searchPatentIpcClasses(IpcPatentsFilterDTO filter) {
        TypedQuery<PatentIpcEntity> query = createPatentIpcClassesQuery(filter, false);
        query.setMaxResults(filter.getPageSize());
        query.setFirstResult((filter.getPage()) * filter.getPageSize());
        return query.getResultList();
    }

    public int getPatentIpcClassesCount(IpcPatentsFilterDTO filter) {
        TypedQuery<Number> query = createPatentIpcClassesQuery(filter, true);
        Number result = query.getSingleResult();
        return result.intValue();
    }

    protected <T> TypedQuery<T> createPatentIpcClassesQuery(IpcPatentsFilterDTO filter, boolean isCount) {
        Map<String, Object> queryParameters = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        queryBuilder.append(isCount ? " COUNT(r) " : " r ");
        queryBuilder.append(" FROM ").append(PatentIpcEntity.class.getSimpleName()).append(" r");
        queryBuilder.append(" WHERE 1=1 ");
        filterQuery(filter, queryParameters, queryBuilder);
        if (!isCount) {
            queryBuilder.append(" order by r.pk.patent.pk.fileSeq, r.pk.patent.pk.fileTyp, r.pk.patent.pk.fileSer, r.pk.patent.pk.fileNbr ");
        }
        Class<? extends Serializable> queryClass = isCount ? Number.class : PatentIpcEntity.class;
        TypedQuery typedQuery = em.createQuery(queryBuilder.toString(), queryClass);
        queryParameters.keySet().forEach(key -> typedQuery.setParameter(key, queryParameters.get(key)));
        return typedQuery;
    }

    private static void filterQuery(IpcPatentsFilterDTO filter, Map<String, Object> queryParameters, StringBuilder queryBuilder) {
        String edition = filter.getIpcEditionCode();
        if (StringUtils.hasText(edition)) {
            queryBuilder.append(" AND LOWER(r.pk.ipc.ipcEntityPK.ipcEditionCode) = LOWER(:edition) ");
            queryParameters.put("edition", edition);
        }

        String section = filter.getIpcSectionCode();
        if (StringUtils.hasText(section)) {
            queryBuilder.append(" AND LOWER(r.pk.ipc.ipcEntityPK.ipcSectionCode) = LOWER(:section) ");
            queryParameters.put("section", section);
        }

        String ipcClass = filter.getIpcClassCode();
        if (StringUtils.hasText(ipcClass)) {
            queryBuilder.append(" AND LOWER(r.pk.ipc.ipcEntityPK.ipcClassCode) = LOWER(:ipcClass) ");
            queryParameters.put("ipcClass", ipcClass);
        }

        String ipcSubclass = filter.getIpcSubclassCode();
        if (StringUtils.hasText(ipcSubclass)) {
            queryBuilder.append(" AND LOWER(r.pk.ipc.ipcEntityPK.ipcSubclassCode) = LOWER(:ipcSubclass) ");
            queryParameters.put("ipcSubclass", ipcSubclass);
        }

        String group = filter.getIpcGroupCode();
        if (StringUtils.hasText(group)) {
            queryBuilder.append(" AND LOWER(r.pk.ipc.ipcEntityPK.ipcGroupCode) = LOWER(:group) ");
            queryParameters.put("group", group);
        }

        String subGroup = filter.getIpcSubgroupCode();
        if (StringUtils.hasText(subGroup)) {
            queryBuilder.append(" AND LOWER(r.pk.ipc.ipcEntityPK.ipcSubgroupCode) = LOWER(:subGroup) ");
            queryParameters.put("subGroup", subGroup);
        }

        String fileTyp = filter.getFileTyp();
        if (StringUtils.hasText(fileTyp)) {
            queryBuilder.append(" AND LOWER(r.pk.patent.pk.fileTyp) = LOWER(:fileTyp) ");
            queryParameters.put("fileTyp", fileTyp);
        }

        String fileSer = filter.getFileSer();
        if (StringUtils.hasText(fileSer)) {
            queryBuilder.append(" AND LOWER(r.pk.patent.pk.fileSer) = LOWER(:fileSer) ");
            queryParameters.put("fileSer", fileSer);
        }

        String fileNbr = filter.getFileNbr();
        if (StringUtils.hasText(fileNbr)) {
            queryBuilder.append(" AND LOWER(r.pk.patent.pk.fileNbr) = LOWER(:fileNbr) ");
            queryParameters.put("fileNbr", fileNbr);
        }

        String regNum = filter.getRegNum();
        if (StringUtils.hasText(regNum)) {
            queryBuilder.append(" AND LOWER(r.pk.patent.registrationNbr) = LOWER(:regNum) ");
            queryParameters.put("regNum", regNum);
        }

        String title = filter.getPatentTitle();
        if (StringUtils.hasText(title)) {
            queryBuilder.append(" AND LOWER(r.pk.patent.title) like LOWER(:title) ");
            queryParameters.put("title", "%" + title + "%");
        }
    }

    public List<PatentIpcEntity> selectByPatentKeyAndIpc(List<String> ids, IPCEntityPK ipcPk) {
        TypedQuery query = em.createQuery("select x from PatentIpcEntity x where x.pk.ipc.ipcEntityPK.ipcEditionCode = :edition and x.pk.ipc.ipcEntityPK.ipcSectionCode = :section and x.pk.ipc.ipcEntityPK.ipcClassCode = :ipcClass and x.pk.ipc.ipcEntityPK.ipcSubclassCode = :ipcSubclass and x.pk.ipc.ipcEntityPK.ipcGroupCode = :group  and x.pk.ipc.ipcEntityPK.ipcSubgroupCode = :subGroup " +
                "and concat(x.pk.patent.pk.fileSeq, '/', x.pk.patent.pk.fileTyp, '/', x.pk.patent.pk.fileSer, '/', x.pk.patent.pk.fileNbr) in :ids", PatentIpcEntity.class);
        query.setParameter("edition", ipcPk.getIpcEditionCode());
        query.setParameter("section", ipcPk.getIpcSectionCode());
        query.setParameter("ipcClass", ipcPk.getIpcClassCode());
        query.setParameter("ipcSubclass", ipcPk.getIpcSubclassCode());
        query.setParameter("group", ipcPk.getIpcGroupCode());
        query.setParameter("subGroup", ipcPk.getIpcSubgroupCode());
        query.setParameter("ids", ids);
        return query.getResultList();
    }

}
