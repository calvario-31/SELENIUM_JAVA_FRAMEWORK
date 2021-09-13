# SELENIUM_POM_COMPLETE_FRAMEWORK

URL TESTED: https://www.saucedemo.com/
It is configured to attach it to a jenkins build

to run on command line:
mvn clean test -DsuiteName=${suite name} -Dbrowser=${browser name}

example:
mvn clean test -DsuiteName=failed -Dbrowser=EDGE

PD: If no browser specified then it will run on CHROME

Jenkins top maven script:
clean test -DsuiteName=${SUITE} -Dos="${OS}" -DosVersion="${OS_VERSION}" -Dbrowser=${BROWSER} -DbrowserVersion=${BROWSER_VERSION}
