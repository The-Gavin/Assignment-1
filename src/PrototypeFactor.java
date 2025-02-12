import project.annotations.ConceptualAPIPrototype;

public class PrototypeFactor
{
	@ConceptualAPIPrototype
	public void prototype(Engine engine)
	{
		//Component for initialization, reading, and writing
		UserData output = engine.printFactor();
		//component for computation
		UserData data = engine.factorCalc();
	}
}