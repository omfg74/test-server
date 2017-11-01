package Objects;

public class Task {
    private Long id;



    private String login;
    private String name;
    private String result;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {

        return status;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLogin() {

        return login;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
