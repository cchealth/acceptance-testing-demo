Feature('Welcome Screen');

Scenario('Verify Welcome Text', (I) => {
	I.waitForElement("//android.view.View[contains(@content-desc, 'Welcome to My Care Team Hub:')]", 40);
});

Scenario('Verify Our Purpose Title', (I) => {
	I.waitForElement("//android.view.View[@content-desc='Improving Lives Together']", 40);
});

Scenario('Verify Our Purpose Text', (I) => {
	I.waitForElement("//android.view.View[contains(@content-desc, 'We want to make it easy for members')]", 40);
});

Scenario('Verify Terms and Conditions link exists', (I) => {
	I.waitForElement("//android.view.View[@content-desc='Terms & Conditions']", 40);
	I.tap("//android.view.View[@content-desc='Terms & Conditions']");
	I.waitForElement("//android.view.View[@content-desc='http://www.amerihealthcaritas.com/privacy-policy.aspx']", 10);
	I.tap("//android.view.View[@content-desc='http://www.amerihealthcaritas.com/privacy-policy.aspx']");
	I.wait(15);
});

Scenario('Verify Contact Us link exists', (I) => {
	I.waitForElement("//android.view.View[@content-desc='Contact Us']", 40);
	I.tap("//android.view.View[@content-desc='Contact Us']");
	I.waitForElement("//android.view.View[@content-desc='emailaddress@partnerhospital.com']", 10);
});

Scenario('Verify FAQs link exists', (I) => {
	let locator = "#app";
	I.waitForElement("//android.view.View[@content-desc='FAQ']", 40);
	I.swipeUp(locator); // simple swipe
	I.swipeUp(locator, 500); // set speed
	I.swipeUp(locator, 1200, 1000); // set offset and speed
	I.wait(2);
	I.tap("//android.view.View[@content-desc='FAQ']");
	/** this could go in another feature file **/
	I.waitForElement("//android.view.View[@content-desc='SupportU Questions and Answers']", 10);
	I.waitForElement("//android.view.View[@content-desc='What is SupportU?']", 3);
	I.waitForElement("//android.view.View[@content-desc='How does SupportU work?']", 3);
	I.waitForElement("//android.view.View[@content-desc='What do I do when I get to SupportU?']", 3);
});


