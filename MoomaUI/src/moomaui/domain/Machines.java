package moomaui.domain;

public class Machines {
	public static IMooreMachine exampleWeno() {
		MooreMachine machine = new MooreMachine(); machine.setMachineName("Vending Machine");
		State st0 = new State("0"); st0.setOutput(() -> System.out.println("0 centimos"));
        State st1 = new State("5"); st1.setOutput(() -> System.out.println("5 centimos"));
        State st2 = new State("10"); st2.setOutput(() -> System.out.println("10 centimos"));
        State st3 = new State("15"); st3.setOutput(() -> System.out.println("15 centimos"));
        State st4 = new State("20"); st4.setOutput(() -> System.out.println("20 centimos"));
        State st5 = new State("25"); st5.setOutput(() -> System.out.println("25 centimos"));
        State st6 = new State("30"); st6.setOutput(() -> System.out.println("30 centimos"));

		Transition t0 = new Transition(st0, st1, "5");
		Transition t1 = new Transition(st0, st2, "10");
		Transition t2 = new Transition(st1, st2, "5");
		Transition t3 = new Transition(st1, st3, "10");
		Transition t4 = new Transition(st2, st3, "5");
		Transition t5 = new Transition(st2, st4, "10");
		Transition t6 = new Transition(st3, st4, "5");
		Transition t7 = new Transition(st3, st5, "10");
		Transition t8 = new Transition(st4, st5, "5");
		Transition t9 = new Transition(st5, st6, "5");
		Transition t10 = new Transition(st4, st6, "10");

		Transition t11 = new Transition(st4, st0, "20");
		Transition t12 = new Transition(st5, st0, "20");
		Transition t13 = new Transition(st6, st0, new String[] {"30", "20"});
        
		machine.addState(st0);
		machine.addState(st1);
		machine.addState(st2);
        machine.addState(st3);
        machine.addState(st4);
        machine.addState(st5);
        machine.addState(st6);
        
        machine.addTransition(t0);
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
        machine.addTransition(t11);
        machine.addTransition(t12);
        machine.addTransition(t13);
        
        machine.setInitialState(st0);

		return machine;
	}
}
