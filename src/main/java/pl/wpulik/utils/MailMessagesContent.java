package pl.wpulik.utils;

public class MailMessagesContent {
	
	private final static String orderConfirmationMessageSubject = "Potwierdzenie zamówienia nr ";
	
	public MailMessagesContent() {}
	
	public static String getOrderConfirmationMessage(String firstName) {
		String orderConfirmationMessage = 
				"Witaj " + firstName + ","
				+ "\n "
				+ "\ndziękujemy za złożenie zamówienia w naszym sklepie!"
				+ "\nNasz zespół zajmie się Twoim zamówieniem najszybciej jak to możliwe."
				+ "\nWkrótce dostaniesz informację na temat statusu zamówienia."
				+ "\nGdyby realizacja zamówienia wymagała naszych dodatkowych działań" 
				+ " lub była z jakicholwiek względów niemożliwa do zrealizowania, " 
				+ "skontaktujemy się z Tobą niezwłocznie. "
				+ "\n "
				+ "\nW razie pytań prosimy o kontakt z nami: "
				+ "\n * Telefon: 698 930 778 "
				+ "\n * E-mail: wojciechpulik@gmail.com "
				+ "\n "
				+ "\nZespół splepu internetowego 'Mydło i Powidło'"
				+ "\n**Mydło i Powidło Sp. z o.o.**";
		return orderConfirmationMessage;
	}
	
	public static String getOrderConfirmationMessageSubject() {
		return orderConfirmationMessageSubject;
	}
			

}
