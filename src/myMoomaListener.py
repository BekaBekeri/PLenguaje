from antlr4 import *
from moomaListener import moomaListener
from moomaParser import moomaParser

import os
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


class MyMoomaListener(moomaListener):
    def __init__(self, directory):
        self.directory = directory
        self.file = open(os.path.join(directory, "Machines.java"), "w")
        self.languages = []
        self.automatons = []
        self.outputs = {}

        self.env = None
        self.error = False
        self.errorLog = []

    def exitProgram(self, ctx:moomaParser.ProgramContext):
        if self.error:
            for error in self.errorLog:
                sys.stderr.write("{}\n".format(error))

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
