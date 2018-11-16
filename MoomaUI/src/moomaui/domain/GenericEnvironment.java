package moomaui.domain;

public class GenericEnvironment<T> implements IEnvironment<T> {

	@Override
	public String translate(T input) {
		return input.toString();
	}

}
