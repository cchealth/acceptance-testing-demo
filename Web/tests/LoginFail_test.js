
Feature('LoginFail');

Scenario('Login Fail', (I,loginPage) => {
  I.amOnPage('/');
  I.wait(10);
  I.seeElement('.action-box');
  I.wait(10);
  loginPage.loginForm('WrongUsername','WrongPass');
  I.wait(5);
  I.see("We didn't recognize the username or password you entered. Please try again.");
});
