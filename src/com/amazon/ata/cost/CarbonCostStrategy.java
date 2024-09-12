package com.amazon.ata.cost;

import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;
import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;


import java.math.BigDecimal;

public class CarbonCostStrategy implements CostStrategy {
    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal cost = BigDecimal.valueOf(0);


        if (packaging.getMaterial().equals(Material.CORRUGATE)) {
            cost = packaging.getMass().multiply(BigDecimal.valueOf(.017));
        }
        if (packaging.getMaterial().equals(Material.LAMINATED_PLASTIC)) {
            cost = packaging.getMass().multiply(BigDecimal.valueOf(.012));
        }

        return new ShipmentCost(shipmentOption, cost);
    }
}
