public class Env<T> implements IEnvironment<T> {

	@Override
	public String translate(T input) {
		return input.toString();
	}
    
    public void predictTaken(){
        System.out.println("Predecimos salto");
    }
    
    public void predictNotTaken(){
        System.out.println("Predecimos no salto");
    }

}
