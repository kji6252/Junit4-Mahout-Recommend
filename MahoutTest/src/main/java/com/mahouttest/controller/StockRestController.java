package com.mahouttest.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mahouttest.dao.StockInfoDAO;
import com.mahouttest.dao.UserInfoDAO;
import com.mahouttest.domain.model.StockData;
import com.mahouttest.domain.model.StockInfo;
import com.mahouttest.domain.model.UserInfo;
import com.mahouttest.service.StockDataService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(produces={"application/json; charset=utf8", "text/plain"})
public class StockRestController {
	
	@Autowired
	private StockInfoDAO stockInfoDao;
	@Autowired
	private UserInfoDAO userInfoDao;
	@Autowired
	private StockDataService stockDataService;

	@RequestMapping(value="allStockInfo",method=RequestMethod.GET)
	public List<StockInfo> allStockInfo(Model model) {
		return stockInfoDao.findAll();
	}
	
	@RequestMapping(value="allUserInfo",method=RequestMethod.GET)
	public List<UserInfo> allUserInfo(Model model) {
		return userInfoDao.findAll();
	}
	
	@RequestMapping(value="stockInfo",method=RequestMethod.GET)
	//@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
	public List<StockData> stockInfo(Model model
			,@RequestParam(name="id", defaultValue="1") long stockId) throws Exception {
		return stockDataService.stockItemRecommendList(stockId);
	}
	
	@RequestMapping(value="userInfo",method=RequestMethod.GET)
	public List<StockData> userInfo(Model model
			,@RequestParam(name="id", defaultValue="1") long userId) throws Exception {
		return stockDataService.stockUserRecommendList(userId);
	}




}
