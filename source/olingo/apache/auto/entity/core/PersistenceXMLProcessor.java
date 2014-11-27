/**
 * 
 */
package olingo.apache.auto.entity.core;

import olingo.apache.auto.entity.infos.PathInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 2014-11-19
 * 
 * @author Bruce Li
 */
public class PersistenceXMLProcessor {
	private final String PERSISTENCE_ELEMENTSBYTAGNAME = "persistence-unit";
	private boolean isFind = false;
	public void updataPersistenceXML(PathInfo pathInfo) throws SAXException,
			IOException, ParserConfigurationException,
			TransformerConfigurationException, TransformerException,
			TransformerFactoryConfigurationError {
		/* Find persistence file real path */
		pathInfo = findPersistenceXMLPath(pathInfo);
		/* Add <class> elements */
		addClassElements(pathInfo);
	}
	protected PathInfo findPersistenceXMLPath(PathInfo pathInfo)
			throws FileNotFoundException {
		File fi = new File(pathInfo.getRealPath());
		if (!fi.isFile()) {
			pathInfo = find(pathInfo, fi);
		}
		return pathInfo;
	}
	protected PathInfo find(PathInfo pathInfo, File fi) {
		File[] files = fi.listFiles();
		for (File f : files) {
			if ((!isFind && f.isFile())
					&& pathInfo.getPersistenceClassPath().contains(f.getName())) {
				isFind = true;
				pathInfo.setPersistenceClassPath(f.getPath());
				return pathInfo;
			}
			if (!f.isFile()) {
				pathInfo = find(pathInfo, f);
			}
		}
		return pathInfo;
	}
	private void addClassElements(PathInfo pathInfo) throws SAXException,
			IOException, ParserConfigurationException,
			TransformerConfigurationException, TransformerException,
			TransformerFactoryConfigurationError {
		Document book = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(pathInfo.getPersistenceClassPath());
		Node persistence_unit_Node = getPersistence_unit_Node(pathInfo, book);
		/* Delect all <class> elements */
		NodeList nodeList2 = persistence_unit_Node.getChildNodes();
		int nodeListLength = nodeList2.getLength();
		for (int k = 0; k < nodeListLength; k++) {
			Node node2 = nodeList2.item(k);
			if (node2 != null && node2.getNodeName().equals("class")) {
				node2.getParentNode().removeChild(node2);
			}
		}
		/* Add new <class> elements */
		List<String> entityPathNameList = pathInfo.getEntityPackAndNames();
		for (String entityPathName : entityPathNameList) {
			Element element = book.createElement("class");
			element.setTextContent(entityPathName);
			persistence_unit_Node.appendChild(element);
		}
		TransformerFactory
				.newInstance()
				.newTransformer()
				.transform(new DOMSource(book),
						new StreamResult(pathInfo.getPersistenceClassPath()));
	}
	protected Node getPersistence_unit_Node(PathInfo pathInfo, Document book)
			throws SAXException, IOException, ParserConfigurationException {
		NodeList nodeList = book
				.getElementsByTagName(PERSISTENCE_ELEMENTSBYTAGNAME);
		int length = nodeList.getLength();
		Node persistence_unit_Node = null;
		for (int i = 0; i < length; i++) {
			persistence_unit_Node = nodeList.item(i);
			NamedNodeMap namedNodeMap = persistence_unit_Node.getAttributes();
			int attrLength = namedNodeMap.getLength();
			for (int j = 0; j < attrLength; j++) {
				if (pathInfo.getPersistenceUnitName().equals(
						namedNodeMap.item(j).getNodeValue())) {
					break;
				}
			}
		}
		return persistence_unit_Node;
	}
}
