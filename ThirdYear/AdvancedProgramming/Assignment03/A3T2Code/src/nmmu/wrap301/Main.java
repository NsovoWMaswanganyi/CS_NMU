package nmmu.wrap301;

import nmmu.wrap301.menu.Menu;

public class Main {
    public static void main(String[] args) {

        Menu menu = MenuBuilderUtil.build(".//A3T2Code/resources/menu.xml");
        menu.run();

    }
}
