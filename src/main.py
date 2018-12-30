import sys
import os
from antlr4 import *
from moomaLexer import moomaLexer
from moomaParser import moomaParser
from myMoomaListener import MyMoomaListener
from moomaErrorListener import moomaErrorListener
from argparse import ArgumentParser


def main(args):
    file = FileStream(args.input_file)

    errorListener = moomaErrorListener()

    lexer = moomaLexer(file)
    stream = CommonTokenStream(lexer)
    parser = moomaParser(stream)
    parser.removeErrorListeners()
    parser.addErrorListener(errorListener)
    tree = parser.program()

    args.output = os.path.abspath(args.output)
    if not os.path.isdir(args.output):
        sys.stderr.write("Directory not found: {}\n".format(args.output))
        sys.exit(2)

    listener = MyMoomaListener(args.output)
    walker = ParseTreeWalker()
    walker.walk(listener, tree)

    write_parsed_to_file(listener)

    listener.file.close()

def write_parsed_to_file(listener):
    if listener.error:
        print(os.linesep + "Errors detected, will not write files.")
    else:
        write_machines(listener)
        write_auxiliary(listener)
    
def write_machines(listener):
    listener.file.write("public class Machines {\n")
    for auto in listener.automatons:
        listener.file.write("\tpublic static IMooreMachine {0}(){{\n\t\tMooreMachine machine = new MooreMachine();\n\t\tmachine.setMachineName(\"{0}\");".format(auto.ident))
        for key, value in auto.states.items():
            listener.file.write("\n\t\tState {0} = new State(\"{0}\");\n\t\t{0}.setOutput(() -> {1});\n\t\tmachine.addState({0});".format(key, listener.outputs[value].replace("\n", "").replace("\r", "").replace(" ", "")))
        tranCount = 0
        for transition in auto.transitions:
            tranCount+=1
            listener.file.write("\n\t\tTransition t{0} = new Transition ({1},{2},\"{3}\");\n\t\tmachine.addTransition(t{0});".format(tranCount, transition.origin, transition.dest, transition.event))
        listener.file.write("\n\t\tmachine.setInitialState({0});".format(auto.initial))
        listener.file.write("\n\t\treturn machine;\n\t}")
    listener.file.write("\n}")

