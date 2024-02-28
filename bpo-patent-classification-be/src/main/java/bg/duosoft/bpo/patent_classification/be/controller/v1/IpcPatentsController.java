
package bg.duosoft.bpo.patent_classification.be.controller.v1;

import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcPatentTableRowDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.filter.IpcPatentsFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.service.ipc.IPCService;
import bg.duosoft.bpo.patent_classification.be.service.patent_ipc.PatentIpcService;
import bg.duosoft.bpo.patent_classification.be.util.ActivityToken;
import bg.duosoft.bpo.patent_classification.be.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ipc-patents")
@RequiredArgsConstructor
public class IpcPatentsController {
    private final ActivityToken activityToken;
    private final PatentIpcService patentIpcService;
    private final IPCService ipcService;

    @GetMapping(value = "/search")
    @PreAuthorize("hasAnyRole(" +
            "T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_MISSING_CLASSES, " +
            "T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_OLD_CLASS_VERSIONS)")
    public Page<IpcPatentTableRowDTO> searchData(IpcPatentsFilterDTO filter) throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {
            if (StringUtils.hasText(filter.getSymbol())) {
                IPCEntityPK pk = Utils.symbolToPk(filter.getSymbol());
                filter.setIpcSectionCode(pk.getIpcSectionCode());
                filter.setIpcClassCode(pk.getIpcClassCode());
                filter.setIpcSubclassCode(pk.getIpcSubclassCode());
                filter.setIpcGroupCode(pk.getIpcGroupCode());
                filter.setIpcSubgroupCode(pk.getIpcSubgroupCode());
            }
            List<IpcPatentTableRowDTO> results = patentIpcService.searchIpcPatents(filter);
            return new PageImpl<>(results, PageRequest.of(filter.getPage(), filter.getPageSize()), patentIpcService.getIpcPatentsCount(filter));
        }
        return null;
    }

    @PostMapping("/classify-multiple")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_OLD_CLASS_VERSIONS)")
    public void classifyMultiple(@RequestParam("ids") List<String> ids, @RequestParam("symbol") String symbol, @RequestParam("oldEdition") String oldEdition, @RequestParam("newEdition") String newEdition) throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {
            patentIpcService.changeIpcByPatentKey(ids, symbol, oldEdition, newEdition);
            ipcService.deleteIpcIfOldAndUnused(symbol, oldEdition);
        }
    }

    @PostMapping("/classify-all")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_OLD_CLASS_VERSIONS)")
    public void classifyAll(@RequestParam("symbol") String symbol, @RequestParam("oldEdition") String oldEdition, @RequestParam("newEdition") String newEdition) throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {
            patentIpcService.changeEditionForUsedIPC(symbol, oldEdition, newEdition);
            ipcService.deleteIpcIfOldAndUnused(symbol, oldEdition);
        }
    }

    @PostMapping("/classify-all-missing")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_MISSING_CLASSES)")
    public void classifyAllMissing(@RequestParam("oldEdition") String oldEdition,
                                   @RequestParam("oldSymbol") String oldSymbol,
                                   @RequestParam("newEditionAndSymbol") String newEditionAndSymbol) throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {
            patentIpcService.changeMissingIPC(oldEdition, oldSymbol, newEditionAndSymbol);
            ipcService.deleteIpcIfOldAndUnused(oldSymbol, oldEdition);
        }
    }
}
