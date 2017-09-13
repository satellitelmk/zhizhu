package com.ZhiYe.dataUtil.graph;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.gephi.appearance.api.AppearanceController;
import org.gephi.appearance.api.AppearanceModel;
import org.gephi.appearance.api.Function;
import org.gephi.appearance.plugin.RankingElementColorTransformer;
import org.gephi.appearance.plugin.RankingNodeSizeTransformer;
import org.gephi.graph.api.Column;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.impl.NodeStore;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDirectionDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.importer.plugin.file.ImporterGML;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.junit.Test;
import org.openide.util.Lookup;

import com.alibaba.fastjson.JSON;

public class GetGexf {

	@Test
	 public void script(String uri,String questionId,String root) {

	        //Init a project - and therefore a workspace

	        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);

	        pc.newProject();

	        Workspace workspace = pc.getCurrentWorkspace();

	        ImportController importController = Lookup.getDefault().lookup(ImportController.class);

	        //Generate a new random graph into a container

	        Container container;

	        try {

	            File file = new File(uri);

	            ImporterGML getGml = new ImporterGML();
	            
	            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
	            
	            getGml.setReader(new InputStreamReader(new FileInputStream(file), "GBK"));
	            
	            
	            container = importController.importFile(reader,getGml);

	            container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);   //Force DIRECTED

	            container.getLoader().setAllowAutoNode(false);  //Don't create missing nodes
	            
	            
	        } catch (Exception ex) {

	            ex.printStackTrace();

	            return;

	        }



	        //Append container to graph structure

	       

	        importController.process(container, new DefaultProcessor(), workspace);



	        //See if graph is well imported

	        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();

	        DirectedGraph graph = graphModel.getDirectedGraph();
	        
	        
	        AppearanceController appearanceController = Lookup.getDefault().lookup(AppearanceController.class);

	        AppearanceModel appearanceModel = appearanceController.getModel();
	        
	        
	        
	        

	        System.out.println("Nodes: " + graph.getNodeCount());

	        System.out.println("Edges: " + graph.getEdgeCount());


	        
	        
	        
	        
	        
	        Column countColumn = graphModel.getNodeTable().getColumn("vote-number");

	        Function countRanking = appearanceModel.getNodeFunction(graph, countColumn, RankingNodeSizeTransformer.class);

	        RankingNodeSizeTransformer transformer = (RankingNodeSizeTransformer) countRanking.getTransformer();

	        transformer.setMinSize(15);//50

	        transformer.setMaxSize(60);//150

	        appearanceController.transform(countRanking);
	        
	        
	        
	        Column countColumnNew = graphModel.getNodeTable().getColumn("textLength");
	        
	        Function degreeRanking = appearanceModel.getNodeFunction(graph, countColumnNew, RankingElementColorTransformer.class);

	        RankingElementColorTransformer degreeTransformer = (RankingElementColorTransformer) degreeRanking.getTransformer();

//	        degreeTransformer.setColors(new Color[]{new Color(0xDDFFFF), new Color(0x0066FF),new Color(0x0000EE)});

	        degreeTransformer.setColors(new Color[]{new Color(0xFF9900), new Color(0x00FF33),new Color(0xCC00FF)});
	        
	        
	        degreeTransformer.setColorPositions(new float[]{0f,0.2f ,1f});

	        appearanceController.transform(degreeRanking);
	        
	        
	        
	        
	        
	        int duration = graph.getNodeCount()/100;
	        
	        
	        
	        

	        //Layout for 1 minute

	        AutoLayout autoLayout = new AutoLayout(duration, TimeUnit.SECONDS);

	        autoLayout.setGraphModel(graphModel);

	        YifanHuLayout firstLayout = new YifanHuLayout(null, new StepDisplacement(1f));
	        
	        firstLayout.setOptimalDistance((float) 50);

//	        ForceAtlasLayout secondLayout = new ForceAtlasLayout(null);

//	        AutoLayout.DynamicProperty adjustBySizeProperty = AutoLayout.createDynamicProperty("forceAtlas.adjustSizes.name", Boolean.TRUE, 0.1f);//True after 10% of layout time

//	        AutoLayout.DynamicProperty repulsionProperty = AutoLayout.createDynamicProperty("forceAtlas.repulsionStrength.name", 500., 0f);//500 for the complete period

	        autoLayout.addLayout(firstLayout, 1f);

//	        autoLayout.addLayout(secondLayout, 0.5f, new AutoLayout.DynamicProperty[]{adjustBySizeProperty, repulsionProperty});

	        autoLayout.execute();

	        SigmaExporter exporter = new SigmaExporter();
	        
	        exporter.setRoot(root);
	        
	        
	        final Workspace currentWorkspace = Lookup.getDefault().lookup(ProjectController.class).getCurrentWorkspace();

	        exporter.setWorkspace(currentWorkspace);
	        
	        exporter.execute(questionId);

	        //Export

	        ExportController ec = Lookup.getDefault().lookup(ExportController.class);

	        try {

	            ec.exportFile(new File(root+"zhihuGraph.gexf"));
	            ec.exportFile(new File(root+"zhihuGraph.png"));

	        } catch (IOException ex) {

	            ex.printStackTrace();

	        }

	    }
	
	
	
	
}




//
//
//class Group{
//	
//	
//	private List<Node> nodes;
//	
//	private List<Edge> edges;
//
//	public Group(List<Node> nodeList, List<Edge> edgeList) {
//		super();
//		this.nodes = nodeList;
//		this.edges = edgeList;
//	}
//
//	public List<Node> getNodes() {
//		return nodes;
//	}
//
//	public void setNodeList(List<Node> nodes) {
//		this.nodes = nodes;
//	}
//
//	public List<Edge> getEdges() {
//		return edges;
//	}
//
//	public void setEdgeList(List<Edge> edges) {
//		this.edges = edges;
//	}
//	
//	public Group(){};
//	
//}
//
//
//
//
//class myNode{
//	
//	private String label;
//	private float x;
//	private float y;
//	private String id;
//	private String color;
//	private float size;
//	
//	private NodeAttributes attributes;
//
//	public String getLabel() {
//		return label;
//	}
//
//	public void setLabel(String label) {
//		this.label = label;
//	}
//
//	public float getX() {
//		return x;
//	}
//
//	public void setX(float x) {
//		this.x = x;
//	}
//
//	public float getY() {
//		return y;
//	}
//
//	public void setY(float y) {
//		this.y = y;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getColor() {
//		return color;
//	}
//
//	public void setColor(String color) {
//		this.color = color;
//	}
//
//	public float getSize() {
//		return size;
//	}
//
//	public void setSize(float size) {
//		this.size = size;
//	}
//
//	public NodeAttributes getAttributes() {
//		return attributes;
//	}
//
//	public void setAttributes(NodeAttributes attributes) {
//		this.attributes = attributes;
//	}
//
//	public myNode(String label, float x, float y, String id, String color,
//			float size, NodeAttributes attributes) {
//		super();
//		this.label = label;
//		this.x = x;
//		this.y = y;
//		this.id = id;
//		this.color = color;
//		this.size = size;
//		this.attributes = attributes;
//	}
//	
//	public myNode(){};
//	
//	
//	
//}
//
//
//class NodeAttributes{
//	
//	private float count;
//
//	public NodeAttributes(float count) {
//		super();
//		this.count = count;
//	}
//
//	public float getCount() {
//		return count;
//	}
//
//	public void setCount(float count) {
//		this.count = count;
//	}
//	
//	public NodeAttributes(){};
//	
//	
//}
//
//
//class myEdge{
//	
//	
//	private 
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}



