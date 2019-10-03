

import java.net.URISyntaxException;
import java.util.HashMap;

import org.jgrasstools.gears.io.timedependent.OmsTimeSeriesIteratorReader;
import org.jgrasstools.gears.io.timedependent.OmsTimeSeriesIteratorWriter;
import org.junit.Test;

import muskingumCunge.MuskingumCunge1;

public class TestMuskingum1{

	@Test
	public void testLinear() throws Exception {

		String startDate = "2018-12-20 11:00";
		String beforeDate = "2018-12-20 10:00";
		String endDate = "2018-12-20 20:00";
		int timeStepMinutes = 60;
		String fId = "ID";
		

		String inpathToQout= "/Users/marialaura/Desktop/IdrogrammaInput_11_Basento.csv";
		String inpathToQt= "/Users/marialaura/Desktop/Storico_Idrogramma_11_Basento.csv";
		String inpathToQt_i= "/Users/marialaura/Desktop/Storico_IdrogrammaInput_11_Basento.csv";
		String inpathToQ= "/Users/marialaura/Desktop/prova.csv";

		OmsTimeSeriesIteratorReader QoutReader = getTimeseriesReader(inpathToQout, fId, startDate, endDate, timeStepMinutes);
		OmsTimeSeriesIteratorReader QtReader = getTimeseriesReader(inpathToQt, fId, beforeDate, beforeDate, timeStepMinutes);
		OmsTimeSeriesIteratorReader Qt_1Reader = getTimeseriesReader(inpathToQt_i, fId, beforeDate, beforeDate, timeStepMinutes);
		
		MuskingumCunge1 test= new MuskingumCunge1();
		
		OmsTimeSeriesIteratorWriter writerQ = new OmsTimeSeriesIteratorWriter();
		
		writerQ.file = inpathToQ;
		writerQ.tStart = startDate;
		writerQ.tTimestep = timeStepMinutes;
		writerQ.fileNovalue="-9999";

		while( QoutReader.doProcess ) {
		
			QoutReader.nextRecord();
			test.l=6.56;
			test.uc=0.70;
			test.x=0.10;
			test.ID=11;

	
			HashMap<Integer, double[]> id2ValueMap = QoutReader.outData;
            test.inComputation= id2ValueMap;
            
            QtReader.nextRecord();
			id2ValueMap = QtReader.outData;
			test.initialConditionMusk_i = id2ValueMap;
			
            Qt_1Reader.nextRecord();
			id2ValueMap = Qt_1Reader.outData;
			test.initialConditionQ_i = id2ValueMap;
            

            test.exec();
            
            System.out.println("Finished");
            
            HashMap<Integer, double[]> outHMQ= test.outMusk;
            
			writerQ.inData = outHMQ ;
			writerQ.writeNextLine();
			
			if (inpathToQ != null) {
				writerQ.close();
			}
			
            


			
		}
		

		QoutReader.close();
		QtReader.close();
		

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
