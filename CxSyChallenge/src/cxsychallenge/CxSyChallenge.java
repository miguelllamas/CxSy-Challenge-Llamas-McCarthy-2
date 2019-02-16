package cxsychallenge;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * CxSy Challenge
 * @author Miggy Llamas, Tadhg McCarthy
 */

public class CxSyChallenge {

    public static void main(String[] args) {
        // agents and their respective attributes
        int[] agents = new int[50];
        int[] agentsCurrentPools = new int[50];
        int[] agentsCurrentEarnings = new int[50];
        
        // pools and their respective attributes
        ArrayList pools[] = new ArrayList[3];
        pools[0] = new ArrayList<Integer>();
        pools[1] = new ArrayList<Integer>();
        pools[2] = new ArrayList<Integer>();
            
        float stableEarnings = 1;
        float highEarnings = 0;
        float lowEarnings = 0;
        int stableCount = 0;
        int highCount = 0;
        int lowCount = 0;
        int[] stableAllCounts = new int[100];
        int[] highAllCounts = new int[100];
        int[] lowAllCounts = new int[100];
//        float[] stableAllEarnings = new float[100];
//        float[] highAllEarnings = new float[100];
//        float[] lowAllEarnings = new float[100];
        
        ArrayList<Float> stableAllEarnings = new ArrayList<Float>();
        ArrayList<Float> highAllEarnings = new ArrayList<Float>();
        ArrayList<Float> lowAllEarnings = new ArrayList<Float>();
                
        int tau = 2;
        Random rand = new Random();
        
        // setup
        for(int i = 0; i < 50; i++) {
            agentsCurrentEarnings[i] = 0;
        }
        
        // 100 timesteps
        for(int i = 0; i < 100; i++) {            
            pools[0].clear();
            pools[1].clear();
            pools[2].clear();
            
            // agent selects pool
            for(int j = 0; j < 50; j++) {                
                if(i == 0) {
                    agentsCurrentPools[j] = rand.nextInt(3);
                }
                else {
                    if(agentsCurrentEarnings[j] >= tau) {
                        if(rand.nextInt(100) < 75) {
                            int temp = agentsCurrentPools[j];
//                            float leastAgents = Math.min(Math.min(averageArray(stableAllCounts), averageArray(highAllCounts)), averageArray(lowAllCounts));                            
                            float highestEarnings = Math.max(Math.max(averageFloatArray(stableAllEarnings), averageFloatArray(highAllEarnings)), averageFloatArray(lowAllEarnings));                            
                            if(highestEarnings == averageFloatArray(stableAllEarnings)) {
                                agentsCurrentPools[j] = 0;
                            }
                            else if(highestEarnings == averageFloatArray(highAllEarnings)) {
                                agentsCurrentPools[j] = 1;
                            }
                            else {
                                agentsCurrentPools[j] = 2;
                            }
                            
                            if(temp != agentsCurrentPools[j]) {                                
                                agentsCurrentEarnings[j] = agentsCurrentEarnings[j] - tau;
                            }
                        }
                        else {
                            int temp = rand.nextInt(3);
                            if(temp != agentsCurrentPools[j]) {                                
                                agentsCurrentEarnings[j] = agentsCurrentEarnings[j] - tau;
                            }                                    
                            agentsCurrentPools[j] = temp;
                        }
                    }
                }
                pools[agentsCurrentPools[j]].add(j);
            }
            
            stableAllCounts[i] = pools[0].size();
            highAllCounts[i] = pools[1].size();
            lowAllCounts[i] = pools[2].size();
            
            // agent earns based on selected pool
            for(int j = 0; j < 50; j++) {       
                // stable pool
                if(agentsCurrentPools[j] == 0) {
                    agentsCurrentEarnings[j] += stableEarnings;
                }
                // high pool
                else if(agentsCurrentPools[j] == 1) {
                    if(rand.nextInt(100) > 25) {
                        highEarnings = 80 / (float)pools[1].size();
                    }
                    else {
                        highEarnings = 0;
                    }                    
                    agentsCurrentEarnings[j] += highEarnings;
                }
                // low pool
                else {
                    if(rand.nextInt(100) > 50) {
                        lowEarnings = 40 / (float)pools[2].size();
                    }
                    else {
                        lowEarnings = 0;
                    }                    
                    agentsCurrentEarnings[j] += lowEarnings;
                }
            }
            
            stableAllEarnings.add(stableEarnings);
            highAllEarnings.add(highEarnings);
            lowAllEarnings.add(lowEarnings);
            
            // summary
            System.out.println("Timestep " + (i + 1));
            System.out.println("Stable: " + pools[0]);
            System.out.println("Earnings: $" + String.format("%.02f", stableEarnings));
            System.out.println("High: " + pools[1]);
            System.out.println("Earnings: $" + String.format("%.02f", highEarnings));
            System.out.println("Low: " + pools[2]);
            System.out.println("Earnings: $" + String.format("%.02f", lowEarnings));            
            System.out.println("Agents' current earnings: ");
            for(int j = 0; j < 50; j++) {                
                System.out.print(j + ": $" + agentsCurrentEarnings[j] + ", ");
            }           
            System.out.println("\n ----------");
        }        
    }    
    
    private static float averageFloatArray(ArrayList<Float> array) {
        int sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum += array.get(i);
        }
        float average = sum / (float)array.size();
//        System.out.print(array.size() + " ");
//        System.out.print(average + " ");
        return average;
    }    
    
    private static float averageIntegerArray(ArrayList<Integer> array) {
        int sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum += array.get(i);
        }
        float average = sum / (float)array.size();
//        System.out.print(array.size() + " ");
//        System.out.print(average + " ");
        return average;
    }
}