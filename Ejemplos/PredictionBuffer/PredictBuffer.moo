/* 
Prediction Buffer para 2 bits
*/

environment := Environment;

taken := {: 
        Environment.predictTaken();
        :};
notTaken := {:
        Environment.predictNotTaken();
        :};


define alfabeto1{
    in := 0, 1;
    out := taken, notTaken;
}

automaton buffer (alfabeto1){
    states := q0|notTaken, q1|notTaken, q2|taken, q3|taken;
    initial := q2;
    transitions{
        q0|0 -> q0;
        q0|1 -> q1;
        q1|0 -> q0;
        q1|1 -> q2;
        q2|0 -> q3;
        q2|1 -> q2;
        q3|0 -> q0;
        q3|1 -> q2;
    }
}

