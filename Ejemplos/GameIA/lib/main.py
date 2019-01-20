import sys
import os
import auxilaryCode

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

    write_parsed_to_file(listener, args.package)

    listener.file.close()

def write_parsed_to_file(listener, package):
    if listener.error:
        print(os.linesep + "Errors detected, will not write files.")
    else:
        package = "package {};{}".format(package, '\n')
        write_machines(listener, package)
        write_auxiliary(listener, package)
    
def write_machines(listener, package):
    listener.file.write(package)
    listener.file.write("public class Machines {\n")
    for auto in listener.automatons:
        listener.file.write("\tpublic static IMooreMachine {0}(){{\n\t\tMooreMachine machine = new MooreMachine();\n\t\tmachine.setMachineName(\"{0}\");".format(auto.ident))
        for key, value in auto.states.items():
            listener.file.write("\n\t\tState {0} = new State(\"{0}\");\n\t\t{0}.setOutput(({2} env) -> {1});\n\t\tmachine.addState({0});".format(key, listener.outputs[value].replace("\n", "").replace("\r", "").replace(" ", ""), listener.env))
        tranCount = 0
        for transition in auto.transitions:
            tranCount += 1
            listener.file.write("\n\t\tTransition t{0} = new Transition ({1},{2},\"{3}\");\n\t\tmachine.addTransition(t{0});".format(tranCount, transition.origin, transition.dest, transition.event))
        listener.file.write("\n\t\tmachine.setInitialState({0});".format(auto.initial))
        listener.file.write("\n\t\treturn machine;\n\t}")
    listener.file.write("\n}")

def write_auxiliary(listener, package):
    file = open(os.path.join(listener.directory, "State.java"), "w")
    file.write(auxilaryCode.state.format(package))
    file.close()

    file = open(os.path.join(listener.directory, "IState.java"), "w")
    file.write(auxilaryCode.istate.format(package))
    file.close()

    file = open(os.path.join(listener.directory, "IMooreMachine.java"), "w")
    file.write(auxilaryCode.imooremachine.format(package))
    file.close()

    file = open(os.path.join(listener.directory, "MooreMachine.java"), "w")
    file.write(auxilaryCode.mooremachine.format(package))
    file.close()

    file = open(os.path.join(listener.directory, "ITransition.java"), "w")
    file.write(auxilaryCode.itransition.format(package))
    file.close()

    file = open(os.path.join(listener.directory, "Transition.java"), "w")
    file.write(auxilaryCode.transition.format(package))
    file.close()

    file = open(os.path.join(listener.directory, "MachineController.java"), "w")
    file.write(auxilaryCode.machinecontroller.format(listener.env, package))
    file.close()

    file = open(os.path.join(listener.directory, "IEnvironment.java"), "w")
    file.write(auxilaryCode.ienvironment.format(package))
    file.close()

    file = open(os.path.join(listener.directory, "MachineSimulator.java"), "w")
    file.write(auxilaryCode.machinesimulator.format(package))
    file.close()

    file = open(os.path.join(listener.directory, "OutputInterface.java"), "w")
    file.write(auxilaryCode.outputinterface.format(listener.env, package))
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
    parser.add_argument(
        "-p", "--package", default="",
        help="Package that will be added to generated files"
    )
    main(parser.parse_args())
