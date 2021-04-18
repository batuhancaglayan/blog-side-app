package com.assignment.text.identifier.infra.util.hash;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	public static String hash(String text) {
		return DigestUtils.md5Hex(text);
	}
}
