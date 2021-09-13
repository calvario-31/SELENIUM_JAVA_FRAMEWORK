# SELENIUM_POM_COMPLETE_FRAMEWORK

Url tested: 

    https://www.saucedemo.com/
    

Run on command line:

    mvn clean test -DsuiteName=${suite name} -Dbrowser=${browser name}

Example:

    mvn clean test -DsuiteName=failed -Dbrowser=EDGE

*PD: If no browser specified then it will run on CHROME*

Jenkins top maven script:

    clean test -DsuiteName=${SUITE} -Dos="${OS}" -DosVersion="${OS_VERSION}" -Dbrowser=${BROWSER} -DbrowserVersion=${BROWSER_VERSION}
