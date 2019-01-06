/* 
Identificador del automata sin numeros, actualmente da error
porque a 'automata' se le asigna el token Clase
*/

environment := VendingEnvironment;

code1 := {: env.notify("V0") :};

define alfabeto1{
    in := 10;
    out := code1;
}

automaton automata (alfabeto1){
    states := V0|code1, V1|code1;
    initial := V0;
    transitions{
        V0|10 -> V1;
    }
}

