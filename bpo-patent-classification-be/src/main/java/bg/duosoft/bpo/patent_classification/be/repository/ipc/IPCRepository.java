package bg.duosoft.bpo.patent_classification.be.repository.ipc;

import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCClassEntity;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.repository.BaseRepository;
import bg.duosoft.bpo.patent_classification.be.util.ipc.IPCCodeWithoutEdition;
import bg.duosoft.bpo.patent_classification.be.util.ipc.IPCWithDifferentEdition;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPCRepository extends BaseRepository<IPCClassEntity, IPCEntityPK> {
    @Query("select c from IPCClassEntity c where c.ipcEntityPK.ipcSectionCode = :ipcSectionCode and " +
            "c.ipcEntityPK.ipcClassCode = :ipcClassCode and " +
            "c.ipcEntityPK.ipcSubclassCode = :ipcSubclassCode and " +
            "c.ipcEntityPK.ipcGroupCode = :ipcGroupCode and " +
            "c.ipcEntityPK.ipcSubgroupCode = :ipcSubgroupCode")
    List<IPCClassEntity> selectAllWithSameCode(String ipcSectionCode, String ipcClassCode, String ipcSubclassCode, String ipcGroupCode, String ipcSubgroupCode);

    @Query(nativeQuery = true, value = "select i.IPC_SECTION_CODE as ipcSectionCode, i.IPC_CLASS_CODE as ipcClassCode, i.IPC_SUBCLASS_CODE as ipcSubclassCode, i.IPC_GROUP_CODE as ipcGroupCode, i.IPC_SUBGROUP_CODE as ipcSubgroupCode from ipasprod.CF_CLASS_IPC i group by i.IPC_SECTION_CODE, i.IPC_CLASS_CODE, i.IPC_SUBCLASS_CODE, i.IPC_GROUP_CODE, i.IPC_SUBGROUP_CODE having count(distinct IPC_NAME)>1")
    List<IPCCodeWithoutEdition> getRepeatingEntitiesWithDifferentNames();

    @Query(nativeQuery = true, value = "select i.IPC_SECTION_CODE as ipcSectionCode, i.IPC_CLASS_CODE as ipcClassCode, i.IPC_SUBCLASS_CODE as ipcSubclassCode, i.IPC_GROUP_CODE as ipcGroupCode, i.IPC_SUBGROUP_CODE as ipcSubgroupCode from ipasprod.CF_CLASS_IPC i group by i.IPC_SECTION_CODE, i.IPC_CLASS_CODE, i.IPC_SUBCLASS_CODE, i.IPC_GROUP_CODE, i.IPC_SUBGROUP_CODE having count(*)>1")
    List<IPCCodeWithoutEdition> getRepeatingEntities();

    @Query(nativeQuery = true, value = "select i.IPC_SECTION_CODE as ipcSectionCode, i.IPC_CLASS_CODE as ipcClassCode, i.IPC_SUBCLASS_CODE as ipcSubclassCode, i.IPC_GROUP_CODE as ipcGroupCode, i.IPC_SUBGROUP_CODE as ipcSubgroupCode, i.IPC_NAME as ipcName, i.IPC_EDITION_CODE as ipcEditionCode, x.IPC_NAME as newIpcName, x.IPC_EDITION_CODE as newIpcEditionCode, x.IPC_LATEST_VERSION as newIpcLatestVersion from ipasprod.CF_CLASS_IPC i join ipasprod.CF_CLASS_IPC_LATEST_SPEC x on x.IPC_SECTION_CODE = i.IPC_SECTION_CODE and x.IPC_CLASS_CODE = i.IPC_CLASS_CODE and x.IPC_SUBCLASS_CODE = i.IPC_SUBCLASS_CODE and x.IPC_GROUP_CODE = i.IPC_GROUP_CODE and x.IPC_SUBGROUP_CODE = i.IPC_SUBGROUP_CODE where x.IPC_EDITION_CODE <> i.IPC_EDITION_CODE or x.IPC_LATEST_VERSION <> i.IPC_LATEST_VERSION order by i.IPC_SECTION_CODE, i.IPC_CLASS_CODE, i.IPC_SUBCLASS_CODE, i.IPC_GROUP_CODE, i.IPC_SUBGROUP_CODE, i.IPC_EDITION_CODE")
    List<IPCWithDifferentEdition> getEntitiesWithDifferentEditionCodes();

    @Modifying
    @Transactional
    @Query("update IPCClassEntity c set c.ipcLatestVersion = :latestVersion, c.ipcEntityPK.ipcEditionCode = :editionCode, c.ipcName = :ipcName where c.ipcEntityPK.ipcEditionCode = :ipcEditionCode and c.ipcEntityPK.ipcSectionCode = :ipcSectionCode and c.ipcEntityPK.ipcClassCode = :ipcClassCode and c.ipcEntityPK.ipcSubclassCode = :ipcSubclassCode and c.ipcEntityPK.ipcGroupCode = :ipcGroupCode and c.ipcEntityPK.ipcSubgroupCode = :ipcSubgroupCode")
    void updateEditionAndLatestVersion(String editionCode, String latestVersion, String ipcName, String ipcEditionCode, String ipcSectionCode, String ipcClassCode, String ipcSubclassCode, String ipcGroupCode, String ipcSubgroupCode);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into ipasprod.CF_CLASS_IPC SELECT a.ROW_VERSION, a.IPC_EDITION_CODE, a.IPC_SECTION_CODE, a.IPC_CLASS_CODE, a.IPC_SUBCLASS_CODE, a.IPC_GROUP_CODE, a.IPC_SUBGROUP_CODE, a.IPC_NAME, a.XML_DESIGNER, a.IPC_LATEST_VERSION, a.SYMBOL from CF_CLASS_IPC_LATEST_SPEC a LEFT OUTER JOIN CF_CLASS_IPC b ON a.IPC_EDITION_CODE = b.IPC_EDITION_CODE AND a.IPC_SECTION_CODE = b.IPC_SECTION_CODE AND a.IPC_CLASS_CODE = b.IPC_CLASS_CODE AND a.IPC_SUBCLASS_CODE = b.IPC_SUBCLASS_CODE AND a.IPC_GROUP_CODE = b.IPC_GROUP_CODE AND a.IPC_SUBGROUP_CODE = b.IPC_SUBGROUP_CODE WHERE B.IPC_EDITION_CODE IS NULL")
    void insertMissingEntities();

    @Query("select c.ipcEntityPK from IPCClassEntity c where c.ipcName like '%??%' or c.ipcName like '%unknown%' or c.ipcName = '' or c.ipcName is null")
    List<IPCEntityPK> selectIpcsWithInvalidNames();

    @Modifying
    @Transactional
    @Query("update IPCClassEntity c set c.ipcName = :ipcName, c.ipcEntityPK.ipcEditionCode = :edition, c.ipcLatestVersion = :latestVersion where c.ipcEntityPK = :pk ")
    void updateIpcByPK(String ipcName, String edition, String latestVersion, IPCEntityPK pk);

    @Query(nativeQuery = true, value = "select i.IPC_SECTION_CODE as ipcSectionCode, i.IPC_CLASS_CODE as ipcClassCode, i.IPC_SUBCLASS_CODE as ipcSubclassCode, i.IPC_GROUP_CODE as ipcGroupCode, i.IPC_SUBGROUP_CODE as ipcSubgroupCode, i.IPC_NAME as ipcName, i.IPC_EDITION_CODE as ipcEditionCode, x.IPC_NAME as newIpcName, x.IPC_EDITION_CODE as newIpcEditionCode, x.IPC_LATEST_VERSION as newIpcLatestVersion from ipasprod.CF_CLASS_IPC i join ipasprod.CF_CLASS_IPC_LATEST_SPEC x on x.IPC_SECTION_CODE=i.IPC_SECTION_CODE and x.IPC_CLASS_CODE=i.IPC_CLASS_CODE and x.IPC_SUBCLASS_CODE=i.IPC_SUBCLASS_CODE and x.IPC_GROUP_CODE=i.IPC_GROUP_CODE and x.IPC_SUBGROUP_CODE=i.IPC_SUBGROUP_CODE where i.ipc_name<>x.ipc_name and replace(i.ipc_name, ' ', '') like replace(x.ipc_name, ' ', '')+'%' and len(i.IPC_NAME)-len(x.IPC_NAME)<5 and  i.IPC_EDITION_CODE<9 order by len(i.ipc_name)")
    List<IPCWithDifferentEdition> getEntitiesWithLessThan5CharDifference();

    @Query(nativeQuery = true, value = "select i.IPC_SECTION_CODE as ipcSectionCode, i.IPC_CLASS_CODE as ipcClassCode, i.IPC_SUBCLASS_CODE as ipcSubclassCode, i.IPC_GROUP_CODE as ipcGroupCode, i.IPC_SUBGROUP_CODE as ipcSubgroupCode, i.IPC_NAME as ipcName, i.IPC_EDITION_CODE as ipcEditionCode, x.IPC_NAME as newIpcName, x.IPC_EDITION_CODE as newIpcEditionCode, x.IPC_LATEST_VERSION as newIpcLatestVersion from ipasprod.CF_CLASS_IPC i join ipasprod.CF_CLASS_IPC_LATEST_SPEC x on x.IPC_SECTION_CODE=i.IPC_SECTION_CODE and x.IPC_CLASS_CODE=i.IPC_CLASS_CODE and x.IPC_SUBCLASS_CODE=i.IPC_SUBCLASS_CODE and x.IPC_GROUP_CODE=i.IPC_GROUP_CODE and x.IPC_SUBGROUP_CODE=i.IPC_SUBGROUP_CODE where i.ipc_name<>x.ipc_name and replace(x.ipc_name, ' ', '') like replace(i.ipc_name, ' ', '')+'%' and len(i.ipc_name)=199 and i.IPC_EDITION_CODE<9 order by len(i.ipc_name)")
    List<IPCWithDifferentEdition> getEntitiesWithCutNames();

    @Query(nativeQuery = true, value = "select i.IPC_SECTION_CODE as ipcSectionCode, i.IPC_CLASS_CODE as ipcClassCode, i.IPC_SUBCLASS_CODE as ipcSubclassCode, i.IPC_GROUP_CODE as ipcGroupCode, i.IPC_SUBGROUP_CODE as ipcSubgroupCode, i.IPC_NAME as ipcName, i.IPC_EDITION_CODE as ipcEditionCode, x.IPC_NAME as newIpcName, x.IPC_EDITION_CODE as newIpcEditionCode, x.IPC_LATEST_VERSION as newIpcLatestVersion from ipasprod.CF_CLASS_IPC i join ipasprod.CF_CLASS_IPC_LATEST_SPEC x on x.IPC_SECTION_CODE = i.IPC_SECTION_CODE and x.IPC_CLASS_CODE = i.IPC_CLASS_CODE and x.IPC_SUBCLASS_CODE = i.IPC_SUBCLASS_CODE and x.IPC_GROUP_CODE = i.IPC_GROUP_CODE and x.IPC_SUBGROUP_CODE = i.IPC_SUBGROUP_CODE where i.ipc_name<>x.ipc_name and x.IPC_EDITION_CODE='20060101' order by i.IPC_SECTION_CODE, i.IPC_CLASS_CODE, i.IPC_SUBCLASS_CODE, i.IPC_GROUP_CODE, i.IPC_SUBGROUP_CODE, i.IPC_EDITION_CODE")
    List<IPCWithDifferentEdition> getEntitiesWithDifferentNamesFrom2006();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update IPASPROD.EXT_CONFIG_PARAM set VALUE = (select MAX(IPC_LATEST_VERSION) from CF_CLASS_IPC) where CONFIG_CODE = 'IPC_LATEST_VERSION'")
    void setExtConfigIpcParam();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update IPASPROD.CF_CLASS_IPC set IPC_LATEST_VERSION = IPC_EDITION_CODE where IPC_LATEST_VERSION is null or IPC_LATEST_VERSION = ''")
    void setEmptyLatestVersions();
}
