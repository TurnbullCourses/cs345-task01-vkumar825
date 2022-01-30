package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() throws IllegalArgumentException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);

        BankAccount bankAccount2 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount2.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException, IllegalArgumentException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        BankAccount bankAccount2 = new BankAccount("a2@b.com", 300);
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(400));

        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(-100));

        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(100.567));
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(-100.56));
    
    }

    @Test
    void depositTest() throws IllegalArgumentException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance());

        // Cases where you try to deposit negative amount, and more than 2 decimal places
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-100));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-10.585));

        //Case where you try to deposit non-negative amount with more than 2 decimal places.
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(50.585));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(87.569));

        //Case where you deposit non-negative amount, and less than or equal to 2 decimal places
        bankAccount.deposit(120.58);
        assertEquals(420.58, bankAccount.getBalance());
        bankAccount.deposit(0.01);
        assertEquals(420.59, bankAccount.getBalance());

    }

    @Test
    void transferTest() throws IllegalArgumentException, InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        BankAccount bankAccount2 = new BankAccount("a@b2.com", 0);

        bankAccount.transfer(bankAccount2, 50);
        assertEquals(50, bankAccount2.getBalance());
        assertEquals(150, bankAccount.getBalance());

        // Case where you try to transfer negative amount
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(bankAccount2, -15));

        //Case where you try to transfer amount with more than two decimal places
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(bankAccount2, -15.787));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(bankAccount2, 60.975));

        // Case where you try to transfer more than what you have 
        assertThrows(InsufficientFundsException.class, ()-> bankAccount.transfer(bankAccount2, 400));
        assertThrows(InsufficientFundsException.class, ()-> bankAccount2.transfer(bankAccount, 300));

    }

    @Test 
    void isAmountValidTest(){
        // Cases where numbers are positive but have more than two decimal places
        assertFalse(BankAccount.isAmountValid(70.001));
        assertFalse(BankAccount.isAmountValid(345.7648));

        // Cases where amount is positive and has less than or equal to two decimal places
        assertTrue(BankAccount.isAmountValid(59.99));
        assertTrue(BankAccount.isAmountValid(129.52));

        // Cases where amount is negative
        assertFalse(BankAccount.isAmountValid(-3.99));
        assertFalse(BankAccount.isAmountValid(-100.456));

        // Case where withdraw amount is zero
        assertTrue(BankAccount.isAmountValid(0.00));
        assertFalse(BankAccount.isAmountValid(0.001));

    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //correct format, not a border case
        assertFalse( BankAccount.isEmailValid(""));//border case, no characters present
        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));//a dash must be followed by a letter, not a border case
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com"));//hashtags are not allowed, not a border case
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));//a dot is followed by a latter, not a border case
        assertFalse(BankAccount.isEmailValid(".abc@mail.com"));//dot can't be used at the beginning, border case
        assertFalse(BankAccount.isEmailValid("def.abc@mail.c"));//dot in the domain must not be the last or second last character, border case

        assertFalse(BankAccount.isEmailValid("hello@mail")); // no dot for the suffix, right-side border case equivalence class
        assertFalse(BankAccount.isEmailValid("hello@")); // no suffix, right-side border case equivalence class
        assertTrue(BankAccount.isEmailValid("he-llo@mail.com")); // email with dash, middle equivalence class
        assertTrue(BankAccount.isEmailValid("h_e-ll.o@mail.com")); // email with all non-letter or number symbols before @, middle equivalence class
        
    }

    @Test
    void constructorTest() throws IllegalArgumentException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 48.208));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -18.208));
    }

}