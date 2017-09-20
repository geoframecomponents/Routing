

import java.net.URISyntaxException;
import java.util.HashMap;

import org.jgrasstools.gears.io.timedependent.OmsTimeSeriesIteratorReader;
import org.jgrasstools.gears.io.timedependent.OmsTimeSeriesIteratorWriter;
import org.jgrasstools.gears.libs.monitor.PrintStreamProgressMonitor;
import org.junit.Test;

import ex0.MuskingumCunge1;

public class TestMuskingum1{

	@Test
	public void testLinear() throws Exception {

		String startDate = "1994-01-01 00:00";
		String endDate = "1994-01-03 23:00";
		int timeStepMinutes = 60;
		String fId = "ID";

		PrintStreamProgressMonitor pm = new PrintStreamProgressMonitor(System.out, System.out);

		String inpathToQout= "resources/Input/rainfall.csv";


		OmsTimeSeriesIteratorReader QoutReader = getTimeseriesReader(inpathToQout, fId, startDate, endDate, timeStepMinutes);
		
		MuskingumCunge1 test= new MuskingumCunge1();
		


		while( QoutReader.doProcess ) {
		
			QoutReader.nextRecord();
			test.C1=0.2;
			test.C2=0.3;
			test.C3=0.5;

	
			HashMap<Integer, double[]> id2ValueMap = QoutReader.outData;
            test.inComputation= id2ValueMap;
            test.inFromAboveVert=id2ValueMap;
            

            test.exec();
            
            System.out.println("Finished");
            


			
		}
		

		QoutReader.close();
		

	}


	private OmsTimeSeriesIteratorReader getTimeseriesReader( String inPath, String id, String startDate, String endDate,
			int timeStepMinutes ) throws URISyntaxException {
		OmsTimeSeriesIteratorReader reader = new OmsTimeSeriesIteratorReader();
		reader.file = inPath;
		reader.idfield = "ID";
		reader.tStart = startDate;
		reader.tTimestep = 60;
		reader.tEnd = endDate;
		reader.fileNovalue = "-9999";
		reader.initProcess();
		return reader;
	}
}
