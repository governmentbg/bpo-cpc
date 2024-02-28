package bg.duosoft.bpo.patent_classification.be.repository.cpc;

import bg.duosoft.bpo.patent_classification.be.domain.entity.cpc.CPCClassEntity;
import bg.duosoft.bpo.patent_classification.be.domain.entity.cpc.CPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.repository.BaseRepository;
import bg.duosoft.bpo.patent_classification.be.util.cpc.CPCWithDifferentEdition;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CPCRepository extends BaseRepository<CPCClassEntity, CPCEntityPK> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete c from ipasprod.CF_CLASS_CPC c left outer join ipasprod.IP_PATENT_CPC_CLASSES pc on c.CPC_EDITION_CODE = pc.CPC_EDITION_CODE and c.CPC_SECTION_CODE = pc.CPC_SECTION_CODE and c.CPC_CLASS_CODE = pc.CPC_CLASS_CODE and c.CPC_SUBCLASS_CODE = pc.CPC_SUBCLASS_CODE and c.CPC_GROUP_CODE = pc.CPC_GROUP_CODE and c.CPC_SUBGROUP_CODE = pc.CPC_SUBGROUP_CODE where pc.CPC_SECTION_CODE is null")
    void deleteUnusedCpcClasses();

    @Query(nativeQuery = true, value = "select i.CPC_SECTION_CODE as cpcSectionCode, i.CPC_CLASS_CODE as cpcClassCode, i.CPC_SUBCLASS_CODE as cpcSubclassCode, i.CPC_GROUP_CODE as cpcGroupCode, i.CPC_SUBGROUP_CODE as cpcSubgroupCode, i.CPC_NAME as cpcName, i.CPC_EDITION_CODE as cpcEditionCode, x.CPC_NAME as newCpcName, x.CPC_EDITION_CODE as newCpcEditionCode, x.CPC_LATEST_VERSION as newCpcLatestVersion from ipasprod.CF_CLASS_CPC i join ipasprod.CF_CLASS_CPC_LATEST_SPEC x on x.CPC_SECTION_CODE = i.CPC_SECTION_CODE and x.CPC_CLASS_CODE = i.CPC_CLASS_CODE and x.CPC_SUBCLASS_CODE = i.CPC_SUBCLASS_CODE and x.CPC_GROUP_CODE = i.CPC_GROUP_CODE and x.CPC_SUBGROUP_CODE = i.CPC_SUBGROUP_CODE where x.CPC_EDITION_CODE <> i.CPC_EDITION_CODE or x.CPC_LATEST_VERSION <> i.CPC_LATEST_VERSION order by i.CPC_SECTION_CODE, i.CPC_CLASS_CODE, i.CPC_SUBCLASS_CODE, i.CPC_GROUP_CODE, i.CPC_SUBGROUP_CODE, i.CPC_EDITION_CODE")
    List<CPCWithDifferentEdition> getEntitiesWithDifferentEditionCodes();

    @Modifying
    @Transactional
    @Query("update CPCClassEntity c set c.cpcLatestVersion = :latestVersion, c.cpcEntityPK.cpcEditionCode = :editionCode, c.cpcName = :cpcName where c.cpcEntityPK.cpcEditionCode = :cpcEditionCode and c.cpcEntityPK.cpcSectionCode = :cpcSectionCode and c.cpcEntityPK.cpcClassCode = :cpcClassCode and c.cpcEntityPK.cpcSubclassCode = :cpcSubclassCode and c.cpcEntityPK.cpcGroupCode = :cpcGroupCode and c.cpcEntityPK.cpcSubgroupCode = :cpcSubgroupCode")
    void updateEditionAndLatestVersion(String editionCode, String latestVersion, String cpcName, String cpcEditionCode, String cpcSectionCode, String cpcClassCode, String cpcSubclassCode, String cpcGroupCode, String cpcSubgroupCode);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into ipasprod.CF_CLASS_CPC SELECT a.ROW_VERSION, a.CPC_EDITION_CODE, a.CPC_SECTION_CODE, a.CPC_CLASS_CODE, a.CPC_SUBCLASS_CODE, a.CPC_GROUP_CODE, a.CPC_SUBGROUP_CODE, a.CPC_NAME, a.CPC_LATEST_VERSION, a.SYMBOL from CF_CLASS_CPC_LATEST_SPEC a LEFT OUTER JOIN CF_CLASS_CPC b ON a.CPC_EDITION_CODE = b.CPC_EDITION_CODE AND a.CPC_SECTION_CODE = b.CPC_SECTION_CODE AND a.CPC_CLASS_CODE = b.CPC_CLASS_CODE AND a.CPC_SUBCLASS_CODE = b.CPC_SUBCLASS_CODE AND a.CPC_GROUP_CODE = b.CPC_GROUP_CODE AND a.CPC_SUBGROUP_CODE = b.CPC_SUBGROUP_CODE WHERE B.CPC_EDITION_CODE IS NULL")
    void insertMissingEntities();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update IPASPROD.EXT_CONFIG_PARAM set VALUE = :latestVersion where CONFIG_CODE = 'CPC_LATEST_VERSION'")
    void setExtConfigCpcParam(String latestVersion);
}
