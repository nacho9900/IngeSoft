package Controller;

import Model.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class InputController {
    private Controller controller;
    public InputController(Controller controller){
        this.controller=controller;
    }
    public Controller getController(){
        return controller;
    }
    public int checkAll(InputPack inputPack){
        Set<Map.Entry<Integer, MyEntry>> formEntries= inputPack.getFormEntries().entrySet();
        String password=null;
        for(Map.Entry<Integer,MyEntry> fe: formEntries){
            int code = fe.getValue().getCode();
            switch (code){
                case 1:
                    if(!checkUserName((String)fe.getValue().getItem())) return 1;
                    break;
                case 2:
                    if(!checkUserUserName((String)fe.getValue().getItem())) return 2;
                    break;
                case 3:
                    if(!checkUserPassword((String)fe.getValue().getItem())) return 3;
                    if(password==null) {
                        password = (String)fe.getValue().getItem();
                    }else{
                        if(!password.equals((String)fe.getValue().getItem())) return 3;
                    }
                    break;
                case 4:
                    if(fe.getValue().getItem()==null || !checkUserCountry(((Country)fe.getValue().getItem()).getName())) return 4;
                    break;
                case 5:
                    if(fe.getValue().getItem()==null||!checkUserNeighbourhood(((City)(fe.getValue().getItem())).getName())) return 5;
                    break;
                case 6:
                    if(!checkUserStreet((String)fe.getValue().getItem())) return 6;
                    break;
                case 7:
                    if(!checkUserStreetNumber((String)fe.getValue().getItem())) return 7;
                    break;
                case 8:
                    if(!checkUserStreet((String)fe.getValue().getItem())) return 8;
                    break;
                case 9:
                    if(!checkUserPhoneNumber((String)fe.getValue().getItem())) return 9;
                    break;
                case 10:
                    if(!checkUserSurname((String)fe.getValue().getItem())) return 10;
                    break;
                case 11:
                    if(!checkUserEmail((String)fe.getValue().getItem())) return 11;
                    break;
                case 12:
                    if(fe.getValue().getItem()==null||!checkUserState(((Province)(fe.getValue().getItem())).getName())) return 12;
                    break;
                case 13:
                    if(fe .getValue().getItem()==null || !checkUserDocumentType((DocType)fe.getValue().getItem())) return 13;
                    break;
                case 14:
                    if(!checkUserDocument((String)(fe.getValue().getItem()))) return 14;
                    break;
                case 15:
                    if(!checkUserPostalCode((String)fe.getValue().getItem())) return 15;
                    break;
                case 16:
                    if(!checkUserPassword((String)fe.getValue().getItem())) return 3;
                    if(password==null) {
                        password = (String)fe.getValue().getItem();
                    }else{
                        if(!password.equals((String)fe.getValue().getItem())) return 3;
                    }
                    break;

                case 21:
                    if(!checkProductTitle((String)fe.getValue().getItem())) return 21;
                    break;
                case 22:
                    if(!checkProductAmount((String)fe.getValue().getItem())) return 22;
                    break;
                case 23:
                    if(!checkProductPrice((String)fe.getValue().getItem())) return 23;
                    break;
                case 24:
                    if(!checkProductDescription((String)fe.getValue().getItem())) return 24;
                    break;
                case 25:
                    if(fe.getValue().getItem()==null || !checkProductCategory(((Category)fe.getValue().getItem()).getName())) return 25;
                    break;

                case 31:
                    if(!checkCardName((String)fe.getValue().getItem())) return 31;
                    break;
                case 32:
                    if(!checkCardSurname((String)fe.getValue().getItem())) return 32;
                    break;
                case 33:
                    if(!checkCardExpMonth((String)fe.getValue().getItem())) return 33;
                    break;
                case 34:
                    if(!checkCardExpYear((String)fe.getValue().getItem())) return 34;
                    break;
                case 35:
                    if(!checkCardSecurityNumber((String)fe.getValue().getItem())) return 35;
                    break;
                case 36:
                    if(!checkCardNumber((String)fe.getValue().getItem())) return 36;
                    break;
                default:
                    throw new NotImplementedException();
            }
        }
        return 0;
    }

    private boolean checkProductCategory(String name) {
        return !name.isEmpty();
    }

    private boolean checkUserStreetNumber(String string) {
        for(Character c : string.toCharArray()){
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    private boolean checkCardNumber(String string) {
        if(string.length()!=16){
            return false;
        }
        for(Character c : string.toCharArray()){
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    private boolean checkCardExpYear(String string) {
        if(string.length()!=4){
            return false;
        }
        for(Character c : string.toCharArray()){
            if (!Character.isDigit(c)){
                return false;
            }
        }
        int year = Integer.parseInt(string);
        return year<2050 && year>2017;

    }

    private boolean checkCardExpMonth(String string) {
        if(string.length()!=2){
            return false;
        }
        for(Character c : string.toCharArray()){
            if (!Character.isDigit(c)){
                return false;
            }
        }
        int month = Integer.parseInt(string);
        return month<13 && month>0;
    }

    private boolean checkCardSurname(String string) {
        return !string.isEmpty();
    }
    private boolean checkCardName(String string) {
        return !string.isEmpty();
    }

    private boolean checkCardSecurityNumber(String string) {
        if(string.length()!=3){
            return false;
        }
        for (char c : string.toCharArray())
        {
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public User addUser(UserPack userPack){
        String name= (String) userPack.getFormEntries().get(1).getItem();
        String userName= (String) userPack.getFormEntries().get(2).getItem();
        String password = (String) userPack.getFormEntries().get(3).getItem();
        Country country = (Country) userPack.getFormEntries().get(4).getItem();// Country country = new Country(userPack.getFormEntries().get(4);
        City city = (City) userPack.getFormEntries().get(5).getItem();
        String street = (String) userPack.getFormEntries().get(6).getItem();
        int streetNumber = new Integer(userPack.getFormEntries().get(7).getItem().toString());
        String apartment = (String) userPack.getFormEntries().get(8).getItem();
        String phoneNumber = (String) userPack.getFormEntries().get(9).getItem();
        String surname = (String) userPack.getFormEntries().get(10).getItem();
        String email = (String) userPack.getFormEntries().get(11).getItem();
        Province province = (Province) userPack.getFormEntries().get(12).getItem();
        DocType doctype =  DocType.list().get(0);
        String docNumber = (String) userPack.getFormEntries().get(14).getItem();
        String postalcode = (String) userPack.getFormEntries().get(15).getItem();
        boolean enabled = false;
        boolean confirmed = true;
        boolean admin = false;
        int validationCode = 1000 + (int)(Math.random() * ((9999 - 1000) + 1));
        Cart cart = null;

        UserBuilder builder = new UserBuilder();

        User newUser = builder.addDoc(docNumber).addUsername(userName).addPassword(password).addName(name).addSurname(surname)
                .addEmail(email).addEnabled(enabled).addConfirmed(confirmed).addAdmin(admin).addDocType(doctype).addCart(cart)
                .addCountry(country).addCity(city).addProvince(province).addStreet(street).addStreetNumber(streetNumber)
                .addApartment(apartment).addPostCode(postalcode).addPhone(phoneNumber).addValidation(validationCode).build();
        if(newUser != null) {
            this.controller.getModel().setUser(newUser);
            sendConfirmationEmail(name, userName, password, email, validationCode);
        }
        return newUser;
    }

    private void sendConfirmationEmail(String name, String username, String password, String email, int validationCode) {
        String from = "kaufenteam";
        String fromPassword = "kaufen1234";
        String[] to = {email};
        String subject = "Welcome to Kaufen Desktop App";
        String body = "Hi "+name+",\nWelcome to Kaufen Desktop App! Your account has been successfully created!!!!\n\n\t\tYour username: "+username+"\n\t\tYour password: "+password+"\n\n\nPlease activate your account with the following validation code:\n\t\tValidation Code: "+Integer.toString(validationCode)+"\n\n\n Many Thanks, \nThe Kaufen Desktop App Team";
        sendFromGMail(from, fromPassword, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    private boolean checkUserSurname(String surname) {
        return !surname.isEmpty();
    }

    private boolean checkUserCountry(String country) {
        return !country.isEmpty();
    }

    private boolean isPositiveInteger(String str){
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    public boolean checkUserName(String name){
        return !name.isEmpty();
    }
    public boolean checkUserUserName(String username){
        return !username.isEmpty();
    }
    public boolean checkUserDocument(String dni){
        if(dni.length() != 8){
            return false;
        }
        System.out.println(true);
        return isPositiveInteger(dni);
    }
    public boolean checkUserEmail(String email){
        String[] strs= email.split("@");
        return (strs.length==2 && !strs[0].isEmpty() && !strs[1].isEmpty());
    }
    public boolean checkUserPassword(String password){
        return password.length() >= 4 && password.length() <= 8;

    }
    public boolean checkUserNeighbourhood(String neighbourhood){
        return !neighbourhood.isEmpty();
    }

    public boolean checkUserStreet(String street){
        return !street.isEmpty();
    }

    public boolean checkUserPhoneNumber(String phoneNumber){
        if(phoneNumber.length()!=8 && phoneNumber.length()!=10){
            return false;
        }
        for (char c : phoneNumber.toCharArray())
        {
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;

    }
    public boolean checkUserState(String state){
        return !state.isEmpty();
    }

    public boolean checkUserPostalCode(String postalCode){
        return !postalCode.isEmpty();

    }
    public boolean checkUserDocumentType(DocType documentType){
        ArrayList<DocType> list= DocType.list();
        for(DocType docType : list) {
            if(docType.getName().equals(documentType.getName())){
                return true;
            }
        }
        return false;
    }

    public boolean checkUserBirthDate(String birthDate){
        String[] strs = birthDate.split("/");
        if(strs.length!=3 || isPositiveInteger(strs[0]) || isPositiveInteger(strs[1]) || isPositiveInteger(strs[2])) return false;
        int int1=Integer.parseInt(strs[0]);
        int int2=Integer.parseInt(strs[1]);
        int int3=Integer.parseInt(strs[2]);
        return (int1<32 && int1>0 && int2<13 && int2>0 && int3>1900 && int3<2018);
    }

    public boolean checkProductTitle(String title){
        return !title.isEmpty();
    }

    public boolean checkProductPrice(String price){
        double a;
        try
        {
            a=Double.parseDouble(price);
        }
        catch(NumberFormatException e){
            return false;
        }
        return a>=0;
    }
    public boolean checkProductAmount(String amount){
        if(amount.isEmpty() || !isPositiveInteger(amount)){
            return false;
        }
        int i = Integer.parseInt(amount);
        return i>0;
    }
    public boolean checkProductDescription(String description){
        return !description.isEmpty() && !description.contains("'");
    }

    public void addCard(CardPack cardStruct) {
        User user = Controller.getInstance().getCurrentUser();
        String name = (String)cardStruct.getFormEntries().get(31).getItem();
        String surname = (String)cardStruct.getFormEntries().get(32).getItem();
        String number = (String)cardStruct.getFormEntries().get(36).getItem();
        int month = Integer.parseInt((String)cardStruct.getFormEntries().get(33).getItem());
        int year = Integer.parseInt((String)cardStruct.getFormEntries().get(34).getItem());
        int code = Integer.parseInt((String)cardStruct.getFormEntries().get(35).getItem());
        if(user == null){
            System.out.println("user is null");
        }
        if(name == null){
            System.out.println("name is null");
        }
        if(surname == null){
            System.out.println("surname is null");
        }
        if(number == null){
            System.out.println("number is null");
        }

        Card.create(user, name, surname, number, month, year, code);
    }

    public User modifyUser(UserPack userPack, User currentUser) {

        currentUser.setName((String) userPack.getFormEntries().get(1).getItem());
        currentUser.setUsername((String) userPack.getFormEntries().get(2).getItem());
        currentUser.setPassword((String) userPack.getFormEntries().get(3).getItem());
        currentUser.setCountry((Country) userPack.getFormEntries().get(4).getItem());
        currentUser.setCity((City) userPack.getFormEntries().get(5).getItem());
        currentUser.setStreet((String) userPack.getFormEntries().get(6).getItem());
        currentUser.setStreetNumber(new Integer((userPack.getFormEntries().get(7).getItem()).toString()));
        currentUser.setApartment((String) userPack.getFormEntries().get(8).getItem());
        currentUser.setPhone((String) userPack.getFormEntries().get(9).getItem());
        currentUser.setSurname((String) userPack.getFormEntries().get(10).getItem());
        currentUser.setEmail((String) userPack.getFormEntries().get(11).getItem());
        currentUser.setProvince((Province) userPack.getFormEntries().get(12).getItem());
        currentUser.setDocType( (DocType) userPack.formEntries.get(13).getItem());
        currentUser.setdoc((String) userPack.getFormEntries().get(14).getItem());
        currentUser.setPostCode((String) userPack.getFormEntries().get(15).getItem());
        currentUser.save();
        return currentUser;
    }
}

