package co.edu.uniandes.wiki.mapRed;

import co.edu.uniandes.wiki.filter.CountryFinder;
import co.edu.uniandes.wiki.filter.DateFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import co.edu.uniandes.wiki.dto.Elemento;
import co.edu.uniandes.wiki.util.TipoElemento;
import co.edu.uniandes.wiki.util.Utilidades;

public class WikiMapper extends Mapper<LongWritable, Text, Text, Text> {
	public static final Log log = LogFactory.getLog(WikiMapper.class);
	public static ConcurrentHashMap<String, String> personasMap = null;
	public static Integer numMappers = 0;

	@SuppressWarnings("null")
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		if (numMappers < 1000 ) {
			numMappers++;
			DocumentBuilder builder = null;
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			Document xmlDocument;

			DateFilter dateFilter = null;

			CountryFinder countryFind = new CountryFinder();
			String countries = "";
			boolean dateInRange = false;
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date initialDate = new Date();
			Date endDate = new Date();

			Configuration conf = context.getConfiguration();
			// Country Sent through Command Line
			String countryParameter = conf.get("countryIn");
			// Date Sent through Command Line
			String tempDate = conf.get("initialDate");
			try {
				initialDate = df.parse(tempDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			tempDate = conf.get("endDate");
			try {
				endDate = df.parse(tempDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/*
			 * Pattern patron; Matcher matcher; List<String> posiblesPaises =
			 * new ArrayList<String>(); String mached;
			 */
			try {
				/*
				 * builderFactory.setFeature(
				 * "http://apache.org/xml/features/nonvalidating/load-dtd-grammar"
				 * , false); builderFactory.setFeature(
				 * "http://apache.org/xml/features/nonvalidating/load-external-dtd"
				 * , false);
				 */
				builderFactory.setFeature(Utilidades.DTD_GRAMMAR, false);
				builderFactory.setFeature(Utilidades.EXTERNAL_DTD, false);
				builder = builderFactory.newDocumentBuilder();

				String valuesStr = value.toString();
				valuesStr = valuesStr + "</page>";
				xmlDocument = builder.parse(new ByteArrayInputStream(valuesStr.getBytes()));
				
				Node contenidoWiki = xmlDocument.getElementsByTagName("text").item(0);
				
				Node tituloWiki = xmlDocument.getElementsByTagName("title").item(0);
				
				//if (tituloWiki != null && contenidoWiki.getFirstChild() != null
				//		&& tituloWiki.getFirstChild().getNodeValue() != null) {
					
					//String contenidoWikiStr = contenidoWiki.getFirstChild().getNodeValue();
					
					String tituloWikiStr = tituloWiki.getFirstChild().getNodeValue();
					//contenidoWiki = null;
					//xmlDocument = null;
					Elemento elemento = null;
					//if (contenidoWikiStr.matches(Utilidades.PAIS_EXP)) {
					//	elemento = new Elemento(tituloWikiStr,
					//			TipoElemento.PAIS);
					//	log.info("expresion: " + elemento.toString());
					//	context.write(new Text(tituloWikiStr), new Text(
					//			elemento.toString()));
					//} else 
					
					//if (contenidoWikiStr.matches(Utilidades.PERSONA_EXP)) {
					if (valuesStr.matches(Utilidades.PERSONA_EXP)) {
						elemento = new Elemento(tituloWikiStr,	TipoElemento.PERSONA);
						
						log.info("elemento: " + elemento.toString());

						countries = countryFind.filter(valuesStr,countryParameter);
						if(countries.length()>1){
							dateInRange = dateFilter.filter(initialDate, endDate,valuesStr);
							if(dateInRange){
							countries = tituloWikiStr+","+countries;
							context.write(new Text(tituloWikiStr), new Text(countries));
							}
						}
						// context.write(new Text(tituloWikiStr), new
						// Text(elemento.getTipoElemento().toString()));

					}/*
					 * else{ log.info("No encontr� "); context.write(new
					 * Text(tituloWikiStr), new Text("No encontr� ")); }
					 */
				/*}else{
					log.info("No entro para pagina" + tituloWiki.toString());
				}*/

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		/*
		 * for(String k:palabrasLinea.keySet()){ context.write(new Text(k), new
		 * Text("")); }
		 */

	}
}
