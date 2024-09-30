package shadowfox;
import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;
    private String phoneNumber2;
    private String email;
    Contact(String n,String phn,String em)
    {
        name=n;
        phoneNumber=phn;
        email=em;
    }
    Contact(String n,String phn1,String phn2, String em)
    {
        name=n;
        phoneNumber=phn1;
        phoneNumber2=phn2;
        email=em;
    }
    Contact(String n,String phn)
    {
        name=n;
        phoneNumber=phn;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String newName)
    {
        name=newName;
    }
    public void setPhone(String newPhoneNumber)
    {
        phoneNumber=newPhoneNumber;
    }
    public void setEmail(String newEmail)
    {
        email=newEmail;
    }
    @Override
    public String toString()
    {
        if(ContactManagementSystem.b) {
            return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
        }
        else
        {
            return "Name: " + name + ", Phone1: " + phoneNumber + ",Phone 2:"+phoneNumber2 + ", Email: " + email;
        }
    }

}

public class ContactManagementSystem
{
    protected ArrayList<Contact> contacts = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    String name, phone, phone2, email;
    static boolean b=true;
    public void menu()
    {
        System.out.println("1. Add Contact");
        System.out.println("2. View Contacts");
        System.out.println("3. Update Contact");
        System.out.println("4. Delete Contact");
        System.out.println("5. Exit");
    }
    public void createContact()
    {

        System.out.println("Enter Name: ");scanner.nextLine();
        name = scanner.nextLine();
        System.out.println("Enter Phone Number: ");
        phone = scanner.nextLine();
        System.out.println("Enter Email: ");
        email = scanner.nextLine();

        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact created successfully.");
    }
    public void updateContact(String name)
    {
        for (Contact contact : contacts)
        {
            if (contact.getName().equalsIgnoreCase(name))
            {
                if(ifChangeDetails())
                {
                    System.out.println("Current details: " + contact);

                    scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhone = scanner.nextLine();
                    System.out.print("Enter new email address: ");
                    String newEmail = scanner.nextLine();

                    // Update the contact
                    contact.setName(newName);
                    contact.setPhone(newPhone);
                    contact.setEmail(newEmail);
                    System.out.println("Contact updated: \n" ); viewContacts();
                }
                if(ifAddPhoneNumber())
                {
                    System.out.println("enter the new phone number");scanner.nextLine();
                    phone2=scanner.nextLine();
                    contacts.remove(contact);
                    b=false;
                    contacts.add(new Contact(name, phone, phone2, email));
                }
                if (ifRemoveEmail())
                {
                    contacts.remove(contact);
                    contacts.add(new Contact(name,phone));
                    System.out.println("Contact updated: \n" ); viewContacts();
                }
                return;
            }
        }
    }
    public boolean ifChangeDetails()
    {
        System.out.println("Do you want to change name. phone number and email?");
        System.out.println("Enter 1 for yes\n Enter 2 for no");
        int x=scanner.nextInt();
        return x == 1;
    }
    public boolean ifAddPhoneNumber()
    {
        System.out.println("Do you want to add another phone number?");
        System.out.println("Enter 1 for yes\n Enter 2 for no");
        int x=scanner.nextInt();
        return x == 1;
    }
    public boolean ifRemoveEmail()
    {
        System.out.println("Do you want to remove email contact?");
        System.out.println("Enter 1 for yes\n Enter 2 for no");
        int x=scanner.nextInt();
        return x == 1;
    }
    public void viewContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
    public void deleteContact(String name) {
        contacts.removeIf(contact -> contact.getName().equals(name));
        System.out.println("Contact deleted successfully.");
    }
    public static void main(String[] a)
    {
        ContactManagementSystem cms=new ContactManagementSystem();
        while(true)
        {
            cms.menu();
            System.out.println("Enter your choice:");
            int choice= scanner.nextInt();
            switch (choice)
            {
                case 1:
                    cms.createContact();
                    break;
                case 2:
                    cms.viewContacts();
                    break;
                case 3:
                    System.out.println("enter the contact name which is to be updated:");
                    scanner.nextLine();
                    String refname = scanner.nextLine();
                    cms.updateContact(refname);
                    break;
                case 4:
                    System.out.println("enter the contact name which is to be deleted:");
                    scanner.nextLine();
                    String refname2 = scanner.nextLine();
                    cms.deleteContact(refname2);
                    break;
                case 5:System.exit(0);
                default:System.out.println("Invalid Input");
            }
        }
    }
}