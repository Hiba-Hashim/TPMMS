package DummyDataGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DummyData {

	public static void main(String args[]) throws NumberFormatException, IOException {
		generateDummyData(500000, "./data/data1.txt");
		generateDummyData(1000000, "./data/data2.txt");
	}

	private static void generateDummyData(int numberOfRecordsToGenerate, String newDataFilePath) throws NumberFormatException, IOException {
		String existingDataFilePath = "./data/sample.txt";
		int[] empIds = new int[10000];
		String[] data = new String[10000];

		BufferedReader br = new BufferedReader(new FileReader(existingDataFilePath));

		String line;
		int index = 0;

		while ((line = br.readLine()) != null) {
			int empId = Integer.parseInt(line.substring(0, 8));
			empIds[index] = empId;
			data[index] = line.substring(8);
			index++;
		}

		int[] newEmpIds = new int[numberOfRecordsToGenerate];
		String[] newData = new String[numberOfRecordsToGenerate];
		copyOldEmpIds(empIds, newEmpIds);
		generateNewEmpIds(newEmpIds, numberOfRecordsToGenerate);
		copyOldData(newData, data, numberOfRecordsToGenerate);
		saveDataIntoDisk(newEmpIds, newData, numberOfRecordsToGenerate, newDataFilePath);
		br.close();
	}

	private static void saveDataIntoDisk(int[] newEmpIds, String[] newData, int length, String filePath) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
		for (int i = 0; i < length; i++) {
			if (newEmpIds[i] == 0) {
				break;
			}
			writer.append(newEmpIds[i] + newData[i] + "\n");
		}
		writer.close();
	}

	private static void copyOldData(String[] newData, String[] data, int numberOfRecordsToGenerate) {

		Random random = new Random(0);
		int min = 0;
		int max = 9999;

		for (int i = 0; i < numberOfRecordsToGenerate; i++) {
			int randomRecordIndex = random.nextInt((max - min) + 1) + min;
			newData[i] = data[randomRecordIndex];
		}

	}

	private static void generateNewEmpIds(int[] newEmpIds, int numberOfRecordsToGenerate) {
		Random random = new Random(0);
		int min = 10000000;
		int max = 99999999;
		for (int i = 10000; i < numberOfRecordsToGenerate; i++) {
			int randomId = random.nextInt((max - min) + 1) + min;
			newEmpIds[i] = randomId;
		}
	}

	private static void copyOldEmpIds(int[] empIds, int[] newEmpIds) {
		for (int i = 0; i < empIds.length; i++) {
			newEmpIds[i] = empIds[i];
		}
	}

}
