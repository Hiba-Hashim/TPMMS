package pass2;

import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.text.*;
import java.util.Date;

import appConstants.AppConstants;
import memory.Memory;

public class Pass2 {

	public static void executePhase2(int numberOfChunks) throws IOException, ParseException {
        Memory memory = Memory.getInstance(); // no. blocks in a chunk

        int numberOfBlocksInAChunk = memory.numberOfBlocksInAChunk;
        int numberOfRecordsInAChunk = numberOfBlocksInAChunk * AppConstants.blockSize;
        int blockSize = AppConstants.blockSize; //buffer size ( is no. records in a block = size of block)

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<ArrayList<ArrayList<Integer>>> empIds = new ArrayList<ArrayList<ArrayList<Integer>>>();
        ArrayList<ArrayList<ArrayList<Date>>> dates = new ArrayList<ArrayList<ArrayList<Date>>>();
        ArrayList<ArrayList<ArrayList<String>>> data = new ArrayList<ArrayList<ArrayList<String>>>();

        // M-1 buffers for read & 1 buffer for write
        ArrayList<ArrayList<ArrayList<String>>> inputBuffers = new ArrayList<ArrayList<ArrayList<String>>>();
        //String[][][] inputBuffers = new String[numberOfChunks][numberOfBlocksInAChunk][numberOfRecordsInAChunk];
        ArrayList<String> outputBuffer = new ArrayList<String>();
        //String[] outputBuffer = new String[blockSize];


        String line;
        // Initialize inputBuffer
//        for (int i = 0; i < numberOfChunks; i++) {
//            inputBuffers.set(i, new ArrayList<ArrayList<String>>(numberOfBlocksInAChunk));
//            for (int j = 0; j < numberOfBlocksInAChunk; j++) {
//                inputBuffers.get(i).set(i, new ArrayList<String>(numberOfRecordsInAChunk));
//            }
//        }

        //store the blocks in the inputBuffers
        for (int chunk = 0; chunk < inputBuffers.size(); chunk++) {
            String chunkFilePath = AppConstants.sortedChunksPath + "chunk" + chunk + ".txt";
            BufferedReader br = new BufferedReader(new FileReader(chunkFilePath));
            while ((line = br.readLine()) != null) {
                for (int block = 0; block <= inputBuffers.get(chunk).size(); block++) {
                    for (int record = 0; record <= inputBuffers.get(chunk).get(block).size(); record++) {
                        inputBuffers.get(chunk).get(block).set(record, br.readLine());
                        int empId = Integer.parseInt(line.substring(0, 8));
                        empIds.get(chunk).get(block).set(record, empId);
                        String date = line.substring(9, 18);
                        dates.get(chunk).get(block).set(record, sdformat.parse(date));
                        data.get(chunk).get(block).set(record, line.substring(19));
                    }
                }
            }
            br.close();
        }



        //compare blocks
        String smallestRecord = null;
        for (int i = 0; i < inputBuffers.size(); i++) {//put the chunck loop last
            for (int m = i + 1; i < inputBuffers.size(); m++) {
                for (int j = 0; j < inputBuffers.get(i).size(); j++) {
                    for (int k = 0; k < blockSize; k++) {
                        if (empIds.get(i).get(j).get(0) == empIds.get(m).get(j).get(0)) {
                            // compare date and keep the resent one
                            if (dates.get(i).get(j).get(0).compareTo(dates.get(m).get(j).get(0)) < 0) {
                                smallestRecord = inputBuffers.get(m).get(j).get(0);
                                inputBuffers.get(m).get(j).remove(0);
                                empIds.get(m).get(j).remove(0);
                                dates.get(m).get(j).remove(0);
                            } else {
                                smallestRecord = inputBuffers.get(i).get(j).get(0);
                                inputBuffers.get(i).get(j).remove(0);
                                empIds.get(i).get(j).remove(0);
                                dates.get(i).get(j).remove(0);
                            }
                        }
                        else if (empIds.get(i).get(j).get(0) < empIds.get(m).get(j).get(0)) {
                            smallestRecord = inputBuffers.get(i).get(j).get(0);
                            inputBuffers.get(i).get(j).remove(0);
                            empIds.get(i).get(j).remove(0);
                        }
                        else if (empIds.get(i).get(j).get(0) > empIds.get(m).get(j).get(0)) {
                            smallestRecord = inputBuffers.get(i).get(j).get(0);
                            inputBuffers.get(m).get(j).remove(0);
                            empIds.get(m).get(j).remove(0);
                        }
                        if(k < blockSize) {//if its full save it into the disk
                            outputBuffer.add(k, smallestRecord);
                        }
                        else{
                            savetoDisk(outputBuffer);
                            outputBuffer = null;
                        }
                    }
                }
            }
        }
    }

    private static void savetoDisk(ArrayList<String> outputBuffer) throws IOException {

        String filePath = AppConstants.resultFilePath;
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
        writer.append(outputBuffer + "\n");
        writer.close();
	
	}
	
}
