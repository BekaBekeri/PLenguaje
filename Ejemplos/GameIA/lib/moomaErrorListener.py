from antlr4.error.ErrorListener import ErrorListener
import sys


class moomaErrorListener(ErrorListener):
    def __init__(self):
        super().__init__()

    def syntaxError(self, recognizer, offendingSymbol, line, column, msg, e):
        sys.stderr.write("line {}:{} {}\n".format(line, column, msg))
        self.underlineError(recognizer, offendingSymbol, line, column)

    def underlineError(self, recognizer, offendingToken, line, column):
        tokens = recognizer.getInputStream()
        input = str(tokens.tokenSource.inputStream)
        lines = input.split("\n")
        errorLine = lines[line - 1]
        sys.stderr.write(errorLine + "\n")
        for i in range(column):
            sys.stderr.write(" ")
        start = offendingToken.start
        stop = offendingToken.stop
        if start >= 0 and stop >= 0:
            for x in range(start, stop + 1):
               sys.stderr.write("^")
        sys.stderr.write("\n")
