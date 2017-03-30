package ru.academits.novoselovda.main;
/*
1. Сделать класс для односвязного списка и узла списка.
Надо реализовать методы:
•	получение размера списка+
•	получение первого узла+
•	получение/изменение значения по указанному индексу
•	получение узла по индексу
•	удаление элемента по индексу
•	вставка элемента по индексу
•	удаление узла по значению
•	вставка и удаление узла после указанного узла
•	разворот списка за линейное время
2* (Эта задача не обязательная). Есть односвязный список, каждый элемент которого хранит дополнительную
ссылку на произвольный элемент списка. Эта ссылка может быть и null.
Надо создать копию этого списка, чтобы в копии эти произвольные ссылки ссылались на соответствующие элементы в копии.

 */
import ru.academits.novoselovda.list.List;
import ru.academits.novoselovda.list.Node;

public class Main {
    public static void main(String[] args) {
        try {
            List<String> singleList = new List<>();
            Node node1 = new Node<>("первый элемент");
            singleList.insertNode(node1, 0);
            Node node2 = new Node<>("второй элемент");
            singleList.insertNode(node2, 0);
            Node node3 = new Node<>("третий элемент");
            singleList.insertNode(node3, 1);
            Node node4 = new Node<>("четвёртый элемент");
            singleList.insertNode(node4, 3);
            System.out.println(singleList);
            System.out.println("получение размера списка: " + singleList.getLength());
            System.out.println("получение первого узла: " + singleList.getFirstNode().getData());
            int userIndex = 2;
            System.out.println("получение значения по указанному индексу " + userIndex + ": " + singleList.getNodeData(userIndex));
            String userData = "изменён";
            singleList.setNodeData(userData, userIndex);
            System.out.println("изменение значения по указанному индексу " + userIndex + ": " + singleList);
            userIndex = 3;
            System.out.println("получение узла по индексу " + userIndex + ": "
                    + singleList.getNode(userIndex).getData());
            singleList.dellNode(userIndex);
            System.out.println("удаление элемента по индексу " + userIndex + ": " + singleList);
            System.out.println("размер списка: " + singleList.getLength());
            Node node5 = new Node<>("пятый элемент");
            singleList.insertNode(node5, userIndex);
            System.out.println("вставка элемента по индексу " + userIndex + ": " + singleList);
            System.out.println("размер списка: " + singleList.getLength());
            userData = "второй элемент";
            singleList.dellNode(userData);
            System.out.println("удаление узла по значению  - " + userData + ": " + singleList);
            System.out.println("размер списка: " + singleList.getLength());
            Node node6 = new Node<>("шестой элемент");
            singleList.insertNextNode(node6, node3);
            System.out.println("вставка 6 узла после указанного 3 узла: " + singleList);
            System.out.println("размер списка: " + singleList.getLength());
            singleList.dellNexNode(node3);
            System.out.println("удаление узла после указанного 3 узла: " + singleList);
            System.out.println("размер списка: " + singleList.getLength());
            singleList.reverseOfList();
            System.out.println("разворот списка за линейное время: " + singleList);
            System.out.println("размер списка: " + singleList.getLength());
            System.out.println("------------------------ЗАДАНИЕ С *-----------------------------------------");
            node1.setData("первый элемент");
            singleList.insertNode(node2, 0);
            singleList.insertNode(node4, 0);
            singleList.insertNode(node6, 0);
            System.out.println("оригниальный список: " + singleList);
            node6.setRandomNext(node1);
            node4.setRandomNext(node6);
            node2.setRandomNext(node1);
            node5.setRandomNext(node3);
            node1.setRandomNext(node4);
            node3.setRandomNext(null);
            for (int i = 0; i < singleList.getLength(); i++) {
                System.out.println(singleList.getNode(i) + " ссылается на "
                        + singleList.getNode(i).getRandomNext());
            }
            List cloneList = singleList.cloneList();
            System.out.println("клонированный список: " + cloneList);
            for (int i = 0; i < cloneList.getLength(); i++) {
                System.out.println(cloneList.getNode(i) + " ссылается на "
                        + cloneList.getNode(i).getRandomNext());
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
