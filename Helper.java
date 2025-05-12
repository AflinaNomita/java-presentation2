package GroupBProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Helper {
    //Write a static method that accepts a path and a file name
    //it opend the file (file symbols) and reads it and creates a
    //Vector of strings of all files.
    public static Vector<String> loadSymbols(String path, String file){
        //TODO: Create a Vector symbols
        Vector<String> Vsymbols = new Vector<>();
        String fullPath=path+"/"+file;
        //Open the input file and read line by line, trim the string

        //and add it to the Vector symbols
        //return the vector
        try (BufferedReader br = new BufferedReader(new FileReader(fullPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    Vsymbols.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + fullPath);
            e.printStackTrace();
        }

        return Vsymbols;
    }

    //Write a Static method that accepts a Vector of Trades and goes
    //through it to compute all statistics, return the statistics as
    //an object.
    public static Statistics computeStats(Vector<Trade> trades){
        //Create a Statistics object
        Statistics stat = new Statistics();

        double percentL=0,percentS =0;
        int numL=0,numS =0;
        int winL = 0, winS = 0;
        int lholding = 0, sholding = 0;

        //go through Vector trades one by one and compute all the Stats
        for(int i = 0; i < trades.size(); i++) {
            //compute all the stats


        }
        double averageProfit;
        double averageHoldingPeriod;
        double averageProfitPerDay;
        double winningPercent;
        double averageProfitLong;
        double averageHoldingPeriodLong;
        double averageProfitPerDayLong;
        double winningPercentLong;
        //Do the same fgor shorts
        double averageProfitShort;
        double averageHoldingPeriodShort;
        double averageProfitPerDayShort;
        double winningPercentShort;
        //return your object.
        return stat;
    }
}
