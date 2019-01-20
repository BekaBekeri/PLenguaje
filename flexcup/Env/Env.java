package Env;
public class Env implements IEnvironment {

	@Override
	public String translate(Object input) {
        if (input.toString().equals("Taken")){
            return "2";
        }
        else if (input.toString().equals("Not Taken")){
            return "1";
        }
        else{
            return "0";
        }
	}
    
    public static void predictTaken(){
        System.out.println("Predecimos salto");
    }
    
    public static void predictNotTaken(){
        System.out.println("Predecimos no salto");
    }

}
