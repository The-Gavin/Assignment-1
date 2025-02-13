import project.annotations.ConceptualAPIPrototype;

public class PrototypeFactor
{
	@ConceptualAPIPrototype
	public void prototype(CompFactor factoring)
	{
		//Component for initialization, reading, and writing
		String output = factoring.printFactor();
		//component for computation
		int[] data = factoring.factorCalc();
	}
}