grammar mooma;

/*
 * Lexer Rules
 */
 
/*
 * Fragments
 */  
 

fragment Letra
  : ('a' .. 'z') | ('A' .. 'Z')
  ;

fragment Digito
  : ('0' .. '9')
  ;

fragment A
  : 'A' | 'a'
  ;
  
fragment U
  : 'U' | 'u'
  ;
 
fragment T
  : 'T' | 't'
  ;
 
fragment O
  : 'O' | 'o'
  ; 
  
fragment M
  : 'M' | 'm'
  ;
  
fragment N
  : 'N' | 'n'
  ;
  
fragment I
  : 'I' | 'i'
  ;
  
fragment R
  : 'R' | 'r'
  ;
  
fragment S
  : 'S' | 's'
  ;
  
fragment L
  : 'L' | 'l'
  ;
  
fragment E
  : 'E' | 'e'
  ;
  
fragment D
  : 'D' | 'd'
  ;
  
fragment F
  : 'F' | 'f'
  ;
  
fragment V
  : 'V' | 'v'
  ;
 
/*
 * Symbols
 */

Flecha
  : '->'
  ;

Separator
  : '|'
  ;

Par_derecho
  : ')'
  ;
  
Par_izquierdo
  : '('
  ;
  
Llave_izquierda
  : '{'
  ;

Llave_derecha
  : '}'
  ;

Punto_y_coma
  :	';'
  ;

Asignacion
  :	':='
  ;

Coma
  : ','
  ;
  
/*
 * Keywords
 */
 
Auto
  : A U T O M A T O N
  ;
  
In
  : I N
  ;

Out
  : O U T
  ;
  
Trans
  : T R A N S I T I O N S
  ;

Ini
  : I N I T I A L
  ;
  
Sta
  : S T A T E S
  ;

Def
  : D E F I N E
  ;
  
Env
  : E N V I R O N M E N T
  ;
  
/*
 * Generic tokens
 */

Clase
  : Letra+ ('.' Letra+)*
  ;
 
Ident
  : Letra (Letra | Digito)*
  ;

Evento
  : ('1' .. '9') Digito*
  ;

Codigo
  : '{:' (.)*? ':}'
  ;

Comentario
  : '/*' (.)*? '*/' -> skip
  ;

/*
 * Spaces and tabs.
 * (Skipped; only maintained inside token Codigo)
 */ 
  
WS : [ \r\t\n]+ -> skip ;


/*
 * Parser rules
 */

program
  : environment l_output l_define l_automaton
  ;
  
environment
  : Env Asignacion Clase Punto_y_coma
  | ;

l_output
  : output l_output
  | ;

output
  : Ident Asignacion Codigo Punto_y_coma
  ;

l_define
  : define l_define
  | ;

define
  : Def Ident Llave_izquierda entrada salida Llave_derecha
  ;


entrada
  : In Asignacion l_evento Punto_y_coma
  ;

salida
  : Out Asignacion l_ident Punto_y_coma
  ;

l_evento
  : Evento l_evento_fact
  ;

l_evento_fact
  : Coma l_evento
  | ;

l_ident
  : Ident l_ident_fact
  ;

l_ident_fact
  : Coma l_ident
  | ;
  
l_automaton
  : automaton l_automaton
  | ;

automaton
  : Auto Ident Par_izquierdo Ident Par_derecho Llave_izquierda states initial transitions Llave_derecha
  ;
  
states
  : Sta Asignacion l_states Punto_y_coma
  ;

l_states
  : Ident Separator Ident l_states_fact
  ;

l_states_fact
  : Coma l_states
  | ;

initial
  : Ini Asignacion Ident Punto_y_coma
  ;

transitions
  : Trans Llave_izquierda l_transitions Llave_derecha
  ;

l_transitions
  : Ident Separator l_evento Flecha Ident Punto_y_coma l_transitions
  | ; 
 
