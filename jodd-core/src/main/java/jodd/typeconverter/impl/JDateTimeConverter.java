// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.

package jodd.typeconverter.impl;

import jodd.datetime.DateTimeStamp;
import jodd.datetime.JDateTime;
import jodd.datetime.JulianDateStamp;
import jodd.typeconverter.TypeConversionException;
import jodd.typeconverter.TypeConverter;
import jodd.util.StringUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Converts object to {@link JDateTime}.
 * Conversion rules:
 * <ul>
 * <li><code>null</code> value is returned as <code>null</code></li>
 * <li>object of destination type is simply casted</li>
 * <li><code>Calendar</code> object is converted</li>
 * <li><code>Date</code> object is converted</li>
 * <li><code>Number</code> is used as number of milliseconds</li>
 * <li>finally, if string value contains only numbers it is parsed as milliseconds;
 * otherwise as JDateTime pattern</li>
 * </ul>
 */
public class JDateTimeConverter implements TypeConverter<JDateTime> {

	public JDateTime convert(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof JDateTime) {
			return (JDateTime) value;
		}
		if (value instanceof Calendar) {
			return new JDateTime((Calendar)value);
		}
		if (value instanceof Date) {
			return new JDateTime((Date) value);
		}

		if (value instanceof Number) {
			return new JDateTime(((Number) value).longValue());
		}

		if (value instanceof JulianDateStamp) {
			return new JDateTime((JulianDateStamp) value);
		}
		if (value instanceof DateTimeStamp) {
			return new JDateTime((DateTimeStamp) value);
		}

		String stringValue = value.toString().trim();

		if (StringUtil.containsOnlyDigits(stringValue) == false) {
			return new JDateTime(stringValue, JDateTime.DEFAULT_FORMAT);
		}
		try {
			long milliseconds = Long.parseLong(stringValue);
			return new JDateTime(milliseconds);
		} catch (NumberFormatException nfex) {
			throw new TypeConversionException(value, nfex);
		}
	}

}
