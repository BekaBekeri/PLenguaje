from antlr4 import *
from moomaListener import moomaListener
from moomaParser import moomaParser
import sys


class Language:
    def __init__(self, ident):
        self.ident = ident
        self.inputs = []
        self.outputs = []

    def existInput(self, _input):
        return True if _input in self.inputs else False

    def existOutput(self, output):
        return True if output in self.outputs else False

    def __eq__(self, other):
        return other == self.ident

    def __str__(self):
        retval = "{}:\nInputs: ".format(self.ident)
        for x in self.inputs:
            retval += "{}\t".format(x)
        retval += "\nOutputs: "
        for x in self.outputs:
            retval += "{}\t".format(x)
        return retval

class Automaton:
    def __init__(self, ident, language):
        self.ident = ident
        self.states = {}
        self.transitions = []
        self.language = language
        self.initial = None

    def addinitial(self, state):
        # Initial should be a state of the automaton
        if state in self.states:
            self.initial = state

    def __eq__(self, other):
        return other == self.ident

    def __str__(self):
        retval = "{}:\nStates: ".format(self.ident)
        for x, y in self.states.items():
            retval += "{}:{}\t".format(x, y)
        retval += "\nInitial: {}".format(self.initial)
        retval += "\nLanguage: {}".format(self.language.ident)
        retval += "\nTransitions:\n"
        for x in self.transitions:
            retval += "\t{}\n".format(str(x))
        return retval


class Transition:
    def __init__(self, origin, event, dest):
        self.origin = origin
        self.event = event
        self.dest = dest

    def __str__(self):
        return "{}|{} -> {}".format(self.origin, self.event, self.dest)

