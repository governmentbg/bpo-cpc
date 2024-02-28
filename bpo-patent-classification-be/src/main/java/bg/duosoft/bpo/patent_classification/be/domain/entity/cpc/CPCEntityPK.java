package bg.duosoft.bpo.patent_classification.be.domain.entity.cpc;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class CPCEntityPK implements Serializable {

  @Column(name = "CPC_EDITION_CODE")
  private String cpcEditionCode;

  @Column(name = "CPC_SECTION_CODE")
  private String cpcSectionCode;

  @Column(name = "CPC_CLASS_CODE")
  private String cpcClassCode;

  @Column(name = "CPC_SUBCLASS_CODE")
  private String cpcSubclassCode;

  @Column(name = "CPC_GROUP_CODE")
  private String cpcGroupCode;

  @Column(name = "CPC_SUBGROUP_CODE")
  private String cpcSubgroupCode;

  @Override
  public String toString() {
    return cpcEditionCode + ' ' + cpcSectionCode + ' ' + cpcClassCode + ' ' + cpcSubclassCode + ' ' + cpcGroupCode + ' ' + cpcSubgroupCode;
  }
}
