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
		
/* ������ �� ���� */ 
		
		//DataModel dm = new FileDataModel(new File(MahoutBootApplication.class.getResource("/data/movies.csv").getFile())); 
		DataModel dm = new FileDataModel(new File("bin/data/movies.csv"));
		
		/* ���絵 �� ���� */ 
		UserSimilarity sim; 
		sim = new PearsonCorrelationSimilarity(dm);
		/* ��� ������κ��� �־��� ������ Ư�� �Ӱ谪�� �����ϰų� �ʰ��ϴ� neighborhood ���� */ 
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, sim, dm); 
		/* ����� ��õ�� ���� */ 
		UserBasedRecommender recommender = new GenericUserBasedRecommender(dm, neighborhood, sim); 
		
		int x= 1; 
		/* ������ �� ���� �������� iterator�� �ܰ躰�� �̵��ϸ� ��õ �����۵� ���� */ 
		for(LongPrimitiveIterator users = dm.getUserIDs(); users.hasNext();){ 
			
			long userID = users.nextLong(); /* ���� ���� ID */ 
			
			/* ���� ���� ID�� �ش�Ǵ� 5�� ������ ��õ */ 
			List<RecommendedItem> recommendations = recommender.recommend(userID, 5); 
			for(RecommendedItem recommenation : recommendations){ 
				System.out.println(userID +","+ recommenation.getItemID()+","+recommenation.getValue()); 
				} 
			
			if(++x > 5) break; /* ���� ID 5������ ��� */ 
		}
	}
}