def writeAuxiliar(self):
    file = open(self.directory+"State.java","w")
    file.write("package domain;\n\npublic class State implements IState {\n\tprotected String name;\n\tprivate OutputInterface output;\n\t\n\tpublic State(String name) {\n\t\tthis.name = name;\n\t}\n\t\n\t@Override\n\tpublic boolean equals(Object obj) {\n\t\tif (obj instanceof State) {\n\t\t\treturn ((State) obj).name.equals(this.name);\n\t\t}\n\t\treturn false;\n\t}\n\t\n\tpublic String getName() {\n\t\treturn name;\n\t}\n\tpublic void setName(String name) {\n\t\tthis.name = name;\n\t}\n\n\tpublic OutputInterface getOutput() {\n\t\treturn output;\n\t}\n\n\tpublic void setOutput(OutputInterface output) {\n\t\tthis.output = output;\n\t}\n}\n")
    file = open(self.directory+"IState.java","w")
    file.write("package domain;\n\npublic interface IState {\n\tString getName();\n\tvoid setName(String name);\n\tOutputInterface getOutput();\n\tvoid setOutput(OutputInterface output);\n}\n")
    file = open(self.directory+"IMooreMachine.java","w")
    file.write("package domain;\n\nimport java.util.LinkedList;\n\npublic interface IMooreMachine {\n\tboolean addState(IState state);\n\tboolean addTransition(ITransition transition);\n\tboolean removeState(IState state);\n\tboolean removeTransition(ITransition transition);\n\t\n\tLinkedList<IState> getStates();\n\tIState getDestinationState(IState fromState, String input);\n\tLinkedList<ITransition> getTransitions();\n\tIState getInitialState();\n\tvoid setInitialState(IState initialState);\n\tvoid setInitialState(IState initialState, boolean overwrite);\n\t\n\tString getMachineName();\n\tvoid setMachineName(String name);\n}\n")
    file = open(self.directory+"MooreMachine.java","w")
    file.write("package domain;\n\nimport java.util.LinkedList;\n\npublic class MooreMachine implements IMooreMachine {\n\tprotected LinkedList<IState> states;\n\tprotected LinkedList<ITransition> transitions;\n\tprotected String name;\n\tprotected IState initialState;\n\t\n\tpublic MooreMachine() {\n\t\tstates = new LinkedList<IState>();\n\t\ttransitions = new LinkedList<ITransition>();\n\t}\n\t\n\t@Override\n\tpublic boolean addState(IState state) {\n\t\tif (!hasState(state)) {\n\t\t\tstates.add(state);\n\t\t\treturn true;\n\t\t}\n\t\treturn false;\n\t\t\n\t}\n\n\t@Override\n\tpublic boolean addTransition(ITransition transition) {\n\t\tif (hasState(transition.getFromState()) && hasState(transition.getToState())) {\n\t\t\ttransitions.add(transition);\n\t\t\treturn true;\n\t\t}\n\t\treturn false;\n\t}\n\t\n\t@Override\n\tpublic boolean removeState(IState state) {\n\t\treturn false;\n\t}\n\t@Override\n\tpublic boolean removeTransition(ITransition transition) {\n\t\treturn false;\n\t}\n\n\t@Override\n\tpublic LinkedList<IState> getStates() {\n\t\treturn this.states;\n\t}\n\n\t@Override\n\tpublic IState getDestinationState(IState originState, String input) {\n\t\tfor (ITransition t : transitions) {\n\t\t\tif (t.getFromState().equals(originState) && t.getInputs().contains(input))\n\t\t\t\treturn t.getToState();\n\t\t}\n\t\treturn null;\n\t}\n\t\n\t@Override\n\tpublic LinkedList<ITransition> getTransitions() {\n\t\treturn this.transitions;\n\t}\n\n\t@Override\n\tpublic IState getInitialState() {\n\t\treturn this.initialState;\n\t}\n\n\t@Override\n\tpublic void setInitialState(IState initialState) {\n\t\tthis.setInitialState(initialState, false);\n\t}\n\n\t@Override\n\tpublic void setInitialState(IState initialState, boolean overwrite) {\n\t\tif (initialState == null && !overwrite) {\n\t\t\tthrow new RuntimeException(\"The initial state is already defined\");\n\t\t} else {\n\t\t\tthis.initialState = initialState;\n\t\t}\n\t}\n\t\n\t@Override\n\tpublic String getMachineName() {\n\t\treturn name;\n\t}\n\t\n\t@Override\n\tpublic void setMachineName(String name) {\n\t\tthis.name = name;\n\t}\n\t\n\tpublic boolean hasState(IState state) {\n\t\tfor (IState st : states) {\n\t\t\tif (state.equals(st))\n\t\t\t\treturn true;\n\t\t}\n\t\treturn false;\n\t}\n\n}\n")
    file = open(self.directory+"ITransition.java","w")
    file.write("package domain;\n\nimport java.util.LinkedList;\n\npublic interface ITransition {\n\tIState getFromState();\n\tvoid setFromState(IState fromState);\n\tIState getToState();\n\tvoid setToState(IState toState);\n\tLinkedList<String> getInputs();\n\tvoid setInputs(LinkedList<String> inputs);\n}\n")
    file = open(self.directory+"Transition.java","w")
    file.write("package domain;\n\nimport java.util.Arrays;\nimport java.util.LinkedList;\n\npublic class Transition implements ITransition {\n\tprotected IState fromState;\n\tprotected IState toState;\n\tprotected LinkedList<String> inputs;\n\t\n\tpublic Transition(IState fromState, IState toState) {\n\t\tthis.setFromState(fromState);\n\t\tthis.setToState(toState);\n\t\tthis.inputs = new LinkedList<String>();\n\t}\n\t\n\tpublic Transition(IState fromState, IState toState, String input) {\n\t\tthis.setFromState(fromState);\n\t\tthis.setToState(toState);\n\t\tthis.inputs = new LinkedList<String>();\n\t\tthis.inputs.add(input);\n\t}\n\t\n\tpublic Transition(IState fromState, IState toState, LinkedList<String> inputs) {\n\t\tthis.setFromState(fromState);\n\t\tthis.setToState(toState);\n\t\tthis.inputs = inputs;\n\t}\n\t\n\tpublic Transition(IState fromState, IState toState, String[] inputs) {\n\t\tthis(fromState, toState, new LinkedList<String>(Arrays.asList(inputs)));\n\t}\n\n\tpublic IState getFromState() {\n\t\treturn fromState;\n\t}\n\n\tpublic void setFromState(IState fromState) {\n\t\tthis.fromState = fromState;\n\t}\n\n\tpublic IState getToState() {\n\t\treturn toState;\n\t}\n\n\tpublic void setToState(IState toState) {\n\t\tthis.toState = toState;\n\t}\n\n\tpublic LinkedList<String> getInputs() {\n\t\treturn inputs;\n\t}\n\n\tpublic void setInputs(LinkedList<String> inputs) {\n\t\tthis.inputs = inputs;\n\t}\n\t\n\t@Override\n\tpublic boolean equals(Object obj) {\n\t\tif (obj instanceof Transition) {\n\t\t\tTransition t = (Transition) obj;\n\t\t\treturn this.getFromState().equals(t.getFromState()) && \n\t\t\t\t\tthis.getToState().equals(t.getToState()) && \n\t\t\t\t\tthis.getInputs().containsAll(t.getInputs());\n\t\t}\n\t\treturn false;\n\t}\n}\n")
    file = open(self.directory+"MachineController.java","w")
    file.write("package domain;\n\nimport java.util.LinkedList;\n\npublic class MachineController<T> {\n\tprivate IMooreMachine machine;\n\tprivate MachineSimulator simulator;\n\tprivate GenericEnvironment<T> environment;\n\t\n\tpublic MachineController(IMooreMachine machine) {\n\t\tthis.machine = machine;\n\t\tthis.simulator = new MachineSimulator(machine);\n\t\tthis.environment = new GenericEnvironment<T>();\n\t}\n\t\n\tpublic LinkedList<IState> getStates() {\n\t\treturn machine.getStates();\n\t}\n\t\n\tpublic LinkedList<ITransition> getTransitions() {\n\t\treturn machine.getTransitions();\n\t}\n\t\n\tpublic IState getInitialState() {\n\t\treturn machine.getInitialState();\n\t}\n\t\n\tpublic IState getTransitionDestination(String stateName, String input) {\n\t\tIState originState = new State(stateName);\n\t\treturn machine.getDestinationState(originState, input);\n\t}\n\t\n\tpublic IState addNewInput(T input) {\t\t\n\t\tboolean transitioned = simulator.addNewInput(environment.translate(input));\n\t\tif (transitioned)\n\t\t\treturn simulator.getCurrentState();\n\t\telse\n\t\t\treturn null;\n\t}\n\t\n\tpublic IState removeInput() {\n\t\treturn simulator.removeInput();\n\t}\n\t\n\tpublic IState getCurrentState() {\n\t\treturn simulator.getCurrentState();\n\t}\n\t\n\tpublic IState getPreviousState() {\n\t\treturn simulator.getPreviousState();\n\t}\n}\n")
    file = open(self.directory+"IEnvironment.java","w")
    file.write("package domain;\n\npublic interface IEnvironment<T> {\n\tString translate(T input);\n}\n")
    file = open(self.directory+"MachineSimulator.java","w")
    file.write("package domain;\n\nimport java.util.Stack;\n\npublic class MachineSimulator {\n\tprivate IMooreMachine machine;\n\tprivate IState currentState;\n\tprivate Stack<IState> previousStates = new Stack<>();\n\tprivate Stack<String> previousInputs = new Stack<>();\n\t\t\n\tpublic MachineSimulator(IMooreMachine machine) {\n\t\tthis.setMachine(machine);\n\t\tthis.currentState = this.machine.getInitialState();\n\t}\n\t\n\tpublic boolean addNewInput(String input) {\n\t\tIState destinationState = getMachine().getDestinationState(getCurrentState(), input);\n\t\tif (destinationState == null) {\n\t\t\treturn false;\n\t\t}\n\t\t\n\t\tpreviousStates.push(currentState);\n\t\tpreviousInputs.push(input);\n\t\tsetCurrentState(destinationState);\n\t\t//getCurrentState().getOutput().run();\n\t\treturn true;\n\t}\n\t\n\tpublic IState removeInput() {\n\t\tif (!previousStates.isEmpty()) {\n\t\t\tpreviousInputs.pop();\n\t\t\tIState previousState = previousStates.pop();\n\t\t\tthis.currentState = previousState;\n\t\t\treturn previousState;\n\t\t}\n\t\treturn null;\n\t}\n\n\tpublic IMooreMachine getMachine() {\n\t\treturn machine;\n\t}\n\n\tpublic void setMachine(IMooreMachine machine) {\n\t\tthis.machine = machine;\n\t}\n\n\tpublic IState getCurrentState() {\n\t\treturn currentState;\n\t}\n\n\tpublic void setCurrentState(IState currentState) {\n\t\tthis.currentState = currentState;\n\t}\n\t\n\tpublic IState getPreviousState() {\n\t\tif (previousStates.size() == 0) {\n\t\t\treturn null;\n\t\t} else {\n\t\t\treturn previousStates.peek();\n\t\t}\n\t}\n}\n")
    file = open(self.directory+"GenericEnvironment.java","w")
    file.write("package domain;\n\npublic class GenericEnvironment<T> implements IEnvironment<T> {\n\n\t@Override\n\tpublic String translate(T input) {\n\t\treturn input.toString();\n\t}\n\n}\n")
    file = open(self.directory+"OutputInterface.java","w")
    file.write("package domain;\n\n// This class is used only for semantic purposes \n// (Functional Interface that accepts nothing and returns nothing)\n@FunctionalInterface\npublic interface OutputInterface extends Runnable {}\n")
    return True


