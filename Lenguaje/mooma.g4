grammar mooma;

program
  : l_output l_define l_automaton
  ;

l_output
  : output l_output
  | ;

output
  : Ident Asignacion codigo Punto_y_coma
  ;

l_define
  : define l_define
  | ;

define
  : Def Ident Llave_izquierda entrada salida Llave_derecha
  ;
Def
  : 'define'
  ;

entrada
  : In Asignacion l_evento Punto_y_coma
  ;

salida
  : Out Asignacion l_ident Punto_y_coma
  ;

In
  : 'in'
  ;

Out
  : 'out'
  ;

l_automaton
  : automaton l_automaton
  | ;

l_evento
  : Evento l_evento_fact
  ;

l_evento_fact
  : Coma l_evento
  | ;

automaton
  : Auto Ident Par_izquierdo Ident Par_derecho Llave_izquierda states Punto_y_coma initial Punto_y_coma transitions Llave_derecha
  ;

l_ident
  : Ident l_ident_fact
  ;

l_ident_fact
  : Coma l_ident
  | ;

Auto
  : 'automaton'
  ;

Trans
  : 'transitions'
  ;

Ini
  : 'initial'
  ;
Sta
  :'states'
  ;

states
  : Sta Asignacion l_states
  ;

l_states
  : Ident Separator Ident l_states_fact
  ;

l_states_fact
  : Coma l_states
  | ;

initial
  : Ini Asignacion Ident
  ;

transitions
  : Trans Llave_izquierda l_transitions Llave_derecha
  ;

l_transitions
  : Ident Separator l_evento Flecha Ident Punto_y_coma l_transitions
  | ;

Ident
  : Letra (Letra | Digito)*
  ;

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

Coma
  : ','
  ;

Evento
  : ('1' .. '9') Digito*
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

Letra
  : ('a' .. 'z') | ('A' .. 'Z')
  ;

Digito
  : ('0' .. '9')
  ;

Inicio_codigo
	:	'{:'
	;

Fin_codigo
	:	':}'
	;

ascii
  : .*?
  ;

codigo
  : Inicio_codigo ascii Fin_codigo
  ;

WS : [ \r\t\n]+ -> skip ;
