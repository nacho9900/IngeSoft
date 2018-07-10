package tests;

import Controller.Controller;
import Controller.InputController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ControllerTests {

    private Controller controller;
    private InputController inputController;

    @Before
    public void init() {
        this.controller = Controller.getInstance();
        this.inputController = controller.getInputController();
    }

    @Test
    public void checkUserNameGivenEmptyNameTest() {
        Assert.assertFalse(inputController.checkUserName(""));
    }

    @Test
    public void checkUserNameGivenValidNameTest() {
        Assert.assertTrue(inputController.checkUserName("testUserName"));
    }

    @Test
    public void checkUserPasswordGivenShortPasswordTest() {
        Assert.assertFalse(inputController.checkUserPassword("a"));
    }

    @Test
    public void checkUserPasswordGivenLongPasswordTest() {
        Assert.assertFalse(inputController.checkUserPassword("123456789"));
    }

    @Test
    public void checkUserPasswordGivenValidPasswordTest() {
        Assert.assertTrue(inputController.checkUserPassword("12345678"));
    }

    @Test
    public void checkUserDocumentGivenShortDocumentTest() {
        Assert.assertFalse(inputController.checkUserDocument("1"));
    }

    @Test
    public void checkUserDocumentGivenLongDocumentTest() {
        Assert.assertFalse(inputController.checkUserDocument("123456789"));
    }

    @Test
    public void checkUserDocumentGivenValidDocumentTest() {
        Assert.assertTrue(inputController.checkUserDocument("12345678"));
    }

    @Test
    public void checkUserStreetGivenEmptyStringTest() {
        Assert.assertFalse(inputController.checkUserStreet(""));
    }

    @Test
    public void checkUserStreetGivenValidStringTest() {
        Assert.assertTrue(inputController.checkUserStreet("Cochabamba"));
    }

    @Test
    public void checkUserUsernameGivenEmptyStringTest() {
        Assert.assertFalse(inputController.checkUserUserName(""));
    }

    @Test
    public void checkUserUsernameGivenValidUsernameTest() {
        Assert.assertTrue(inputController.checkUserUserName("test@.2014"));
    }

    @Test
    public void checkUserEmailGivenNonDomainEmailTest() {
        Assert.assertFalse(inputController.checkUserEmail("emailTest@"));
    }

    @Test
    public void checkUserEmailGivenNonAddressEmailTest() {
        Assert.assertFalse(inputController.checkUserEmail("@Test.com"));
    }

    @Test
    public void checkUserEmailGivenOnlyAtEmailTest() {
        Assert.assertFalse(inputController.checkUserEmail("@"));
    }

    @Test
    public void checkUserEmailGivenEmptyStringTest() {
        Assert.assertFalse(inputController.checkUserEmail(""));
    }

    @Test
    public void checkUserEmailGivenValidFormatEmailTest() {
        Assert.assertTrue(inputController.checkUserEmail("test@test.com"));
    }

    @Test
    public void checkUserPhoneNumberGivenEmptyStringTest() {
        Assert.assertFalse(inputController.checkUserPhoneNumber(""));
    }

    @Test
    public void checkUserPhoneNumberGivenShortNumberTest() {
        Assert.assertFalse(inputController.checkUserPhoneNumber("12"));
    }

    @Test
    public void checkUserPhoneNumberGivenLongNumberTest() {
        Assert.assertFalse(inputController.checkUserPhoneNumber("123456789123456789"));
    }

    @Test
    public void checkUserPhoneNumberGivenInvalidCharactersTest() {
        Assert.assertFalse(inputController.checkUserPhoneNumber("4545...."));
        Assert.assertFalse(inputController.checkUserPhoneNumber("..65656565"));
        Assert.assertFalse(inputController.checkUserPhoneNumber(".888888."));
    }

    @Test
    public void checkUserPhoneNumberGivenValidFormatNumberTest() {
        Assert.assertTrue(inputController.checkUserPhoneNumber("45454545"));
        Assert.assertTrue(inputController.checkUserPhoneNumber("1545454545"));
        Assert.assertTrue(inputController.checkUserPhoneNumber("1145454545"));
    }

    @Test
    public void checkUserStateGivenEmptyStringTest() {
        Assert.assertFalse(inputController.checkUserState(""));
    }

    @Test
    public void checkUserStateGivenValidFormatStateTest() {
        Assert.assertTrue(inputController.checkUserState("Buenos Aires"));
    }

    @Test
    public void checkUserPostalCodeGivenEmptyStringTest() {
        Assert.assertFalse(inputController.checkUserPostalCode(""));
    }

    @Test
    public void checkUserPostalCodeGivenValidFormatTest() {
        Assert.assertTrue(inputController.checkUserPostalCode("1111"));
    }

    @Test
    public void checkProductTitleGivenEmptyStringTest() {
        Assert.assertFalse(inputController.checkProductTitle(""));
    }

    @Test
    public void checkProductTitleGivenValidFormatTest() {
        Assert.assertTrue(inputController.checkProductTitle("Television"));
    }

    @Test
    public void checkProductDescriptionGivenEmptyStringTest() {
        Assert.assertFalse(inputController.checkProductDescription(""));
    }

    @Test
    public void checkProductDescriptionGivenValidFormatTest() {
        Assert.assertTrue(inputController.checkProductDescription("this is a valid description"));
    }

    @Test
    public void checkProductAmountGivenEmptyStringTest() {
        Assert.assertFalse(inputController.checkProductAmount(""));
    }

    @Test
    public void checkProductAmountGivenNegativeAmountTest() {
        Assert.assertFalse(inputController.checkProductAmount("-255"));
    }

    @Test
    public void checkProductAmountGivenValidFormatTest() {
        Assert.assertTrue(inputController.checkProductAmount("1"));
    }

    @Test
    public void checkProductPriceGivenEmptyStringTest(){
        Assert.assertFalse(inputController.checkProductPrice(""));
    }

    @Test
    public void checkProductPriceGivenNegativePriceTest(){
        Assert.assertFalse(inputController.checkProductPrice("-10"));
    }

    @Test
    public void checkProductPriceGivenValidFormatTest(){
        Assert.assertTrue(inputController.checkProductPrice("10"));
    }
}
