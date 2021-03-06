package l210902;

import java.util.Map;

import l210902.ListTest.Reader;
import l210902.ListTest.Writer;

abstract class MapTester extends Tester<Map<Integer, Integer>>{

	public MapTester(String testId, int nReaders, int nWriters) {
		super(testId, nReaders, nWriters);
	}
	
	class Reader extends TestTask {
		long result = 0;

		@Override
		public void test() {
			for (int i = 0; i < testCycles; i++) {
				for (int j = 0; j < containerSize; j++) {
					result += testContariner.get(j);
				}
			}
		}

		@Override
		public void putResults() {
			readResult += result;
			readTime += duration;
		}
	}
	
	class Writer extends TestTask {

		@Override
		public void test() {
			for (int i = 0; i < testCycles; i++) {
				for (int j = 0; j < containerSize; j++) {
					testContariner.put(j, writeData[j]);
				}
			}
		}

		@Override
		public void putResults() {
			writeTime += duration;
		}
		
	}

	@Override
	public void startReadersAndWriters() {
		for (int i = 0; i < nReaders; i++) {
			exec.execute(new Reader());
		}
		
		for (int i = 0; i < nWriters; i++) {
			exec.execute(new Writer());
		}
	}

}
