
'use strict';

let I;

module.exports = {

  _init() {
    I = actor();
  },

  fields:{
    login_button: "//*[@id='content']/div/div[2]/div[2]/ul[1]/li/a",
    signin_button: '/html/body/div/div[2]/div[2]/form/div[6]/a',
    image: '//*[@id="carousel-747fa04a-4fac-4916-9852-e08329fce265"]/div/div[2]/ul/li[3]/button/div[2]/img'
  },

  loginForm(username,password){
    I.click(this.fields.login_button);
    I.fillField('pf.username',username);
    I.fillField('pf.pass',password);
    I.click(this.fields.signin_button);
  },

  clickSurvey(){
    I.wait(25);
    I.click(this.fields.image);
  }

  // insert your locators and methods here
}
