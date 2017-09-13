package com.ZhiYe.dataUtil.graph;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gephi.graph.api.Column;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.Table;
import org.gephi.io.exporter.spi.Exporter;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.Workspace;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

public class SigmaExporter  {


	    private String path = "";

	    private boolean renumber = true;

	    private Workspace workspace;
	    
	    private String root;

//	    private ProgressTicket progress;

//	    private boolean cancel = false;


	    
	    public void setRoot(String root){
	    	
	    	System.out.println(root);
	    	this.root = root;
	    	
	    	
	    }
	    
	    


	    public void execute(String questionId) {

	        try {

	        	 this.path = this.root+"data.json";
                 
                 

                 
	        	
	        	
	            final File pathFile = new File(path);
	            
                 if(!pathFile.getParentFile().exists()){
                 	
                	 pathFile.getParentFile().mkdirs();
                 	
                 }
	            
	            
	            
	            if (pathFile.exists()) {pathFile.delete();}
	            
	            
	            pathFile.createNewFile();

	            if (pathFile.exists()) {

	            	

	                

	                OutputStreamWriter writer = null;

	                FileOutputStream outStream = null;

	                final Charset utf8 = Charset.forName("UTF-8");



	              




	                //Gson to handle JSON writing and escape

	                Gson gson = new Gson();

//	                Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

	                    

	              

	                HashMap<String,String> nodeIdMap = new HashMap<String,String>();

	                int nodeId=0;

	                EdgeColor colorMixer = new EdgeColor(EdgeColor.Mode.MIXED);

	                //Write data.json

	                Graph graph = null;

	                try {

	                    GraphModel graphModel = workspace.getLookup().lookup(GraphModel.class);

	                    graph = graphModel.getGraphVisible();

	                    graph.readLock();



	                    //Count the number of tasks (nodes + edges) and start the progress

//	                    int tasks = graph.getNodeCount() + graph.getEdgeCount();

//	                    Progress.start(progress, tasks);

	                    

	                    Table attModel = graphModel.getNodeTable();

	                    HashSet<GraphElement> jNodes = new HashSet<GraphElement>();

	                    Node[] nodeArray = graph.getNodes().toArray();

	                    for (Node n : nodeArray) {

	                        String id = n.getId().toString();

	                        String label = n.getLabel();

	                        float x = n.x();

	                        float y = n.y();

	                        float size = n.size();

	                        String color = "rgb(" + (int) (n.r() * 255) + "," + (int) (n.g() * 255) + "," + (int) (n.b() * 255) + ")";



	                        if (renumber) {

	                           String newId=String.valueOf(nodeId);

	                           nodeIdMap.put(id,newId); 

	                           id=newId;

	                           nodeId++;

	                        }

	                        

	                        GraphNode jNode = new GraphNode(id);

	                        jNode.setLabel(label);

	                        jNode.setX(x);

	                        jNode.setY(y);

	                        jNode.setSize(size);

	                        jNode.setColor(color);



	                        for (Column col : attModel) {//要改json文件点的属性栏  就要在这里开始改——————————————————————————

	                            String cid = col.getId();

	                            if (cid.equalsIgnoreCase("id") || cid.equalsIgnoreCase("label")) {

	                                continue;

	                            }



	                            Object valObj = n.getAttribute(col);

	                            if (valObj == null) {

	                                continue;

	                            }

	                            String name = col.getTitle();

	                            String val = valObj.toString();

	                            jNode.putAttribute(name, val);

	                        }

	                        /////////////////////////
	                        
	                        String name = "link";
	                        String val;
	                        
	                        if(n.getId().toString().equals("1")){
	                        	
	                        	val = "<a href=\"https://www.zhihu.com/question/"+questionId+"\"  target=_blank>"+"点击查看</a>";
	                        }else{
	                        	
	                        	val = "<a href=\"https://www.zhihu.com/question/"+questionId+"/answer/"+n.getId().toString()+"\"  target=\"_blank\">"+"点击查看</a>";
	                        }
	                        
	                        
	                        jNode.putAttribute(name, val);
	                        
	                        
	                        
	                        String nameNew = "show summary";
	                        String valNew;
	                        
	                        if(n.getId().toString().equals("1")){
	                        	
	                        	valNew  = "请直接查看原问题";
	                        }else{
	                        	
	                        	valNew = "<a href= \"#\" ><span  id = \"showSummary\" idnum = \""+ n.getId().toString()+"\" >"+"查看观点摘要</span></a>";
	                        }
	                        
	                        
	                        jNode.putAttribute(nameNew, valNew);
	                        
	                        
	                        
	                        
	                        
	                        
	                        
	                        
	                        
	                        //////////////////////////
	                        
	                        
	                        

	                        jNodes.add(jNode);



//	                        if (cancel) {
//
//	                            return false;
//
//	                        }
//
//	                        Progress.progress(progress);

	                    }





	                    //Export edges. Progress is incremented at each step.

	                    HashSet<GraphElement> jEdges = new HashSet<GraphElement>();

	                    Edge[] edgeArray = graph.getEdges().toArray();

	                    for (Edge e : edgeArray) {

	                        String sourceId = e.getSource().getId().toString();

	                        String targetId = e.getTarget().getId().toString();

	                        

	                        if (renumber) {

	                            sourceId = nodeIdMap.get(sourceId);

	                            targetId = nodeIdMap.get(targetId);

	                        }

	                        



	                        //GraphEdge jEdge = new GraphEdge();

	                        GraphEdge jEdge = new GraphEdge(String.valueOf(e.getId()));

	                        jEdge.setSource(sourceId);

	                        jEdge.setTarget(targetId);

	                        jEdge.setSize(e.getWeight());

	                        jEdge.setLabel(e.getLabel());

	                        

	                        float r=e.r();

	                        float g=e.g();

	                        float b=e.b();



	                        Iterator<Column> eAttr = e.getAttributeColumns().iterator();

	                        while (eAttr.hasNext()) {

	                            Column col = eAttr.next();

	                            if (col.isProperty() || "weight".equalsIgnoreCase(col.getId())) {

	                                //isProperty() excludes id, label, but not weight

	                                continue;

	                            }

	                            String name = col.getTitle();

	                            Object valObj = e.getAttribute(col);

	                            if (valObj == null) {

	                                continue;

	                            }

	                            String val = valObj.toString();

	                            jEdge.putAttribute(name, val);

	                        }



	                        String color;

	                        if (e.alpha()!=0) {

	                            color = "rgb(" + (int) (r* 255) + "," + (int) (g* 255) + "," + (int) (b* 255) + ")";

	                        } else {

	                            //no colour has been set. Colour will be mix of connected nodes

	                            Node n = e.getSource();

	                            Color source = new Color(n.r(),n.g(),n.b());

	                            n = e.getTarget();

	                            Color target = new Color(n.r(),n.g(),n.b());

	                            Color result = colorMixer.getColor(null, source, target);

	                            color = "rgb(" + result.getRed() + "," + result.getGreen() + "," + result.getBlue() + ")";

	                        }

	                        jEdge.setColor(color);



	                        jEdges.add(jEdge);



//	                        if (cancel) {
//
//	                            return false;
//
//	                        }
//
//	                        Progress.progress(progress);

	                    }



	                   

	                    outStream = new FileOutputStream(path);

	                    writer = new OutputStreamWriter(outStream,utf8);

	                    

	                    HashMap<String, HashSet<GraphElement>> json = new HashMap<String, HashSet<GraphElement>>();

	                    json.put("nodes", jNodes);

	                    json.put("edges", jEdges);

	                    

	                    gson.toJson(json, writer);

	                    
	                    System.out.println("目录的路径是：" + this.root);
	                    
	                    
	                    
	                    
	                    
	                    
	                    this.copyFile( this.root+"config.json",questionId);
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    

	                } catch (Exception e) {

	                    Logger.getLogger(SigmaExporter.class.getName()).log(Level.SEVERE, null, e);

	                } finally {

	                    if (graph!=null) {

	                        graph.readUnlock();

	                    }

	                    if (writer != null) {

	                        writer.close();

	                        writer = null;

	                    }

	                    if (outStream != null) {

	                        outStream.close();

	                        outStream = null;

	                    }

	                }

	            } else {

	                throw new Exception("Invalid path. Please make sure the specified directory exists. The network will be exported into a new 'network' directory in this directory.");

	            }

	        } catch (Exception e) {

	            Logger.getLogger(SigmaExporter.class.getName()).log(Level.SEVERE, null, e);

	        }

	        //Finish progress

//	        Progress.finish(progress);

//	        return !cancel; //true if task has not been cancelled and we've gotten to the end

	    }






