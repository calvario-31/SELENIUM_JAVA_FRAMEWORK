package utilities.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import utilities.Log;

import static utilities.DriverManager.assignDriverParameters;
import static utilities.DriverManager.writeEnvVariables;

public class SuiteListeners implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        Log.info("Beginning: " + suite.getName());
        assignDriverParameters();
    }

    @Override
    public void onFinish(ISuite suite) {
        Log.info("Ending: " + suite.getName());
        writeEnvVariables();
    }
}
