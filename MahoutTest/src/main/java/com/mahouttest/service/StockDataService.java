package com.mahouttest.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahouttest.dao.StockInfoDAO;
import com.mahouttest.dao.UserInfoDAO;
import com.mahouttest.domain.model.StockData;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@NoArgsConstructor
public class StockDataService {
	
	@Autowired
	private StockInfoDAO stockInfoDao;
	
	@Autowired
	private UserInfoDAO userInfoDao;
	
	@Transactional(readOnly=true)
	public List<StockData> stockUserRecommendList(long userId) throws Exception {
		
		int recommendCount = 5;
		/*데이터 모델 생성*/
		//DataModel dm = new FileDataModel(new File(MahoutBootApplication.class.getResource("/data/movies.csv").getFile())); 
		DataModel dm = new FileDataModel(new File("stockData.csv"));
		
		/* 유사도 모델 생성 */ 
		UserSimilarity sim = new PearsonCorrelationSimilarity(dm);
		
		/*모든 유저들로부터 주어진 유저와 특정 임계값을 충족하거나 초과하는 이웃 Neighborhood 기준*/
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, sim, dm);
		
		/*사용자 추천기 생성*/
		UserBasedRecommender recommender =  new GenericUserBasedRecommender(dm, neighborhood, sim);
		
		/* 현재 유저 ID에 해당되는 5개 아이템 추천 */ 
		List<RecommendedItem> recommendations = recommender.recommend(userId, recommendCount);
		long[] ids =  recommender.mostSimilarUserIDs(userId, recommendCount);
		List<Long> mostUserIdList = com.google.common.primitives.Longs.asList(ids);
/*		
		for(RecommendedItem recommenation : recommendations){ 
			System.out.println(userId +","+ recommenation.getItemID()+","+recommenation.getValue());
			
		} 
*/			
		List<StockData> sdList = new ArrayList<StockData>();
		
		for (int i = 0; i < recommendCount; i++) {
			StockData sd = new StockData();
			if(recommendations.isEmpty() && recommendations != null){
				if(recommendations.size() <= i){
				sd.setRate(recommendations.get(i).getValue());
				sd.setStockInfo(stockInfoDao.getOne(recommendations.get(i).getItemID()));
				}
			}
			sd.setUserInfo(userInfoDao.getOne(mostUserIdList.get(i)));
			sdList.add(sd);
		}
		
		log.info(sdList.toString());
		return sdList;
	}

	
	@Transactional(readOnly=true)
	public List<StockData> stockItemRecommendList(long stockId) throws Exception {
		
		/*데이터 모델 생성*/
		//DataModel dm = new FileDataModel(new File(MahoutBootApplication.class.getResource("/data/movies.csv").getFile())); 
		DataModel dm = new FileDataModel(new File("stockData.csv"));
		
		/* 유사도 모델 생성 */ 
		//ItemSimilarity sim = new LogLikelihoodSimilarity(dm);
		ItemSimilarity sim = new PearsonCorrelationSimilarity(dm);
		
		/*추천기 선택 ItemBased*/
		GenericItemBasedRecommender recommender =  new GenericItemBasedRecommender(dm, sim);
		
		/* 현재 stock ID에 해당되는 5개 아이템 추천 */ 
		List<RecommendedItem> recommendations = recommender.mostSimilarItems(stockId, 5);
		List<StockData> sdList = new ArrayList<StockData>();
		for (RecommendedItem recommendedItem : recommendations) {
			StockData sd = new StockData();
			sd.setRate(recommendedItem.getValue());
			sd.setStockInfo(stockInfoDao.getOne(recommendedItem.getItemID()));
			
			sdList.add(sd);
		}
		log.info(sdList.toString());
		return sdList;
	}

}
