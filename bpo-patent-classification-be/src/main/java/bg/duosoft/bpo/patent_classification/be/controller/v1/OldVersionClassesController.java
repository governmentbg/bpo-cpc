package bg.duosoft.bpo.patent_classification.be.controller.v1;

import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcTableRowDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.filter.IpcClassesFilterDTO;
import bg.duosoft.bpo.patent_classification.be.service.ipc.IPCService;
import bg.duosoft.bpo.patent_classification.be.util.ActivityToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/old-version-classes")
@RequiredArgsConstructor
public class OldVersionClassesController {
    private final IPCService ipcService;
    private final ActivityToken activityToken;

    @GetMapping(value = "/search")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_OLD_CLASS_VERSIONS)")
    public Page<IpcTableRowDTO> searchData(IpcClassesFilterDTO filter) {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {
            List<IpcTableRowDTO> results = ipcService.searchOldVersionClasses(filter);
            return new PageImpl<>(results, PageRequest.of(filter.getPage(), filter.getPageSize()), ipcService.getOldVersionClassesCount(filter));
        }
        return null;
    }
}
