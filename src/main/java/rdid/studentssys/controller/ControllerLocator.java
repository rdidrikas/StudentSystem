package rdid.studentssys.controller;

public class ControllerLocator {
    private static HomeController homeController;
    public static void setHomeController(HomeController c) {
        homeController = c;
    }
    public static HomeController getHomeController() {
        return homeController;
    }
}
