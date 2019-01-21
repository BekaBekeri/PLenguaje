// Driver for parser

import java_cup.runtime.*;
import java.io.*;



class Main{

  static boolean do_debug_parse=true;

  static public void main(String[]args)throws java.io.IOException{

		parser parser_obj = new parser(new analex(new FileReader(args[0])));

		Symbol parse_tree = null;

		try{
			if(do_debug_parse)
				parse_tree=parser_obj.debug_parse();
			else
				parse_tree=parser_obj.parse();
		}catch(Exception e) {
			System.out.println("Análisis INCORRECTO !!");
		}
	 	finally{System.out.println("Fin del Análisis");}
}
}
