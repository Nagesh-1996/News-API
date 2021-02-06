package com.robosoftin.evaluation.util;

/**
 * Author nagesh
 * created on 04/01/20
 * inside the package - com.robosoftin.evaluation.util
 **/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtils {

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String OKYC_DATE_FORMAT = "dd/MM/yyyy";

	public static Date formatDate(String date) {

		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Date formatOkycDate(String date) {

		try {
			return new SimpleDateFormat(OKYC_DATE_FORMAT).parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
