import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface Engine
{
	// output the integer followed by each of its factors
	public String printFactor();
	// find all the factors of the integers in order
	public int[] factorCalc();
}