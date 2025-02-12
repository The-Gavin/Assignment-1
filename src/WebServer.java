import project.annotations.NetworkAPI;

@NetworkAPI
public interface WebServer
{
	//take in a number of integers
	
	public int intMethod();
	
	// output the integer followed by each of its factors
	// find all the factors of the integers in order
	public int[] factorCalc();
	
	//Split the output by the user-specified character
	public String outputSplit();
}

/*
Scanner input = new Scanner(System.in); //won't be a scanner, 
int inputVar = input.nextInt();
System.out.print(inputVar + ": ");
for (int i = 0; i<= inputVar; i++)
{
	if (inputVar % i == 0)
	{
		System.out.print(i+",");
	}
}
*/