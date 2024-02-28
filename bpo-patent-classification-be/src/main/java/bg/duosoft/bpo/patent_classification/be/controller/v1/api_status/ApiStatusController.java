package bg.duosoft.bpo.patent_classification.be.controller.v1.api_status;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/checkStatus")
public class ApiStatusController {

    @GetMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkApiStatus() {}

}
