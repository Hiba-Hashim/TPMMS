package pass1;

public class MergeSort {

	public static void mergeSort(int[] empIds, String[] data, int left, int right) {
		if (left < right) {
			int middle = (left + right) / 2;

			mergeSort(empIds, data, left, middle);
			mergeSort(empIds, data, middle + 1, right);

			merge(empIds, data, left, middle, right);
		}
	}

	private static void merge(int[] empIds, String[] data, int left, int middle, int right) {
		int n1 = middle - left + 1;
		int n2 = right - middle;

		int empIdsL[] = new int[n1];
		int empIdsR[] = new int[n2];
		String[] dataL = new String[n1];
		String[] dataR = new String[n2];

		for (int i = 0; i < n1; ++i) {
			empIdsL[i] = empIds[left + i];
			dataL[i] = data[left + i];
		}
			
		for (int j = 0; j < n2; ++j) {
			empIdsR[j] = empIds[middle + 1 + j];
			dataR[j] = data[middle + 1 + j];
		}

		int i = 0, j = 0;

		int k = left;
		while (i < n1 && j < n2) {
			if (empIdsL[i] <= empIdsR[j]) {
				empIds[k] = empIdsL[i];
				data[k] = dataL[i];
				i++;
			} else {
				empIds[k] = empIdsR[j];
				data[k] = dataR[j];
				j++;
			}
			k++;
		}

		while (i < n1) {
			empIds[k] = empIdsL[i];
			data[k] = dataL[i];
			i++;
			k++;
		}

		while (j < n2) {
			empIds[k] = empIdsR[j];
			data[k] = dataR[j];
			j++;
			k++;
		}
	}

}
