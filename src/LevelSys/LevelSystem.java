package LevelSys;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;

import com.angrymilou.yassir.MainGame;

public class LevelSystem {

	private DocumentBuilderFactory factory ;
	private DocumentBuilder builder ;
	private Document dc;
	private Element racine ;
	private NodeList levels;
	private Element level ;
	private TransformerFactory transformerFactory ;
	private Transformer transformer ;
	private DOMSource source ;
	private StreamResult sortie;
    
	public LevelSystem(File src){
		
		factory =DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments (true);
        factory.setIgnoringElementContentWhitespace (true);
        
			try {
				builder = factory.newDocumentBuilder();
				dc=builder.parse(src);
				racine =dc.getDocumentElement();
				levels =racine.getChildNodes();
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	
	public String getStatut(int num){
		level =(Element) levels.item(num);
		return level.getAttribute("statut");
	}
	
	public void setStatut(int num,String statut){
		
	    try {
	    	level =(Element) levels.item(num);
			level.setAttribute("statut",statut);
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			source = new DOMSource(dc);

			sortie = new StreamResult(MainGame.levelDest);

		    //prologue
		    transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
		    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");			
		    		
		    //formatage
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				
		    //sortie
		    transformer.transform(source, sortie);	
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	}
}
