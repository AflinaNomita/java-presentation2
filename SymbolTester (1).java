package GroupBProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class SymbolTester {
    private int riskFactor; //TODO: Change this to suit your need
    private String mSymbol;
    private String dataPath; //= "C:\Users\oaith\Courses\MAC286\Fall2023\Data\";

    private Vector<Bar> mData;
    private Vector<Trade> mTrades;
    private boolean loaded = false;

    //Tests one symbol for specific risk factor.
    public SymbolTester(String s, String p, int risk) {
        riskFactor = risk;
        mSymbol = s;
        dataPath = p;
        mData = new Vector<Bar>(3000, 100);
        mTrades = new Vector<Trade>(200, 100);
        loaded = false;
    }

    public Vector<Trade> getTrades() {
        return mTrades;
    }
    public void loadData() {
        //create file name
        String fileName = dataPath + mSymbol + "_Daily.csv";
        //"C:\Users\oaith\Courses\MAC286\Fall2023\Data\AAPL_Daily.csv"
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while((line = br.readLine()) != null) {
                //create a bar using the constructor that accepts the data as a String
                Bar b = new Bar(line);
                //add the bar to the Vector
                mData.add(b);
            }
            loaded = true;
            br.close();
            fr.close();
        }catch(IOException e) {
            System.out.println("Something is wrong: " + e.getMessage());
            loaded = false;
            return;
        }
    }

    private boolean xDaysLow(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getLow() < mData.elementAt(ind).getLow())
                return false;
        }
        return true;
    }
    private boolean xDaysHigh(int ind, int days) {
        for (int i = ind-1; i > ind-days; i--) {
            if(mData.elementAt(i).getHigh() > mData.elementAt(ind).getHigh())
                return false;
        }
        return true;
    }

    public boolean test() {
        if(!loaded) {
            loadData();
            if (!loaded) {
                System.out.println("cannot load data");
                return false;
            }
        }
        //TODO: Code your pattern here
            //My Pattern
        /*
        Long pattern
1.	Day i-1 or day i-2 make a 20 day high
(The high of i-1 or i-2 is higher than the previous 20 highs)
2.	Day i open lower than i-1high and its low is lower than low of i-1 and its high is higher than high of i-1
3.	Day i closes near the high (H-C)/(H-L)< 0.1
4.	Buy at close of i
5.	Test selling at close of i+1, close i+2, close i_3, close i+5, close i+10
         */
        for(int i=22; i<mData.size()-1-riskFactor;i++){
            if((xDaysHigh(i-1,20)||xDaysHigh(i-2,20))
                    && mData.elementAt(i).getOpen()<mData.elementAt(i-1).getClose()
                    && mData.elementAt(i).getLow()<mData.elementAt(i-1).getLow()
                    && mData.elementAt(i).getHigh()>mData.elementAt(i-1).getHigh()
                    && (mData.elementAt(i).getHigh()-mData.elementAt(i).getClose())/(mData.elementAt(i).range())<0.1)
            {
                float entryprice=mData.elementAt(i).getClose();
                Trade T=new Trade();
                T.open(mSymbol,mData.elementAt(i).getDate(),entryprice,0,0,Direction.LONG);
                T.close(mData.elementAt(i+riskFactor).getDate(),mData.elementAt(i+riskFactor).getClose(),riskFactor);
                
                mTrades.add(T);

            }
            else if ((xDaysLow(i-1,20)||xDaysLow(i-2,20))
                        && mData.elementAt(i).getOpen()>mData.elementAt(i-1).getClose()
                        &&mData.elementAt(i).getLow()<mData.elementAt(i-1).getLow()
                        &&mData.elementAt(i).getHigh()>mData.elementAt(i-1).getHigh()
                        &&(mData.elementAt(i).getClose()-mData.elementAt(i).getLow())/(mData.elementAt(i).getLow())<0.1)
            {
                float entryprice=mData.elementAt(i+riskFactor).getClose();
                Trade T=new Trade();
                //public void close(exit date, exit price,holdingP)
                T.open(mSymbol,mData.elementAt(i+riskFactor).getDate(),mData.elementAt(i+riskFactor).getClose(),0,0,Direction.SHORT);
                T.close(mData.elementAt(i).getDate(),mData.elementAt(i).getClose(),riskFactor);
                //public void open(symbol, entrydate, entryprice, float loss, float target, Direction d) {


                mTrades.add(T);

            }


            }
        return true;

        }


    }

