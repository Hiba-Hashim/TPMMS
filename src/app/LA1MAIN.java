package app;

import java.io.IOException;

import pass1.Pass1;
import pass2.Pass2;

public class LA1MAIN {

	public static void main(String args[]) {
		double start = (double) System.currentTimeMillis();
		double phase2EndTime = 0;
		double phase1EndTime = 0;
		
		try {
			
			Pass1.executePhase1();
			phase1EndTime = (double) System.currentTimeMillis();
			System.out.println("Pass 1 Execution time :" + (phase1EndTime - start) / 1000 + " seconds");
			
			Pass2.executePhase2(Pass1.chunksCreated);
			phase2EndTime = (double) System.currentTimeMillis();
			System.out.println("Pass 2 Execution time :" + (phase2EndTime - phase1EndTime) / 1000 + " seconds");
			
			System.out.println("Execution time :" + (phase2EndTime - start) / 1000 + " seconds");
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

	}

}
