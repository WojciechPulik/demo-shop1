package pl.wpulik.utils;

public class MailMessagesContent {
	
	private final static String orderConfirmationMessageSubject = "Potwierdzenie zamówienia nr ";
	
	public MailMessagesContent() {}
	
	public static String getOrderConfirmationMessage(String firstName) {
		String orderConfirmationMessage = 
				"<!DOCTYPE html>\r\n" +
				"<html>\r\n" +
				"<head>\r\n" + 
				"<meta charset=\"UTF-8\"/>\r\n" +  
				"<title>Potwierdzeni zamówienia</title>\r\n" + 
				"\n<style>" +
				"\n.inner{" +
				"\nborder:1px solid grey; " +
				"\nbackground-color:#E0FFFF;" + 
				"\nborder-radius:3px;" +
				"\nwidth:80%;" +
				"\n}" +
				"\n.text{" +
				"\npadding:15px; " +
				"\n}" +
				"\np{" +
				"\ntext-decoration:none; " +
				"\npadding-top:3px; " +
				"\npadding-bottom:3px; " +
				"\n}" +
				"\n</style>" +
				"\n</head>" +
				"\n<body>" +
				"\n<div class=\"inner\">" +
				"\n<div class=\"text\">" +
				"<p>Witaj " + firstName + ",</p>"
				+"\n<br>"
				+ "<p>dziękujemy za złożenie zamówienia w naszym sklepie!</p>"
				+ "<p>Nasz zespół zajmie się Twoim zamówieniem najszybciej jak to możliwe. "
				+ "\nWkrótce dostaniesz informację na temat statusu zamówienia.</p>"
				+ "<p>Gdyby realizacja zamówienia wymagała naszych dodatkowych działań" 
				+ " lub była z jakicholwiek względów niemożliwa do zrealizowania, " 
				+ "skontaktujemy się z Tobą niezwłocznie. </p>"
				+ "<br> "
				+ "<p>W razie pytań prosimy o kontakt z nami: </p>"
				+ "<p> * Telefon: 698 930 778 </p>"
				+ "<p> * E-mail: wojciechpulik@gmail.com </p>"
				+ "<br> "
				+ "<p><b>Zespół splepu internetowego 'Mydło i Powidło'</b></p>"
				+ "<p><b>**Mydło i Powidło Sp. z o.o.**</b></p>"
				+ "<p><b>**Kapitał zakładowy: Kanapka z Salcesonem**</b></p>"
				+ "\n</div>" 
				+ "\n</div>" 
				+ "\n</body>";
		return orderConfirmationMessage;
	}
	
	public static String getOrderConfirmationMessageSubject() {
		return orderConfirmationMessageSubject;
	}
			

}
