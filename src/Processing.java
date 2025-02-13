import project.annotations.ProcessAPI;

@ProcessAPI
public interface Processing
{
	//Read data from user-specified output source
	public ReadData readMethod();
	//Store data to user-specified input source
	public WriteData writeMethod();
}