package uk.tw.energy.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.tw.energy.domain.PricePlan;
import uk.tw.energy.domain.SmartMeter;

@Service
public class AccountService {

    private final Map<String, String> smartMeterToPricePlanAccounts;
    private final List<PricePlan> pricePlans;
    private final List<String> plans;

    public AccountService(Map<String, String> smartMeterToPricePlanAccounts, List<PricePlan> pricePlans) {
        this.smartMeterToPricePlanAccounts = smartMeterToPricePlanAccounts;
        this.pricePlans = pricePlans;
        this.plans = pricePlans.stream().map(PricePlan::getPlanName).toList();
    }

    public boolean registerNewSmartMeter(SmartMeter smartMeter) {
        if (!plans.contains(smartMeter.pricePlan())
                || smartMeterToPricePlanAccounts.containsKey(smartMeter.smartMeterId())) {
            System.out.println("Price plan doesn't exist or smart meter already registered");
            return false;
        }
        String put = smartMeterToPricePlanAccounts.put(smartMeter.smartMeterId(), smartMeter.pricePlan());
        System.out.println("26 put" + put);
        return true;
    }

    public String getPricePlanIdForSmartMeterId(String smartMeterId) {
        return smartMeterToPricePlanAccounts.get(smartMeterId);
    }

    public Map<String, String> getSmartMeterDetails(String smartMeterId) {
        if (!smartMeterToPricePlanAccounts.containsKey(smartMeterId)) {
            return null;
        }
        return Map.of("Smart meter id", smartMeterId, "Price plan", smartMeterToPricePlanAccounts.get(smartMeterId));
    }
}
