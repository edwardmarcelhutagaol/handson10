package entities;

public class Todolist {
    private String todo;
    private Integer id;

    public Todolist() {

    }

    public Todolist(final String todo) {
        this.todo = todo;
    }
    public String getTodo() {
        return todo;
    }

    public void setTodo(final String todo) {
        this.todo = todo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
