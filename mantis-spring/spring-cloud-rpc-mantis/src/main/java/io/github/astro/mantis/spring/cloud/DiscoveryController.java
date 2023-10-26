package io.github.astro.mantis.spring.cloud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscoveryController {

    @RequestMapping("/actuator/health")
    public String healthCheck() {
        return "success";
    }
}
