# Generated from .\src\mooma.g4 by ANTLR 4.7.1
from antlr4 import *
if __name__ is not None and "." in __name__:
    from .moomaParser import moomaParser
else:
    from moomaParser import moomaParser

# This class defines a complete listener for a parse tree produced by moomaParser.
class moomaListener(ParseTreeListener):

    # Enter a parse tree produced by moomaParser#program.
    def enterProgram(self, ctx:moomaParser.ProgramContext):
        pass

    # Exit a parse tree produced by moomaParser#program.
    def exitProgram(self, ctx:moomaParser.ProgramContext):
        pass


    # Enter a parse tree produced by moomaParser#environment.
    def enterEnvironment(self, ctx:moomaParser.EnvironmentContext):
        pass

    # Exit a parse tree produced by moomaParser#environment.
    def exitEnvironment(self, ctx:moomaParser.EnvironmentContext):
        pass


    # Enter a parse tree produced by moomaParser#l_output.
    def enterL_output(self, ctx:moomaParser.L_outputContext):
        pass

    # Exit a parse tree produced by moomaParser#l_output.
    def exitL_output(self, ctx:moomaParser.L_outputContext):
        pass


    # Enter a parse tree produced by moomaParser#output.
    def enterOutput(self, ctx:moomaParser.OutputContext):
        pass

    # Exit a parse tree produced by moomaParser#output.
    def exitOutput(self, ctx:moomaParser.OutputContext):
        pass


    # Enter a parse tree produced by moomaParser#l_define.
    def enterL_define(self, ctx:moomaParser.L_defineContext):
        pass

    # Exit a parse tree produced by moomaParser#l_define.
    def exitL_define(self, ctx:moomaParser.L_defineContext):
        pass


    # Enter a parse tree produced by moomaParser#define.
    def enterDefine(self, ctx:moomaParser.DefineContext):
        pass

    # Exit a parse tree produced by moomaParser#define.
    def exitDefine(self, ctx:moomaParser.DefineContext):
        pass


    # Enter a parse tree produced by moomaParser#entrada.
    def enterEntrada(self, ctx:moomaParser.EntradaContext):
        pass

    # Exit a parse tree produced by moomaParser#entrada.
    def exitEntrada(self, ctx:moomaParser.EntradaContext):
        pass


    # Enter a parse tree produced by moomaParser#salida.
    def enterSalida(self, ctx:moomaParser.SalidaContext):
        pass

    # Exit a parse tree produced by moomaParser#salida.
    def exitSalida(self, ctx:moomaParser.SalidaContext):
        pass


    # Enter a parse tree produced by moomaParser#l_evento.
    def enterL_evento(self, ctx:moomaParser.L_eventoContext):
        pass

    # Exit a parse tree produced by moomaParser#l_evento.
    def exitL_evento(self, ctx:moomaParser.L_eventoContext):
        pass


    # Enter a parse tree produced by moomaParser#l_evento_fact.
    def enterL_evento_fact(self, ctx:moomaParser.L_evento_factContext):
        pass

    # Exit a parse tree produced by moomaParser#l_evento_fact.
    def exitL_evento_fact(self, ctx:moomaParser.L_evento_factContext):
        pass


    # Enter a parse tree produced by moomaParser#l_ident.
    def enterL_ident(self, ctx:moomaParser.L_identContext):
        pass

    # Exit a parse tree produced by moomaParser#l_ident.
    def exitL_ident(self, ctx:moomaParser.L_identContext):
        pass


    # Enter a parse tree produced by moomaParser#l_ident_fact.
    def enterL_ident_fact(self, ctx:moomaParser.L_ident_factContext):
        pass

    # Exit a parse tree produced by moomaParser#l_ident_fact.
    def exitL_ident_fact(self, ctx:moomaParser.L_ident_factContext):
        pass


    # Enter a parse tree produced by moomaParser#l_automaton.
    def enterL_automaton(self, ctx:moomaParser.L_automatonContext):
        pass

    # Exit a parse tree produced by moomaParser#l_automaton.
    def exitL_automaton(self, ctx:moomaParser.L_automatonContext):
        pass


    # Enter a parse tree produced by moomaParser#automaton.
    def enterAutomaton(self, ctx:moomaParser.AutomatonContext):
        pass

    # Exit a parse tree produced by moomaParser#automaton.
    def exitAutomaton(self, ctx:moomaParser.AutomatonContext):
        pass


    # Enter a parse tree produced by moomaParser#states.
    def enterStates(self, ctx:moomaParser.StatesContext):
        pass

    # Exit a parse tree produced by moomaParser#states.
    def exitStates(self, ctx:moomaParser.StatesContext):
        pass


    # Enter a parse tree produced by moomaParser#l_states.
    def enterL_states(self, ctx:moomaParser.L_statesContext):
        pass

    # Exit a parse tree produced by moomaParser#l_states.
    def exitL_states(self, ctx:moomaParser.L_statesContext):
        pass


    # Enter a parse tree produced by moomaParser#l_states_fact.
    def enterL_states_fact(self, ctx:moomaParser.L_states_factContext):
        pass

    # Exit a parse tree produced by moomaParser#l_states_fact.
    def exitL_states_fact(self, ctx:moomaParser.L_states_factContext):
        pass


    # Enter a parse tree produced by moomaParser#initial.
    def enterInitial(self, ctx:moomaParser.InitialContext):
        pass

    # Exit a parse tree produced by moomaParser#initial.
    def exitInitial(self, ctx:moomaParser.InitialContext):
        pass


    # Enter a parse tree produced by moomaParser#transitions.
    def enterTransitions(self, ctx:moomaParser.TransitionsContext):
        pass

    # Exit a parse tree produced by moomaParser#transitions.
    def exitTransitions(self, ctx:moomaParser.TransitionsContext):
        pass


    # Enter a parse tree produced by moomaParser#l_transitions.
    def enterL_transitions(self, ctx:moomaParser.L_transitionsContext):
        pass

    # Exit a parse tree produced by moomaParser#l_transitions.
    def exitL_transitions(self, ctx:moomaParser.L_transitionsContext):
        pass


