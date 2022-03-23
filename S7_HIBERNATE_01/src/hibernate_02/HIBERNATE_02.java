/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate_02;

import controller.Operation;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author JLO
 */
public class HIBERNATE_02 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           TimerTask timerTask = new TimerTask()
     {
         @Override
         public void run() 
         {
//        Operation operation;
//        operation = new Operation();
//        operation.insert("9999");
         }
     };
     Timer timer = new Timer();
     timer.scheduleAtFixedRate(timerTask, 0, 5000);
    }
    
}
