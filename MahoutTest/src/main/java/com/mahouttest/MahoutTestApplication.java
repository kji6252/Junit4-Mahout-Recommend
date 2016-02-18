package com.mahouttest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MahoutTestApplication {

	public static void main(String[] args) throws IOException, TasteException {
		SpringApplication.run(MahoutTestApplication.class, args);
		
/* 데이터 모델 생성 */ 
		
		//DataModel dm = new FileDataModel(new File(MahoutBootApplication.class.getResource("/data/movies.csv").getFile())); 
		DataModel dm = new FileDataModel(new File("bin/data/movies.csv"));
		
		/* 유사도 모델 생성 */ 
		UserSimilarity sim; 
		sim = new PearsonCorrelationSimilarity(dm);
		/* 모든 유저들로부터 주어진 유저와 특정 임계값을 충족하거나 초과하는 neighborhood 기준 */ 
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, sim, dm); 
		/* 사용자 추천기 생성 */ 
		UserBasedRecommender recommender = new GenericUserBasedRecommender(dm, neighborhood, sim); 
		
		int x= 1; 
		/* 데이터 모델 내의 유저들의 iterator를 단계별로 이동하며 추천 아이템들 제공 */ 
		for(LongPrimitiveIterator users = dm.getUserIDs(); users.hasNext();){ 
			
			long userID = users.nextLong(); /* 현재 유저 ID */ 
			
			/* 현재 유저 ID에 해당되는 5개 아이템 추천 */ 
			List<RecommendedItem> recommendations = recommender.recommend(userID, 5); 
			for(RecommendedItem recommenation : recommendations){ 
				System.out.println(userID +","+ recommenation.getItemID()+","+recommenation.getValue()); 
				} 
			
			if(++x > 5) break; /* 유저 ID 5까지만 출력 */ 
		}
	}
}
