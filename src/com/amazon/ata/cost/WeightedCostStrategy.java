package com.amazon.ata.cost;

import com.amazon.ata.types.Material;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;
import java.math.BigDecimal;
import java.util.Map;
import com.amazon.ata.types.Packaging;

public class WeightedCostStrategy implements CostStrategy {
    private CarbonCostStrategy carbonCostStrategy;
    private MonetaryCostStrategy monetaryCostStrategy;
//    private Map<BigDecimal, CostStrategy> costStrategyMap;


    public WeightedCostStrategy(MonetaryCostStrategy monetaryCostStrategy, CarbonCostStrategy carbonCostStrategy) {
        this.monetaryCostStrategy = monetaryCostStrategy;
        this.carbonCostStrategy = carbonCostStrategy;
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        BigDecimal cost;

        BigDecimal monetaryCost = monetaryCostStrategy.getCost(shipmentOption).getCost();
        monetaryCost = monetaryCost.multiply(BigDecimal.valueOf(.8));

        BigDecimal carbonCost = carbonCostStrategy.getCost(shipmentOption).getCost();
        carbonCost = carbonCost.multiply(BigDecimal.valueOf(.2));

        cost = monetaryCost.add(carbonCost);
        return new ShipmentCost(shipmentOption, cost);


    }
}
