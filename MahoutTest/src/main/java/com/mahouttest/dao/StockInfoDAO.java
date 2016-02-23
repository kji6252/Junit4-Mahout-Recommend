package com.mahouttest.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahouttest.domain.model.StockInfo;

@Repository
public interface StockInfoDAO extends JpaRepository<StockInfo, Long> {

}
