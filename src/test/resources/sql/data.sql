INSERT INTO `USER` (USER_ID, EMAIL, USERNAME, PASSWORD, IS_DELETED) VALUES
(-1, 'ANONYMOUS', 'ANONYMOUS', 'ANONYMOUS', 'N'),
(1, 'user@demo.com', 'user', '$2a$10$7aRvnzdG99xiIRpQsrJSl.Nifpd2mj4XwJT5xaoelp09Yyz5mrvKy', 'N'),
(2, 'admin@demo.com', 'admin', '$2a$10$q.rsGmyzGmivHMCZ6ngZTOeTsZyyREffk/iARcK97BUx9TtQ1AxxG', 'N'),
(3, 'translator@demo.com', 'translator', '$2a$10$PuKiF.IfL6bRmBDWI2MRO.xsx0Og.K23/rxdlEwUzZObS/MM12G.2', 'N'),
(4, 'moderator@demo.com', 'moderator', '$2a$10$8U9EMfXR2vyzm.buo02uUes7zKYbRfb6nzs1iJshICOvHiEvqkyNO', 'N');

INSERT INTO ROLE (ROLE_ID, ROLE) VALUES
(1, 'USER'),
(2, 'ADMIN'),
(3, 'TRANSLATOR'),
(4, 'MODERATOR');

INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 3),
(4, 1),
(4, 4);

INSERT INTO LOCALE (LOCALE_ID, LOCALE_CODE) VALUES
(1, 'en-US'),
(2, 'ru-RU');

INSERT INTO `LEVEL` (LEVEL_ID, `TYPE`) VALUES
(1, 'JUNIOR'),
(2, 'MIDDLE'),
(3, 'SENIOR');

INSERT INTO LEVEL_LOCALIZED (LEVEL_ID, LOCALE_ID, LEVEL_LOCALIZED) VALUES
(1, 1, 'Junior'),
(1, 2, 'Джуниор'),
(2, 1, 'Middle'),
(2, 2, 'Мидл'),
(3, 1, 'Senior'),
(3, 2, 'Сеньор');

INSERT INTO QUESTION (QUESTION_ID, USER_ID, LINK, LEVEL_ID, IS_FULLY_LOCALIZED) VALUES
(1, 1, 'http://www.java67.com/2013/08/can-we-override-private-method-in-java-inner-class.html', 1, 1),
(2, 2, 'http://javarevisited.blogspot.sg/2010/10/difference-between-hashmap-and.html#axzz53B6SD769', 1, 1),
(3, 3, 'http://javarevisited.blogspot.sg/2012/04/difference-between-list-and-set-in-java.html#axzz53n9YK0Mb', 1, 1),
(4, 4, 'http://www.java67.com/2013/06/how-get-method-of-hashmap-or-hashtable-works-internally.html', 1, 1),
(5, 1, 'http://www.java67.com/2012/12/difference-between-arraylist-vs-LinkedList-java.html', 1, 1),
(6, 2, 'http://www.java67.com/2015/08/top-10-method-overloading-overriding-interview-questions-answers-java.html', 1, 1),
(7, 3, 'http://www.java67.com/2014/04/difference-between-polymorphism-and-Inheritance-java-oops.html', 1, 1),
(8, 4, 'http://www.java67.com/2012/08/can-we-override-private-method-in-java.html', 1, 1),
(9, 1, 'http://www.java67.com/2017/08/difference-between-abstract-class-and-interface-in-java8.html', 1, 1),
(10, 2, 'http://www.java67.com/2012/10/difference-between-throw-vs-throws-in.html', 1, 1),
(11, 3, 'http://www.java67.com/2013/06/difference-between-this-and-super-keyword-java.html', 1, 1),
(12, 4, 'http://www.java67.com/2012/08/how-java-achieves-platform-independence.html', 2, 1),
(13, 1, 'http://javarevisited.blogspot.sg/2012/12/how-classloader-works-in-java.html#axzz59AWpr6cb', 2, 1),
(14, 2, 'http://www.java67.com/2016/04/why-double-checked-locking-was-broken-before-java5.html', 2, 1),
(15, 3, 'http://javarevisited.blogspot.sg/2012/12/how-to-create-thread-safe-singleton-in-java-example.html', 2, 1),
(16, 4, 'http://www.java67.com/2012/08/what-is-volatile-variable-in-java-when.html', 2, 1),
(17, 1, 'http://www.java67.com/2012/08/what-are-difference-between-wait-and.html', 2, 1),
(18, 2, 'http://www.java67.com/2012/08/difference-between-countdownlatch-and-cyclicbarrier-java.html', 2, 1),
(19, 3, 'http://javarevisited.blogspot.sg/2012/07/auto-boxing-and-unboxing-in-java-be.html#axzz59AWpr6cb', 2, 1),
(20, 4, 'http://www.java67.com/2016/10/5-difference-between-stringbuffer.html', 2, 1),
(21, 1, 'http://www.java67.com/2012/08/can-we-override-static-method-in-java.html', 2, 1),
(22, 2, 'http://www.java67.com/2012/09/dom-vs-sax-parser-in-java-xml-parsing.html', 2, 1),
(23, 3, 'http://www.java67.com/2015/06/what-is-fail-safe-and-fail-fast-iterator-in-java.html', 2, 1),
(24, 4, 'http://javarevisited.blogspot.sg/2016/07/difference-in-string-pool-between-java6-java7.html#axzz4pGGwsyna', 2, 1),
(25, 1, 'http://javarevisited.blogspot.sg/2016/09/how-to-serialize-object-in-java-serialization-example.html', 2, 1),
(26, 2, 'http://javarevisited.blogspot.sg/2011/12/checked-vs-unchecked-exception-in-java.html', 2, 1),
(27, 3, 'http://www.java67.com/2012/12/difference-between-error-vs-exception.html', 2, 1),
(28, 4, 'http://javarevisited.blogspot.sg/2012/02/what-is-race-condition-in.html#axzz59AbkWuk9', 2, 1),
(29, 1, 'https://google.com', 3, 1);

