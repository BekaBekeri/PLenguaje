public class Main {

    public static void main(String[] args) {
        String[] inAlphabet = {"0", "1"};
        MooreMachine halver = new MooreMachine(MooreMachine.States.Q0, inAlphabet);

        halver.input("111");
    }
}
