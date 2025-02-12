import project.annotations.ConceptualAPIPrototype;

public class PrototypeFactor
{
	@ConceptualAPIPrototype
	public void prototype(CompFactor factoring)
	{
		//Component for initialization, reading, and writing
		UserData output = factoring.printFactor();
		//component for computation
		UserData data = factoring.factorCalc();
	}
}