package bg.duosoft.bpo.patent_classification.be.repository.ipc;

import bg.duosoft.bpo.common.dto.filter.AutocompleteFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.filter.IpcClassesFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCClassEntity;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCLatestEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class IPCRepositoryCustom {

    private final EntityManager em;

    @Transactional
    public void deleteUnusedIpcClasses() {
        Query query = em.createNativeQuery("delete c from ipasprod.CF_CLASS_IPC c left outer join ipasprod.IP_PATENT_IPC_CLASSES pc on c.IPC_EDITION_CODE = pc.IPC_EDITION_CODE and c.IPC_SECTION_CODE = pc.IPC_SECTION_CODE and c.IPC_CLASS_CODE = pc.IPC_CLASS_CODE and c.IPC_SUBCLASS_CODE = pc.IPC_SUBCLASS_CODE and c.IPC_GROUP_CODE = pc.IPC_GROUP_CODE and c.IPC_SUBGROUP_CODE = pc.IPC_SUBGROUP_CODE where pc.IPC_SECTION_CODE is null");
        query.executeUpdate();
    }

    public List<IPCClassEntity> searchMissingClasses(IpcClassesFilterDTO filter) {
        TypedQuery<IPCClassEntity> query = createMissingClassesQuery(filter, false);
        query.setMaxResults(filter.getPageSize());
        query.setFirstResult((filter.getPage()) * filter.getPageSize());
        return query.getResultList();
    }

    public int getMissingClassesCount(IpcClassesFilterDTO filter) {
        TypedQuery<Number> query = createMissingClassesQuery(filter, true);
        Number result = query.getSingleResult();
        return result.intValue();
    }

    protected <T> TypedQuery<T> createMissingClassesQuery(IpcClassesFilterDTO filter, boolean isCount) {
        Map<String, Object> queryParameters = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        queryBuilder.append(isCount ? " COUNT(r) " : " r ");
        queryBuilder.append(" FROM ").append(IPCClassEntity.class.getSimpleName()).append(" r");
        queryBuilder.append(" LEFT JOIN ").append(IPCLatestEntity.class.getSimpleName()).append(" x").append(" ON r.ipcEntityPK.ipcSectionCode = x.ipcEntityPK.ipcSectionCode and r.ipcEntityPK.ipcClassCode = x.ipcEntityPK.ipcClassCode and r.ipcEntityPK.ipcSubclassCode = x.ipcEntityPK.ipcSubclassCode and r.ipcEntityPK.ipcGroupCode = x.ipcEntityPK.ipcGroupCode and r.ipcEntityPK.ipcSubgroupCode = x.ipcEntityPK.ipcSubgroupCode ");
        queryBuilder.append(" WHERE x.ipcEntityPK.ipcSectionCode is null and x.ipcEntityPK.ipcClassCode is null and x.ipcEntityPK.ipcSubclassCode is null and x.ipcEntityPK.ipcGroupCode is null and x.ipcEntityPK.ipcSubgroupCode is null ");
//        queryBuilder.append(" and size(r.patentIpcEntities)>0 ");
        filterQuery(filter, queryParameters, queryBuilder);
        if (!isCount) {
            queryBuilder.append(" order by r.ipcEntityPK.ipcSectionCode, r.ipcEntityPK.ipcClassCode, r.ipcEntityPK.ipcSubclassCode, r.ipcEntityPK.ipcGroupCode, r.ipcEntityPK.ipcSubgroupCode ");
        }
        Class<? extends Serializable> queryClass = isCount ? Number.class : IPCClassEntity.class;
        TypedQuery typedQuery = em.createQuery(queryBuilder.toString(), queryClass);
        queryParameters.keySet().forEach(key -> typedQuery.setParameter(key, queryParameters.get(key)));
        return typedQuery;
    }

    public List<IPCClassEntity> searchOldVersionClasses(IpcClassesFilterDTO filter) {
        TypedQuery<IPCClassEntity> query = createOldVersionClassesQuery(filter, false);
        query.setMaxResults(filter.getPageSize());
        query.setFirstResult((filter.getPage()) * filter.getPageSize());
        return query.getResultList();
    }

    public int getOldVersionClassesCount(IpcClassesFilterDTO filter) {
        TypedQuery<Number> query = createOldVersionClassesQuery(filter, true);
        Number result = query.getSingleResult();
        return result.intValue();
    }

    protected <T> TypedQuery<T> createOldVersionClassesQuery(IpcClassesFilterDTO filter, boolean isCount) {
        Map<String, Object> queryParameters = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        queryBuilder.append(isCount ? " COUNT(r) " : " r ");
        queryBuilder.append(" FROM ").append(IPCClassEntity.class.getSimpleName()).append(" r");
        queryBuilder.append(" JOIN ").append(IPCLatestEntity.class.getSimpleName()).append(" x").append(" ON r.ipcEntityPK.ipcSectionCode = x.ipcEntityPK.ipcSectionCode and r.ipcEntityPK.ipcClassCode = x.ipcEntityPK.ipcClassCode and r.ipcEntityPK.ipcSubclassCode = x.ipcEntityPK.ipcSubclassCode and r.ipcEntityPK.ipcGroupCode = x.ipcEntityPK.ipcGroupCode and r.ipcEntityPK.ipcSubgroupCode = x.ipcEntityPK.ipcSubgroupCode ");
        queryBuilder.append(" WHERE r.ipcLatestVersion <> :latestVersion ");
        queryParameters.put("latestVersion", getLatestVersionParam());
        filterQuery(filter, queryParameters, queryBuilder);
        if (!isCount) {
            queryBuilder.append(" order by r.ipcEntityPK.ipcSectionCode, r.ipcEntityPK.ipcClassCode, r.ipcEntityPK.ipcSubclassCode, r.ipcEntityPK.ipcGroupCode, r.ipcEntityPK.ipcSubgroupCode ");
        }
        Class<? extends Serializable> queryClass = isCount ? Number.class : IPCClassEntity.class;
        TypedQuery typedQuery = em.createQuery(queryBuilder.toString(), queryClass);
        queryParameters.keySet().forEach(key -> typedQuery.setParameter(key, queryParameters.get(key)));
        return typedQuery;
    }

    private static void filterQuery(IpcClassesFilterDTO filter, Map<String, Object> queryParameters, StringBuilder queryBuilder) {
        String edition = filter.getIpcEditionCode();
        if (StringUtils.hasText(edition)) {
            queryBuilder.append(" AND LOWER(r.ipcEntityPK.ipcEditionCode) like LOWER(:edition) ");
            queryParameters.put("edition", "%" + edition + "%");
        }

        String section = filter.getIpcSectionCode();
        if (StringUtils.hasText(section)) {
            queryBuilder.append(" AND LOWER(r.ipcEntityPK.ipcSectionCode) like LOWER(:section) ");
            queryParameters.put("section", "%" + section + "%");
        }

        String ipcCLass = filter.getIpcClassCode();
        if (StringUtils.hasText(ipcCLass)) {
            queryBuilder.append(" AND LOWER(r.ipcEntityPK.ipcClassCode) like LOWER(:ipcCLass) ");
            queryParameters.put("ipcCLass", "%" + ipcCLass + "%");
        }

        String ipcSubClass = filter.getIpcSubclassCode();
        if (StringUtils.hasText(ipcSubClass)) {
            queryBuilder.append(" AND LOWER(r.ipcEntityPK.ipcSubclassCode) like LOWER(:ipcSubClass) ");
            queryParameters.put("ipcSubClass", "%" + ipcSubClass + "%");
        }

        String group = filter.getIpcGroupCode();
        if (StringUtils.hasText(group)) {
            queryBuilder.append(" AND LOWER(r.ipcEntityPK.ipcGroupCode) like LOWER(:group) ");
            queryParameters.put("group", "%" + group + "%");
        }

        String subGroup = filter.getIpcSubgroupCode();
        if (StringUtils.hasText(subGroup)) {
            queryBuilder.append(" AND LOWER(r.ipcEntityPK.ipcSubgroupCode) like LOWER(:subGroup) ");
            queryParameters.put("subGroup", "%" + subGroup + "%");
        }

        String ipcName = filter.getIpcName();
        if (StringUtils.hasText(ipcName)) {
            queryBuilder.append(" AND LOWER(r.ipcName) like LOWER(:ipcName) ");
            queryParameters.put("ipcName", "%" + ipcName + "%");
        }

        String ipcLatestVersion = filter.getIpcLatestVersion();
        if (StringUtils.hasText(ipcLatestVersion)) {
            queryBuilder.append(" AND r.ipcLatestVersion = :ipcLatestVersion ");
            queryParameters.put("ipcLatestVersion", ipcLatestVersion);
        }
    }

    public String getLatestVersionParam() {
        Query query = em.createNativeQuery("select VALUE from EXT_CONFIG_PARAM where CONFIG_CODE = 'IPC_LATEST_VERSION'");
        return query.getSingleResult().toString();
    }

    public List<String> ipcAutocomplete(AutocompleteFilterDTO filter) {
        Map<String, Object> queryParameters = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder("select concat('(', IPC_EDITION_CODE, ') ', IPC_SECTION_CODE, IPC_CLASS_CODE, IPC_SUBCLASS_CODE, IPC_GROUP_CODE, '-', IPC_SUBGROUP_CODE, ' - ', IPC_NAME) from IPASPROD.CF_CLASS_IPC where lower(concat('(', IPC_EDITION_CODE, ') ', IPC_SECTION_CODE, IPC_CLASS_CODE, IPC_SUBCLASS_CODE, IPC_GROUP_CODE, '-', IPC_SUBGROUP_CODE, ' - ', IPC_NAME)) like lower(:name) order by IPC_EDITION_CODE, IPC_SECTION_CODE, IPC_CLASS_CODE, IPC_SUBCLASS_CODE, IPC_GROUP_CODE, IPC_SUBGROUP_CODE, IPC_NAME");
        queryParameters.put("name", "%" + filter.getName() + "%");
        Query nativeQuery = em.createNativeQuery(queryBuilder.toString(), String.class);
        queryParameters.keySet().forEach((key) -> {
            nativeQuery.setParameter(key, queryParameters.get(key));
        });
        nativeQuery.setMaxResults(filter.getPageSize());
        nativeQuery.setFirstResult((filter.getPage()) * filter.getPageSize());
        return nativeQuery.getResultList();
    }

}
