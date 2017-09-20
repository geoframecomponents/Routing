/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex0;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import oms3.annotations.*;

/**
 *
 * @author Marialaura Bancheri, Francesco Serafin
 */
public class MuskingumCunge3 {

    @In public Map<Integer, double[]> inFromAboveVert1;
    @In public Map<Integer, double[]> inFromAboveVert2;
    @In public Map<Integer, double[]> inFromAboveVert3;
    @In public Map<Integer, double[]> inComputation;
    @In public double C1_FromAboveVert1;
    @In public double C2_FromAboveVert1;
    @In public double C3_FromAboveVert1;
    
    @In public double C1_FromAboveVert2;
    @In public double C2_FromAboveVert2;
    @In public double C3_FromAboveVert2;
    
    @In public double C1_FromAboveVert3;
    @In public double C2_FromAboveVert3;
    @In public double C3_FromAboveVert3;
    
    @Out public HashMap<Integer, double[]> outSum;

    
    double valueFromAboveVert1;
    double valueFromAboveVert2;
    double valueFromAboveVert3;
    double valueinComputation;
    double muskingumTot;


    @Execute
    public void exec() {

        outSum = new HashMap<Integer, double[]>();

        Iterator<Entry<Integer, double[]>> iterVert1 = inFromAboveVert1.entrySet().iterator();
        Iterator<Entry<Integer, double[]>> iterVert2 = inFromAboveVert2.entrySet().iterator();
        Iterator<Entry<Integer, double[]>> iterVert3 = inFromAboveVert3.entrySet().iterator();
        
        Iterator<Entry<Integer, double[]>> iter2 = inComputation.entrySet().iterator();
        
        
        double muskingum1=C2_FromAboveVert1*valueFromAboveVert1+C3_FromAboveVert1*muskingumTot;
        double muskingum2=C2_FromAboveVert2*valueFromAboveVert2+C3_FromAboveVert2*muskingumTot;
        double muskingum3=C3_FromAboveVert3*valueFromAboveVert3+C3_FromAboveVert3*muskingumTot;

        while(iterVert1.hasNext() || iter2.hasNext()) {

            Entry<Integer, double[]> eVert1 = iterVert1.next();
            Integer keyVert1 = eVert1.getKey();
            double[] valVert1 = eVert1.getValue();
            valueFromAboveVert1=valVert1[0];
            
            Entry<Integer, double[]> eVert2 = iterVert2.next();
            Integer keyVert2 = eVert2.getKey();
            double[] valVert2 = eVert2.getValue();
            valueFromAboveVert2=valVert2[0];
            
            Entry<Integer, double[]> eVert3 = iterVert3.next();
            Integer keyVert3 = eVert3.getKey();
            double[] valVert3 = eVert3.getValue();
            valueFromAboveVert3=valVert3[0];

            Entry<Integer, double[]> e2 = iter2.next();
            Integer key2 = e2.getKey();
            double[] val2 = e2.getValue();
            valueinComputation=val2[0];
            
            muskingumTot=C1_FromAboveVert1*valueFromAboveVert1+
            					C1_FromAboveVert2*valueFromAboveVert2+
            					C1_FromAboveVert3*valueFromAboveVert3+
            					muskingum1+muskingum2+muskingum3+valueinComputation;

            // add check on equals key: MUST BE EQUAL
            //double[] outval = new double[]{val1[0] + val2[0]};

            outSum.put(key2, new double[]{muskingumTot} );

        }

    }

}
