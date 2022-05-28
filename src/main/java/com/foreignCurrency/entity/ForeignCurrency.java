package com.foreignCurrency.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Foreign_Currency")
public class ForeignCurrency implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "currency")
	private String currency;// 幣別

	@Column(name = "currencyChinese")
	private String currencyChinese;// 幣別中文名稱

	@Column(name = "exchangeRate")
	private Float exchangeRate;// 匯率

	@Column(name = "updateDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;// 更新日期

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp date) {
		this.updateDate = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyChinese() {
		return currencyChinese;
	}

	public void setCurrencyChinese(String currencyChinese) {
		this.currencyChinese = currencyChinese;
	}

	public Float getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Override
	public String toString() {
		return "ForeignCurrency [id=" + id + ", currency=" + currency + ", currencyChinese=" + currencyChinese
				+ ", exchangeRate=" + exchangeRate + ", updateDate=" + updateDate + "]";
	}
}
