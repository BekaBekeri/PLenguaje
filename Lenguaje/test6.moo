/* 
  Ejemplo creado a raiz de un bug encontrado.
  el alfabeto no existe.
*/

environment := VendingEnvironment;

code1 := {: env.notify("V0") :};

define alfabeto1{
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

