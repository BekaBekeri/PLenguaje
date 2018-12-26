import sys
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
    listener = MyMoomaListener("out.java")
    walker = ParseTreeWalker()
    walker.walk(listener, tree)
    print("adios")
    #write(data)


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

