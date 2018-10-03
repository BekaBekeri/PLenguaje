public class MooreMachine {
    public enum States{
        Q0, Q1, Q2, Q3;
    }

    private States currentState;
    private String[] inAlphabet;

    MooreMachine(States initialState, String[] inAlphabet){
        this.currentState = initialState;
        this.inAlphabet = inAlphabet;
    }

    public States getCurrentState() {
        return currentState;
    }

    public void transition(String token)
    {
        switch (currentState){
            case Q0:
                if (token.equals("0"))
                {
                    currentState = States.Q0;
                }
                else if (token.equals("1"))
                {
                    currentState = States.Q1;
                }
                break;
            case Q1:
                if (token.equals("0"))
                {
                    currentState = States.Q3;
                }
                else if (token.equals("1"))
                {
                    currentState = States.Q2;
                }
                break;
            case Q2:
                if (token.equals("0"))
                {
                    currentState = States.Q2;
                }
                else if (token.equals("1"))
                {
                    currentState = States.Q3;
                }
                break;
            case Q3:
                if (token.equals("0"))
                {
                    currentState = States.Q0;
                }
                else if (token.equals("1"))
                {
                    currentState = States.Q1;
                }
                break;
        }
    }

    public void input(String input){
        String[] splittedInput = input.split("");


        System.out.println("Starting analysis of input");
        System.out.print(output());
        for (String token: splittedInput) {
            transition(token);
            System.out.print(output());
        }

    }

    public char output(){
        switch (currentState) {
            case Q0:
                return '0';
            case Q1:
                return '0';
            case Q2:
                return '1';
            default:
                return '1';
        }
    }
}
