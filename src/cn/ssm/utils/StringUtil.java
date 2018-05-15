package cn.ssm.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;


public class StringUtil {
	
	
	public static String substring(String str, int toCount, String more) {
		int reInt = 0;
		String reStr = "";
		if (str == null)
			return "";
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			reInt += b.length;
			reStr += tempChar[kk];
		}
		if (toCount == reInt || (toCount == reInt - 1))
			reStr += more;
		return reStr;
	}
	public static String int2String(int num, int length){
		String str = "";
		str = String.valueOf(num);
		for(int i = str.length(); i < length ;i++){
			str = "0" + str;
		}
		
		return str;
		
	}
	
	public static String int2String(int num){
		String str = "00";
		if(num>99){
			str = "99";
		}else if(num<10){
			str = "0" + String.valueOf(num);
		}else{
			str = String.valueOf(num);
		}
		return str;
	}

	public static String getLastDayOfMonth(String time) {  
		String year = time.substring(0,4);
		String month = time.substring(5);
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, Integer.valueOf(year));     
        cal.set(Calendar.MONTH, Integer.valueOf(month)-1);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
       return  new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
    }
	
	
	public static String toString(String[] string){
		String str = "";
		if(string!=null && string.length>0 ){
			for(String temp : string){				
				str += temp +","; 
			}
			str = str.substring(0,str.length()-1);
		}
		return str;
	}
	/**
	 * 
	 * @param sourceString
	 *            eg:&#28023;&#27915;&#28065;&#26059;&#31995;&#21015;&#35762;&#
	 *            24231;11-12&#26149;&#23395;
	 * @param maxLength
	 *            最大长度，一个中文占一个单位长度
	 * @param more
	 *            如果超过所截取的长度，字符串最后要加上的省略符。
	 * @return
	 */
	public static String limitForHtml(String sourceString, int maxLength, String more) {
		String str = org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4(sourceString);
		return limit(str, maxLength, more);
	}

	/**
	 * 
	 * @param sourceString
	 *            eg:测试字符串
	 * @param maxLength
	 *            最大长度，一个中文占一个单位长度
	 * @param more
	 *            如果超过所截取的长度，字符串最后要加上的省略符。
	 * @return
	 */
	public static String limit(String sourceString, int maxLength, String more) {
		String resultString = "";
		if (sourceString == null || sourceString.equals("") || maxLength < 1) {
			return resultString;
		} else if (sourceString.length() <= maxLength) {
			return sourceString;
		} else if (sourceString.length() > 2 * maxLength) {
			// sourceString = sourceString.substring(0, 2 * maxLength);
		}

		if (sourceString.length() > maxLength) {
			char[] chr = sourceString.toCharArray();
			int strNum = 0;
			int strGBKNum = 0;
			boolean isHaveDot = false;

			for (int i = 0; i < sourceString.length(); i++) {
				if (chr[i] >= 0xa1) // 0xa1汉字最小位开始
				{
					strNum = strNum + 2;
					strGBKNum++;
				} else {
					strNum++;
				}

				if (strNum == 2 * maxLength || strNum == 2 * maxLength + 1) {
					if (i + 1 < sourceString.length()) {
						isHaveDot = true;
					}

					break;
				}
			}

			resultString = sourceString.substring(0, strNum - strGBKNum);
			if (isHaveDot) {
				resultString = resultString + more;
			}
		}
		return resultString;
	}

	/**
	 * 格式化时间
	 * 
	 * @param milliSecond
	 *            毫秒
	 * @return
	 */
	public static String timeToString(long milliSecond) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		long day = hh * 24;
		long month = day * 30;
		long year = month * 12;

		long years = (milliSecond) / year;
		long months = (long) ((milliSecond - years * year) / month);
		long days = (milliSecond - years * year - months * month) / day;
		long hour = (long) ((milliSecond - years * year - months * month - days * day) / hh);
		long minute = (int) ((milliSecond - years * year - months * month - days * day - hour * hh) / mi);
		long second = (int) ((milliSecond - years * year - months * month - days * day - hour * hh - minute * mi) / ss);
		String result = "";
		if (years != 0) {
			result += years + "年前";
		} else if (months != 0) {
			result += months + "月前";
		} else if (days != 0) {
			result += days + "天前";
		} else if (hour != 0) {
			result += hour + "小时前";
		} else if (minute != 0) {
			result += minute + "分钟前";
		} else if (second != 0) {
			result += second + "秒前";
		}
		return result;
	}

	/**
	 * 格式化时间
	 * 
	 * @param milliSecond
	 *            毫秒
	 * @return
	 */
	public static String timeToString2(long milliSecond) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		long day = hh * 24;
		long month = day * 30;
		long year = month * 12;

		long years = (milliSecond) / year;
		long months = (long) ((milliSecond - years * year) / month);
		long days = (milliSecond - years * year - months * month) / day;
		long hour = (long) ((milliSecond - years * year - months * month - days * day) / hh);
		long minute = (int) ((milliSecond - years * year - months * month - days * day - hour * hh) / mi);
		long second = (long) ((milliSecond - years * year - months * month - days * day - hour * hh - minute * mi) / ss);
		long ms = milliSecond - years * year - months * month - days * day - hour * hh - minute * mi - second * ss;
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		String result = "";
		if (years != 0) {
			result += years + "年";
		} else if (months != 0) {
			result += months + "月";
		} else if (days != 0) {
			result += days + "天";
		} else if (hour != 0) {
			result += hour + "小时";
		} else if (minute != 0) {
			result += minute + "分钟";
		} else if (second != 0) {
			// result += second + "秒";
			result += nf.format(second + (ms / 1000.0)) + "秒";
		}
		return result;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString3(Date date) {
		if (date == null)
			return "无";
		long milliSecond = System.currentTimeMillis() - date.getTime();
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		long day = hh * 24;
		long month = day * 30;
		long year = month * 12;

		long years = (milliSecond) / year;
		long months = (long) ((milliSecond - years * year) / month);
		long days = (milliSecond - years * year - months * month) / day;
		long hour = (long) ((milliSecond - years * year - months * month - days * day) / hh);
		long minute = (int) ((milliSecond - years * year - months * month - days * day - hour * hh) / mi);
		long second = (int) ((milliSecond - years * year - months * month - days * day - hour * hh - minute * mi) / ss);
		String result = "";
		if (years != 0) {
			result += years + "年前";
		} else if (months != 0) {
			result += months + "月前";
		} else if (days != 0) {
			result += days + "天前";
		} else if (hour != 0) {
			result += hour + "小时前";
		} else if (minute != 0) {
			result += minute + "分钟前";
		} else if (second != 0) {
			result += second + "秒前";
		}
		return result;
	}

	/**
	 * 判断字符串是否为NULL或空字符串
	 * 
	 * @param obj
	 *            被判断的字符串
	 * @return 为空返回true，否则返回 false
	 */
	public static boolean isNullOrEmpty(String obj) {
		if (obj == null)
			return true;
		if ("".equals(obj.trim()))
			return true;
		if ("null".equals(obj.trim()))
			return true;
		if ("NULL".equals(obj.trim()))
			return true;
		return false;
	}

	public static boolean isNotNullOrEmpty(String obj) {
		return !isNullOrEmpty(obj);
	}

	public static boolean isNullOrEmpty(List<?> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotNullOrEmpty(List<?> list) {
		return !isNullOrEmpty(list);
	}

	/**
	 * 将字符串转化为日期
	 * 
	 * @param dateStr
	 *            源字符串
	 * @param style
	 *            格式化串
	 * @return 返回Date
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr, String style) {
		if (isNullOrEmpty(dateStr)) {
			return null;
		}
		SimpleDateFormat sf = new SimpleDateFormat(style);
		Date date;
		try {
			date = sf.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将日期格式化为字符串
	 * 
	 * @param date
	 *            日期
	 * @param style
	 *            格式化形式
	 * @return
	 */
	public static String formatDate(Date date, String style) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sf = new SimpleDateFormat(style);
		String str = sf.format(date);
		return str;
	}

	public static Long toLong(String data) {
		Long _data = null;
		if (data != null && !"".equals(data) && !"null".equals(data)) {
			_data = Long.parseLong(data);
		}
		return _data;
	}

	public static Integer toInteger(String data) {
		Integer _data = null;
		if (data != null && !"".equals(data) && !"null".equals(data) && isNumeric(data)) {
			_data = Integer.parseInt(data);
		}
		return _data;
	}

	public static String toString(String _data) {
		String data = _data;
		if (data != null) {
			data = data.trim();
		}
		if ("".equals(data) || "null".equals(data)) {
			return null;
		}
		return data;
	}

	public static Double toDouble(String data) {
		Double _data = null;
		if (data != null && !"".equals(data) && !"null".equals(data)) {
			try {
				_data = Double.parseDouble(data);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return _data;
	}

	public static String replaceContent(HttpServletRequest request, String src) {
		String result = src;
		if (isNullOrEmpty(result)) {
			return result;
		}
		Pattern pattern = Pattern.compile("<img.*?src *= *['\"]?([^>]+)['\"]?.*?>", Pattern.CASE_INSENSITIVE);
		Pattern pattern2 = Pattern.compile("src *= *['\"]?([^>]+)['\"]?", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(src);

		String domain = request.getScheme() + "://" + request.getHeader("host") + request.getContextPath();

		while (matcher.find()) {
			String tt = matcher.group();
			Matcher matcher2 = pattern2.matcher(tt);
			if (matcher2.find()) {
				String image = matcher2.group();
				String image2 = image.replace(domain, "\\${pageContext.request.contextPath}");
				String tt2 = matcher2.replaceAll(image2);
				result = result.replace(tt, tt2);
			}

		}
		return result;
	}

	public static int getCurrentYear() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR);
	}

	public static String getFileSizeString(Long _size) {
		String size = "";
		long size_long = Math.abs(_size == null ? 0 : _size.longValue());
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(2);
		if (size_long > 700000000L) {
			String[] args = { formatter.format(1.0 * size_long / (1024L * 1024L * 1024L)) };
			for (int _a = 0; _a < args.length; _a++) {
				size += args[_a];
			}
			size += " GB";
		} else if (size_long > 700000L) {
			String[] args = { formatter.format(1.0 * size_long / (1024L * 1024L)) };
			for (int _a = 0; _a < args.length; _a++) {
				size += args[_a];
			}
			size += " MB";
		} else if (size_long > 700L) {
			String[] args = { formatter.format(1.0 * size_long / 1024L) };
			for (int _a = 0; _a < args.length; _a++) {
				size += args[_a];
			}
			size += " KB";
		} else {
			String[] args = { formatter.format(size_long) };
			for (int _a = 0; _a < args.length; _a++) {
				size += args[_a];
			}
			size += " Bytes";
		}
		if (_size < 0) {
			size = "-" + size;
		}
		return size;
	}

	public static String convertToCNNumber(Integer num) {
		if (num == null)
			return null;

		String[] cn_names = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

		int v = num.intValue();
		StringBuilder str = new StringBuilder();

		String tmp = "" + v;
		for (int i = 0; i < tmp.length(); i++) {
			char c = tmp.charAt(i);
			int tt = Integer.parseInt("" + c);
			str.append(cn_names[tt]);

		}

		if (str.length() > 0) {
			return str.toString();
		} else {
			return null;
		}
	}

	public static boolean isHttpUrl(String str) {
		if (StringUtil.isNullOrEmpty(str)) {
			return false;
		}
		String patternstr = "^http://.*";
		Pattern pattern = Pattern.compile(patternstr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		if (matcher != null && matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean startsWith(String src, String start) {
		String patternstr = "^" + start;
		Pattern pattern = Pattern.compile(patternstr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(src);
		if (matcher != null && matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean endsWith(String src, String end) {
		String patternstr = end + "$";
		Pattern pattern = Pattern.compile(patternstr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(src);
		if (matcher != null && matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static String fillUrl(String str, String contextPath) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		if (!isHttpUrl(str)) {
			return contextPath + str;
		}
		return str;
	}

	public static void main2(String[] args) {
		double[] dds = getAround(39.9050402, 116.2163832, 1000);
		System.out.println("" + dds[0] + "," + dds[1] + "   " + dds[2] + "," + dds[3]);
	}

	/**
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            经度
	 * @param raidus
	 *            半径，单位米
	 * @return {最小纬度,最小经度,最大纬度,最大经度}
	 */
	public static double[] getAround(double lat, double lon, int raidus) {

		Double latitude = lat;
		Double longitude = lon;

		Double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		return new double[] { minLat, minLng, maxLat, maxLng };
	}

	public static double[] getAround2(double lat, double lon, int raidus) {

		Double latitude = lat;
		Double longitude = lon;

		Double degree = 69.17032342863616d;
		double raidusMile = raidus;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		return new double[] { minLat, minLng, maxLat, maxLng };
	}

	public static byte[] compress(String str) {
		if (StringUtil.isNullOrEmpty(str)) {
			return null;
		}
		byte[] compressed = compress(str.getBytes());
		return compressed;
	}

	public static byte[] compress(byte[] src) {
		if (src == null || src.length == 0) {
			return null;
		}
		byte[] compressed = null;
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			GZIPOutputStream zOut = new GZIPOutputStream(b);
			zOut.write(src);
			zOut.flush();
			zOut.close();
			b.close();
			compressed = b.toByteArray();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compressed;
	}

	public static final String decompress(byte[] compressed) {
		if (compressed == null || compressed.length == 0)
			return null;
		GZIPInputStream zIn = null;
		ByteArrayOutputStream outStream = null;
		try {
			zIn = new GZIPInputStream(new ByteArrayInputStream(compressed));
			outStream = new ByteArrayOutputStream();
			byte[] buff = new byte[4096];
			int length = 0;
			while ((length = zIn.read(buff)) >= 0) {
				outStream.write(buff, 0, length);
			}
			String decompressed = new String(outStream.toByteArray());
			zIn.close();
			outStream.close();
			return decompressed;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final byte[] decompressToByte(byte[] compressed) {
		if (compressed == null || compressed.length == 0)
			return null;
		GZIPInputStream zIn = null;
		ByteArrayOutputStream outStream = null;
		try {
			zIn = new GZIPInputStream(new ByteArrayInputStream(compressed));
			outStream = new ByteArrayOutputStream();
			byte[] buff = new byte[4096];
			int length = 0;
			while ((length = zIn.read(buff)) >= 0) {
				outStream.write(buff, 0, length);
			}
			byte[] result = outStream.toByteArray();
			zIn.close();
			outStream.close();
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	 /** 
	  * 判断是不是一个合法的电子邮件地址 
	  * @param email 
	  * @return 
	  */ 
	 public static boolean isEmail(String email){ 
		 Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"); 
	     if(isNullOrEmpty(email)) return false; 
	     email = email.toLowerCase(); 
	     if(email.endsWith(".con")) return false; 
	     if(email.endsWith(".cm")) return false; 
	     
	     return emailer.matcher(email).matches(); 
	 } 
	 
	 /**
	  * 克隆字符
	  */
	 public static String cloneChar(char charater,int count){
		 if(count <1) return null;
		 StringBuffer sb = new StringBuffer();
		 for(int i=0;i<count;i++){
			 sb.append(charater);
		 }
		 return sb.toString();
	 }
	 
	 public static boolean isNumeric(String str)
     {
           Pattern pattern = Pattern.compile("[0-9]*");
           Matcher isNum = pattern.matcher(str);
           if( !isNum.matches() )
           {
                 return false;
           }
           return true;
     }
	 
	
	 

	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	
	    String ip = request.getHeader("x-forwarded-for");
	
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	
	        ip = request.getHeader("Proxy-Client-IP");
	
	    }
	
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	
	        ip = request.getHeader("WL-Proxy-Client-IP");
	
	    }
	
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	
	        ip = request.getRemoteAddr();
	
	    }
	
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	
	}
	

	
	
	public  static String replacePunctuation(String str){
		str=str.replaceAll("-","");
		str=str.replaceAll("_","");
		str=str.replaceAll("/","");
		str=str.replaceAll("\\.","");
		return str;
	}
	
	public  static String replacePunctuationforAccountCode(String str){
		str=str.replaceAll("\\.0","");
		str=str.replaceAll("-","");
		str=str.replaceAll("_","");
		str=str.replaceAll("/","");
		str=str.replaceAll("\\.","");
		return str;
	}

	public static String getParent(String str){
		if(str.lastIndexOf("-")>0){
			str=str.substring(0,str.lastIndexOf("-"));
			str=StringUtil.replacePunctuation(str);
		}else if(str.lastIndexOf("_")>0){
			str=str.substring(0,str.lastIndexOf("_"));
			str=StringUtil.replacePunctuation(str);
		}else if(str.lastIndexOf("/")>0){
			str=str.substring(0,str.lastIndexOf("/"));
			str=StringUtil.replacePunctuation(str);
		}else if(str.lastIndexOf(".")>0){
			str=str.substring(0,str.lastIndexOf("."));
			str=StringUtil.replacePunctuation(str);
		}else if(str.lastIndexOf("//.")>0){
			str=str.substring(0,str.lastIndexOf("//."));
			str=StringUtil.replacePunctuation(str);
		}else str="-1";
		return str;			
	}
	
	public static boolean compare(String v1, String v2) {
		  // v1不为空时：返回v1.equals(v2)
		  // v1为空时v2不为空：返回v2.equals(v1)
		  // v1、v2都为null时，返回true
		  return v1 != null ? v1.equals(v2) : (v2 != null ? v2.equals(v1) : true);
		 }
	
	 public static boolean compareAndNotNull(String v1, String v2) {
		  // v1不为空时：返回v1.equals(v2)
		  // v1为空时v2不为空：返回v2.equals(v1)
		  // v1、v2都为null时，返回false
		  return v1 != null ? v1.equals(v2) : (v2 != null ? v2.equals(v1) : false);
		 }
	 
	 public static String getLastDay(String month){
		 String[] strarr = month.split("-");
		 int year = Integer.parseInt(strarr[0]);
		 int months = Integer.parseInt(strarr[1]);
		 String day = getLastDayOfMonth(year, months);
		 return day;
	 }
	 
	 private static String getLastDayOfMonth(int year,int month){
	        Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,year);
	        //设置月份
	        cal.set(Calendar.MONTH, month-1);
	        //获取某月最大天数
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);
	        //格式化日期
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String lastDayOfMonth = sdf.format(cal.getTime());
	         
	        return lastDayOfMonth;
	 }
		
// 	public static void main(String[] args)  throws IOException
//	{
//		new StringUtil().getParent("2241.16.110");
// 		System.out.println(getLastDay("2016-2"));
//	}

	
}
