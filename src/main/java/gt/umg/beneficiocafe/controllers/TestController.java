package gt.umg.beneficiocafe.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/web-service")
    public String allAccess() {
        return "El Web Service esta funcionando correctamente";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('UMG_BC_PESO_CABAL')")
    public String adminAccess() {
        return "Admin Content.";
    }
}
