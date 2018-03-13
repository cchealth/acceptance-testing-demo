
'use strict';

let I;

module.exports = {

  _init() {
    I = actor();
  },

  fields:{
    login_button: "//*[@id='content']/div/div[2]/div[2]/ul[1]/li/a",
    signin_button: ".ping-button",
    image: '//*[@id="carousel-747fa04a-4fac-4916-9852-e08329fce265"]/div/div[2]/ul/li[3]/button/div[2]/img'
  },

  loginForm(username,password){
    I.waitForElement(this.fields.login_button,30);
    I.wait(2);
    I.click(this.fields.login_button);
    I.waitForElement('#username',30);
    I.wait(2);
    I.fillField('pf.username',username);
    I.fillField('pf.pass',password);
    I.click(this.fields.signin_button);
  },

  clickSurvey(){
    I.waitForElement(this.fields.image,60);
    I.wait(2);
    I.click(this.fields.image);
  }

}
