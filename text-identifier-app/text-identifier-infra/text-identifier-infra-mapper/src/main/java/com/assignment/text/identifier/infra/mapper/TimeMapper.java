package com.assignment.text.identifier.infra.mapper;

import java.time.Instant;

import com.assignment.text.identifier.infra.util.hash.date.DateTimeConverter;

public class TimeMapper {

    public long toEpoch(Instant instant) {
        return DateTimeConverter.toEpoch(instant);
    }

    public Instant toInstant(long epoch) {
        return DateTimeConverter.toInstant(epoch);
    }
}
