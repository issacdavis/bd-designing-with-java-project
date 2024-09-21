package com.amazon.ata.service;

import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.ShipmentOption;
import org.checkerframework.checker.units.qual.Mass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ShipmentServiceTest {

    private Item smallItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1))
            .withWidth(BigDecimal.valueOf(1))
            .withLength(BigDecimal.valueOf(1))
            .withAsin("abcde")
            .build();

    private Item largeItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1000))
            .withWidth(BigDecimal.valueOf(1000))
            .withLength(BigDecimal.valueOf(1000))
            .withAsin("12345")
            .build();

    private FulfillmentCenter existentFC = new FulfillmentCenter("ABE2");
    private FulfillmentCenter nonExistentFC = new FulfillmentCenter("NonExistentFC");
    @Mock
    private PackagingDAO packagingDAO;
    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @InjectMocks
    private ShipmentService shipmentService = new ShipmentService(new PackagingDAO(new PackagingDatastore()),
            new MonetaryCostStrategy());

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        List<ShipmentOption> result = new ArrayList<>();
        result.add(ShipmentOption.builder()
                .withItem(smallItem)
                .withFulfillmentCenter(existentFC)
                .build());
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, existentFC);

        //Mocking Behavior
        when(packagingDAO.findShipmentOptions(smallItem, existentFC)).thenReturn(result);

        // THEN
        assertNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        List<ShipmentOption> result = new ArrayList<>();
        result.add(ShipmentOption.builder()
                .withItem(largeItem)
                .withFulfillmentCenter(existentFC)
                .build());
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        // Mocking Behavior
        when(packagingDAO.findShipmentOptions(largeItem, existentFC)).thenReturn(result);

        // THEN
        assertNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        List<ShipmentOption> result = new ArrayList<>();
        result.add(ShipmentOption.builder()
                .withItem(smallItem)
                .withFulfillmentCenter(nonExistentFC)
                .build());
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, nonExistentFC);

        // Mocking Behavior
        when(packagingDAO.findShipmentOptions(smallItem, nonExistentFC)).thenReturn(result);

        // THEN
        assertNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCannotFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        List<ShipmentOption> result = new ArrayList<>();
        result.add(ShipmentOption.builder()
                .withItem(largeItem)
                .withFulfillmentCenter(nonExistentFC)
                .build());
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, nonExistentFC);

        //Mocking Behavior
        when(packagingDAO.findShipmentOptions(largeItem, nonExistentFC)).thenReturn(result);

        // THEN
        assertNull(shipmentOption);
    }
}