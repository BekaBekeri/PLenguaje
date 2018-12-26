import Domain.Env;

public class Machines {
	public static IMooreMachine auto1(){
		MooreMachine machine = new MooreMachine();
		machine.setMachineName("auto1");
		State q0 = new State("q0");
		q0.setOutput(() -> 
		        printear("Hello");
		        );
		machine.addState(q0);
		State q1 = new State("q1");
		q1.setOutput(() -> 
		        /* ADIOS \asd <> -.* */
		        printear("Adios");
		        );
		machine.addState(q1);
		Transition t1 = new Transition (q0,"1",q1);
		machine.addTransition(t1);
		Transition t2 = new Transition (q0,"2",q0);
		machine.addTransition(t2);
		Transition t3 = new Transition (q1,"1",q1);
		machine.addTransition(t3);
		Transition t4 = new Transition (q1,"2",q0);
		machine.addTransition(t4);
		machine.setInitialState(q0);
		return machine;
	}
}