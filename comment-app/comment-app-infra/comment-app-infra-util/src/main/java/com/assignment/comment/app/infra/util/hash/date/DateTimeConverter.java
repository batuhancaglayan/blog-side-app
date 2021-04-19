package com.assignment.comment.app.infra.util.hash.date;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class DateTimeConverter {

	public static long toEpoch(Instant instant) {
		if (instant == null) {
			return 0;
		}
		return TimeUnit.SECONDS.toMillis(instant.getEpochSecond());
	}
	
	public static Instant toInstant(long epoch) {
		return Instant.ofEpochMilli(epoch);
	}
}
