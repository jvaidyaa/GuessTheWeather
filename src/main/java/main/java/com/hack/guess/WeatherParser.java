package main.java.com.hack.guess;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class WeatherParser {

	public WeatherObject parseWeather(String zipCode) {

		String url = "http://wxdata.weather.com/wxdata/weather/local/"
				+ zipCode + "?cc=*&dayf=1";

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		WeatherObject weatherObject = new WeatherObject();

		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL(url).openStream());

			// doc.getDocumentElement().normalize();
			// NodeList nList = doc.getElementsByTagName("staff");

			weatherObject.setCondition(doc.getElementsByTagName("icon").item(0)
					.getTextContent());
			weatherObject.setDewPoint(doc.getElementsByTagName("dewp").item(0)
					.getTextContent());
			weatherObject.setFeelTemperature(doc.getElementsByTagName("flik")
					.item(0).getTextContent());
			weatherObject.setHumidity(doc.getElementsByTagName("hmid").item(0)
					.getTextContent());
			weatherObject.setPressure(doc.getElementsByTagName("r").item(0)
					.getTextContent());
			weatherObject.setTemperature(doc.getElementsByTagName("tmp")
					.item(0).getTextContent());
			weatherObject.setUvIndex(doc.getElementsByTagName("i").item(0)
					.getTextContent());
			weatherObject.setVisibility(doc.getElementsByTagName("vis").item(0)
					.getTextContent());
			weatherObject.setWindDirection(doc.getElementsByTagName("d")
					.item(1).getTextContent());
			weatherObject.setWindSpeed(doc.getElementsByTagName("s").item(0)
					.getTextContent());

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return weatherObject;
	}
}
