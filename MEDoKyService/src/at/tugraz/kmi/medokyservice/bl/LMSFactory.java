package at.tugraz.kmi.medokyservice.bl;

import at.tugraz.kmi.medokyservice.io.ARLearnIO;
import at.tugraz.kmi.medokyservice.io.TestLMS;
import at.tugraz.kmi.medokyservice.resources.LMSLayerIO;

public class LMSFactory {

	public void LMSFacory(){}
	
	public LMSLayerIO getLMSInterface(String name){
		if (name.equals("ARLearn"))
			return new ARLearnIO();
		else if (name.equals("TestLMS"))
			return new TestLMS();
		return null;
	}
}
