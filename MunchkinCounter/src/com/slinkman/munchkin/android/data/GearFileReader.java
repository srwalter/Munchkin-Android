package com.slinkman.munchkin.android.data;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.Environment;
import android.util.Log;

public class GearFileReader {

	private ArrayList<Integer> bonusList;
	private ArrayList<String> armorList;

	public GearFileReader() {
		bonusList = new ArrayList<Integer>();
		armorList = new ArrayList<String>();
	}

	public ArrayList<Integer> getBonusList() {
		return bonusList;
	}

	public ArrayList<String> getArmorList() {
		return armorList;
	}

	public void startReader() {
		ContentHandler temp = new ContentHandler() {

			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void endDocument() throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void endPrefixMapping(String prefix) throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void ignorableWhitespace(char[] ch, int start, int length)
					throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void processingInstruction(String target, String data)
					throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setDocumentLocator(Locator locator) {
				// TODO Auto-generated method stub

			}

			@Override
			public void skippedEntity(String name) throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void startDocument() throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes atts) throws SAXException {
				for (int i=0; i < atts.getLength();i++){
					if (atts.getLocalName(i).equals("type"))
						armorList.add(atts.getValue(i));
					else if (atts.getLocalName(i).equals("bonus"))
						bonusList.add(Integer.parseInt(atts.getValue(i)));
					Log.i("ReadTag", localName);
				}

			}

			@Override
			public void startPrefixMapping(String prefix, String uri)
					throws SAXException {
				// TODO Auto-generated method stub

			}

		};
		try {
			SAXParserFactory f = SAXParserFactory.newInstance();
			SAXParser parser = f.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setContentHandler(temp);
			File root = Environment.getExternalStorageDirectory();
			Log.i("Reader", "ParseStart");
			File gearFile = new File(root,"gear_list.xml");
			if (!gearFile.exists())
				gearFile.createNewFile();
			reader.parse(new InputSource(new FileInputStream(gearFile)));
		} catch (Exception ex) {

		}
	}
}
