package hu.codecool.annotation_assignment;

public class Routes {

    @WebRoute(path = "/test")
    public String test1() {
        return "This is the fist test route";
    }

    @WebRoute(path = "/another-test")
    public String test2() {
        return "This is another test route";
    }

}
