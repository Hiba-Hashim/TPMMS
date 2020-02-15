package pass1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import appConstants.AppConstants;
import memory.Memory;

//TODO: Execute without calling garbage collection
public class Pass1 {

	public static int chunksCreated = 0;

	public static void executePhase1() throws NumberFormatException, IOException {

		Memory memory = Memory.getInstance();
		
		int numberOfBlocksInAChunk = memory.numberOfBlocksInAChunk;
		int numberOfRecordsInAChunk = numberOfBlocksInAChunk * AppConstants.blockSize;

		readAndSortData(AppConstants.dataFile1, numberOfRecordsInAChunk);
		readAndSortData(AppConstants.dataFile2, numberOfRecordsInAChunk);

	}

	private static void readAndSortData(String filePath, int numberOfRecordsInAChunk) throws NumberFormatException, IOException {

		int[] empIds = new int[numberOfRecordsInAChunk];
		String[] data = new String[numberOfRecordsInAChunk];

		BufferedReader br = new BufferedReader(new FileReader(filePath));

		String line;
		int index = 0;

		while ((line = br.readLine()) != null) {
			int empId = Integer.parseInt(line.substring(0, 8));
			empIds[index] = empId;
			data[index] = line.substring(8);
			index++;
			if (index == numberOfRecordsInAChunk) {
				MergeSort.mergeSort(empIds, data, 0, numberOfRecordsInAChunk - 1);
				saveChunkIntoDisk(empIds, data, numberOfRecordsInAChunk);
				empIds = null;
				data = null;
				empIds = new int[numberOfRecordsInAChunk];
				data = new String[numberOfRecordsInAChunk]; 
				System.gc();
				index = 0;
			}
		}
		
		if(index != 0) {
			MergeSort.mergeSort(empIds, data, 0, numberOfRecordsInAChunk - 1);
			saveChunkIntoDisk(empIds, data, numberOfRecordsInAChunk);
			empIds = null;
			data = null;
			empIds = new int[numberOfRecordsInAChunk];
			data = new String[numberOfRecordsInAChunk];
		}		
		
		System.gc();
		
		br.close();

	}
	
	private static void saveChunkIntoDisk(int[] empIds, String[] data, int length) throws IOException {
		
		chunksCreated++;
		String chunkFilePath = AppConstants.sortedChunksPath + "chunk" + chunksCreated + ".txt";
	    BufferedWriter writer = new BufferedWriter(new FileWriter(chunkFilePath, false));
	    for(int i = 0; i  < length; i++) {
	    	if(empIds[i] == 0) {
	    		break;
	    	}
	    	writer.append(empIds[i] + data[i] + "\n");
	    }	     
	    writer.close();
	}

}
