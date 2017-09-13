package com.ZhiYe.dataUtil.summary.nlpir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.ZhiYe.dataUtil.summary.nlpir.LJSentimentAnalysisLibrary.CLibrarySentimentAnalysis;


public class SummaryTask {

	
	public void beginSummary(String root){
		

		System.out.println(System.getProperty("user.dir"));
		
		String argu = "";
		String system_charset = "UTF-8";
		int charset_type = 1;

		try {
			if (!CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset),
					charset_type, "0".getBytes(system_charset))) {
				System.err.println("初始化失败！");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		int flag = CLibrarySentimentAnalysis.Instance.LJST_Inits(
				"", 1, "");
		if (flag == 0) {
			System.out.println("SentimentAnalysis初始化失败");
			
		} else {
			System.out.println("SentimentAnalysis初始化成功");
		}
		
		
		boolean flag0 = CLibraryDS.Instance.DS_Init("", 1, "0");
		if (flag0 == false) {// 如果初始化失败，就打印出失败原因
			System.out.println(CLibraryDS.Instance.DS_GetLastErrMsg());
		}
		
		
		
		////////////
		File file0 = new File(root+"zhihuSummary.csv");
		OutputStreamWriter writer;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(file0,true),"GBK");

		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		
		////////////
		
		writer.write("answerId,author,keywords,summary,sentiment,upCount,commentCount,time,isAnswer\r\n");
		
		
		
		File file = new File(root+"zhihuAfter.csv");
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file),"GBK" );
		
		BufferedReader bufferedReader = new BufferedReader(read);
		
		String lineText = "";
		int num = 0;
		

		while((lineText = bufferedReader.readLine())!=null){
			
			if(num != 0){
				
				
				String[] texts = lineText.split(",");
				
				
				if(texts.length<7)continue;
				
				String result = texts[1]+","+texts[2]+",";
				
				String content = texts[6];
				
				
				String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(content,15,false);
				
				result= result+nativeByte+",";
				
				
				int length = content.length();
				
				String summary;
				
				
				if(length>100){
					
					
					
					summary = CLibraryDS.Instance
							.DS_SingleDoc(content, 0, 200, true);
					
				}else{
					
					summary = content;
				}
				
				
				result= result+summary+",";
				
					
				byte[] resultByte = new byte[10000];
				
				CLibrarySentimentAnalysis.Instance.LJST_GetParagraphSent(content,
						resultByte);
				
				
				String sentiment = new String(resultByte).replaceAll("\n", "/").replaceAll("EMOTION.*?/", "").trim();
				
				result= result +sentiment+","+texts[4]+","+texts[5]+","+texts[3]+",";
				
				String isAnswer = texts[0].equals("1")?"true":"false";
				
				
				result= result+isAnswer+"\r\n";
				
				
				writer.write(result);
				
			}
			
			
			num++;
			
			
		}
		
		writer.flush();
		
		writer.close();
		bufferedReader.close();
		bufferedWriter.close();
		CLibrary.Instance.NLPIR_Exit();
		CLibraryDS.Instance.DS_Exit();
		CLibrarySentimentAnalysis.Instance.LJST_Exits();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}
	
	@Test
	public void testSummary(){
		
		
		
		this.beginSummary("D:\\eclipse-j2ee\\eclipse-jee-kepler-SR2-win32-x86_64\\项目\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ZhiYe\\45757391\\");
		
		
		
		
	}
	
	
	
	
	
	
	@Test
	public void getData() throws IOException{
		
		String argu = "";
		String system_charset = "UTF-8";
		int charset_type = 1;

		if (!CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset),
				charset_type, "0".getBytes(system_charset))) {
			System.err.println("初始化失败！");
		}
		
		
		
		int flag = CLibrarySentimentAnalysis.Instance.LJST_Inits(
				"", 1, "");
		if (flag == 0) {
			System.out.println("SentimentAnalysis初始化失败");
			System.exit(0);
		} else {
			System.out.println("SentimentAnalysis初始化成功");
		}
		
		
		boolean flag0 = CLibraryDS.Instance.DS_Init("", 1, "0");
		if (flag0 == false) {// 如果初始化失败，就打印出失败原因
			System.out.println(CLibraryDS.Instance.DS_GetLastErrMsg());
		}
		
		
		
		////////////
		File file0 = new File("C:\\Users\\asus\\Desktop\\testResult.csv");
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file0,true),"GBK");
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		
		////////////
		
		writer.write("answerId,content,keywords,summary,sentiment\r\n");
		
		
		
		File file = new File("C:\\Users\\asus\\Desktop\\test.csv");
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file),"GBK" );
		
		BufferedReader bufferedReader = new BufferedReader(read);
		
		String lineText = "";
		int num = 0;
		

		while((lineText = bufferedReader.readLine())!=null){
			
			if(num != 0){
				
				
				String[] texts = lineText.split(",");
				
				
				if(texts.length<7)continue;
				
				String result = texts[1]+","+texts[6]+",";
				
				String content = texts[6];
				
				
				String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(content,20,false);
				
				result= result+nativeByte+",";
				
				
				int length = content.length();
				
				String summary;
				
				
				if(length>100){
					
					
					
					summary = CLibraryDS.Instance
							.DS_SingleDoc(content, 0, 150, true);
					
				}else{
					
					summary = content;
				}
				
				
				result= result+summary+",";
				
					
				byte[] resultByte = new byte[10000];
				
				CLibrarySentimentAnalysis.Instance.LJST_GetParagraphSent(content,
						resultByte);
				
				
				String sentiment = new String(resultByte).replaceAll("\n", "/").replaceAll("EMOTION.*?/", "");
				
				result= result+sentiment+"\r\n";
				
				writer.write(result);
				
			}
			
			
			num++;
			
			
		}
		
		writer.flush();
		
		writer.close();
		bufferedReader.close();
		bufferedWriter.close();
		CLibrary.Instance.NLPIR_Exit();
		CLibraryDS.Instance.DS_Exit();
		CLibrarySentimentAnalysis.Instance.LJST_Exits();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
