package src;

import java.io.File;

import src.NetworkAPIPrototype;

public class PrototypeFactor {
	@NetworkAPIPrototype
	public void prototype(WebServer server)
	{
		//Send integers to webserver
		//server.intMethod(f);
		server.factorCalc();
		server.outputSplit();
		//specify the output separation
		
		
		// create a bunch of objects and call the methods that are relevant to them owhen necesary
	}

	public int intMethod(File f) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void factorCalc() {
		// TODO Auto-generated method stub
		
	}

	public void outputSplit() {
		// TODO Auto-generated method stub
		
	}
}