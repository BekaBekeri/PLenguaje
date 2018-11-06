package moomaui.presentation;

import moomaui.domain.DrawableState;
import moomaui.domain.DrawableTransition;
import moomaui.domain.MooreMachine;

public class Machines {

	public MachineCanvas examplemachine() {
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
	}

	public static MachineCanvas examplemachine2() {
		MooreMachine<DrawableState, DrawableTransition> machine = new MooreMachine<DrawableState, DrawableTransition>();
		DrawableState st1 = new DrawableState("1");
		DrawableState st2 = new DrawableState("2");

		DrawableTransition t1 = new DrawableTransition(st1, st2);
		
		machine.addState(st1);
		machine.addState(st2);
		machine.addTransition(t1);

		MachineCanvas canvas = new MachineCanvas(machine);
		canvas.setMachineName("Maquina2");
		return canvas;
	}
}
