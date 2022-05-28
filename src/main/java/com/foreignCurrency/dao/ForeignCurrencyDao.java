package com.foreignCurrency.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.foreignCurrency.entity.ForeignCurrency;

@Repository
public interface ForeignCurrencyDao extends JpaRepository<ForeignCurrency, Long> {

	@Query("select f from ForeignCurrency f where f.currency = ?1")
	public ForeignCurrency findByCurrency(String currency);

}
