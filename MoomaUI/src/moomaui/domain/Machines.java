package moomaui.domain;

public class Machines {
	public static IMooreMachine exampleWeno() {
		MooreMachine machine = new MooreMachine(); machine.setMachineName("Vending Machine");
		
		State st0 = new State("0 Sin"); st0.setOutput((IEnvironment env) -> System.out.println("0 centimos"));
        State st1 = new State("10 Sin"); st1.setOutput((IEnvironment env) -> System.out.println("10 centimos"));
        State st2 = new State("20 Sin"); st2.setOutput((IEnvironment env) -> System.out.println("20 centimos"));
        State st3 = new State("30 Sin"); st3.setOutput((IEnvironment env) -> System.out.println("30 centimos"));

		State st4 = new State("0 Be D"); st4.setOutput((IEnvironment env) -> System.out.println("0 centimos, he vendido una bebida y he devuelto 10 ctms"));
		State st5 = new State("0 Be"); st5.setOutput((IEnvironment env) -> System.out.println("0 centimos y he vendido una bebida"));
        State st6 = new State("10 Be"); st6.setOutput((IEnvironment env) -> System.out.println("10 centimos"));
        State st7 = new State("20 Be"); st7.setOutput((IEnvironment env) -> System.out.println("20 centimos"));
        State st8 = new State("30 Be"); st8.setOutput((IEnvironment env) -> System.out.println("30 centimos"));

		State st9 = new State("0 Bo"); st9.setOutput((IEnvironment env) -> System.out.println("0 centimos"));
        State st10 = new State("10 Bo"); st10.setOutput((IEnvironment env) -> System.out.println("10 centimos"));
        State st11 = new State("20 Bo"); st11.setOutput((IEnvironment env) -> System.out.println("20 centimos"));
        State st12 = new State("30 Bo"); st12.setOutput((IEnvironment env) -> System.out.println("30 centimos"));

        State st13 = new State("0 To D"); st13.setOutput((IEnvironment env) -> System.out.println("0 centimos y he devuelto 10 ctms"));
        State st14 = new State("0 To"); st14.setOutput((IEnvironment env) -> System.out.println("0 ctms y he vendido todo"));

		Transition t0 = new Transition(st0, st1, "10");
		Transition t1 = new Transition(st1, st2, "10");
		Transition t2 = new Transition(st2, st3, "10");

		Transition t3 = new Transition(st2, st5, "20");
		Transition t4 = new Transition(st3, st4, "20");
		Transition t5 = new Transition(st3, st9, "30");
		

		Transition t6 = new Transition(st4, st6, "10");
		Transition t7 = new Transition(st5, st6, "10");
		Transition t8 = new Transition(st6, st7, "10");
		Transition t9 = new Transition(st7, st8, "10");
		
		Transition t10 = new Transition(st8, st14, "30");
		
		
		Transition t11 = new Transition(st9, st10, "10");
		Transition t12 = new Transition(st10, st11, "10");
		Transition t13 = new Transition(st11, st12, "10");
		
		Transition t14 = new Transition(st11, st14, "20");		
		Transition t15 = new Transition(st12, st13, "20");
		
		        
		machine.addState(st0);
		machine.addState(st1);
		machine.addState(st2);
        machine.addState(st3);
        machine.addState(st4);
        machine.addState(st5);
        machine.addState(st6);
        machine.addState(st7);
        machine.addState(st8);
        machine.addState(st9);
        machine.addState(st10);
        machine.addState(st11);
        machine.addState(st12);
        machine.addState(st13);
        machine.addState(st14);
        
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
        machine.addTransition(t14);
        machine.addTransition(t15);
        
        machine.setInitialState(st0);

		return machine;
	}
}
