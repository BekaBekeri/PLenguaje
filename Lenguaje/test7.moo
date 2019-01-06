/* 
  Ejemplo creado a raiz de un bug encontrado.
  Nombre de alfabeto inexistente.
  El nombre del alfabeto para el primer automata no existe
*/

environment := VendingEnvironment;

code1 := {: env.notify("V0") :};

define {
    in := 10;
    out := code1;
}

automaton automata (cosa){
    states := V0|code1, V1|code1;
    initial := V0;
    transitions{
        V0|10 -> V1;
    }
}

