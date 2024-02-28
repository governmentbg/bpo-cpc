package bg.duosoft.bpo.patent_classification.be.repository.cpc;

import bg.duosoft.bpo.patent_classification.be.domain.entity.concordance.ConcordanceEntity;
import bg.duosoft.bpo.patent_classification.be.domain.entity.concordance.ConcordanceEntityPK;
import bg.duosoft.bpo.patent_classification.be.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ConcordanceRepository extends BaseRepository<ConcordanceEntity, ConcordanceEntityPK> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from CF_CLASS_CPC_IPC_CONCORDANCE where 1=1")
    void clearConcordance();
}
