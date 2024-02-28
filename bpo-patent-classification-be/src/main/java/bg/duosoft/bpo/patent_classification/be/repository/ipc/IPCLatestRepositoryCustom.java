package bg.duosoft.bpo.patent_classification.be.repository.ipc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
@RequiredArgsConstructor
public class IPCLatestRepositoryCustom {

    private final EntityManager em;

    @Transactional
    public void deleteAll() {
        Query query = em.createNativeQuery("delete from IPASPROD.CF_CLASS_IPC_LATEST_SPEC where 1=1");
        query.executeUpdate();
    }

}
