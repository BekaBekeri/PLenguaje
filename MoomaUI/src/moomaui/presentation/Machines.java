package moomaui.presentation;

import moomaui.domain.DrawableState;
import moomaui.domain.DrawableTransition;
import moomaui.domain.MooreMachine;

public class Machines {

	/*public static MachineCanvas examplemachine() {
		MooreMachine<DrawableState, DrawableTransition> machine = new MooreMachine<DrawableState, DrawableTransition>();
		DrawableState st1 = new DrawableState("1");
		DrawableState st2 = new DrawableState("2");
		DrawableState st3 = new DrawableState("3");
		DrawableState st4 = new DrawableState("4");
		DrawableState st5 = new DrawableState("5");

		DrawableTransition t1 = new DrawableTransition(st1, st2, "maquina1");
		DrawableTransition t2 = new DrawableTransition(st1, st3, "adios");
		DrawableTransition t3 = new DrawableTransition(st4, st5);
		DrawableTransition t4 = new DrawableTransition(st3, st1);
		
		st1.setAction(() -> System.out.println("ola"));
		
		machine.addState(st1);machine.addState(st2);machine.addState(st3);machine.addState(st4);machine.addState(st5);
		machine.addTransition(t1);machine.addTransition(t2);machine.addTransition(t3);machine.addTransition(t4);
		
		MachineCanvas canvas = new MachineCanvas(machine);
		canvas.setMachineName("Maquina1");
		return canvas;
	}*/

	public static MachineCanvas examplemachine2() {
		MooreMachine<DrawableState, DrawableTransition> machine = new MooreMachine<DrawableState, DrawableTransition>();
		DrawableState st1 = new DrawableState("1"); st1.setAction(() -> System.out.println("Primer Estado"));
		DrawableState st2 = new DrawableState("2"); st1.setAction(() -> System.out.println("Segundo Estado"));

		DrawableTransition t1 = new DrawableTransition(st1, st2, "2");
		DrawableTransition t2 = new DrawableTransition(st2, st1, "1");
		
		machine.addState(st1);
		machine.addState(st2);
		machine.addTransition(t1);
		machine.addTransition(t2);

		MachineCanvas canvas = new MachineCanvas(machine);
		canvas.setMachineName("Maquina2");
		return canvas;
	}
	
	public static MachineCanvas examplemachinetrafficLights() {
		MooreMachine<DrawableState, DrawableTransition> machine = new MooreMachine<DrawableState, DrawableTransition>();
		DrawableState st0 = new DrawableState("q0"); st0.setAction(() -> System.out.println("VRVR"));
        DrawableState st1 = new DrawableState("q1"); st1.setAction(() -> System.out.println("ARAR"));
        DrawableState st2 = new DrawableState("q2"); st2.setAction(() -> System.out.println("RRRV"));
        DrawableState st3 = new DrawableState("q3"); st3.setAction(() -> System.out.println("VRAR"));
        DrawableState st4 = new DrawableState("q4"); st4.setAction(() -> System.out.println("RRRA"));
        DrawableState st5 = new DrawableState("q5"); st5.setAction(() -> System.out.println("VARR"));
        DrawableState st6 = new DrawableState("q6"); st6.setAction(() -> System.out.println("VVAR"));
        DrawableState st7 = new DrawableState("q7"); st7.setAction(() -> System.out.println("AARR"));

		DrawableTransition t0 = new DrawableTransition(st0, st0, "1");
        DrawableTransition t1 = new DrawableTransition(st0, st1, "2");
        DrawableTransition t3 = new DrawableTransition(st0, st1, "4");
        DrawableTransition t4 = new DrawableTransition(st1, st2, "5");
        DrawableTransition t5 = new DrawableTransition(st2, st2, "2");
        DrawableTransition t6 = new DrawableTransition(st2, st2, "4");
        DrawableTransition t7 = new DrawableTransition(st0, st3, "3");
        DrawableTransition t8 = new DrawableTransition(st2, st4, "1");
        DrawableTransition t9 = new DrawableTransition(st2, st4, "3");
        DrawableTransition t10 = new DrawableTransition(st4, st6, "3");
        DrawableTransition t11 = new DrawableTransition(st4, st6, "4");
        DrawableTransition t12 = new DrawableTransition(st3, st6, "5");
        DrawableTransition t13 = new DrawableTransition(st6, st5, "1");
        DrawableTransition t14 = new DrawableTransition(st5, st0, "5");
        DrawableTransition t15 = new DrawableTransition(st6, st7, "2");
        DrawableTransition t16 = new DrawableTransition(st7, st2, "5");
        DrawableTransition t17 = new DrawableTransition(st6, st6, "3");
        DrawableTransition t18 = new DrawableTransition(st6, st6, "4");
        
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
        

		MachineCanvas canvas = new MachineCanvas(machine);
		canvas.setMachineName("Kill Traffic Lights");
		return canvas;
	}
}
