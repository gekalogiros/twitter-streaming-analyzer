package com.gkalogiros.main;

import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args)
    {
        try
        {
            App app = new App();
            app.run();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        catch (java.text.ParseException e)
        {
            e.printStackTrace();
        }

    }


}
