package com.city.parkingMeter.parking.domain.vo;

import com.city.parkingMeter.infrastructure.exception.InvalidValueObjectDataException;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class HashIdTest {

    @Test(expected = InvalidValueObjectDataException.class)
    public void ofInvalidUUID() {
        UUID uuid = null;
        HashId.of(uuid);
    }

    @Test(expected = InvalidValueObjectDataException.class)
    public void ofInvalidString() {
        String uuid = null;
        HashId.of(uuid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ofInvalidFormatString() {
        String uuid = "bad-format-uuid";
        HashId.of(uuid);
    }

    @Test
    public void ofUUID() {
        UUID uuid = UUID.fromString("74d1f926-2059-4f5f-8059-afc406af95a1");
        HashId hashId = HashId.of(uuid);
        Assert.assertEquals(hashId.getValue(), uuid);
    }

    @Test
    public void ofString() {
        String uuid = "74d1f926-2059-4f5f-8059-afc406af95a1";
        HashId hashId = HashId.of(uuid);
        Assert.assertEquals(hashId.getValue(), UUID.fromString(uuid));
    }
}





