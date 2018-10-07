define alfabetos1{
    in := 1, 2;
    out := code1, code2;
}

automaton cosa1 (alfabetos1){
    states := q0|code1, q1|code2;
    initial := q0;
    transitions{
        q0 -> q1|1, q0|2;
        q1 -> q1|1, q0|2;
    }
}

code1 := {: 
        System.out.println("Hello");
        :};
code2 := {:
        System.out.println("Goodbye");
        :}
