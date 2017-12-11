package automa.builder;

public interface BuilderI<T> {
	
	public boolean buildValido();
	
	public T build();

}
