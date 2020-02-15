package pass2;

import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
//import java.time.LocalDate;

import appConstants.AppConstants;
import memory.Memory;

public class Pass2 {

	public static void executePhase2(int numberOfChunks) throws IOException{
		Memory memory = Memory.getInstance(); // no. blocks in a chunk
		
		int numberOfBlocksInAChunk = memory.numberOfBlocksInAChunk;
		int numberOfRecordsInAChunk = numberOfBlocksInAChunk * AppConstants.blockSize;
		int blockSize = AppConstants.blockSize; //buffer size ( is no. records in a block = size of block)
		
		int[] empIds = new int[numberOfRecordsInAChunk];
		String[] dates = new String[numberOfRecordsInAChunk];  
		String[] data = new String[numberOfRecordsInAChunk];
		
		// M-1 buffers for read & 1 buffer for write
		String[][] inputBuffers = new String[numberOfChunks][numberOfRecordsInAChunk];
		int[] outputBuffer = new int[blockSize]; 
		
		String line;
        
        //store the blocks in the inputBuffers
		for(int chunk = 1 ; chunk <= numberOfChunks ; chunk++){
			String chunkFilePath = AppConstants.sortedChunksPath + "chunk" + chunk + ".txt";
			//BufferedReader br = new BufferedReader(new FileReader(chunkFilePath));
			//RandomAccessFile raf = new RandomAccessFile(chunkFilePath, "r");
			int nextblock = 0;
			for( int block = 0; block <= numberOfRecordsInAChunk; block++){
				inputBuffers[chunk][block]= new String(readFromBlock(chunkFilePath, nextblock, blockSize));
			    nextblock=+ blockSize;		
			}
		}
		
		for(int j=0; j<= numberOfBlocksInAChunk; j++){
			for (int i =0; i<= numberOfChunks; i++){
				String records = inputBuffers[i][j];
				
			}
		}
		
		//compare blocks
		int k=0;
		for (int i =0; i<= numberOfChunks; i++){
			String chunkFilePath = AppConstants.sortedChunksPath + "chunk" + i + ".txt";
			BufferedReader br = new BufferedReader(new FileReader(chunkFilePath));
			for(int j=0; j<= numberOfBlocksInAChunk; j++){
				while (( line  = br.readLine()) != null){
					int empId = Integer.parseInt(line.substring(0, 8));
			        empIds[k] = empId;
			        String date = line.substring(9, 18);
			        dates[k] = date;
			        data[k] = line.substring(19);
				}							
			}
		}
	}

	private static byte[] readFromBlock(String filePath, int position, int size) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "r");
        file.seek(position);
        byte[] bytes = new byte[size];
        file.read(bytes);
        file.close();
        return bytes;
    }
	
	private static void savetoDisk() {
	
	}
	
}
