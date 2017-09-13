package com.ZhiYe.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class ZipDownloadAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String questionId;
	

	


	public String getQuestionId() {
		return questionId;
	}


	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}


	public InputStream getInputStream(){
		
		InputStream input = null;
		
		String zipFilePath = this.theRoot()+"zip//"+this.questionId+".zip";
		String dirFilePath = this.theRoot()+this.questionId;
		
		
		File zipFile = new File(zipFilePath);
		if(zipFile.exists())zipFile.delete();
		
		this.fileToZip(dirFilePath, this.theRoot()+"zip", this.questionId);
		
		
		
		try {
			input = new FileInputStream(zipFilePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		return input;
	}
	
	
	public String excute(){
		

		return "success";
	}
	
	private String fileName;
	
	
	public String getFileName() {
		
		
		return this.questionId+".zip";
		
		
	}
	
	
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
	private String contentType;

	public String getContentType() {
		
		return "application/x-zip-compressed";
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	public String theRoot(){
		
		ActionContext actionContext = ActionContext.getContext();
		
		ServletContext servletContext = (ServletContext) actionContext.get(ServletActionContext.SERVLET_CONTEXT);
		
		return servletContext.getRealPath("/");
		
		
		
		
		
		
	}
	
	public boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){
		
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		
		if(sourceFile.exists()== false){
			System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
		
		}else{
			
			try {
				File zipFile = new File(zipFilePath+"/"+fileName+".zip");
				if(zipFile.exists()){
					
					System.out.println(zipFilePath+"目录下存在名字为:"+fileName+".zip"+"打包文件.");
				}else{
					
					File[] sourceFiles = sourceFile.listFiles();
					if(sourceFiles == null|| sourceFile.length()<1){
						System.out.println("待压缩的文件目录:"+sourceFilePath+"里面不存在文件，无需压缩.");
						
					}else{
						
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024*10];
						for(int i= 0;i<sourceFiles.length;i++){
							
							if(sourceFiles[i].getName().equals("config.json") || sourceFiles[i].getName().equals("data.json"))continue;
							//创建zip实体，并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							//读取待亚索文件并写进压缩包里
							zos.putNextEntry(zipEntry);
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis,1024*10);
							int read = 0;
							while((read = bis.read(bufs, 0, 1024*10))!= -1){
								
								zos.write(bufs, 0, read);
							}
							
							
							
						}
						flag = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
				try {
					if(null != bis)bis.close();
					if(null != zos)zos.close();
				} catch (Exception e2) {
					
					e2.printStackTrace();
				}
				
			}
			
			
		}
		return flag;
		
		
		
		
		
	}
	
	@Test
	public void testZip(){
		
		this.fileToZip("C:\\Users\\asus\\Desktop\\try", "C:\\Users\\asus\\Desktop", "myzip");
		
		
	}
	
}
