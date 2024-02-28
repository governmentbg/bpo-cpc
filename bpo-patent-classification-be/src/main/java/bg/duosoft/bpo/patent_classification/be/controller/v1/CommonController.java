package bg.duosoft.bpo.patent_classification.be.controller.v1;


import bg.duosoft.bpo.common.dto.filter.AutocompleteFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcViewDTO;
import bg.duosoft.bpo.patent_classification.be.service.ipc.IPCLatestService;
import bg.duosoft.bpo.patent_classification.be.service.ipc.IPCService;
import bg.duosoft.bpo.patent_classification.be.util.ActivityToken;
import bg.duosoft.bpo.patent_classification.be.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/common")
@RequiredArgsConstructor
public class CommonController {

    private final ActivityToken activityToken;
    private final IPCService ipcService;
    private final IPCLatestService ipcLatestService;

    @GetMapping(value = "/get-activity-token")
    public ActivityToken getActivityToken() {
        return activityToken;
    }

    @GetMapping(value = "/get-ipc")
    @PreAuthorize("hasAnyRole(" +
            "T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_MISSING_CLASSES, " +
            "T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_OLD_CLASS_VERSIONS)")
    public IpcViewDTO getIpcView(@RequestParam("symbol") String symbol, @RequestParam("edition") String edition) throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {
            IpcViewDTO ipcViewDTO = new IpcViewDTO();
            IpcDTO ipc = ipcService.getById(symbol, edition);
            ipcViewDTO.setCurrentIpc(ipc);
            IpcDTO latestIpc = ipcLatestService.getDtoBySymbol(Utils.symbolToPk(symbol));
            ipcViewDTO.setLatestIpc(latestIpc);
            return ipcViewDTO;
        }
        return null;
    }

    @GetMapping("/ipc-autocomplete")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_MISSING_CLASSES)")
    public List<String> ipcAutocomplete(AutocompleteFilterDTO filter) {
        return ipcService.ipcAutocomplete(filter);
    }

}
