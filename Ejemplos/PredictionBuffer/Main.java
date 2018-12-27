import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        MachineSimulator ms = new MachineSimulator(Machines.automata1());


        System.out.print("Input: ");
        String input = sc.nextLine();

        while(!input.equals("")){
            if (!ms.addNewInput(input)){
                System.err.println("Evento no reconocido por el lenguaje");
            }
            ms.getCurrentState().getOutput().run();

            System.out.print("Input: ");
            input = sc.nextLine();
        }

        sc.close();
    }
}
