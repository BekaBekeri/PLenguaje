/* 
Test con 2 lenguajes y tres automatas
Sin Fallos

*/

environment := Env;

code1 := {: 
        printear("Hello");
        :};
code2 := /* HOLA \asd <> -.*/{:
        /* ADIOS \asd <> -.* */
        printear("Adios");
        :};
code3 := {:
            printear("Jaja que loco xd");
        :};

define alfabetos1{
    in := 1, 2;
    out := code1, code2;
}

define alfabetos2{
    in := 10, 20, 30;
    out := code1, code3;
}

automaton auto1 (alfabetos1){
    states := q0|code1, q1|code2;
    initial := q0;
    transitions{
        q0|1 -> q1;
        q0|2 -> q0;
        q1|1 -> q1;
        q1|2 -> q0;
    }
}

automaton auto2 (alfabetos1){
    /* Comentario random sobre este automata */
    states := estate1|code1, estate2|code2, estate3|code2;
    initial := estate1;
    transitions{
        estate1|1 -> estate2;
        estate2|2 -> estate3;
        estate3|1 -> estate1;
    }
}

automaton auto3 (alfabetos2){
    states := a0|code1, a1|code3, a2|code1, a3|code3;
    initial := a2;
    transitions{
        a0|10 -> a1;
        a0|20 -> a2;
        a0|30 -> a3;
        a1|20 -> a0;
        a1|30 -> a2;
        a2|10 -> a0;
        a2|30 -> a3;
        a3|10 -> a0;
        a3|20 -> a1;
    }
}

