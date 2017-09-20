/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex0;

import java.util.Map;

/**
 *
 * @author sidereus
 */
public abstract class InputProcessing {

    protected void getSingleTimeStepData(Integer key, double val, Map<Integer, double[]> outval) {
        double[] tmpval = new double[]{val};
        outval.put(key, tmpval);
    }

    protected void removeTimeStepFromBuffer(Integer key, double[] val, Map<Integer, double[]> inval) {
        double[] newval = new double[val.length - 1];
        System.arraycopy(val, 1, newval, 0, newval.length);
        inval.replace(key, newval);
    }

}
