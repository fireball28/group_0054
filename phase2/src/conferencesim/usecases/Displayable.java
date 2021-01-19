package conferencesim.usecases;

import conferencesim.view.MainView;

import java.util.List;

public interface Displayable {
    MainView callView();
    void printList(List<String> stuff);
    void print(String s);

}
