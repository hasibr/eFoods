import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws Exception {
		Middleware m1 = new Middleware();
		m1.merge2();
		m1.cleanDirectory();
	}

}
