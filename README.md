# example-percy-automate-appium-java

Example repo used by the Percy on Automate demonstrating Percy on Automate integration with Java using testNG.

## Java on Automate Appium Tutorial

The tutorial assumes you're already familiar with Java and Appium and focuses on using it with Percy. You'll still be able to follow along if you're not familiar with Java, Appium, but we won't spend time introducing Java, Appium concepts.

### Prerequisites

TestNG v6.8+, Java v8+, Selenium v2.5+, Appium v1.7+ (W3C/JSON Wire)

If you are using CLI for running tests, ensure that Maven is installed on your machine, its environment variables are set, and its bin is added to system path, $PATH

### Step 1

Clone the example application and install dependencies:

Minimum required `@percy/cli` and `@percy/webdriver-utils` version is `1.27.3` for this to work correctly. If you already have `@percy/cli` or `@percy/webdriver-utils` installed please update it to latest or minium required version.

```bash
$ git clone git@github.com:percy/example-percy-automate-appium-java.git
$ cd example-percy-automate-appium-java
$ npm install
$ npm install -g @percy/cli@latest
$ mvn compile
```

This tutorial specifically uses Browserstack Automate to run appium test.

For automate you will need credentials so refer to following instructions to get the same

1. You will need a BrowserStack `username` and `access key`. To obtain your access credentials, [sign up](https://www.browserstack.com/users/sign_up?utm_campaign=Search-Brand-India&utm_source=google&utm_medium=cpc&utm_content=609922405128&utm_term=browserstack) for a free trial or [purchase a plan](https://www.browserstack.com/pricing).

2. Please get your `username` and `access key` from [profile](https://www.browserstack.com/accounts/profile) page.

### Step 2

Sign in to Percy and create a new **Automate** project under **Web** category. You can name the project "test-project" if you'd like. After you've created the project, you'll be shown a token environment variable.

### Step 3

In the shell window you're working in, export the token and other environment variable:

**PERCY_TOKEN** is used by percy to identify the project and create the builds.

**Note:** In case of automate projects, token will start with ***auto_*** keyword.

**Unix**

``` shell
$ export PERCY_TOKEN="<your token here>"
```

**Windows**

``` shell
$ set PERCY_TOKEN="<your token here>"

# PowerShell
$ $Env:PERCY_TOKEN="<your token here>"
```

Set the necessary **BROWSERSTACK ENVIRONMENT** variables

**Unix**

``` shell
$ export BROWSERSTACK_USERNAME="<your browserstack user_name>"
$ export BROWSERSTACK_ACCESS_KEY="<your browserstack access_key>"
```

**Windows**

``` shell
$ set BROWSERSTACK_USERNAME="<your browserstack access_key>"
$ set BROWSERSTACK_ACCESS_KEY="<your browserstack access_key>"

# PowerShell
$ $Env:BROWSERSTACK_USERNAME="<your browserstack access_key>"
$ $Env:BROWSERSTACK_ACCESS_KEY="<your browserstack access_key>"
```

Alternatively you can also update `USER_NAME`, `ACCESS_KEY` with Browserstack User name, Access key in the script as well.

### Step 4

Considering all the above steps are done, we will run our tests, which will create automate session as well as percy build.

``` shell
$ npx percy exec --verbose  --  mvn test -P sample-percy-test
```

Your **First Percy on Automate** build is created.
On completion of the script, you would be able to see the your percy build. Since we ran for the first time, we would see these are new screenshots and hence there would be no comparisons.

### Step 5

Now in order to make comparisons happen we need to make changes to the existing website so that a visual change can occur you can go to following file in `PercyTest.java`

```java 
webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"1\"]/div[4]"))); // Say change id to 3 
```
Or else just run `PercyAfterTest.java`, we have already made visual changes in this script. If you run the `PercyAfterTest.java` script in tests, this would create few visual changes and would get compared to the last build and we would be able to see few diffs.

``` shell
$ npx percy exec --verbose  --  mvn test -P sample-percy-after-test
```

On completion of this script, this build would get compared to the previous build and hence we can see the visual changes which percy detected.

### Finished! 😀

From here, you can try making your own changes to the website and functional tests, if you like. If you do, re-run
the tests and you'll see any visual changes reflected in Percy.