	    public List<String> getNodeAttributes() {

	        List<String> attr = new ArrayList<String>();

	        GraphModel graphModel = workspace.getLookup().lookup(GraphModel.class);

	        Table attModel = graphModel.getNodeTable();

	        for (Column col : attModel) {

	            attr.add(col.getTitle());

	        }

	        return attr;

	    }






	    public void setWorkspace(Workspace wrkspc) {

	        this.workspace = wrkspc;

	    }




	    public Workspace getWorkspace() {

	        return workspace;

	    }



//	    @Override
//
//	    public boolean cancel() {
//
//	        cancel = true;
//
//	        return true;
//
//	    }




//	    public void setProgressTicket(ProgressTicket pt) {
//
//	        this.progress = pt;
//
//	    }   
	
	
	    
	    
	    
	    
	    public void copyFile(String to , String questionId){
	    	
	    	
	    	FileOutputStream out = null;
	    	
	    	try {
				
	    		
	    		
	    		out = new FileOutputStream(to,true);
	    		
	    		
	    		OutputStreamWriter writer = new OutputStreamWriter(out);
	    		
	    		
	    		
	    		BufferedWriter bufferedWriter= new BufferedWriter(writer);
	    		
	    		String lineText = "{\"type\": \"network\",\"version\": \"1.0\",\"data\": \""+questionId+"/data.json\","
	    				+ "\"logo\": {\"text\": \"\",\"file\": \"\",\"link\": \"\"},"
	    						+ "\"text\": {\"title\": \"\",\"more\": \"\",\"intro\": \"\"},"
	    								+ "\"legend\": {\"edgeLabel\": \"\","
	    								+ "\"colorLabel\": \"\","
	    								+ "\"nodeLabel\": \"\"},"
	    								+ "\"features\": {\"search\": true,\"groupSelectorAttribute\": false,"
	    								+ "\"hoverBehavior\": \"dim\"},"
	    								+ "\"informationPanel\": {"
	    								+ "\"imageAttribute\": false,\"groupByEdgeDirection\": false"
	    								+ "},"
	    								+ "\"sigma\": {"
	    								+ "\"graphProperties\": {"
	    								+ "\"minEdgeSize\": 0.2,"
	    								+ "\"maxNodeSize\": 13,"
	    								+ "\"maxEdgeSize\": 0.5,"
	    								+ "\"minNodeSize\": 1"
	    								+ "},"
	    								+ "\"drawingProperties\": {"
	    								+ "\"labelThreshold\": 10,"
	    								+ "\"hoverFontStyle\": \"bold\","
	    								+ "\"defaultEdgeType\": \"curve\","
	    								+ "\"defaultLabelColor\": \"#000\","
	    								+ "\"defaultLabelHoverColor\": \"#fff\","
	    								+ "\"defaultLabelSize\": 14,"
	    								+ "\"activeFontStyle\": \"bold\",\"fontStyle\": \"bold\","
	    								+ "\"defaultHoverLabelBGColor\": \"#002147\",\"defaultLabelBGColor\": \"#ddd\"},"
	    								+ "\"mouseProperties\": {"
	    								+ "\"minRatio\": 0.75,"
	    								+ "\"maxRatio\": 20"
	    								+ "}}}";
	    		
	    		
	    			
	    			
	    			
	    			writer.write(lineText);
	    			
	    		
	    	
	    		writer.close();
	    		
	    		
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
				
				try {
					
					out.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
				
			}
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    public void copyFile1(String from ,String to , String answerId){
	    	
	    	
	    	FileInputStream in = null;
	    	FileOutputStream out = null;
	    	
	    	try {
				
	    		
	    		
	    		in = new FileInputStream(from);
	    		out = new FileOutputStream(to,true);
	    		
	    		InputStreamReader reader = new InputStreamReader(in);
	    		OutputStreamWriter writer = new OutputStreamWriter(out);
	    		
	    		
	    		BufferedReader bufferedReader = new BufferedReader(reader);
	    		
	    		BufferedWriter bufferedWriter= new BufferedWriter(writer);
	    		
	    		String lineText = "";
	    		
	    		while((lineText = bufferedReader.readLine() )!= null){
	    			
	    			
	    			if(lineText.contains("data.json")){
	    				
	    				lineText = lineText.replace("data.json", answerId+"/data.json");
	    			}
	    			
	    			
	    			
	    			writer.write(lineText);
	    			
	    		}
	    		
	    		reader.close();
	    		writer.close();
	    		
	    		
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
				
				try {
					
					in.close();
					out.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
				
			}
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    }
	
	
}
