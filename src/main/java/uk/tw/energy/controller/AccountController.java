package uk.tw.energy.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uk.tw.energy.domain.SmartMeter;
import uk.tw.energy.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@Validated @RequestBody SmartMeter smartMeter) {
        // logic for registering new account
        boolean success = accountService.registerNewSmartMeter(smartMeter);
        if (success) {
            return ResponseEntity.ok("Smart meter registered successfully");
        }
        return ResponseEntity.badRequest().body("Unable to register smart meter !!");
    }

    @GetMapping("/smart-meter/{smartMeterId}")
    public ResponseEntity<Map<String, String>> getSmartMeterDetails(@PathVariable String smartMeterId) {
        Map<String, String> smartMeterDetails = accountService.getSmartMeterDetails(smartMeterId);
        if (smartMeterDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(smartMeterDetails);
    }
}
