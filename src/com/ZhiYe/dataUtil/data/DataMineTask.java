package com.ZhiYe.dataUtil.data;

import java.io.IOException;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

import com.ZhiYe.base.ClientBase;
import com.ZhiYe.dataUtil.graph.GetGexf;


public class DataMineTask{

	public void beginTask(String filePath , CookieStore cookieStore, String questionId, Integer answerNum){
		
		String zhihuBefore = filePath+"zhihuBefore.csv";
		String zhiHuAfter = filePath+"zhihuAfter.csv";
		
		
		
		
		CloseableHttpClient httpClient = ClientBase.getClient(cookieStore);
		
		GetWriter writer = new GetWriter();
		writer.getNewOut(zhihuBefore);
		
		GetBeforeAnswersCsv writeto = new GetBeforeAnswersCsv(questionId);
		writeto.writeHeader(writer.getWriter());
		
		
		
		for(int i =0;i< answerNum;i+=20){
			
			
			System.out.println(i);
			writeto.write(httpClient, writer.getWriter(), i);
		}
		writer.closeOut();
		
		GetWriter newWriter = new GetWriter();
		newWriter.getNewOut(zhiHuAfter);
		
		
		
		new GetCommentTask().doIterator(zhihuBefore, newWriter.getWriter(), httpClient , answerNum);
		try {
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		newWriter.closeOut();
	
		
		
		
		new GetEdgeAndPoint().getEP(zhiHuAfter, filePath);
		
		
		new GetGml().writeGml(filePath, filePath+"zhihuEdge.csv",
				filePath+"zhihuPoint.csv");	
		
		new GetGexf().script(filePath+"zhihuGml.gml", questionId,filePath);
	}
	

	
}
