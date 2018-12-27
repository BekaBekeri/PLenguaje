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
    if not(os.path.isdir(args.output)):
        sys.stderr.write("\'{}\' is not a directory.\n".format(args.output))
        sys.exit()
    else:
        if args.output[len(args.output)-1] != "/":
            listener = MyMoomaListener(args.output+"/")
        else:
            listener = MyMoomaListener(args.output)
    walker = ParseTreeWalker()
    walker.walk(listener, tree)
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
