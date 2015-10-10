package co.edu.uniandes.wiki.job;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import co.edu.uniandes.wiki.mapRed.WikiMapper;
import co.edu.uniandes.wiki.mapRed.WikiReducer;

public class WikiJob {
	public static void main(String[] args) {
		if (args.length < 5) {
			System.out
					.println("Se necesitan las carpetas de entrada y salida, como el pais y la fecha de Inicio y Fin");
			System.exit(-1);
		}
		System.out.println("args[0] Input Path   : " + args[0]);
		System.out.println("args[1] Country      : " + args[1]);
		System.out.println("args[2] Initial Date : " + args[2]);
		System.out.println("args[3] End Date     : " + args[3]);
		System.out.println("args[4] Output Path  : " + args[4]);

		String entrada = args[0]; // Input Path
		String country = args[1]; // Country
		String initialDate = args[2]; // Initial Date
		String endDate = args[3]; // End Date
		String salida = args[4]; // Output file

		try {
			// Countries name such as Dominican republic are sent as
			// Dominican_Republic
			country.replace("_", " ");
			ejecutarJob(entrada, salida, country, initialDate, endDate);
		} catch (Exception e) { // Puede ser IOException, ClassNotFoundException
								// o InterruptedException
			e.printStackTrace();
		}

	}

	public static void ejecutarJob(String entrada, String salida,
			String country, String initialDate, String endDate)
			throws IOException, ClassNotFoundException, InterruptedException {
		/**
		 * Objeto de configuracion, dependiendo de la version de Hadoop uno u
		 * otro es requerido.
		 * */
		Configuration conf = new Configuration();
		conf.set("textinputformat.record.delimiter", "</page>");
		/*
		 * conf.set("stream.recordreader.class",
		 * "org.apache.hadoop.streaming.StreamXmlRecordReader");
		 * conf.set("stream.recordreader.begin", "<page>");
		 * conf.set("stream.recordreader.end", "</page>");
		 */
		// JobConf jobConf = new JobConf(conf);
		// Path dir = new Path(entrada);
		/*
		 * Path dir = new Path(System.getProperty("test.build.data", ".") +
		 * "/mapred"); StreamWikiDumpInputFormat.setInputPaths(jobConf, dir);
		 * StreamWikiDumpInputFormat formatSWD = new
		 * StreamWikiDumpInputFormat(); formatSWD.configure(jobConf);
		 */

		conf.set("mapreduce.jobtracker.taskscheduler.maxrunningtasks.perjob",
				"2");
		conf.set("mapred.child.java.opts", "-Xmx512m");
		conf.set("mapreduce.map.memory.mb", "256");
		conf.set("mapreduce.reduce.memory.mb", "256");

		conf.set("countryIn", country);
		conf.set("initialDate", initialDate);
		conf.set("endDate", endDate);

		Job wcJob = Job.getInstance(conf, "WikiGraph Job");

		wcJob.setJarByClass(WikiJob.class);
		// ////////////////////
		// Mapper
		// ////////////////////

		wcJob.setMapperClass(WikiMapper.class);
		wcJob.setMapOutputKeyClass(Text.class);
		wcJob.setMapOutputValueClass(Text.class);

		// /////////////////////////
		// Reducer
		// /////////////////////////
		wcJob.setReducerClass(WikiReducer.class);
		wcJob.setOutputKeyClass(Text.class);
		wcJob.setOutputValueClass(Text.class);

		// /////////////////////////
		// Input Format
		// /////////////////////////
		// Advertencia: Hay dos clases con el mismo nombre,
		// pero no son equivalentes.
		// Se usa, en este caso,
		// org.apache.hadoop.mapreduce.lib.input.TextInputFormat
		TextInputFormat.setInputPaths(wcJob, new Path(entrada));
		wcJob.setInputFormatClass(TextInputFormat.class);

		// //////////////////
		// /Output Format
		// ////////////////////
		TextOutputFormat.setOutputPath(wcJob, new Path(salida));
		wcJob.setOutputFormatClass(TextOutputFormat.class);
		wcJob.waitForCompletion(true);

		System.out.println(wcJob.toString());
		System.out.println("\nEstado del trabajo: "
				+ wcJob.getStatus().getState());
	}
}
