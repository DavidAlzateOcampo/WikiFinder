package co.edu.uniandes.wiki.util;

public class Utilidades {

	public static String DTD_GRAMMAR = "http://apache.org/xml/features/nonvalidating/load-dtd-grammar";
	public static String EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
	private static String PERSONA_EXP_T = "(.)*Infobox(.)*(person|biography|officeholder|scientist|president|politician|engineer|artist|actor|astronaut|athletics|comedian|Doctor|economist|Ecumenical";
	public static String PERSONA_EXP = PERSONA_EXP_T + "|football|minister|Politician|President|professional|publisher|royalty|monarch|Senator|writer)(\\w|\\s|\\n|\\r)?\\|";
	public static String PAIS_EXP = "(.)*Infobox country(\\s|\\n|\\r)?\\|";
	public static String ELEMENTOS_EXP = "((\\|(.)*\\|)|(\\[(.)*\\|)|(\\|(.)*\\])|(\\[(.)*\\]))";
	public static String ABRE_CORCHETE = "[";
	public static String CIERRA_CORCHETE = "]";
	public static String CADENA_VACIA = "";
	public static String COMA = ",";
	public static String PUNTO_Y_COMA = ";";
}


