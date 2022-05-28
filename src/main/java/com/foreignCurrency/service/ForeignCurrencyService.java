package com.foreignCurrency.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foreignCurrency.dao.ForeignCurrencyDao;
import com.foreignCurrency.entity.ForeignCurrency;

@Service
@Transactional
public class ForeignCurrencyService {

	@Autowired
	private ForeignCurrencyDao foreignCurrencyDao;

	// 查詢所有記錄
	public List<ForeignCurrency> queryAll() {
		List<ForeignCurrency> foreignCurrencyList = null;
		foreignCurrencyList = foreignCurrencyDao.findAll();
		return foreignCurrencyList;
	}

	// 根據Id查詢記錄
	public ForeignCurrency queryById(Long id) {
		ForeignCurrency p = foreignCurrencyDao.findById(id).orElse(null);// 其中的orElse表示如果沒有該記錄，則返回null
		return p;
	}

	// 根據currency查詢記錄
	public ForeignCurrency findByCurrency(String currency) {
		ForeignCurrency foreignCurrency = null;
		foreignCurrency = foreignCurrencyDao.findByCurrency(currency);
		return foreignCurrency;
	}

	// 新增與更新記錄
	public ForeignCurrency insertForeignCurrency(ForeignCurrency foreignCurrency) {
		return foreignCurrencyDao.save(foreignCurrency);
	}

	// 刪除記錄BYID
	public void deleteForeignCurrencyById(Long id) {
		ForeignCurrency fc = this.queryById(id);
		if (fc == null) {
			throw new RuntimeException("ForeignCurrency not found 找不到該ID");
		}
		foreignCurrencyDao.deleteById(id);
	}

	public void deleteByCurrency(String currency) {
		ForeignCurrency fc = this.findByCurrency(currency);
		if (fc == null) {
			throw new RuntimeException("ForeignCurrency not found 找不到該幣別");
		}
		foreignCurrencyDao.delete(fc);
	}

	public void updateCoindesk() {

		String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
		String json = loadJSON(url);
		JSONObject jb;
		try {

			jb = new JSONObject(json);

			Timestamp timestamp = null;
			if (jb.has("time") && !jb.isNull("time")) {
				JSONObject timeJb = jb.getJSONObject("time");
				if (timeJb.has("updated") && !timeJb.isNull("updated")) {
					Date date = null;
					SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss zzz", Locale.UK);
					formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
					try {
						date = formatter.parse(timeJb.getString("updated"));
						timestamp = new Timestamp(date.getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}

			if (jb.has("bpi") && !jb.isNull("bpi")) {
				JSONObject bpiJb = jb.getJSONObject("bpi");
				for (String str : bpiJb.keySet()) {
					ForeignCurrency foreignCurrency = null;
					String currency = bpiJb.getJSONObject(str).getString("code");
					if (findByCurrency(currency) != null) {
						foreignCurrency = findByCurrency(currency);
					} else {
						foreignCurrency = new ForeignCurrency();
					}

					foreignCurrency.setCurrency(currency);
					foreignCurrency.setExchangeRate(bpiJb.getJSONObject(str).getFloat("rate_float"));

					if ("USD".equals(bpiJb.getJSONObject(str).getString("code"))) {
						foreignCurrency.setCurrencyChinese("美元");
					} else if ("GBP".equals(bpiJb.getJSONObject(str).getString("code"))) {
						foreignCurrency.setCurrencyChinese("英鎊");
					} else if ("EUR".equals(bpiJb.getJSONObject(str).getString("code"))) {
						foreignCurrency.setCurrencyChinese("歐元");
					}

					if (timestamp != null) {
						foreignCurrency.setUpdateDate(timestamp);
					}

					this.insertForeignCurrency(foreignCurrency);
					foreignCurrency = null;
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String loadJSON(String url) {
		if (url == null || url.length() == 0) {
			url = "https://api.coindesk.com/v1/bpi/currentprice.json";
		}

		StringBuffer json = new StringBuffer();
		BufferedReader in = null;
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();

			in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "utf-8"));// 防止乱码
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return json.toString();
	}

}
