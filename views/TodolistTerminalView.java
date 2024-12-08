package views;

import java.util.Objects;
import java.util.Scanner;
import services.TodolistService;

import entities.Todolist;
import services.TodolistService;
public class TodolistTerminalView {
    private final TodolistService todolistService;
    public static Scanner scanner = new Scanner(System.in);

    public TodolistTerminalView(final TodolistService todolistService) {
        this.todolistService = todolistService;
    }

    public static String input(String info) {
        System.out.println(info + " : ");
        String data = scanner.nextLine();
        return data;
    }

    public void mainScreen() {
        while (true) {
                    showTodoList();
                    System.out.println("MENU : ");
                    System.out.println("1. Tambah");
                    System.out.println("2. Hapus");
                    System.out.println("3. Keluar");

                    var input = input("Pilih");

                    if (input.equals("1")) {
                        addTodo();
                    } else if (input.equals("2")) {
                        removeTodo();
                    } else if (input.equals("3")) {
                        break;
                    } else {
                        System.out.println("Pilih menu dengan benar");
                    }
                }
            }

            public void showTodoList() {
                System.out.println("TODO LIST");
                var model = todolistService.getTodolist();
                for (var i = 0; i < model.length; i++) {
                    var todo = model[i];
                    var no = i + 1;
                    if (Objects.nonNull(todo)) {
                        System.out.println(no + ". " + todo.getTodo());
                    }
                }
            }

        public void addTodo() {
            System.out.println("MENAMBAH TODO LIST");
            var todo = input("Todo (x jika batal)");
            if (todo.equals("x")) {
                //batal
            } else {
                Todolist newTodo = new Todolist(todo);
                todolistService.addTodolist(newTodo);
            }
    }
        public void removeTodo() {
            System.out.println("MENGHAPUS TODO LIST");

            var number = input("Nomor yang dihapus (x jika batal)");
            if (number.equals("x")) {
                // batal
            } else {
                boolean success = todolistService.removeTodolist(Integer.valueOf(number), false);
                if (!success) {
                    System.out.println("Gagal menghapus todo list : " + number);
                }
            }
        }
}
