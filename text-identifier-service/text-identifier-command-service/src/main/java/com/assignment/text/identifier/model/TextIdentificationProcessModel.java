package com.assignment.text.identifier.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TextIdentificationProcessModel {

	private String id = UUID.randomUUID().toString();

	private Instant createdAt = Instant.now();

	private final String email;

	private final String comment;
}
