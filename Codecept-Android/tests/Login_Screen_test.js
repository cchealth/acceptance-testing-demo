Feature('Login');

Scenario('Failed login', (I) => {
	I.wait(20);
	I.tap('Log In');
	I.wait(5);

	I.fillField('#username', 'john');
	I.fillField('#password', '123456');
	I.hideDeviceKeyboard();
	
	I.tap('#loginButton');
	I.wait(5);

	I.see("We didn't recognize the username or password you entered. Please try again.");
	I.wait(5);
});

Scenario('Succesful Login', (I) => {
	I.wait(20);
	I.tap('Log In');
	I.wait(5);

	I.fillField('#username', 'Raj_Nalam@demo.cch.healthcare');
	I.fillField('#password', 'Welcome123');
	I.hideDeviceKeyboard();

	I.tap('#loginButton');
	I.wait(5);

	I.dontSee("We didn't recognize the username or password you entered. Please try again.");
	//I.see("Welcome");
	//I.wait(5);
});