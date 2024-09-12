package com.amazon.ata.cost;

import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;
import com.amazon.ata.types.Packaging;

public class WeightedCostStrategy implements CostStrategy {
    private CarbonCostStrategy carbonCostStrategy;
    private MonetaryCostStrategy monetaryCostStrategy;
    private Map<BigDecimal, CostStrategy> costStrategyMap;
    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();

        return null;
    }
}
