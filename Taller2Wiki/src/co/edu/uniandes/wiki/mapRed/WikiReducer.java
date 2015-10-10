package co.edu.uniandes.wiki.mapRed;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import co.edu.uniandes.wiki.util.Utilidades;

public class WikiReducer extends Reducer<Text, Text, Text, Text> {
	public static final Log log = LogFactory.getLog(WikiReducer.class);

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		StringBuilder resultado = new StringBuilder();
		// String[] valores;
		for (Text texto : values) {
			log.info("elemento: " + texto.toString());
			resultado.append(Utilidades.PUNTO_Y_COMA);
			resultado.append(texto.toString());
			/*
			 * valores = texto.toString().split(","); for(String str : valores){
			 * resultado.append(Utilidades.PUNTO_Y_COMA); resultado.append(str);
			 * }
			 */

		}

		context.write(key, new Text(resultado.toString()));

	}

}
