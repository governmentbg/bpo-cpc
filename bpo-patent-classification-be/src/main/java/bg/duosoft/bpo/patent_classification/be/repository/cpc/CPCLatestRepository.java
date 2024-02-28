package bg.duosoft.bpo.patent_classification.be.repository.cpc;

import bg.duosoft.bpo.patent_classification.be.domain.entity.cpc.CPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.cpc.CPCLatestEntity;
import bg.duosoft.bpo.patent_classification.be.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CPCLatestRepository extends BaseRepository<CPCLatestEntity, CPCEntityPK> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from CF_CLASS_CPC_LATEST_SPEC where 1=1")
    void clearLatestSpec();

}
