package GroupBProject;

import java.util.Vector;

/*
In this class you desing you main to test All stocks and
ETFs
1- create a Tester with the appropriate path and file name and risk factor
2- Call run
3- Get the trades
4- Call helper function that will compute all statistics from the vector trades
 */
public class Main {
    public static void main(String[] args) {
        //If you are testing a risk based on stoploss and target
        int[] riskFactor = {1, 2, 3, 5, 10};

        String path = "C:\\Users\\eikay\\MAC 286 data\\Data";

        for(int i = 0; i < 5; i++) {

            String fileName = "stocks.txt";
            Tester stockTester=new Tester(path,fileName,riskFactor[i]);       // Create a Tester with with path file and riskFactor[i]
            stockTester.run();                                               //Call run method on the tester
            Vector<Trade> stockTrades = stockTester.getTrades();            // get the trades vector getTrades()
            Statistics stats = Helper.computeStats(stockTrades);            //call the helper method computerStates with the trade vector as input
            //display the results using the toString of the Statistics method
            System.out.println("--------stats for stocks: risk: " + riskFactor + "-------------");
            System.out.println(stats.toString());


            //Change the filename to ETFs.txt and do the same.
            //do all exactely the same.
            //create a new Tester object for ETFs.
            //run the tester
            fileName = "ETFs.txt";
            Tester etfTester=new Tester(path,fileName,riskFactor[i]);
            etfTester.run();
            Vector<Trade> etfTrades = etfTester.getTrades();
            stats = Helper.computeStats(etfTrades);
            System.out.println("--------stats for etfs: risk: " + riskFactor + "-------------");
            System.out.println(stats.toString());


            //create a Vector for all trades conbined stocks and etfs
            Vector<Trade> mTrades = stockTrades;
            mTrades.addAll(etfTrades);
            stats = Helper.computeStats(mTrades);
            System.out.println("--------stats combined stocks and etfs: risk: " + riskFactor + "-------------");
            System.out.println(stats.toString());
        }
    }
}
