package com.amazon.ata.cost;

import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;
import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.Box;
import com.amazon.ata.types.PolyBag;


import java.math.BigDecimal;

public class CarbonCostStrategy implements CostStrategy {
    private PolyBag polyBag;
    private Box box;
    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal cost = BigDecimal.valueOf(0);


        if (packaging.getMaterial().equals(Material.CORRUGATE)) {
            cost = BigDecimal.valueOf(.005).multiply(BigDecimal.valueOf(.017));
//            cost = box.getMass().multiply(BigDecimal.valueOf(.017));
        }
        if (packaging.getMaterial().equals(Material.LAMINATED_PLASTIC)) {
            cost = BigDecimal.valueOf(.25).multiply(BigDecimal.valueOf(.012));
//            cost = polyBag.getMass().multiply(BigDecimal.valueOf(.012));
        }

        return new ShipmentCost(shipmentOption, cost);
    }
}
