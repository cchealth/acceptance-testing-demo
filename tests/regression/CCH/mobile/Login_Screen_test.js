Feature('Login');

Scenario('Failed login', (I) => {
	I.waitForElement("//android.view.View[@content-desc='Log In']", 40);
	I.tap("//android.view.View[@content-desc='Log In']");
	I.wait(5);

	I.fillField('#username', 'john');
	I.fillField('#password', '123456');
	I.hideDeviceKeyboard();
	
	I.tap('#loginButton');
	I.wait(3);

	I.waitForElement("//android.view.View[contains(@content-desc, \"We didn't recognize the username or password you entered.\")]", 10);
	I.wait(5);
});

Scenario('Succesful Login', (I) => {
	I.waitForElement("//android.view.View[@content-desc='Log In']", 40);
	I.tap("//android.view.View[@content-desc='Log In']");
	I.wait(5);

	I.fillField('#username', 'Raj_Nalam@demo.cch.healthcare');
	I.wait(3);
	I.fillField('#password', 'Welcome123');
	I.hideDeviceKeyboard();

	I.tap('#loginButton');
	I.wait(3);

	I.waitForElement("//android.view.View[contains(@content-desc, 'Logging you in')]", 10);
	I.wait(3);
});
