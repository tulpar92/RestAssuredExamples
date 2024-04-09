package utils;

public class Reqres {
    public String firstName;
    public String lastName;
    public String email;
    public String avatar;

    public String job;

    public Integer id;

    public Reqres(int id, String firstname, String job, String email, String avatar) {
        this.id=id;
        this.firstName=firstname;
        this.lastName=job;
        this.email=email;
        this.avatar=avatar;
    }
    public Reqres(String firstname,String job){
        this.firstName=firstname;
        this.job=job;
    }
    public Reqres(){

    }








}
