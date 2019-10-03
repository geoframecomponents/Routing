/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muskingumCunge;

import static org.jgrasstools.gears.libs.modules.JGTConstants.isNovalue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import oms3.annotations.*;

/**
 *
 * @author Marialaura Bancheri, Francesco Serafin
 */
public class MuskingumCunge1 {

	@In public Map<Integer, double[]> inComputation;
	@In public Map<Integer, double[]> initialConditionMusk_i;
	@In public Map<Integer, double[]> initialConditionQ_i;
	@In public double l;
	@In public double uc;
	@In public double x;
	@In public int ID;


	@Out public HashMap<Integer, double[]> outSum;
	@Out public HashMap<Integer, double[]> outMusk;


	double Ca;
	double Cb;
	double C;

	double valueinComputation;
	double muskingumTot;

	int step;

	@Execute
	public void exec() {

		outSum = new HashMap<Integer, double[]>();
		outMusk = new HashMap<Integer, double[]>();

		double k=l*1000/(uc*3600);

		Ca=(k*x+0.5)/(k*(1-x)+0.5);
		Cb=(0.5-k*x)/(k*(1-x)+0.5);
		C=(k*(1-x)-0.5)/(k*(1-x)+0.5);
		double check=Ca+Cb+C;

		Iterator<Entry<Integer, double[]>> iter2 = inComputation.entrySet().iterator();

		if(step==0){
			if(initialConditionMusk_i!=null){
				//Iterator<Entry<Integer, double[]>> iter1 = initialConditionMusk_i.entrySet().iterator();
				//Entry<Integer, double[]> e1 = iter1.next();
				double val1 = initialConditionMusk_i.get(ID)[0];
				if (isNovalue(val1))	val1=0.05;	
				
				//Iterator<Entry<Integer, double[]>> iter3 = initialConditionQ_i.entrySet().iterator();
				//Entry<Integer, double[]> e3 = iter3.next();
				double val3 = initialConditionMusk_i.get(ID)[0];
				if (isNovalue(val3))	val3=0.05;	
				
				muskingumTot=val1;
				valueinComputation=val3;
				//System.out.println(muskingumTot);

			}else{
				muskingumTot=0.05;
				valueinComputation=0.05;
			}			
		}

		double muskingum=Cb* valueinComputation+C*muskingumTot;

		while(iter2.hasNext()) {


			Entry<Integer, double[]> e2 = iter2.next();
			Integer key2 = e2.getKey();
			
			double val2 = inComputation.get(ID)[0];
			valueinComputation=val2;

			muskingumTot=Ca* valueinComputation+muskingum;

			double out=(muskingumTot)<0?0:muskingumTot;
			//double out=muskingumTot;
			
			System.out.println(out);
	
			outMusk.put(key2, new double[]{out} );

		}

		step++;
	}

}
