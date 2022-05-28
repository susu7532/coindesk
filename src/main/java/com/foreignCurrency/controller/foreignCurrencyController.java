package com.foreignCurrency.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foreignCurrency.entity.ForeignCurrency;
import com.foreignCurrency.service.ForeignCurrencyService;

@RestController
public class foreignCurrencyController {

	@Autowired
	private ForeignCurrencyService foreignCurrencyService;


	@RequestMapping("/updateCoindesk")
	public String init22() {
		foreignCurrencyService.updateCoindesk();
		return null;
	}

	@RequestMapping(value = "/queryById")
	public ForeignCurrency queryById() {
		ForeignCurrency fc = foreignCurrencyService.queryById(Long.valueOf("1"));
		return fc;
	}

	@RequestMapping(value = "/list")
	public List querylist() {
		List list = foreignCurrencyService.queryAll();
		return list;
	}

	@RequestMapping(value = "/update")
	public String update() {
		ForeignCurrency fc = foreignCurrencyService.findByCurrency("TWD");
		fc.setExchangeRate(Float.valueOf(2000));
		fc.setUpdateDate(new Timestamp(new Date().getTime()));
		foreignCurrencyService.insertForeignCurrency(fc);
		System.out.println("更新成功");
		
		return "saveAndUpdate";
	}

	@RequestMapping(value = "/delete")
	public String delete() {
		foreignCurrencyService.deleteForeignCurrencyById(Long.valueOf("1"));
		return "delete";
	}

}
