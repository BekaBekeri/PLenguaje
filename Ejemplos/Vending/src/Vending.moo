environment := VendingEnvironment;

code1 := {: env.notify("V0") :};
code2 := {: env.notify("V10") :};
code3 := {: env.notify("V20") :};
code4 := {: env.notify("V30") :};
code5 := {: env.notify("V0BeD") :};
code6 := {: env.notify("V0Be") :};
code7 := {: env.notify("V10Be") :};
code8 := {: env.notify("V20Be") :};
code9 := {: env.notify("V30Be") :};
code10 := {: env.notify("V0Bo") :};
code11 := {: env.notify("V10Bo") :};
code12 := {: env.notify("V20Bo") :};
code13 := {: env.notify("V30Bo") :};
code14 := {: env.notify("V0ToD") :};
code15 := {: env.notify("V0To") :};


define alfabeto1{
    in := 10, 20, 30;
    out := code1, code2, code3, code4, code5, code6, code7, code8, code9, code10, code11, code12, code13, code14, code15;
}

automaton automaton1 (alfabeto1){
    states := V0|code1, V10|code2, V20|code3, V30|code4, V0BeD|code5, V0Be|code6, V10Be|code7, V20Be|code8, V30Be|code9, V0Bo|code10, V10Bo|code11, V20Bo|code12, V30Bo|code13, V0ToD|code14, V0To|code15;
    initial := V0;
    transitions{
        V0|10 -> V10;
		V10|10 -> V20;
		V20|10 -> V30;
		V20|20 -> V0Be;
		V30|20 -> V0BeD;
		V30|30 -> V0Bo;
		
		V0BeD|10 -> V10Be;
		V0Be|10 -> V10Be;
		V10Be|10 -> V20Be;
		V20Be|10 -> V30Be;
		V30Be|30 -> V0To;
		
		V0Bo|10 -> V10Bo;
		V10Bo|10 -> V20Bo;
		V20Bo|10 -> V30Bo;
		V20Bo|20 -> V0To;
		V30Bo|20 -> V0ToD;
    }
}

