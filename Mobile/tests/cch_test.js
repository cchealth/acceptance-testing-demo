Feature('Demo Test');
let url = "";


Scenario('App is opened', function*(I) {
    I.wait(10);
    I.click('//*[@content-desc = "Log In"]');
    I.wait(4);
    I.fillField('//*[@resource-id = "username"]', 'user');
    I.fillField('//*[@resource-id = "password"]', 'password');
/*    I.saveScreenshot("Opened URL.png");
    I.wait(10);*/
})
