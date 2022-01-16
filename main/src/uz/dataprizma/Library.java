package uz.dataprizma;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Library {

    private List<Book> bookList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<UserBookLanding> userBookLandingList = new ArrayList<>();

    public Library(){}

    public Library(List<Book> bookList, List<User> userList, List<UserBookLanding> userBookLandingList) {
        this.bookList = bookList;
        this.userList = userList;
        this.userBookLandingList = userBookLandingList;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        String process;

        System.out.println("Welcome to the our library!!!\n\n");
        do {

            System.out.println("For exiting from the library, you should type:                                                                   exit\n");
            System.out.println("For adding a new book to the library, you should type:                                                           add book\n");
            System.out.println("For registration a new user to the library, you should type:                                                     register user\n");
            System.out.println("For searching an available book from the library, you should type:                                               search books\n");
            System.out.println("For searching book by keyword from the library, you should type:                                                 search books by keyword\n");
            System.out.println("For lending a book to the user by book ID, you should type:                                                      lend book\n");
            System.out.println("For removing a book from the library if ID exists and it is not currently lent, you should type:                 remove book\n");
            System.out.println("For getting all books in the library (distinctly), you should type:                                              get all books\n");
            System.out.println("For getting all users in the library (distinctly), you should type:                                              get all users\n");
            System.out.println("For returning information that how many books are available or not, you should type:                             return information\n");
            System.out.println("For getting all books information or details (title, author, year, status, user information), you should type:   getting all books details\n");
            process = sc.nextLine();

            switch (process) {
                case "exit" -> {
                    System.out.println("Exiting from the library!");
                    return;
                }
                case "add book" -> {
                    System.out.println("Adding a book to the library!\n");
                    System.out.println("Please, enter the title of a new book:");
                    String title = sc.nextLine();
                    System.out.println("Please, enter the author of a new book:");
                    String author = sc.nextLine();
                    System.out.println("Please, enter the year of a new book:");
                    String year = sc.nextLine();
                    Book newBook = new Book(title, year, author, BookStatus.AVAILABLE);
                    library.getBookList().add(newBook);
                    System.out.println("Process is done successfully!\n\n");
                }
                case "register user" -> {
                    System.out.println("Registration a new user to the library!\n");
                    System.out.println("Please, enter the firstName of a new user:");
                    String firstName = sc.nextLine();
                    System.out.println("Please, enter the lastName of a new user:");
                    String lastName = sc.nextLine();
                    System.out.println("Please, enter the middleName of a new user:");
                    String middleName = sc.nextLine();
                    System.out.println("Please, enter the passportSeries of a new user:");
                    String passportSeries = sc.nextLine();
                    System.out.println("Please, enter the passportNumber of a new user:");
                    String passportNumber = sc.nextLine();
                    System.out.println("Please, enter the address of a new user:");
                    String address = sc.nextLine();
                    System.out.println("Please, enter the phone of a new user:");
                    String phone = sc.nextLine();
                    User newUser = new User(firstName, lastName, middleName, passportSeries, passportNumber, address, phone);
                    library.getUserList().add(newUser);
                    System.out.println("Process is done successfully!\n\n");
                }
                case "search books" -> {
                    System.out.println("Searching an available book from the library!\n");
                    List<Book> result = library.getBookList().stream()
                            .filter(item -> item.getBookStatus().name().equalsIgnoreCase("AVAILABLE"))
                            .collect(Collectors.toList());
                    library.printBooks();
                }
                case "lend book" -> {
                    System.out.println("Lending a book to the user by book ID!\n");
                    System.out.println("Please, enter the user ID:");
                    String userID = sc.nextLine();
                    System.out.println("Please, enter the book ID:");
                    String bookID = sc.nextLine();
                    try {
                        UUID bookUUID = UUID.fromString(bookID);
                        UUID userUUID = UUID.fromString(userID);
                        Book book = library.searchBookById(bookUUID, library.getBookList());
                        User user = library.searchUserById(userUUID, library.getUserList());
                        if(book == null){
                            System.out.println("Book is not found by ID: " + bookUUID);
                            System.out.println("\n\n");
                        }else if(user == null){
                            System.out.println("User is not found by ID: " + userUUID);
                            System.out.println("\n\n");
                        }else if(book.getBookStatus().name().equalsIgnoreCase("AVAILABLE")){
                            UserBookLanding userBookLanding = new UserBookLanding(user, book);
                            library.getUserBookLandingList().add(userBookLanding);
                            library.setBookList(library.updateBookStatus(book.getId(), library.getBookList(), BookStatus.LANDED));
                            System.out.println("Process is done successfully!\n\n");
                        }else {
                            System.out.println("The Book by ID: "+ bookID + " is not available to lend!\n\n");
                        }
                    }catch (Exception e){
                        System.out.println("Please, enter the correct ID\n\n");
                    }
                }
                case "remove book" -> {
                    System.out.println("Removing a book from the library if ID exists and it is not currently lent!\n");
                    System.out.println("Please, enter the book ID:");
                    String bookID = sc.nextLine();
                    try {
                        UUID ID = UUID.fromString(bookID);
                        Book book = library.searchBookById(ID, library.getBookList());
                        if(book != null && book.getBookStatus().name().equalsIgnoreCase("AVAILABLE")){
                            library.setBookList(library.removeBook(book, library.getBookList()));
                            System.out.println("Process is done successfully!\n\n");
                        }else {
                            System.out.println("The Book by ID: "+ bookID + " is not available to delete!\n\n");
                        }
                    }catch (Exception e){
                        System.out.println("Please, enter the correct ID\n\n");
                    }
                }
                case "get all books" -> {
                    System.out.println("Getting all books in the library (distinctly)!\n");
                    if(library.getBookList().size() > 0) {
                        library.printBooks();
                    }else {
                        System.out.println("The Library is empty!");
                        System.out.println("\n\n");
                    }
                }
                case "get all users" -> {
                    System.out.println("Getting all users in the library (distinctly)!\n");
                    if(library.getUserList().size() > 0){
                        for (User user: library.getUserList()) {
                            System.out.println("User ID:  " + user.getId());
                            System.out.println("User FirstName:  " + user.getFirstName());
                            System.out.println("User LastName:  " + user.getLastName());
                            System.out.println("User MiddleName:  " + user.getMiddleName());
                            System.out.println("User PassportSeries:  " + user.getPassportSeries());
                            System.out.println("User PassportNumber:  " + user.getPassportNumber());
                            System.out.println("User Address:  " + user.getAddress());
                            System.out.println("User Phone:  " + user.getPhone());
                            System.out.println("//////////////////////////////////////////////////////");
                            System.out.println("\n\n");
                        }
                    }else {
                        System.out.println("There is no user!");
                    }

                }
                case "return information" -> {
                    System.out.println("Returning information that how many books are available or not!\n");
                    int availableBookCount = 0;
                    int lentBookCount = 0;
                    for (Book book: library.getBookList()) {
                        if(book != null && book.getBookStatus().name().equalsIgnoreCase("AVAILABLE")){
                            availableBookCount++;
                        }else {
                            lentBookCount++;
                        }
                    }
                    System.out.println("In the library");
                    System.out.println("available book count: " + availableBookCount);
                    System.out.println("lent book count: " + lentBookCount);
                    System.out.println("all book count: " + library.getBookList().size());
                    System.out.println("\n\n");
                }
                case "getting all books details" -> {
                    System.out.println("Getting all books information or details (title, author, year, status, user information)!\n");
                    if(library.getUserBookLandingList().size() > 0){
                        for (UserBookLanding userBookLanding: library.getUserBookLandingList()) {
                            System.out.println("Book ID:  " + userBookLanding.getBook().getId());
                            System.out.println("Book title:  " + userBookLanding.getBook().getTitle());
                            System.out.println("Book author:  " + userBookLanding.getBook().getAuthor());
                            System.out.println("Book year:  " + userBookLanding.getBook().getYear());
                            System.out.println("Book status:  " + userBookLanding.getBook().getBookStatus().name());
                            System.out.println("User ID:  " + userBookLanding.getUser().getId());
                            System.out.println("User FirstName:  " + userBookLanding.getUser().getFirstName());
                            System.out.println("User LastName:  " + userBookLanding.getUser().getLastName());
                            System.out.println("User MiddleName:  " + userBookLanding.getUser().getMiddleName());
                            System.out.println("User PassportSeries:  " + userBookLanding.getUser().getPassportSeries());
                            System.out.println("User PassportNumber:  " + userBookLanding.getUser().getPassportNumber());
                            System.out.println("User Address:  " + userBookLanding.getUser().getAddress());
                            System.out.println("User Phone:  " + userBookLanding.getUser().getPhone());
                            System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
                            System.out.println("\n\n");
                        }
                    }else {
                        System.out.println("There is no information about books information or details");
                        System.out.println("\n\n");
                    }
                }
                case "search books by keyword" -> {
                    System.out.println("Searching books by keyword!\n");

                    System.out.println("Please, search type: with combination or not:   yes/no");
                    String searchType = sc.nextLine();

                    switch(searchType){
                        case "yes" -> {
                            System.out.println("In order to search books:");
                            System.out.println("please, enter the book title: ");
                            String title = sc.nextLine();
                            System.out.println("please, enter the book author: ");
                            String author = sc.nextLine();
                            Predicate<Book> predicateByTitle = (bookByTitle) -> bookByTitle.getTitle().startsWith(title);
                            Predicate<Book> predicateByAuthor = (bookByAuthor) -> bookByAuthor.getAuthor().startsWith(author);
                            List<Book> collect = library.bookList.stream()
                                    .filter(predicateByTitle.and(predicateByAuthor))
                                    .collect(Collectors.toList());

                            library.printBooks();
                        }
                        case "no" -> {
                            System.out.println("In order to search books:");
                            System.out.println("please, enter the book title or author or year: ");
                            String keyword = sc.nextLine();
                            Predicate<Book> predicateByTitle = (bookByTitle) -> bookByTitle.getTitle().startsWith(keyword);
                            Predicate<Book> predicateByAuthor = (bookByAuthor) -> bookByAuthor.getAuthor().startsWith(keyword);
                            Predicate<Book> predicateByYear = (bookByYear) -> bookByYear.getYear().startsWith(keyword);

                            List<Book> collect = library.bookList.stream()
                                    .filter(predicateByTitle.or(predicateByAuthor).or(predicateByYear))
                                    .collect(Collectors.toList());

                            library.printBooks();
                        }
                        default -> System.out.println("Please, enter only yes or no!");
                    }
                }
                default -> System.out.println("Please, select the available menu item!\n\n");
            }
        }while(!process.equalsIgnoreCase("exit"));

    }

    public  void printBooks(){
        for (Book book: bookList) {
            System.out.println("Book ID:  " + book.getId());
            System.out.println("Book title:  " + book.getTitle());
            System.out.println("Book author:  " + book.getAuthor());
            System.out.println("Book year:  " + book.getYear());
            System.out.println("Book status:  " + book.getBookStatus().name());
            Predicate<UserBookLanding> predicateUserByBookID = (userByBookID) -> userByBookID.getBook().getId().toString().equalsIgnoreCase(book.getId().toString());
            List<UserBookLanding> collect = userBookLandingList.stream()
                    .filter(predicateUserByBookID)
                    .collect(Collectors.toList());
            for (UserBookLanding userBookLanding: collect) {
                System.out.println("User ID:  " + userBookLanding.getUser().getId());
                System.out.println("User FirstName:  " + userBookLanding.getUser().getFirstName());
                System.out.println("User LastName:  " + userBookLanding.getUser().getLastName());
                System.out.println("User MiddleName:  " + userBookLanding.getUser().getMiddleName());
                System.out.println("User PassportSeries:  " + userBookLanding.getUser().getPassportSeries());
                System.out.println("User PassportNumber:  " + userBookLanding.getUser().getPassportNumber());
                System.out.println("User Address:  " + userBookLanding.getUser().getAddress());
                System.out.println("User Phone:  " + userBookLanding.getUser().getPhone());
            }
            System.out.println("//////////////////////////////////////////////////////");
            System.out.println("\n\n");
        }
    }

    public  Book searchBookById(UUID bookID, List<Book> bookList){
        return bookList.stream()
                .filter(item -> bookID.toString().equals(item.getId().toString()))
                .findAny()
                .orElse(null);
    }

    public  User searchUserById(UUID userID, List<User> userList){
        return userList.stream()
                .filter(item -> userID.toString().equals(item.getId().toString()))
                .findAny()
                .orElse(null);
    }

    public List<Book> updateBookStatus(UUID bookID, List<Book> bookList, BookStatus bookStatus){
        for(int i = 0; i < bookList.size(); i++) {
            if(bookList.get(i).getId().toString().equalsIgnoreCase(bookID.toString())){
                Book book = bookList.get(i);
                book.setBookStatus(bookStatus);
                bookList.set(i, book);
            }
        }
        return bookList;
    }

    public List<Book> removeBook(Book book, List<Book> bookList){
        bookList.remove(book);
        return bookList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<UserBookLanding> getUserBookLandingList() {
        return userBookLandingList;
    }

    public void setUserBookLandingList(List<UserBookLanding> userBookLandingList) {
        this.userBookLandingList = userBookLandingList;
    }
}
