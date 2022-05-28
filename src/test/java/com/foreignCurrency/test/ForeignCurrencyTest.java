package com.foreignCurrency.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.foreignCurrency.AppConfig;
import com.foreignCurrency.entity.ForeignCurrency;
import com.foreignCurrency.service.ForeignCurrencyService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForeignCurrencyTest {

	@Autowired
	private ForeignCurrencyService foreignCurrencyService;

	@Test
	public void bFindByCurrency() {
		System.out.println("1. 測試呼叫查詢幣別對應表資料 API，並顯示其內容。");
//		1. 測試呼叫查詢幣別對應表資料 API，並顯示其內容。
		ForeignCurrency foreignCurrency = foreignCurrencyService.findByCurrency("TWD");
		if (foreignCurrency != null) {
			System.out.println("查詢成功");
			System.out.println(foreignCurrency.toString());
		} else {
			System.out.println("沒有此人");
		}
	}

	@Test
	public void aInsert() {
		System.out.println("2. 測試呼叫新增幣別對應表資料 API。");
//		2. 測試呼叫新增幣別對應表資料 API。
		ForeignCurrency fc = new ForeignCurrency();
		fc.setCurrency("TWD");
		fc.setCurrencyChinese("台幣");
		fc.setExchangeRate(Float.valueOf(1000));
		fc.setUpdateDate(new Timestamp(new Date().getTime()));
		fc = foreignCurrencyService.insertForeignCurrency(fc);
		if (fc != null) {
			System.out.println("新增成功");
		} else {
			System.out.println("新增失敗");
		}
	}

//

	@Test
	public void cUpdateForeignCurrency() {
		System.out.println("3. 測試呼叫更新幣別對應表資料 API，並顯示其內容。");
//	3. 測試呼叫更新幣別對應表資料 API，並顯示其內容。
		ForeignCurrency fc = foreignCurrencyService.findByCurrency("TWD");
		fc.setExchangeRate(Float.valueOf(2000));
		fc.setUpdateDate(new Timestamp(new Date().getTime()));
		foreignCurrencyService.insertForeignCurrency(fc);
		System.out.println("更新成功");
//		bFindByCurrency();
	}

	@Test
	public void dDelete() {
		System.out.println("4. 測試呼叫刪除幣別對應表資料 API。");
//		4. 測試呼叫刪除幣別對應表資料 API。
		foreignCurrencyService.deleteByCurrency("TWD");
		System.out.println("刪除成功");
	}

	@Test
	public void eloadJSON() {
		System.out.println("5. 測試呼叫 coindesk API，並顯示其內容。");
//	5. 測試呼叫 coindesk API，並顯示其內容。
		String data = foreignCurrencyService.loadJSON(null);
		System.out.println(data);
	}

	@Test
	public void fDataConvert() {
		System.out.println("6. 測試呼叫資料轉換的 API，並顯示其內容");
//	6. 測試呼叫資料轉換的 API，並顯示其內容
		foreignCurrencyService.updateCoindesk();

		List<ForeignCurrency> list = foreignCurrencyService.queryAll();
		for (int i = 0; i < list.size(); i++) {
			ForeignCurrency foreignCurrency = (ForeignCurrency) list.get(i);
			System.out.println(foreignCurrency);
		}
	}

}
