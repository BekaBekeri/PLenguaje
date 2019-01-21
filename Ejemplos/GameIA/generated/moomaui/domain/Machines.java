package moomaui.domain;
public class Machines {
	public static IMooreMachine MeleeEnemy(){
		MooreMachine machine = new MooreMachine();
		machine.setMachineName("MeleeEnemy");
		State Parado = new State("Parado");
		Parado.setOutput((GameAIEnvironment env) -> env.notify("Parado"));
		machine.addState(Parado);
		State Andar = new State("Andar");
		Andar.setOutput((GameAIEnvironment env) -> env.notify("Andar"));
		machine.addState(Andar);
		State Huir = new State("Huir");
		Huir.setOutput((GameAIEnvironment env) -> env.notify("Huir"));
		machine.addState(Huir);
		State Atacar = new State("Atacar");
		Atacar.setOutput((GameAIEnvironment env) -> env.notify("Atacar"));
		machine.addState(Atacar);
		Transition t1 = new Transition (Parado,Huir,"1");
		machine.addTransition(t1);
		Transition t2 = new Transition (Parado,Andar,"2");
		machine.addTransition(t2);
		Transition t3 = new Transition (Parado,Andar,"5");
		machine.addTransition(t3);
		Transition t4 = new Transition (Huir,Parado,"5");
		machine.addTransition(t4);
		Transition t5 = new Transition (Huir,Huir,"1");
		machine.addTransition(t5);
		Transition t6 = new Transition (Huir,Andar,"2");
		machine.addTransition(t6);
		Transition t7 = new Transition (Andar,Andar,"2");
		machine.addTransition(t7);
		Transition t8 = new Transition (Andar,Andar,"5");
		machine.addTransition(t8);
		Transition t9 = new Transition (Andar,Huir,"1");
		machine.addTransition(t9);
		Transition t10 = new Transition (Andar,Atacar,"10");
		machine.addTransition(t10);
		Transition t11 = new Transition (Atacar,Huir,"1");
		machine.addTransition(t11);
		Transition t12 = new Transition (Atacar,Atacar,"10");
		machine.addTransition(t12);
		machine.setInitialState(Parado);
		return machine;
	}
}