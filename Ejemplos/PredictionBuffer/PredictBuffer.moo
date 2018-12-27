/* 
Prediction Buffer para 2 bits
*/

environment := Env ;

code1 := {: 
        Env.predictTaken()
        :};
code2 := {:
        Env.predictNotTaken()
        :};


define alfabeto1{
    in := 1, 2;
    out := code1, code2;
}

automaton automata1 (alfabeto1){
    states := q0|code2, q1|code2, q2|code1, q3|code1;
    initial := q2;
    transitions{
        q0|1 -> q0;
        q0|2 -> q1;
        q1|1 -> q0;
        q1|2 -> q2;
        q2|1 -> q3;
        q2|2 -> q2;
        q3|1 -> q0;
        q3|2 -> q2;
    }
}

