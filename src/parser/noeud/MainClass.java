package parser.noeud;

import parser.grammaire.BParser;
import parser.noeud.Noeud;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import static parser.grammaire.BParser.*;

public class MainClass {

	public static void main(String[] args) {

		BToXMLVisiteur visitor = new BToXMLVisiteur();
		XMLOutputter xmlOutput = new XMLOutputter();
		try {
            //Noeud bComponent = analyse(new File("ressources/robot.mch"));
			//writeXMLFileAfterParsing((Noeud) bComponent, "robot.xml");
			Element xMachine = null;
			try {
				xMachine = (Element) visitor.visit(analyse(new File("ressources/robot.mch")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Document doc = new Document(xMachine);


			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("file.ebm"));
		} catch ( IOException e) {
			e.printStackTrace();
		}

	}

}
