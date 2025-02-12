package src;

import java.io.File;

import src.NetworkAPI;

@NetworkAPI
public interface WebServer
{
	//take in a number of integers
	public int intMethod(File f);
	
	// output the integer followed by each of its factors
	// find all the factors of the integers in order
	public void factorCalc();
	
	//Split the output by the user-specified character
	public void outputSplit();
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