package moomaui.domain;

public class Machines {

	/*public static MachineCanvas examplemachine() {
		MooreMachine<State, Transition> machine = new MooreMachine<State, Transition>();
		State st1 = new State("1");
		State st2 = new State("2");
		State st3 = new State("3");
		State st4 = new State("4");
		State st5 = new State("5");

		Transition t1 = new Transition(st1, st2, "maquina1");
		Transition t2 = new Transition(st1, st3, "adios");
		Transition t3 = new Transition(st4, st5);
		Transition t4 = new Transition(st3, st1);
		
		st1.setOutput(() -> System.out.println("ola"));
		
		machine.addState(st1);machine.addState(st2);machine.addState(st3);machine.addState(st4);machine.addState(st5);
		machine.addTransition(t1);machine.addTransition(t2);machine.addTransition(t3);machine.addTransition(t4);
		
		MachineCanvas canvas = new MachineCanvas(machine);
		canvas.setMachineName("Maquina1");
		return canvas;
	}*/

	/*public static IMooreMachine examplemachine2() {
		MooreMachine machine = new MooreMachine(); machine.setMachineName("Example Machine");
		
		State st1 = new State("q0"); st1.setOutput(() -> System.out.println("Primer Estado"));
		State st2 = new State("q1"); st2.setOutput(() -> System.out.println("Segundo Estado"));
		State st3 = new State("q2"); st3.setOutput(() -> System.out.println("Tercer Estado"));

		Transition t1 = new Transition(st1, st2, new String[] {"1", "2"});
		Transition t2 = new Transition(st2, st1, "10");
		Transition t3 = new Transition(st1, st3, "5");
		
		machine.addState(st1);
		machine.addState(st2);
		machine.addState(st3);
		machine.addTransition(t1);
		machine.addTransition(t2);
		machine.addTransition(t3);

		machine.setInitialState(st1);

		return machine;
	}*/

	public static IMooreMachine binaryNOT() {
		MooreMachine machine = new MooreMachine(); machine.setMachineName("Binary NOT");
		
		State st0 = new State("q0"); st0.setOutput(() -> System.out.print(""));
		State st1 = new State("q1"); st1.setOutput(() -> System.out.print("0"));
		State st2 = new State("q2"); st2.setOutput(() -> System.out.print("1"));

		Transition t1 = new Transition(st0, st1, "1");
		Transition t2 = new Transition(st0, st2, "0");
		Transition t3 = new Transition(st1, st1, "1");
		Transition t4 = new Transition(st2, st2, "0");
		Transition t5 = new Transition(st1, st2, "0");
		Transition t6 = new Transition(st2, st1, "1");
		
		machine.addState(st0);
		machine.addState(st1);
		machine.addState(st2);
		machine.addTransition(t1);
		machine.addTransition(t2);
		machine.addTransition(t3);
		machine.addTransition(t4);
		machine.addTransition(t5);
		machine.addTransition(t6);

		machine.setInitialState(st0);

		return machine;
	}
	
	/*public static IMooreMachine examplemachinetrafficLights() {
		MooreMachine machine = new MooreMachine(); machine.setMachineName("Kill Traffic Lights");
		State st0 = new State("q0"); st0.setOutput(() -> System.out.println("VRVR"));
        State st1 = new State("q1"); st1.setOutput(() -> System.out.println("ARAR"));
        State st2 = new State("q2"); st2.setOutput(() -> System.out.println("RRRV"));
        State st3 = new State("q3"); st3.setOutput(() -> System.out.println("VRAR"));
        State st4 = new State("q4"); st4.setOutput(() -> System.out.println("RRRA"));
        State st5 = new State("q5"); st5.setOutput(() -> System.out.println("VARR"));
        State st6 = new State("q6"); st6.setOutput(() -> System.out.println("VVAR"));
        State st7 = new State("q7"); st7.setOutput(() -> System.out.println("AARR"));

		Transition t0 = new Transition(st0, st0, "1");
        Transition t1 = new Transition(st0, st1, "2");
        Transition t3 = new Transition(st0, st1, "4");
        Transition t4 = new Transition(st1, st2, "5");
        Transition t5 = new Transition(st2, st2, "2");
        Transition t6 = new Transition(st2, st2, "4");
        Transition t7 = new Transition(st0, st3, "3");
        Transition t8 = new Transition(st2, st4, "1");
        Transition t9 = new Transition(st2, st4, "3");
        Transition t10 = new Transition(st4, st6, "3");
        Transition t11 = new Transition(st4, st6, "4");
        Transition t12 = new Transition(st3, st6, "5");
        Transition t13 = new Transition(st6, st5, "1");
        Transition t14 = new Transition(st5, st0, "5");
        Transition t15 = new Transition(st6, st7, "2");
        Transition t16 = new Transition(st7, st2, "5");
        Transition t17 = new Transition(st6, st6, "3");
        Transition t18 = new Transition(st6, st6, "4");
        
		machine.addState(st0);
		machine.addState(st1);
		machine.addState(st2);
        machine.addState(st3);
        machine.addState(st4);
        machine.addState(st5);
        machine.addState(st6);
        machine.addState(st7);
        
        machine.addTransition(t0);
		machine.addTransition(t1);
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
        machine.addTransition(t16);
        machine.addTransition(t17);
        machine.addTransition(t18);
        

		return machine;
	}*/
}