def write_auxiliary(listener):
    file = open(os.path.join(listener.directory, "State.java"),"w")
    file.write("\n\npublic class State implements IState {\n\tprotected String name;\n\tprivate OutputInterface output;\n\t\n\tpublic State(String name) {\n\t\tthis.name = name;\n\t}\n\t\n\t@Override\n\tpublic boolean equals(Object obj) {\n\t\tif (obj instanceof State) {\n\t\t\treturn ((State) obj).name.equals(this.name);\n\t\t}\n\t\treturn false;\n\t}\n\t\n\tpublic String getName() {\n\t\treturn name;\n\t}\n\tpublic void setName(String name) {\n\t\tthis.name = name;\n\t}\n\n\tpublic OutputInterface getOutput() {\n\t\treturn output;\n\t}\n\n\tpublic void setOutput(OutputInterface output) {\n\t\tthis.output = output;\n\t}\n}\n")
    file.close()

    file = open(os.path.join(listener.directory, "IState.java"),"w")
    file.write("\n\npublic interface IState {\n\tString getName();\n\tvoid setName(String name);\n\tOutputInterface getOutput();\n\tvoid setOutput(OutputInterface output);\n}\n")
    file.close()

    file = open(os.path.join(listener.directory, "IMooreMachine.java"),"w")
    file.write("\n\nimport java.util.LinkedList;\n\npublic interface IMooreMachine {\n\tboolean addState(IState state);\n\tboolean addTransition(ITransition transition);\n\tboolean removeState(IState state);\n\tboolean removeTransition(ITransition transition);\n\t\n\tLinkedList<IState> getStates();\n\tIState getDestinationState(IState fromState, String input);\n\tLinkedList<ITransition> getTransitions();\n\tIState getInitialState();\n\tvoid setInitialState(IState initialState);\n\tvoid setInitialState(IState initialState, boolean overwrite);\n\t\n\tString getMachineName();\n\tvoid setMachineName(String name);\n}\n")
    file.close()

    file = open(os.path.join(listener.directory, "MooreMachine.java"),"w")
    file.write("\n\nimport java.util.LinkedList;\n\npublic class MooreMachine implements IMooreMachine {\n\tprotected LinkedList<IState> states;\n\tprotected LinkedList<ITransition> transitions;\n\tprotected String name;\n\tprotected IState initialState;\n\t\n\tpublic MooreMachine() {\n\t\tstates = new LinkedList<IState>();\n\t\ttransitions = new LinkedList<ITransition>();\n\t}\n\t\n\t@Override\n\tpublic boolean addState(IState state) {\n\t\tif (!hasState(state)) {\n\t\t\tstates.add(state);\n\t\t\treturn true;\n\t\t}\n\t\treturn false;\n\t\t\n\t}\n\n\t@Override\n\tpublic boolean addTransition(ITransition transition) {\n\t\tif (hasState(transition.getFromState()) && hasState(transition.getToState())) {\n\t\t\ttransitions.add(transition);\n\t\t\treturn true;\n\t\t}\n\t\treturn false;\n\t}\n\t\n\t@Override\n\tpublic boolean removeState(IState state) {\n\t\treturn false;\n\t}\n\t@Override\n\tpublic boolean removeTransition(ITransition transition) {\n\t\treturn false;\n\t}\n\n\t@Override\n\tpublic LinkedList<IState> getStates() {\n\t\treturn this.states;\n\t}\n\n\t@Override\n\tpublic IState getDestinationState(IState originState, String input) {\n\t\tfor (ITransition t : transitions) {\n\t\t\tif (t.getFromState().equals(originState) && t.getInputs().contains(input))\n\t\t\t\treturn t.getToState();\n\t\t}\n\t\treturn null;\n\t}\n\t\n\t@Override\n\tpublic LinkedList<ITransition> getTransitions() {\n\t\treturn this.transitions;\n\t}\n\n\t@Override\n\tpublic IState getInitialState() {\n\t\treturn this.initialState;\n\t}\n\n\t@Override\n\tpublic void setInitialState(IState initialState) {\n\t\tthis.setInitialState(initialState, false);\n\t}\n\n\t@Override\n\tpublic void setInitialState(IState initialState, boolean overwrite) {\n\t\tif (initialState == null && !overwrite) {\n\t\t\tthrow new RuntimeException(\"The initial state is already defined\");\n\t\t} else {\n\t\t\tthis.initialState = initialState;\n\t\t}\n\t}\n\t\n\t@Override\n\tpublic String getMachineName() {\n\t\treturn name;\n\t}\n\t\n\t@Override\n\tpublic void setMachineName(String name) {\n\t\tthis.name = name;\n\t}\n\t\n\tpublic boolean hasState(IState state) {\n\t\tfor (IState st : states) {\n\t\t\tif (state.equals(st))\n\t\t\t\treturn true;\n\t\t}\n\t\treturn false;\n\t}\n\n}\n")
    file.close()

    file = open(os.path.join(listener.directory, "ITransition.java"),"w")
    file.write("\n\nimport java.util.LinkedList;\n\npublic interface ITransition {\n\tIState getFromState();\n\tvoid setFromState(IState fromState);\n\tIState getToState();\n\tvoid setToState(IState toState);\n\tLinkedList<String> getInputs();\n\tvoid setInputs(LinkedList<String> inputs);\n}\n")
    file.close()

    file = open(os.path.join(listener.directory, "Transition.java"),"w")
    file.write("\n\nimport java.util.Arrays;\nimport java.util.LinkedList;\n\npublic class Transition implements ITransition {\n\tprotected IState fromState;\n\tprotected IState toState;\n\tprotected LinkedList<String> inputs;\n\t\n\tpublic Transition(IState fromState, IState toState) {\n\t\tthis.setFromState(fromState);\n\t\tthis.setToState(toState);\n\t\tthis.inputs = new LinkedList<String>();\n\t}\n\t\n\tpublic Transition(IState fromState, IState toState, String input) {\n\t\tthis.setFromState(fromState);\n\t\tthis.setToState(toState);\n\t\tthis.inputs = new LinkedList<String>();\n\t\tthis.inputs.add(input);\n\t}\n\t\n\tpublic Transition(IState fromState, IState toState, LinkedList<String> inputs) {\n\t\tthis.setFromState(fromState);\n\t\tthis.setToState(toState);\n\t\tthis.inputs = inputs;\n\t}\n\t\n\tpublic Transition(IState fromState, IState toState, String[] inputs) {\n\t\tthis(fromState, toState, new LinkedList<String>(Arrays.asList(inputs)));\n\t}\n\n\tpublic IState getFromState() {\n\t\treturn fromState;\n\t}\n\n\tpublic void setFromState(IState fromState) {\n\t\tthis.fromState = fromState;\n\t}\n\n\tpublic IState getToState() {\n\t\treturn toState;\n\t}\n\n\tpublic void setToState(IState toState) {\n\t\tthis.toState = toState;\n\t}\n\n\tpublic LinkedList<String> getInputs() {\n\t\treturn inputs;\n\t}\n\n\tpublic void setInputs(LinkedList<String> inputs) {\n\t\tthis.inputs = inputs;\n\t}\n\t\n\t@Override\n\tpublic boolean equals(Object obj) {\n\t\tif (obj instanceof Transition) {\n\t\t\tTransition t = (Transition) obj;\n\t\t\treturn this.getFromState().equals(t.getFromState()) && \n\t\t\t\t\tthis.getToState().equals(t.getToState()) && \n\t\t\t\t\tthis.getInputs().containsAll(t.getInputs());\n\t\t}\n\t\treturn false;\n\t}\n}\n")
    file.close()

    file = open(os.path.join(listener.directory, "MachineController.java"),"w")
    file.write("\n\nimport java.util.LinkedList;\n\npublic class MachineController<T> {\n\tprivate IMooreMachine machine;\n\tprivate MachineSimulator simulator;\n\tprivate " + listener.env + "<T> environment;\n\t\n\tpublic MachineController(IMooreMachine machine) {\n\t\tthis.machine = machine;\n\t\tthis.simulator = new MachineSimulator(machine);\n\t\tthis.environment = new " + listener.env + "<T>();\n\t}\n\t\n\tpublic LinkedList<IState> getStates() {\n\t\treturn machine.getStates();\n\t}\n\t\n\tpublic LinkedList<ITransition> getTransitions() {\n\t\treturn machine.getTransitions();\n\t}\n\t\n\tpublic IState getInitialState() {\n\t\treturn machine.getInitialState();\n\t}\n\t\n\tpublic IState getTransitionDestination(String stateName, String input) {\n\t\tIState originState = new State(stateName);\n\t\treturn machine.getDestinationState(originState, input);\n\t}\n\t\n\tpublic IState addNewInput(T input) {\t\t\n\t\tboolean transitioned = simulator.addNewInput(environment.translate(input));\n\t\tif (transitioned)\n\t\t\treturn simulator.getCurrentState();\n\t\telse\n\t\t\treturn null;\n\t}\n\t\n\tpublic IState removeInput() {\n\t\treturn simulator.removeInput();\n\t}\n\t\n\tpublic IState getCurrentState() {\n\t\treturn simulator.getCurrentState();\n\t}\n\t\n\tpublic IState getPreviousState() {\n\t\treturn simulator.getPreviousState();\n\t}\n}\n")
    file.close()

    file = open(os.path.join(listener.directory, "IEnvironment.java"),"w")
    file.write("\n\npublic interface IEnvironment<T> {\n\tString translate(T input);\n}\n")
    file.close()

    file = open(os.path.join(listener.directory, "MachineSimulator.java"),"w")
    file.write("\n\nimport java.util.Stack;\n\npublic class MachineSimulator {\n\tprivate IMooreMachine machine;\n\tprivate IState currentState;\n\tprivate Stack<IState> previousStates = new Stack<>();\n\tprivate Stack<String> previousInputs = new Stack<>();\n\t\t\n\tpublic MachineSimulator(IMooreMachine machine) {\n\t\tthis.setMachine(machine);\n\t\tthis.currentState = this.machine.getInitialState();\n\t}\n\t\n\tpublic boolean addNewInput(String input) {\n\t\tIState destinationState = getMachine().getDestinationState(getCurrentState(), input);\n\t\tif (destinationState == null) {\n\t\t\treturn false;\n\t\t}\n\t\t\n\t\tpreviousStates.push(currentState);\n\t\tpreviousInputs.push(input);\n\t\tsetCurrentState(destinationState);\n\t\t//getCurrentState().getOutput().run();\n\t\treturn true;\n\t}\n\t\n\tpublic IState removeInput() {\n\t\tif (!previousStates.isEmpty()) {\n\t\t\tpreviousInputs.pop();\n\t\t\tIState previousState = previousStates.pop();\n\t\t\tthis.currentState = previousState;\n\t\t\treturn previousState;\n\t\t}\n\t\treturn null;\n\t}\n\n\tpublic IMooreMachine getMachine() {\n\t\treturn machine;\n\t}\n\n\tpublic void setMachine(IMooreMachine machine) {\n\t\tthis.machine = machine;\n\t}\n\n\tpublic IState getCurrentState() {\n\t\treturn currentState;\n\t}\n\n\tpublic void setCurrentState(IState currentState) {\n\t\tthis.currentState = currentState;\n\t}\n\t\n\tpublic IState getPreviousState() {\n\t\tif (previousStates.size() == 0) {\n\t\t\treturn null;\n\t\t} else {\n\t\t\treturn previousStates.peek();\n\t\t}\n\t}\n}\n")
    file.close()

    file = open(os.path.join(listener.directory, "OutputInterface.java"),"w")
    file.write("\n\n// This class is used only for semantic purposes \n// (Functional Interface that accepts nothing and returns nothing)\n@FunctionalInterface\npublic interface OutputInterface extends Runnable {}\n")
    file.close()

if __name__ == "__main__":
    parser = ArgumentParser("Mooma Parser")
    parser.add_argument(
        "input_file",
        help="Mooma specification file to load."
    )
    parser.add_argument(
        "-o", "--output", default=".",
        help="<directory> Specify where to place generated files"
    )
    main(parser.parse_args())
