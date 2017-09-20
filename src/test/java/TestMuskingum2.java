

import java.net.URISyntaxException;
import java.util.HashMap;

import org.jgrasstools.gears.io.timedependent.OmsTimeSeriesIteratorReader;
import org.jgrasstools.gears.io.timedependent.OmsTimeSeriesIteratorWriter;
import org.jgrasstools.gears.libs.monitor.PrintStreamProgressMonitor;
import org.junit.Test;

import ex0.MuskingumCunge2;

public class TestMuskingum2{

	@Test
	public void testLinear() throws Exception {

		String startDate = "1994-01-01 00:00";
		String endDate = "1994-01-03 23:00";
		int timeStepMinutes = 60;
		String fId = "ID";

		PrintStreamProgressMonitor pm = new PrintStreamProgressMonitor(System.out, System.out);

		String inpathToQout= "resources/Input/rainfall.csv";


		OmsTimeSeriesIteratorReader QoutReader = getTimeseriesReader(inpathToQout, fId, startDate, endDate, timeStepMinutes);
		
		MuskingumCunge2 test= new MuskingumCunge2();
		


		while( QoutReader.doProcess ) {
		
			QoutReader.nextRecord();
			test.C1_FromAboveVert1=0.1;
			test.C2_FromAboveVert1=0.15;
			test.C3_FromAboveVert1=0.25;
			
			test.C1_FromAboveVert2=0.1;
			test.C2_FromAboveVert2=0.15;
			test.C3_FromAboveVert2=0.25;

	
			HashMap<Integer, double[]> id2ValueMap = QoutReader.outData;
            test.inComputation= id2ValueMap;
            test.inFromAboveVert1=id2ValueMap;
            test.inFromAboveVert2=id2ValueMap;
            

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
