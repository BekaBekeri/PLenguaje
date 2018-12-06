/* 
Test basico con un lenguaje y un automata
Una de las transiciones tiene un estado que no existe en el automata

*/

environment := Domain.Env;

code1 := {: 
        printear("Hello");
        :};
code2 := /* HOLA \asd <> -.*/{:
        /* ADIOS \asd <> -.* */
        printear("Adios");
        :};


define alfabetos1{
    in := 1, 2;
    out := code1, code2;
}

automaton auto1 (alfabetos1){
    states := q0|code1, q1|code2;
    initial := q0;
    transitions{
        q0|1 -> q1;
        q0|2 -> q0;
        q1|1 -> q3;
        q1|2 -> q0;
    }
}