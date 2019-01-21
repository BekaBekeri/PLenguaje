//* --------------------------Seccion codigo-usuario ------------------------*/

import java_cup.runtime.*;
import java.io.*;
import java.util.*;


%%

%standalone

/* -----------------Seccionde opciones y declaraciones -----------------*/
/*--OPCIONES --*/
/* Nombre de la clase generada para el analizadorlexico*/

%class analex
%cupsym sym
/* Indicar funcionamientoautonomo*/


/* Posibilitar acceso a la columna y fila actual de analisis*/

%line

%column

/* Habilitar la compatibilidad con el interfaz CUP para el generador sintáctico */

%cup

/*--DECLARACIONES --*/


%{ /* Principio Declaraciones */

/* Crear un nuevo objeto java_cup.runtime.Symbol con informacion sobre
   el token actual sin valor */


 private Symbol symbol(int type) {
   return new Symbol(type,yyline,yycolumn);
 }/* Fin symbol */

 private Symbol symbol(int type,Object value){
   return new Symbol(type,yyline,yycolumn,value);
 }


%} /* Fin Declaraciones */


%%
/* ------------------------Seccionde reglaslexicas----------------------*/
<YYINITIAL> {

"->" {return symbol(sym.FLECHA);}

"|" {return symbol(sym.SEPARATOR);}

")" {return symbol(sym.PAR_DERECHO);}

"(" {return symbol(sym.PAR_IZQUIERDO);}

"{" {return symbol(sym.LLAVE_IZQUIERDA);}

"}" {return symbol(sym.LLAVE_DERECHA);}

";" {return symbol(sym.PUNTO_Y_COMA);}

":=" {return symbol(sym.ASIGNACION);}

"," {return symbol(sym.COMA);}

[eE][nN][vV][iI][rR][oO][nN][mM][eE][nN][tT] {return symbol(sym.ENV,new String(yytext()));}

[aA][uU][tT][oO][mM][aA][tT][oO][nN] {return symbol(sym.AUTO,new String(yytext()));}

[iI][nN] {return symbol(sym.IN,new String(yytext()));}

[oO][uU][tT] {return symbol(sym.OUT,new String(yytext()));}

[tT][rR][aA][nN][sS][iI][tT][iI][oO][nN][sS] {return symbol(sym.TRANS,new String(yytext()));}

[iI][nN][iI][tT][iI][aA][lL] {return symbol(sym.INI,new String(yytext()));}

[sS][tT][aA][tT][eE][sS] {return symbol(sym.STA,new String(yytext()));}

[dD][eE][fF][iI][nN][eE]  {return symbol(sym.DEF,new String(yytext()));}

[a-zA-Z]+[a-zA-Z0-9]* {return symbol(sym.IDENT,new String(yytext()));}

[0-9]+  {return symbol(sym.EVENTO,new String(yytext()));}

"{:"[^":}"]*":}" {return symbol(sym.CODIGO,new String(yytext()));}

"/*"[^"*/"]*"*/" {}

[ \t\n\r] {}

}/* FIN YYIden */
