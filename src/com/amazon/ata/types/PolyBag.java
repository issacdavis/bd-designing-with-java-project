package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {
    private Material material;
    private BigDecimal volume;


    /**
     * Instantiates a new Packaging object.
     *
     * @param material - the Material of the package

     */

    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
    }
    @Override
    public boolean canFitItem(Item item) {

        int result = this.volume.compareTo(item.getHeight().multiply(item.getLength().multiply(item.getWidth())));
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal getMass() {
        return BigDecimal.valueOf(Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6));
    }

    public BigDecimal getVolume() {
        return volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PolyBag polybag = (PolyBag) o;
        return material == polybag.material && Objects.equals(volume, polybag.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), material, volume);
    }
}
