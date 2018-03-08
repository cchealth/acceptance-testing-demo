
'use strict';

let I;

module.exports = {

  _init() {
    I = actor();
  },

  fields:{
    surveyOption: '//*[@id="content"]/div/div[2]/ul/li[1]/button'
  },

  sendOption(surveyText,buttonName){
    I.see(surveyText);
    I.checkOption(this.fields.surveyOption);
    I.click('Submit');
    I.wait(1);
    I.click(buttonName);
    I.wait(5);
}
 
  // insert your locators and methods here
}
