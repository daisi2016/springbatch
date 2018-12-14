package com.hx.data.center.util;


import java.io.Closeable;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


/**
 * 工具类
 * 
 * @author Si.Dai
 * 
 */
public class SimpleUtil {

	public static boolean isNullOrNullString(String str) {
		return isNull(str) || "null".equalsIgnoreCase(str);
	}

	public static boolean isNull(Object obj) {
		return obj == null ? true : false;
	}

	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	public static boolean isEmpty(Collection<?> coll) {
		return (coll == null || coll.isEmpty());
	}

	public static boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}

	public static boolean isEmpty(Object[] arr) {
		return (arr == null || arr.length == 0);
	}

	public static boolean isNotEmpty(Object[] arr) {
		return !isEmpty(arr);
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		return isNotNull(map) && !map.isEmpty();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @return
	 */
	public static boolean isBlank(String nstr) {
		if (nstr == null) {
			return true;
		} else {
			nstr = nstr.replace(" ", "");// 替换所有的空格
			if ("".equals(nstr) || (nstr).length() == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 判断字符串是否不为空(空格字符串也判断为空)
	 * 
	 * @return
	 */
	public static boolean isNotBlank(String nstr) {
		return !isBlank(nstr);
	}

	/**
	 * 数据四舍五入
	 * 
	 * @return 返回double精度的数据 特别注意的是0.00时返回数据是0.0
	 */
	public static double getScale(String str, int seit) {
		String dStr = getScaleString(str, seit);
		return new Double(dStr);
	}

	/**
	 * 数据四舍五入
	 * 
	 * @return 返回全精度的 字符串
	 */
	public static String getScaleString(String str, int seit) {
		BigDecimal bd = new BigDecimal(str + "");
		BigDecimal bd1 = bd.setScale(seit, BigDecimal.ROUND_HALF_UP);
		return bd1.toString();
	}

	/**
	 * 连接列表值 通过逗号分隔
	 * 
	 * @param list
	 * @return
	 */
	public static String getBridgingStr(List<String> list) {
		StringBuilder builder = new StringBuilder("");
		if (list != null && !list.isEmpty()) {
			for (String str : list) {
				builder.append(str + ",");
			}
		}
		if (builder.length() > 0) {
			return builder.substring(0, builder.lastIndexOf(","));
		} else {
			return "";
		}
	}

	/**
	 * 连接列表值 通过逗号分隔
	 * 
	 * @param list
	 * @return
	 */
	public static String getBridgingStrObj(List<Object> list) {
		StringBuilder builder = new StringBuilder("");
		if (list != null && !list.isEmpty()) {
			for (Object str : list) {
				builder.append(str + ",");
			}
			builder.substring(0, builder.lastIndexOf(","));
		}
		return builder.toString();
	}

	/**
	 * 根据传入的格式,返回日期字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateString(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 获取当年第一天日期
	 * 
	 * @return Date
	 */
	public static Date getCurrYearFirst() {
		int year = Integer.parseInt(getDateString(new Date(), "yyyy"));
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return getDate(getDateString(currYearFirst, "yyyy-MM-dd"));
	}

	/**
	 * 获取当年最后一天日期
	 * 
	 * @return Date
	 */
	public static Date getCurrYearLast() {
		int year = Integer.parseInt(getDateString(new Date(), "yyyy"));
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return getDate(getDateString(currYearLast, "yyyy-MM-dd"));
	}

	/*
	 * 将字符串格式yyyy-MM-dd HH:mm:ss转让日期类型
	 * @param dateStr
	 * @return
	 */
	public static Date getDateAll(String dateStr) {
		Date ddd = new Date();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// formatter.applyPattern("yyyy-MM-dd");
			ddd = formatter.parse(dateStr);
		} catch (Exception e) {
		}
		return ddd;
	}

	/**
	 * 将字符串格式yyyy-MM-dd转让日期类型
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDate(String dateStr) {
		Date ddd = new Date();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			// formatter.applyPattern("yyyy-MM-dd");
			ddd = formatter.parse(dateStr);
		} catch (Exception e) {
		}
		return ddd;
	}

	/**
	 * 将字符串格式yyyyMMdd转让日期类型
	 * 
	 * @return
	 */
	public static String getDateyyyyMMddHHmmss(Date date) {
		String str = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			str = formatter.format(date);
		} catch (Exception e) {
		}
		return str;
	}
	
	/**
	 * 将字符串格式yyyyMMdd转让日期类型
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDateyyyyMMdd(String dateStr) {
		Date ddd = new Date();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			ddd = formatter.parse(dateStr);
		} catch (Exception e) {
		}
		return ddd;
	}
	
	public static Date toDate(String dataStr) {
		return (isBlank(dataStr)) ? null : getDateyyyyMMdd(dataStr);
	}

	/**
	 * 将日期类型转字符串格式yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDateFormate(Date date) {
		String str = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			str = formatter.format(date);
		} catch (Exception e) {
		}
		return str;
	}

	/**
	 * 计算未来年
	 * 
	 * @param oldDate 传入时间
	 * @return
	 */
	public static Date getNewDateByDay(Date oldDate, int days) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(oldDate);
		calender.add(Calendar.DATE, days);
		return calender.getTime();
	}
	
	
	/**
	 * 计算未来年
	 * 
	 * @param oldDate 传入时间
	 * @param years 年
	 * @return
	 */
	public static Date getNewDate(Date oldDate, long years) {
		int moth = (int) (years * 12);
		return getNewDateByMoth(oldDate, moth);

	}

	/**
	 * 计算未来年
	 * 
	 * @param oldDate 传入时间
	 * @return
	 */
	public static Date getNewDateByMoth(Date oldDate, int moth) {
		/*
		 * long afterTime = (oldDate.getTime() / 1000) + 60 * 60 * 24 * 30 * moth; Date temp = new Date(); temp.setTime(afterTime * 1000); return temp;
		 */
		Calendar calender = Calendar.getInstance();
		calender.setTime(oldDate);
		calender.add(Calendar.MONTH, moth);
		return calender.getTime();
	}

	/**
	 * 计算过去年
	 * 
	 * @return
	 */
	public static Date getOldDateByMoth(Date newDate, int moth) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(newDate);
		calender.add(Calendar.MONTH, -moth);
		return calender.getTime();

	}

	/**
	 * 计算过去年
	 * 
	 * @return
	 */
	public static Date getOldDateByDay(Date newDate, int day) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(newDate);
		calender.add(Calendar.DAY_OF_YEAR, -day);
		return calender.getTime();

	}
	/**
	 * 计算过去年
	 * 
	 * @return
	 */
	public static String getOldDateByDayToStr(Date newDate, int day) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(newDate);
		calender.add(Calendar.DAY_OF_YEAR, -day);
		return getDateString(calender.getTime(), "yyyyMMdd");

	}
	
	
	/**
	 * 获取某天的零点时间
	 * 
	 * @return
	 */
	public static Date getDateInit(Date newDate) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(newDate);
		calender.set(Calendar.HOUR_OF_DAY,0);
		calender.set(Calendar.MINUTE,0);
		calender.set(Calendar.SECOND,0);
		calender.set(Calendar.MILLISECOND,0);
		return calender.getTime();

	}
	
	/**
	 * 获取某天的零点前一秒时间
	 * 
	 * @return
	 */
	public static Date getDateEnd(Date newDate) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(newDate);
		calender.set(Calendar.HOUR_OF_DAY,23);
		calender.set(Calendar.MINUTE,59);
		calender.set(Calendar.SECOND,59);
		calender.set(Calendar.MILLISECOND,0);
		return calender.getTime();

	}
	/**
	 * 获取两日期之间的年差 注：存在偏差，未考慮閏年
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long getYears(Date start, Date end) {
		Long value = end.getTime() - start.getTime();
		if (value > 0) {
			double value1 = value;
			double del = 1000 * 3600 * 24 * 365l;
			Double year = value1 / del;
			return year.longValue();
			// Double year = value1 / del;
			// year = getScale(year.toString(), 0);
			// BigDecimal bd = new BigDecimal(year);
			// if (year < 1) {
			// return 1l;
			// } else {
			// return bd.longValue();
			// }
		} else {
			return 0l;
		}
	}
	
	/**
	 * 获取两日期之间的日期差
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Integer getDays(Date start, Date end) {
		Long value = end.getTime() - start.getTime();
		if (value > 0) {
			double value1 = value;
			double del = 1000 * 3600 * 24 ;
			Double day = value1 / del;
			 
			BigDecimal result= new BigDecimal(day).setScale(0, BigDecimal.ROUND_DOWN);
			return result.intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 获取两日期之间的日期差
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Integer getDays(String start, String end) {
		Date starDate = SimpleUtil.getDateyyyyMMdd(start);
		Date endDate = SimpleUtil.getDateyyyyMMdd(end);
		Long value = endDate.getTime() - starDate.getTime();
		if (value > 0) {
			double value1 = value;
			double del = 1000 * 60*60 * 24 ;
			Double day = value1 / del;
			 
			BigDecimal result= new BigDecimal(day).setScale(0, BigDecimal.ROUND_DOWN);
			return result.intValue();
		} else {
			return 0;
		}
	}
	/**
	 * 
	 * @Title: transToAge
	 * @Description: 获取指定日期到当前日期的年数，保留一位小数
	 * @return
	 * @throws Exception
	 * @return int 返回类型
	 * @throws
	 * @author chuangkun.chen
	 */
	public static String transToYears(Date startDate) throws Exception {
		String	 age = "0";
		Date currDate = SimpleUtil.getCurrentDateyyyyMMdd(); // 获取当前日期
		long leapYear = (long) getLeapYear(startDate, currDate);// 其实会自动转成int
		if (startDate.getTime() <= currDate.getTime()) { // 以来此 Date // 对象表示的毫秒数
			// 365L表示长整型
			double l= currDate.getTime() - startDate.getTime();
			double y = ((l / (24 * 60 * 60 * 1000) - leapYear) / 365);
			BigDecimal Result= new BigDecimal(y).setScale(1, BigDecimal.ROUND_DOWN);
			age = Result.toString();
		}
		return age+"";
	}

	/**
	 * 
	 * @Title: getLeapYear
	 * @Description: 计算某人的生日到当前一共有几个闰年，此方法是为了更精确考虑闰年，从而计算出生日。
	 * @param sDate
	 * @param eDate
	 * @return
	 * @return int 返回类型
	 * @throws
	 * @author chuangkun.chen
	 */
	public static int getLeapYear(Date sDate, Date eDate) {
		int leapYear = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy");
		int birthYear = Integer.parseInt(myFormat.format(sDate)); // 获取出生日期，解析为Date类型
		int currYear = Integer.parseInt(myFormat.format(eDate)); // 获取当前日期
		for (int year = birthYear; year <= currYear; year++) {
			if (isLeapYear(year)) {
				leapYear++;// 从出生年到当前年，只有是闰年就+1
			}
		}
		return leapYear;
	}

	/**
	 * 
	 * @Title: transToAge
	 * @Description: 获取指定日期到当前日期的年数，既年龄[考虑闰年的情况]
	 * @param birthDate
	 * @return
	 * @throws Exception
	 * @return int 返回类型
	 * @throws
	 * @author chuangkun.chen
	 */
	public static int transToAge(Date birthDate) throws Exception {
		int age = 0;
		Date currDate = SimpleUtil.getCurrentDateyyyyMMdd(); // 获取当前日期
		long leapYear = (long) getLeapYear(birthDate, currDate);// 其实会自动转成int
		if (birthDate.getTime() <= currDate.getTime()) { // 以来此 Date // 对象表示的毫秒数
			// 365L表示长整型
			age = (int) (((currDate.getTime() - birthDate.getTime()) / (24 * 60 * 60 * 1000) - leapYear) / 365);
		}
		return age;
	}

	/**
	 * 
	 * @Title: isLeapYear
	 * @Description: 能被4整除并且不能被100整除 或者 能被400整除的年份是闰年
	 * @param year
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author chuangkun.chen
	 */
	public static boolean isLeapYear(int year) {
		boolean flag = false;
		if ((year % 4 == 0 && year % 100 != 0) | year % 400 == 0)
			flag = true;
		return flag;
	}

	/**
	 * 获取代理服务器的ip
	 * 
	 * @return
	 */
	public static String getProxyIp(HttpServletRequest request) {
		String ip = "";
		// System.out.println(request.getServerName());
		// System.out.println(request.getLocalName());
		// System.out.println(request.getHeader("host"));
		// System.out.println(JsfContext.getRequest().getRequestURL());

		String host = request.getHeader("host");
		if (host.contains(":")) {
			host = host.split(":")[0];
		}
		if (!isBlank(host)) {
			ip = host;
		} else {
			StringBuffer http = request.getRequestURL();
			String url = "";
			if (http != null) {
				url = http.toString();
				if (url.contains("http://")) {
					String url$ = url.split("//")[1];
					ip = url$.split("/")[0];
				}
			}
		}
		return ip;

	}

	/****
	 * 获取superClassGenricType,基于superClass操作,操作Type[0]
	 * 
	 * @param clazz
	 * @return
	 * 
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/****
	 * 获取superClassGenricType,基于superClass操作
	 * 
	 * @param clazz
	 * @return
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if ((index >= params.length) || (index < 0)) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 字符串处理
	 * 
	 * @param str
	 * @return
	 */
	public static String stringToEmpty(String str) {
		String temp = "";
		if (str != null) {
			temp = str.trim();
		}
		return temp;
	}

	/**
	 * 数组转化为List（Arrays.asList不支持remove/clear方法 ）
	 * 
	 * @param <T>
	 * 
	 * @return
	 */
	public static <T> List<T> ArrayToList(T[] str) {
		List<T> list = new ArrayList<T>();
		if (str != null && str.length > 0) {
			for (T obj : str) {
				list.add(obj);
			}
		}
		return list;
	}
	
	public static String  SetToString(Set<String>set) {
		String str="";
		if(SimpleUtil.isNotEmpty(set)){
			for(String temp:set){
				str+=temp+",";
			}
		}
		
	return str;
	}
	
	

	/**
	 * 如果是起始日期，时分秒设为0 如果是终止日期，时分秒设为23:59:59
	 * 
	 * @param d
	 * @param isBegin
	 * @return
	 */
	public static Date getQueryDate(Date d, boolean isBegin) {
		Calendar c = Calendar.getInstance();
		if (isBegin) {

			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
		} else {
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
		}

		return c.getTime();
	}

	/**
	 * @Title: trim
	 * @Description: 将String类型进行trim操作
	 * @param str
	 * @return String 返回类型
	 * @throws
	 * @author Qian.Liu
	 */
	public static String trim(String str) {
		String temp = "";
		if (str != null) {
			if (!"null".equals(str) && !"NULL".equals(str)) {
				temp = str.trim();
			}
		}
		return temp;
	}

	/**
	 * @Title: equals
	 * @Description: 比较两个字符串的内容是否相同
	 * @param str1
	 * @param str2
	 * @return
	 * @return  boolean    返回类型
	 * @throws
	 * @author ross.wei
	 */
	public static boolean equals(String str1, String str2){
		return trim(str1).equals(trim(str2));
	}

	/**
	 * @Title: getCurrentDate
	 * @Description: 获取当前服务器日期字符串(yyyy-MM-dd)
	 * @return String 返回类型
	 * @throws
	 * @author Qian.Liu
	 */
	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	/**
	 * 获取当前日期 ， 格式yyyyMMdd
	 * 
	 * @return
	 */
	public static Date getCurrentDateyyyyMMdd() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			return dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取日期 
	 * 
	 * @return
	 */
	public static Date getDateByFormate(String dataStr,String formate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
		try {
			return dateFormat.parse(dataStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * @Title: objToStrTrim
	 * @Description: 将String类型进行trim操作
	 * @return String 返回类型
	 * @throws
	 * @author Qian.Liu
	 */
	public static String objToStrTrim(Object obj) {
		String temp = "";
		if (obj != null) {
			String str = String.valueOf(obj);
			temp = str.trim();
		}
		return temp;
	}

	/**
	 * @Description: 递归方法深拷贝ArrayLsit
	 * @param src
	 * @param dest
	 * @return void
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void deepCopyListB(List src, List dest) {
		for (int i = 0; i < src.size(); i++) {
			Object obj = src.get(i);
			if (obj instanceof List) {
				dest.add(new ArrayList());
				deepCopyListB((List) obj, (List) ((List) dest).get(i));
			} else {
				dest.add(obj);
			}
		}
	}

	/**
	 * @Description: 检查投资者交易经验是否满两年
	 * @param start
	 * @param end
	 * @return int
	 * @throws
	 */
	public static boolean checkInvestorsExperienceDate(Date start, Date end) {
		try {
			int bigY = Integer.valueOf(getDateFormate(end));// (yyyyMMdd)
			int smallY = Integer.valueOf(getDateFormate(start));// (yyyyMMdd)
			if (bigY - smallY < 20000) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @Description: 获取两日期之间的月数
	 * @param start
	 * @param end
	 * @return months
	 */
	public static int getMonthNums(Date start, Date end){
		int month_num = 0;
		SimpleDateFormat format_Y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MM");
		SimpleDateFormat format_D = new SimpleDateFormat("dd");
		int start_year = Integer.parseInt(format_Y.format(start));
		int start_month = Integer.parseInt(format_M.format(start));
		int start_day = Integer.parseInt(format_D.format(start));
		int end_year = Integer.parseInt(format_Y.format(end));
		int end_month = Integer.parseInt(format_M.format(end));
		int end_day = Integer.parseInt(format_D.format(end));
		
		month_num = (end_year-start_year)*12 + (end_month-start_month);
		
		//开始日期天数大于结束日期，月数减1
		if(start_day>end_day){
			month_num += -1;
		}
		
		return month_num;
	}

	/**
	 * @Title: trimMap
	 * @Description: 将Map中的String类型进行trim操作
	 * @param map
	 * @return
	 * @return Map<String,Object> 返回类型
	 * @throws
	 * @author zongwei.pan
	 */
	public static Map<String, Object> trimMap(Map<String, Object> map) {
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() instanceof String) {
				map.put(entry.getKey(), trim(entry.getValue().toString()));
			} else if (SimpleUtil.isNull(entry.getValue())) {
				map.put(entry.getKey(), "");
			}

		}
		return map;
	}

	/**
	 * @Title: cast
	 * @Description: 强制类型转换，把Object类型转换成特定的类型
	 * @param @param o
	 * @param @return 设定文件
	 * @return T 返回类型
	 * @throws
	 * @author Ross.Wei
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object o) {
		return (T) o;
	}

	/**
	 * @Title: nullToEmpty
	 * @Description: 将空指针的null值或者"null"值转换成空的字符串
	 * @param @param src
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @author Ross.Wei
	 */
	public static String nullToEmpty(String src) {
		if (src == null || "null".equalsIgnoreCase(src)) {
			return "";
		} else {
			return src;
		}
	}

	/**
	 * @Title: getAge
	 * @Description: 自动计算年龄 精确到日
	 * @param birthDate
	 * @return
	 * @return int 返回类型
	 * @throws
	 * @author Cheng.Zhong
	 */
	public static int getAge(Date birthDate) {
		int age = 0;
		if (birthDate == null) {
			return age;
		}
		Date now = new Date();
		SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MMdd");
		String birth_year = format_y.format(birthDate);
		String this_year = format_y.format(now);
		String birth_month = format_M.format(birthDate);
		String this_month = format_M.format(now);
		// 初步，估算
		age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);
		// 如果未到出生月份，则age - 1
		if (this_month.compareTo(birth_month) < 0) {
			age -= 1;
		}
		if (age < 0) {
			age = 0;
		}
		return age;

	}

	/**
	 * @Title: judgeAge
	 * @Description: 判断年龄大小
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Cheng.Zhong
	 */
	public static boolean judgeAge(Date birthDate, int ageLine) {
		String nowStr = SimpleUtil.getDateFormate(new Date());
		String birthdayStr = SimpleUtil.getDateFormate(birthDate);
		int old = Integer.parseInt(nowStr) - Integer.parseInt(birthdayStr);
		ageLine = ageLine * 10000;
		if (old > ageLine) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Title: underToTheAgeOf18
	 * @Description:
	 * @param birthDate
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author qian.liu
	 */
	public static boolean underToTheAgeOf18(Date birthDate) {
		long custAge = getAge(birthDate);
		if (custAge < 18) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Title: getBankAccOpenRstMsgFormat
	 * @Description: 根据传入的银行代码，银行账号进行信息组装
	 * @return String 返回类型
	 * @throws
	 * @author qian.liu
	 */
	public static String getBankAccOpenRstMsgFormat(String bankCode, String bankName, String bankAcct) {
		StringBuilder sbMsg = new StringBuilder();

		if (isNotBlank(bankName)) {
			sbMsg.append("银行账户：" + trim(bankName));
			sbMsg.append(",");
		}

		if (isNotBlank(bankCode)) {
			sbMsg.append("银行代码：" + trim(bankCode));
			sbMsg.append(",");
		}

		if (isNotBlank(bankAcct)) {
			sbMsg.append("银行账号【" + trim(bankAcct) + "】,");
		}

		if (sbMsg.length() > 0) {
			return sbMsg.substring(0, sbMsg.lastIndexOf(","));
		}

		return sbMsg.toString();
	}
	
	/**
	 * @Title: getBankAccOpenRstMsgFormat
	 * @Description: 根据传入的银行代码，银行账号进行信息组装
	 * @return String 返回类型
	 * @throws
	 * @author qian.liu
	 */
	public static String getBankAccOpenRstMsgFormat(String fullName, String name, String certType, String certNo) {
		StringBuilder sbMsg = new StringBuilder();

		if (isNotBlank(fullName)) {
			sbMsg.append("客户全名：" + trim(fullName));
			sbMsg.append(",");
		}

		if (isNotBlank(name)) {
			sbMsg.append("客户名称：" + trim(name));
			sbMsg.append(",");
		}

		if (isNotBlank(certType)) {
			sbMsg.append("证件类型：" + trim(certType));
		}
		
		if (isNotBlank(certNo)) {
			sbMsg.append("证件号码：" + trim(certNo));
		}


		return sbMsg.toString();
	}

	/**
	 * @Description: String类型毫秒数转换成日期
	 * 
	 * @param lo 毫秒数
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static Date stringToDate(String lo) {
		long time = Long.parseLong(lo);
		Date date = new Date(time);
		return date;
	}
	/**yyyyMMdd HH:mm:ss
	 * @return
	 */
	public static Date stringToAllDate(String dataStr) {
		SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date date = new Date();
		try {
			date = formate.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		return date;
	}

	/**
	 * @Title: doubleToString
	 * @Description:将Double转成字符串
	 * @param num
	 * @return String 返回类型
	 * @throws
	 * @author qian.liu
	 */
	public static String doubleToString(Double num) {
		if (num != null) {
			DecimalFormat format = new DecimalFormat("#.00");
			String snum = format.format(num);
			return snum;
		} else {
			return "0";
		}
	}


	public static void closeSilently(Closeable io) {
		try {
			if (isNotNull(io)) {
				io.close();
			}
		} catch (Exception e) {
			// do nothing
		}

	}

	/**
	 * BO的属性和值以字符串形式转换
	 * 
	 * @param obj
	 * @return field1=value1,field2=value2...
	 */
	public static String toString(Serializable obj) {
		String ret = "";
		String comma = ",";
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String name = field.getName();
				if (!"serialVersionUID".equals(name)) {
					String value = (String) field.get(obj);
					if (SimpleUtil.isNotBlank(value)) {
						ret += comma + name + "=" + value;
					}
				}
			}
		} catch (IllegalAccessException e) {
			ret = "";
			e.printStackTrace();
		}

		if ("".equals(ret)) {
			return ret;
		} else {
			return ret.substring(1);
		}
	}

	/**
	 * 字符串形式表示的属性和值转换成Object
	 * 
	 * @param fieldsValues field1=value1,field2=value2...
	 * @param clazz 需要转换成的Object的类型
	 * @return
	 */
	public static <T> T toObj(String fieldsValues, Class<T> clazz) {
		try {
			T bo = clazz.newInstance();

			if (SimpleUtil.isNotBlank(fieldsValues)) {
				Map<String, String> fieldValueMap = new HashMap<String, String>();

				for (String fieldAndValueStr : fieldsValues.split(",")) {
					String[] fieldAndValueArr = fieldAndValueStr.split("=");
					String fieldName = fieldAndValueArr[0];
					String value = fieldAndValueArr[1];
					fieldValueMap.put(fieldName, value);
				}

				Field[] fields = bo.getClass().getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					String fieldName = field.getName();
					String value = fieldValueMap.get(fieldName);
					if (SimpleUtil.isNotNull(value)) {
						field.set(bo, value);
					}

				}
			}

			return bo;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * 把Map的键值以字符串形式转换
	 * 
	 * @param map
	 * @return field1=value1,field2=value2...
	 */
	public static String toString(Map<String, Object> map) {
		String ret = "";
		String comma = ",";

		try {
			if (map != null && !map.isEmpty()) {
				for (Entry<String, Object> entry : map.entrySet()) {
					String value = (String) entry.getValue();
					if (SimpleUtil.isNotBlank(value)) {
						ret += comma + entry.getKey() + "=" + value;
					}

				}
			}
		} catch (Exception e) {
			ret = "";
			e.printStackTrace();
		}

		if ("".equals(ret)) {
			return ret;
		} else {
			return ret.substring(1);
		}
	}

	/**
	 * 把字符串形式表示的键值转换成Map
	 * 
	 * @param keysValues field1=value1,field2=value2...
	 * @return
	 */
	public static Map<String, Object> toMap(String keysValues) {
		Map<String, Object> keyValueMap = new HashMap<String, Object>();

		if (SimpleUtil.isNotBlank(keysValues)) {
			for (String keyAndValueStr : keysValues.split(",")) {
				String[] keyAndValueArr = keyAndValueStr.split("=");
				String key = keyAndValueArr[0];
				String value = keyAndValueArr[1];
				keyValueMap.put(key, value);
			}
		}

		return keyValueMap;
	}

	/**
	 * 获取结果bo的属性，组装结果参数数组
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> String[] getResultFields(Class<T> cls) {

		Field[] fileds = cls.getDeclaredFields();
		List<String> list = new ArrayList<String>();
		for (Field ff : fileds) {
			if (!"serialVersionUID".equals(ff.getName())) {
				list.add(ff.getName());
			}
		}
		String[] resultFields = list.toArray(new String[0]);
		return resultFields;
	}
	
	/**判断当前时间是否在传入时间段内
	 * @return
	 */
	public static boolean isTradeTime(String timeStr){
		boolean flag = false;
		if(SimpleUtil.isNotBlank(timeStr)){
		try {
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			// 格式化当前时间
			String currentTime = df.format(new Date());
			String timeArray[] = timeStr.split(",");
			for(String time:timeArray){
				if(SimpleUtil.isNotBlank(time)){
				String[] times = time.split("-");
				// times[0]<当前时间< times[1],返回true
				if (df.parse(currentTime).compareTo(df.parse(times[0])) >=0 && df.parse(currentTime).compareTo(df.parse(times[1])) <= 0) {
					flag = true;
					break;
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return flag;
	}
}
