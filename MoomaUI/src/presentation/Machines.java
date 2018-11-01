package presentation;

import domain.DrawableState;
import domain.DrawableTransition;
import domain.MooreMachine;

public class Machines {


	public static MachineCanvas extracted() {
		MooreMachine<DrawableState, DrawableTransition> machine = new MooreMachine<DrawableState, DrawableTransition>();
		int[][] coords = MachineCanvas.generateRegularPolygon(5, 120);
		for (int i = 0; i < coords.length; i++) {
			int[] coord = coords[i];
			coord[0] += 300;
			coord[1] += 300;
		}
		DrawableState st1 = new DrawableState(machine.getNextID(), "1"); st1.setCoords(coords[0]);
		DrawableState st2 = new DrawableState(machine.getNextID(), "2"); st2.setCoords(coords[1]);
		DrawableState st3 = new DrawableState(machine.getNextID(), "3"); st3.setCoords(coords[2]);
		DrawableState st4 = new DrawableState(machine.getNextID(), "4"); st4.setCoords(coords[3]);
		DrawableState st5 = new DrawableState(machine.getNextID(), "5"); st5.setCoords(coords[4]);

		DrawableTransition t1 = new DrawableTransition(st1, st2, "maquina1");
		DrawableTransition t2 = new DrawableTransition(st1, st3, "adios");
		DrawableTransition t3 = new DrawableTransition(st4, st5);
		DrawableTransition t4 = new DrawableTransition(st3, st1);
		
		machine.addState(st1);machine.addState(st2);machine.addState(st3);machine.addState(st4);machine.addState(st5);
		machine.addTransition(t1);machine.addTransition(t2);machine.addTransition(t3);machine.addTransition(t4);
		
		MachineCanvas canvas = new MachineCanvas(machine);
		canvas.setMachineName("Maquina1");
		return canvas;
	}

	public static MachineCanvas extracted2() {
		MooreMachine<DrawableState, DrawableTransition> machine = new MooreMachine<DrawableState, DrawableTransition>();
		int[][] coords = MachineCanvas.generateRegularPolygon(2, 120);
		for (int i = 0; i < coords.length; i++) {
			int[] coord = coords[i];
			coord[0] += 300;
			coord[1] += 300;
		}
		DrawableState st1 = new DrawableState(machine.getNextID(), "1"); st1.setCoords(coords[0]);
		DrawableState st2 = new DrawableState(machine.getNextID(), "2"); st2.setCoords(coords[1]);

		DrawableTransition t1 = new DrawableTransition(st1, st2, "maquina2");
		
		machine.addState(st1);machine.addState(st2);
		machine.addTransition(t1);

		MachineCanvas canvas = new MachineCanvas(machine);
		canvas.setMachineName("Maquina2");
		return canvas;
	}
}
