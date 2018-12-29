import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        MachineController mc = new MachineController(Machines.automata1());


        System.out.print("Input: ");
        String input = sc.nextLine();

        while(!input.equals("")){
            if (mc.addNewInput(input) != null){
                mc.getCurrentState().getOutput().run();
            }
            else{
                System.out.println("Entrada no reconocida por el automata");
            }
            System.out.print("Input: ");
            input = sc.nextLine();
        }

        sc.close();
    }
}
