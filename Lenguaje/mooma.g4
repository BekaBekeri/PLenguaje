grammar mooma;

program
  : l_output
  ;

l_output
  : output l_output
  | ;

output
  : ident Asignacion codigo Punto_y_coma
  ;

Punto_y_coma
	:	';'
	;

Asignacion
	:	':='
	;

ident
  : Letra (Letra | Digito)*
  ;

Letra
  : ('a' .. 'z') | ('A' .. 'Z')
  ;

Digito
  : ('0' .. '9')
  ;

codigo
  : Inicio_codigo Ascii Fin_codigo
  ;

Inicio_codigo
	:	'{:'
	;

Fin_codigo
	:	':}'
	;

Ascii
  : . -> skip
  ;
