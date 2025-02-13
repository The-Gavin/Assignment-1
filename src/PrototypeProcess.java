import project.annotations.ProcessAPIPrototype;

public class PrototypeProcess {
	@ProcessAPIPrototype
	public void prototype(Processing readWriter)
	{
		//Read from output
		ReadData outputSource = readWriter.readMethod();
		//Write to input
		WriteData inputSource = readWriter.writeMethod();
	}
}