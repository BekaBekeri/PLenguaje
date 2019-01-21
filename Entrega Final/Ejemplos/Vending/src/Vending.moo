environment := VendingEnvironment;

code1 := {: env.notify("Huir") :};
code2 := {: env.notify("Parado") :};
code3 := {: env.notify("Andar") :};
code4 := {: env.notify("Atacar") :};


define EnemyAlphabet {
    in := 1, 2, 5, 10; /* Mirando, No Mirando, Lejos, Cerca Atacar */
    out := code1, code2, code3, code4;
}

automaton MeleeEnemy (EnemyAlphabet){
    states := Huir|code1, Parado|code2, Andar|code3, Atacar|code4;
    initial := Parado;
    transitions{
        Parado|1 -> Huir;
		Parado|2 -> Andar; Parado|5 -> Andar;
		Huir|5 -> Parado;
		Huir|1 -> Huir;
		Huir|2 -> Andar;
		Andar|2 -> Andar; Andar|5 -> Andar;
		Andar|1 -> Huir;
		Andar|10 -> Atacar;
		Atacar|1 -> Huir;
		Atacar|10 -> Atacar;
    }
}

