package cxsychallenge;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * CxSy Challenge
 * @author Miggy Llamas, Tadhg McCarthy
 */

public class CxSyChallenge {
    
    // agents and their respective attributes
    private static int[] agents = new int[50];
    private static int[] agentsCurrentPools = new int[50];
    private static float[] agentsCurrentEarnings = new float[50];

    // pools and their respective attributes
    private static ArrayList pools[] = new ArrayList[3];
    private static float stableEarnings = 1;
    private static float highEarnings = 0;
    private static float lowEarnings = 0;
    private static int stableCount = 0;
    private static int highCount = 0;
    private static int lowCount = 0;    
    private static ArrayList<Float> stableAllEarnings = new ArrayList<Float>();
    private static ArrayList<Float> highAllEarnings = new ArrayList<Float>();
    private static ArrayList<Float> lowAllEarnings = new ArrayList<Float>();
    private static ArrayList<Integer> stableAllCounts = new ArrayList<Integer>();
    private static ArrayList<Integer> highAllCounts = new ArrayList<Integer>();
    private static ArrayList<Integer> lowAllCounts = new ArrayList<Integer>();

    // cost for moving pools
    private static float tau = 2;
    private static Random rand = new Random();

    public static void main(String[] args) {
        
        // setup
        pools[0] = new ArrayList<Integer>(); //stable
        pools[1] = new ArrayList<Integer>(); //high
        pools[2] = new ArrayList<Integer>(); //low
        
        for(int i = 0; i < 50; i++) {
            agentsCurrentEarnings[i] = 0;
        }        
        
        // run algorithm 5 times
        for(int i = 0; i < 5; i++) {
//            uncomment algorithm to be tested        
//            SelectLowestAverageAgents();
//            SelectLowestAverageAgentsOrRandom();
//            SelectHighestAverageEarnings();
//            SelectHighestAverageEarningsOrRandom();
            SelectRandomPools();            
        }
                
    }

//    algorithm methods    
    // A. agents look at average agents in each pool over all previous timesteps and select pool with lowest average
        private static void SelectLowestAverageAgents() {
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
                        int temp = agentsCurrentPools[j];                           
                        float lowestAgents = Math.min(Math.min(averageIntegerArray(stableAllCounts), averageIntegerArray(highAllCounts)), averageIntegerArray(lowAllCounts));                            
                        if(lowestAgents == averageIntegerArray(stableAllCounts)) {
                            agentsCurrentPools[j] = 0;
                        }
                        else if(lowestAgents == averageIntegerArray(highAllCounts)) {
                            agentsCurrentPools[j] = 1;
                        }
                        else {
                            agentsCurrentPools[j] = 2;
                        }

                        if(temp != agentsCurrentPools[j]) {                                
                            agentsCurrentEarnings[j] = agentsCurrentEarnings[j] - tau;
                        }
                    }
                }
                pools[agentsCurrentPools[j]].add(j);
            }
            
            stableAllCounts.add(pools[0].size());
            highAllCounts.add(pools[1].size());
            lowAllCounts.add(pools[2].size());
            
            // agent earns based on selected pool
            int random = rand.nextInt(100);
            for(int j = 0; j < 50; j++) {       
                // stable pool
                if(agentsCurrentPools[j] == 0) {
                    agentsCurrentEarnings[j] += stableEarnings;
                }
                // high pool
                else if(agentsCurrentPools[j] == 1) {
                    if(random > 25) {
                        highEarnings = 80 / (float)pools[1].size();
                    }
                    else {
                        highEarnings = 0;
                    }                    
                    agentsCurrentEarnings[j] += highEarnings;
                }
                // low pool
                else {
                    if(random > 50) {
                        lowEarnings = 40 / (float)pools[2].size();
                    }
                    else {
                        lowEarnings = 0;
                    }                    
                    agentsCurrentEarnings[j] += lowEarnings;
                }
            }
            
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
                System.out.print(j + ": $" + String.format("%.02f", agentsCurrentEarnings[j]) + ", ");
            }           
            System.out.println("\n ----------");
        }
    }        
    
    // similar to preceding algorithm, but agents have a 25% chance of picking a random pool
    private static void SelectLowestAverageAgentsOrRandom() {
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
                            float lowestAgents = Math.min(Math.min(averageIntegerArray(stableAllCounts), averageIntegerArray(highAllCounts)), averageIntegerArray(lowAllCounts));                            
                            if(lowestAgents == averageIntegerArray(stableAllCounts)) {
                                agentsCurrentPools[j] = 0;
                            }
                            else if(lowestAgents == averageIntegerArray(highAllCounts)) {
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
            
            // agent earns based on selected pool
            int random = rand.nextInt(100);
            for(int j = 0; j < 50; j++) {       
                // stable pool
                if(agentsCurrentPools[j] == 0) {
                    agentsCurrentEarnings[j] += stableEarnings;
                }
                // high pool
                else if(agentsCurrentPools[j] == 1) {
                    if(random > 25) {
                        highEarnings = 80 / (float)pools[1].size();
                    }
                    else {
                        highEarnings = 0;
                    }                    
                    agentsCurrentEarnings[j] += highEarnings;
                }
                // low pool
                else {
                    if(random > 50) {
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
                System.out.print(j + ": $" + String.format("%.02f", agentsCurrentEarnings[j]) + ", ");
            }           
            System.out.println("\n ----------");
        }
    }        
        
    // agents look at total average earnings of each pool over all previous timesteps and select pool with highest average
    private static void SelectHighestAverageEarnings() {
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
                        int temp = agentsCurrentPools[j];                           
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
                }
                pools[agentsCurrentPools[j]].add(j);
            }
            
            // agent earns based on selected pool
            int random = rand.nextInt(100);
            for(int j = 0; j < 50; j++) {       
                // stable pool
                if(agentsCurrentPools[j] == 0) {
                    agentsCurrentEarnings[j] += stableEarnings;
                }
                // high pool
                else if(agentsCurrentPools[j] == 1) {
                    if(random > 25) {
                        highEarnings = 80 / (float)pools[1].size();
                    }
                    else {
                        highEarnings = 0;
                    }                    
                    agentsCurrentEarnings[j] += highEarnings;
                }
                // low pool
                else {
                    if(random > 50) {
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
                System.out.print(j + ": $" + String.format("%.02f", agentsCurrentEarnings[j]) + ", ");
            }           
            System.out.println("\n ----------");
        }
    }
    
    // similar to preceding algorithm, but agents have a 25% chance of picking a random pool
    private static void SelectHighestAverageEarningsOrRandom() {
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
            
            // agent earns based on selected pool
            int random = rand.nextInt(100);
            for(int j = 0; j < 50; j++) {       
                // stable pool
                if(agentsCurrentPools[j] == 0) {
                    agentsCurrentEarnings[j] += stableEarnings;
                }
                // high pool
                else if(agentsCurrentPools[j] == 1) {
                    if(random > 25) {
                        highEarnings = 80 / (float)pools[1].size();
                    }
                    else {
                        highEarnings = 0;
                    }                    
                    agentsCurrentEarnings[j] += highEarnings;
                }
                // low pool
                else {
                    if(random > 50) {
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
                System.out.print(j + ": $" + String.format("%.02f", agentsCurrentEarnings[j]) + ", ");
            }           
            System.out.println("\n ----------");
        }
    }
    
    // agents pick random pool   
    private static void SelectRandomPools(){
        for(int i = 0; i < 100; i++){
            pools[0].clear();
            pools[1].clear();
            pools[2].clear();
            
            for(int j = 0; j < 50; j++){
                //pick random pool if they have enough money
                if(i == 0){
                    agentsCurrentPools[j] = rand.nextInt(3);
                }else if(agentsCurrentEarnings[j] >= 2){
                    agentsCurrentPools[j] = rand.nextInt(3);
                }
                pools[agentsCurrentPools[j]].add(j);
            }
            
            // agent earns based on selected pool
            int random = rand.nextInt(100);
            for(int j = 0; j < 50; j++){
                // stable pool
                if(agentsCurrentPools[j] == 0){
                    agentsCurrentEarnings[j] += stableEarnings;
                }
                // high pool
                else if(agentsCurrentPools[j] == 1){
                    if(random > 25) {
                        highEarnings = 80 / (float)pools[1].size();
                    }
                    else {
                        highEarnings = 0;
                    }                    
                    agentsCurrentEarnings[j] += highEarnings;
                }
                // low pool
                else{
                    if(random > 50) {
                        lowEarnings = 40 / (float)pools[2].size();
                    }
                    else {
                        lowEarnings = 0;
                    }                   
                    agentsCurrentEarnings[j] += lowEarnings;
                }
            }
            
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
                System.out.print(j + ": $" + String.format("%.02f", agentsCurrentEarnings[j]) + ", ");
            }           
            System.out.println("\n ----------");
        }
    }
        
    // additional methods    
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