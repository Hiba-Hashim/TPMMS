package memory;

import appConstants.AppConstants;

public class Memory {
	
	private static Memory memory = null; 
	  
    public int numberOfBlocksInAChunk; 
  
    private Memory() 
    { 
    	numberOfBlocksInAChunk = getNumberOfBlocksInAChunk();
    } 
  
    public static Memory getInstance() 
    { 
        if (memory == null) 
        	memory = new Memory(); 
  
        return memory; 
    } 
	
	private static int getNumberOfBlocksInAChunk() {
		long freeMemory = Runtime.getRuntime().freeMemory() - 524288;
		int numberOfBlocksInAChunk = (int) (freeMemory / (AppConstants.blockSize * 160));
		return numberOfBlocksInAChunk;
	}
	
}
