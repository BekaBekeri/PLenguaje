code1 := {: 
        System.out.println("Hello");
        :};
code2 := /* HOLA \asd <> -.*/{:
        /* ADIOS \asd <> -.* */
        System.out.println("Goodbye");
        :};


define alfabetos1{
    in := 1, 2;
    out := code1, code2
}

automaton cosa1 (alfabetos1){
    states := q0|code1, q1|code2;
    initial := q0;
    transitions{
        q0|1 -> q1;
        q0|2 -> q0;
        q1|1 -> q1;
        q1|2 -> q0;
    }
}

