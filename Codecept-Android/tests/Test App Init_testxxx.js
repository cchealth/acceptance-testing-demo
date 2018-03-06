Feature('Touch Testing flow');

Scenario('test registration', (I) => {
	I.wait(20);

	I.see('Welcome to My Care Team Hub');
	I.tap('Log In');
	I.wait(5);

	I.fillField('#username', 'john');
	I.fillField('#password', '123456');
	I.hideDeviceKeyboard();
	I.tap('#loginButton');
	I.wait(10);

	I.fillField('#username', 'Raj_Nalam@demo.cch.healthcare');
	I.fillField('#password', 'Welcome123');
	I.hideDeviceKeyboard();
	I.tap('#loginButton');
	I.wait(20);

//	(AppiumDriver)driver.runAppInBackground(10);
//	(AppiumDriver)driver.startActivity("appPackage", "com.comcast.crushlovely.external", null, null);
//	I.wait(100);
});


