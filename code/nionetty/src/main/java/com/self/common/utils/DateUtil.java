package com.self.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 * 
 * @author Administrator
 *
 */
public class DateUtil {

	/**
	 * 获取精确时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDate(String format) {
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return time.format(formatter);
	}

	/**
	 * 把原来格式化时间, 重新格式化
	 * 
	 * @param beforeTimeFormat
	 * @param beforeTimeStr
	 * @param formatStr
	 * @return
	 */
	public static String formatDate(String beforeTimeFormat, String beforeTimeStr, String formatStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(beforeTimeFormat);
		LocalDateTime localDateTime = LocalDateTime.parse(beforeTimeStr, formatter);
		formatter = DateTimeFormatter.ofPattern(formatStr);
		return localDateTime.format(formatter);
	}

}