INSERT INTO TITLE (TITLE, LOCALE_ID, QUESTION_ID) VALUES
('Can we override a private method in Java?', 1, 1),
('Можно ли переопределить приватный метод в Java?', 2, 1),
('Difference between Hashtable and HashMap in Java?', 1, 2),
('В чем разница между Hashtable и HashMap в Java?', 2, 2),
('Difference between List and Set in Java?', 1, 3),
('В чем разница между List и Set в Java?', 2, 3),
('Which two methods will you override for an Object to be used as key in HashMap?', 1, 4),
('Какие два метода нужно переопределить, чтобы объект мог быть использован в качестве ключа HashMap?', 2, 4),
('Difference between ArrayList and LinkedList in Java?', 1, 5),
('В чем разница между ArrayList и LinkedList в Java?', 2, 5),
('Difference between method overloading and overriding in Java?', 1, 6),
('В чем разница между переопределением и перегрузкой методов в Java?', 2, 6),
('Difference between polymorphism and iInheritance in Java?', 1, 7),
('В чем разница между полиморфизмом и наследованием в Java?', 2, 7),
('Can we access a private method in Java?', 1, 8),
('Можно ли получить доступ к приватному методу в Java?', 2, 8),
('Difference between interface and abstract class in Java?', 1, 9),
('В чем разница между interface и abstract class в Java?', 2, 9),
('Difference between throw and throws keyword in Java?', 1, 10),
('В чем разница между throw и throws в Java?', 2, 10),
('Difference between this and super in Java?', 1, 11),
('В чем разница между this и super в Java?', 2, 11),
('How does Java achieve platform independence?', 1, 12),
('Как Java добивается платформонезависимости?', 2, 12),
('What is ClassLoader in Java?', 1, 13),
('Что такое ClassLoader в Java?', 2, 13),
('What is double checked locking in singleton?', 1, 14),
('Что такое двойная проверка блокировки в синглтоне?', 2, 14),
('How do you create thread-safe singleton in Java?', 1, 15),
('Как создать потокобезопасный синглтон в Java?', 2, 15),
('When to use the volatile variable in Java?', 1, 16),
('Когда нужно использовать volatile переменную в Java?', 2, 16),
('Difference between wait and sleep in Java?', 1, 17),
('В чем разница между wait и sleep в Java?', 2, 17),
('Difference between CountDownLatch and CyclicBarrier in Java?', 1, 18),
('В чем разница между CountDownLatch и CyclicBarrier в Java?', 2, 18),
('How does autoboxing of Integer work in Java?', 1, 19),
('Как работает автоматическая упаковка Integer в Java?', 2, 19),
('Difference between StringBuilder and StringBuffer in Java?', 1, 20),
('В чем разница между StringBuilder и StringBuffer в Java?', 2, 20),
('Can we override a static method in Java?', 1, 21),
('Можно ли перепоределить статический метод в Java?', 2, 21),
('Difference between DOM and SAX parser in Java?', 1, 22),
('В чем разница между DOM и SAX парсерами в Java?', 2, 22),
('Difference between fail-safe and fail-fast iterators in Java?', 1, 23),
('В чем разница между fail-safe и fail-fast итераторами в Java?', 2, 23),
('What is the String pool in Java?', 1, 24),
('Что такое пул строк в Java?', 2, 24),
('Can a Serializable class contain a non-serializable field in Java?', 1, 25),
('Может ли Serializable класс содержать несериализуемое поле в Java?', 2, 25),
('Difference between checked and unchecked Exception in Java?', 1, 26),
('В чем разница между checked и unchecked исключениями в Java?', 2, 26),
('Difference between Error and Exception in Java?', 1, 27),
('В чем разница между Error и Exception в Java?', 2, 27),
('Difference between Race condition and Deadlock in Java?', 1, 28),
('В чем разница между Race condition и Deadlock в Java?', 2, 28),
('Stub', 1, 29),
('Заглушка', 2, 29);