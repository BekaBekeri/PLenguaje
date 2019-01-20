package moomaui.domain;
public class Machines {
	public static IMooreMachine automaton1(){
		MooreMachine machine = new MooreMachine();
		State st1 = new State("Parado"); st1.setOutput((GameAIEnvironment env) -> System.out.println("Parado"));
		State st2 = new State("Huir"); st2.setOutput((GameAIEnvironment env) -> System.out.println("Huyendo"));
		State st3 = new State("Acercarse"); st3.setOutput((GameAIEnvironment env) -> System.out.println("Acercandose"));
		State st4 = new State("Atacar"); st4.setOutput((GameAIEnvironment env) -> System.out.println("Atacando"));

		Transition t1 = new Transition(st1, st2, "1");
		Transition t2 = new Transition(st1, st3, "2");
		Transition t3 = new Transition(st2, st1, "5");
		Transition t4 = new Transition(st2, st2, "1");
		Transition t5 = new Transition(st2, st3, "2");
		Transition t6 = new Transition(st3, st3, "2");
		Transition t7 = new Transition(st3, st2, "1");
		Transition t8 = new Transition(st3, st4, "10");
		Transition t9 = new Transition(st4, st2, "1");
		Transition t10 = new Transition(st4, st4, "10");

		machine.addState(st1);
		machine.addState(st2);
		machine.addState(st3);
		machine.addState(st4);

		machine.addTransition(t1);
		machine.addTransition(t2);
		machine.addTransition(t3);
		machine.addTransition(t4);
		machine.addTransition(t5);
		machine.addTransition(t6);
		machine.addTransition(t7);
		machine.addTransition(t8);
		machine.addTransition(t9);
		machine.addTransition(t10);
		
		machine.setMachineName("Melee");
		machine.setInitialState(st1);
		return machine;
	}
}