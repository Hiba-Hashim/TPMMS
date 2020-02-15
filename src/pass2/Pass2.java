package pass2;

import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException;

import appConstants.AppConstants;
import memory.Memory;

public class Pass2 {

	public static void executePhase2(int numberOfChunks) {
		Memory memory = Memory.getInstance(); // no. blocks in a chunk
		
		int numberOfBlocksInAChunk = memory.numberOfBlocksInAChunk;
		int blockSize = AppConstants.blockSize; //buffer size
		
		int[] empIds = new int[numberOfRecordsInAChunk];
		String[] data = new String[numberOfRecordsInAChunk];
		
		// M-1 buffers for read & 1 buffer for write
		int[] outputBuffers = new int[numberOfChunks];
		int inputBuffer; 

        String line;
        
		for(chunk =0 ; chunk <= numberOfChunks ;  chunk++){
			String chunkFilePath = AppConstants.sortedChunksPath + "chunk" + chunk + ".txt";
		    BufferedReader r = new BufferedReader(new FileReader(chunkFilePath));
		    
		
		}
	}
	
	private static void readAndSortData(){
	
	}
	
	private static void savetoDisk() {
	
	}
	
}
