package com.hpe.shell.rtsp.integration.LandingPage.UserLangVariation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class UserLangVariation {
	private Properties prop = null;

	public String getHttpHeaders(HttpServletRequest request) {

		InputStream is = null;
		try {
			this.prop = new Properties();
			is = this.getClass().getResourceAsStream("/userlang.properties");
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, String> headermap = new HashMap<String, String>();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			headermap.put(key, value);
		}
		String usercountry = null;
		String userlang = null;

		if (headermap.get("fed_country") != null && !"".equalsIgnoreCase(headermap.get("fed_country"))) {
			usercountry = headermap.get("fed_country");
		} else if (headermap.get("country") != null && !"".equalsIgnoreCase(headermap.get("country"))) {
			usercountry = headermap.get("country");
		}

		if (headermap.get("fed_language") != null && !"".equalsIgnoreCase(headermap.get("fed_language"))) {
			userlang = headermap.get("fed_language");
		} else if (headermap.get("language") != null && !"".equalsIgnoreCase(headermap.get("language"))) {
			userlang = headermap.get("language");
		}

		String LangVariation = getUserLangVariation(usercountry, userlang);
		return LangVariation;
	}

	public String getUserLangVariation(String country, String lang) {

		String userlangvariation = null;
		String langvariation = null;

		if (country != null && !"".equalsIgnoreCase(country) && lang != null && !"".equalsIgnoreCase(country)) {
			userlangvariation = lang + "-" + country;
		} else if (country == null || country.isEmpty() || country.equals("")) {
			userlangvariation = lang;
		}

		String Std_En = prop.getProperty("Standard_English");
		String Std_Zh = prop.getProperty("Traditional_Chinese");

		System.out.println("Standard English :" + Std_En);
		System.out.println("Traditional Chinise: " + Std_Zh);

		List<String> langlist = new ArrayList<String>();

		if (userlangvariation != null && !"".equalsIgnoreCase(userlangvariation) && lang.equalsIgnoreCase("en")) {
			langlist = Arrays.asList(Std_En.split(","));
			for (String str : langlist) {
				String listlang = str;
				if (userlangvariation.equalsIgnoreCase(listlang)) {
					langvariation = "std-" + lang;
				} else {
					langvariation = "std-" + lang;
				}
			}
		} else if (userlangvariation != null && !"".equalsIgnoreCase(userlangvariation)
				&& lang.equalsIgnoreCase("zh")) {
			langlist = Arrays.asList(Std_Zh.split(","));
			for (String str : langlist) {
				String listlang = str;
				if (userlangvariation.equalsIgnoreCase(listlang)) {
					langvariation = "trd-" + lang;
				} else {
					langvariation = "trd-" + lang;
				}
			}
		} else if (userlangvariation != null && !"".equalsIgnoreCase(userlangvariation)
				&& lang.equalsIgnoreCase("fr")) {
			langvariation = prop.getProperty("Standard_French");
		} else if (userlangvariation != null && !"".equalsIgnoreCase(userlangvariation)
				&& lang.equalsIgnoreCase("th")) {
			langvariation = prop.getProperty("Traditional_Thai");
		}

		System.out.println("User Language Variation : " + userlangvariation);
		System.out.println("User Language Variation : " + lang);
		System.out.println("language variation : " + langvariation);
		return langvariation;
	}

}
