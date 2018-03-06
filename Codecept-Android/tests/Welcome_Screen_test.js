Feature('Welcome Screen');

Scenario('Verify Welcome Text', (I) => {
	I.wait(20);

	I.see('Welcome to My Care Team Hub');
	I.wait(5);
});

Scenario('Verify Our Purpose Title', (I) => {
	I.wait(20);

	I.see('Improving Lives Together');
	I.wait(5);
});

Scenario('Verify Our Purpose Text', (I) => {
	I.wait(20);

	I.see('We want to make it easy for members');
	I.wait(5);
});

Scenario('Verify Terms and Conditions link exists', (I) => {
	I.wait(20);

	I.see('Terms & Conditions');
	I.wait(5);
});

Scenario('Verify Contact Us link exists', (I) => {
	I.wait(20);

	I.see('Contact Us');
	I.wait(5);
});

Scenario('Verify FAQs link exists', (I) => {
	I.wait(20);

	I.see('FAQ');
	I.wait(5);
});


