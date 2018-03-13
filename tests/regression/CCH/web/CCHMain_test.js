Feature('Main Page');

Scenario('Login Fail', (I,loginPage) => {
  I.amOnPage('/');
  loginPage.loginForm('WrongUsername','WrongPass');
  I.wait(5);
  I.see("We didn't recognize the username or password you entered. Please try again.");
  I.saveScreenshot('Login fail validation');
});

Scenario('Strokes Survey', (I, loginPage,surveyPage) => {
  I.amOnPage('/');

  loginPage.loginForm('Raj_Nalam@demo.cch.healthcare','Welcome123');

  loginPage.clickSurvey();

  I.waitForElement('.quiz-info__description',10);
  I.wait(2);
  I.see('Test your heart health knowledge.');
  I.click('Retake Survey');
  I.wait(5);

  surveyPage.sendOption('Is it possible to have a stroke without knowing it?','Next');

  surveyPage.sendOption('There are three types of strokes.','Next');

  surveyPage.sendOption('If you see someone having a stroke, you should give them CPR','Complete');

  I.see('Thank you for completing the What do you know about strokes? survey.');
  I.saveScreenshot('Survey completion validation');
});
