Feature('Demo Test 2');
let url = "";


Scenario('Website is opened', function*(I) {
    I.amOnPage(url);
    I.saveScreenshot("Opened URL.png");
    I.wait(10);
})
