package hu.codecool.annotation_assignment;

public class Routes {

    @WebRoute(path = "/test")
    public String test1() {
        return "This is the fist test route";
    }

}
