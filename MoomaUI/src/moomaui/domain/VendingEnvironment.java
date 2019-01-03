package moomaui.domain;

import java.util.HashMap;
import java.util.function.Consumer;

public class VendingEnvironment implements IEnvironment<String> {
	public HashMap<String, Consumer<String>> getNotifiers() {
		HashMap<String, Consumer<String>> dict = new HashMap<>();
		
		dict.put("20", this::canSold);
		
		return dict;
	}
	
	@Override
	public String translate(String input) {
		if (input.equalsIgnoreCase("Bebida")) {
			return "20";
		} else if (input.equalsIgnoreCase("Bolsa")) {
			return "30";
		} else {
			return input;
		}
	}
	
	public void canSold(String currentState) {
		System.out.format("Hemos vendido una bebida y tenemos que devolver %d centimos\n", (Integer.parseInt(currentState) - 20));
	}

}
