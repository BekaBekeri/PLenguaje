import sys
from antlr4 import *
from moomaLexer import moomaLexer
from moomaParser import moomaParser
from myMoomaListener import MyMoomaListener
from moomaErrorListener import moomaErrorListener


def main(argv):
    file = FileStream(sys.argv[1])

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


if __name__ == "__main__":
    main(sys.argv)