class MyMoomaListener(moomaListener):
    def __init__(self, file, directory):
        self.directory = directory
        self.file = open(directory+file, "w")
        self.languages = []
        self.automatons = []
        self.outputs = {}

        self.env = None
        self.error = False
        self.errorLog = []

    def exitProgram(self, ctx:moomaParser.ProgramContext):

        print("Lenguajes: ")
        for lang in self.languages:
            print(lang)
        print("\n\n\nAutomatas: ")
        for aut in self.automatons:
            print(aut)
        print("\n\n\noutputs: ")
        for key, value in self.outputs.items():
            print("{}: {}".format(key, value))
        print("\n\n\nEnv: {}\nError: {}".format(self.env, self.error))

        if self.error:
            for error in self.errorLog:
                sys.stderr.write("{}\n".format(error))

        else:
            self.file.write("package domain;\n")
            if not(self.env is None):
                self.file.write("import {};\n\n".format(self.env))
            self.file.write("public class Machines {\n")
            for auto in self.automatons:
                self.file.write("\tpublic static IMooreMachine {0}(){{\n\t\tMooreMachine machine = new MooreMachine();\n\t\tmachine.setMachineName(\"{0}\");".format(auto.ident))
                for key, value in auto.states.items():
                    self.file.write("\n\t\tState {0} = new State(\"{0}\");\n\t\t{0}.setOutput(() -> {1});\n\t\tmachine.addState({0});".format(key,self.outputs[value].replace("\n","\n\t\t")))
                tranCount = 0
                for transition in auto.transitions:
                    tranCount+=1
                    self.file.write("\n\t\tTransition t{0} = new Transition ({1},\"{2}\",{3});\n\t\tmachine.addTransition(t{0});".format(tranCount,transition.origin,transition.event,transition.dest))
                self.file.write("\n\t\tmachine.setInitialState({0});".format(auto.initial))
                self.file.write("\n\t\treturn machine;\n\t}")
            self.file.write("\n}")
            x = writeAuxiliar(self)

    def enterEnvironment(self, ctx:moomaParser.EnvironmentContext):
        self.env = str(ctx.Clase())

    def enterOutput(self, ctx:moomaParser.OutputContext):
        # Check if a code identifier has been already defined
        if str(ctx.Ident()) in self.outputs.keys():
            self.error = True
            self.errorLog.append("Identificador de código ya definido: {}".format(str(ctx.Ident())))
        else:
            self.outputs[str(ctx.Ident())] = str(ctx.Codigo())[2:-2]

    def enterDefine(self, ctx:moomaParser.DefineContext):
        # Check if a language identifier has already been defined
        if str(ctx.Ident()) in self.languages:
            self.error = True
            self.errorLog.append("Identificador de lenguaje ya definido: {}".format(str(ctx.Ident())))
        else:
            self.languages.append(Language(str(ctx.Ident())))

    def enterEntrada(self, ctx:moomaParser.EntradaContext):
        # The language this goes in is the last one seen due to the way we traverse the tree
        lang = self.languages[-1]
        events = ctx.l_evento().getText().split(",")
        for event in events:
            # Check for no repeated events
            if event not in lang.inputs:
                lang.inputs.append(event)
            else:
                self.error = True
                self.errorLog.append("Evento repetido: {}".format(event))

    def enterSalida(self, ctx:moomaParser.SalidaContext):
        # The language this goes in is the last one seen due to the way we traverse the tree
        lang = self.languages[-1]
        idents = ctx.l_ident().getText().split(",")
        for ident in idents:
            # Check for no repeated idents
            if ident not in lang.outputs:
                lang.outputs.append(ident)
            else:
                self.error = True
                self.errorLog.append("Identificador de salida repetido: {}".format(ident))

    def enterAutomaton(self, ctx:moomaParser.AutomatonContext):
        # Check if this automaton identier has already been defined
        if str(ctx.Ident(0)) in self.automatons:
            self.error = True
            self.errorLog.append("Identificador de automata ya definido: {}".format(str(ctx.Ident())))
        else:
            # Nested ifs for the future: Maybe we want to specify error messages
            # Check if the language for the automaton exists
            if str(ctx.Ident(1)) not in self.languages:
                self.error = True
                self.errorLog.append("Lenguaje no definido: {}".format(str(ctx.Ident(1))))
            else:
                language = self.languages[self.languages.index(str(ctx.Ident(1)))]
                self.automatons.append(Automaton(ctx.Ident(0), language))


    def enterStates(self, ctx:moomaParser.StatesContext):
        # The automaton this goes in is the last one seen due to the way we traverse the tree
        automaton = self.automatons[-1]
        states = ctx.l_states().getText().split(",")

        for state in states:
            state_ident = state.split("|")[0]
            state_out = state.split("|")[1]

            # Check for no repeated states
            if state_ident in automaton.states:
                self.error = True
                self.errorLog.append("Estado repetido en automata {}: {}".format(automaton.ident, state_ident))

            else:
                # Check if output is in language
                if state_out not in automaton.language.outputs:
                    self.error = True
                    self.errorLog.append("Identificador de codigo no definido en automata {}, en el lenguaje: {}".format(automaton.ident, state_out))
                else:
                    automaton.states[state_ident] = state_out

    def enterInitial(self, ctx:moomaParser.InitialContext):
        # The automaton this goes in is the last one seen due to the way we traverse the tree
        automaton = self.automatons[-1]
        automaton.addinitial(str(ctx.Ident()))

        # Check if initial was correctly added
        if automaton.initial is None:
            self.error = True
            self.errorLog.append("Estado inicial incorrecto en automata {}".format(automaton.ident))

    def enterTransitions(self, ctx:moomaParser.TransitionsContext):
        # The automaton this goes in is the last one seen due to the way we traverse the tree
        automaton = self.automatons[-1]
        transitions = ctx.l_transitions().getText().split(";")[:-1]
        for trans in transitions:
            origin = trans.split("|")[0]
            event = trans.split("|")[1].split("->")[0]
            dest = trans.split("|")[1].split("->")[1]

            # Check if origin and dest are actual states of the automaton
            if origin not in automaton.states or dest not in automaton.states:
                self.error = True
                self.errorLog.append("Origen ({}) o destino ({}) no definidos en el automata {}".format(origin, dest, automaton.ident))
            else:
                # Check if input is part of language
                if event not in automaton.language.inputs:
                    self.error = True
                    self.errorLog.append("Evento {} no definido en el lenguaje del automata {}".format(event, automaton.ident))
                else:
                    automaton.transitions.append(Transition(origin, event, dest))

    def visitErrorNode(self, node:ErrorNode):
        self.error = True
