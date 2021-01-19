package conferencesim.usecases;

public abstract class Usecase {
    protected Displayable view;
    protected EDisplayable eView;

    public void setViewCaller(Displayable view) {
        this.view = view;
    }
    public void seteViewCaller(EDisplayable eView) {
        this.eView = eView;
    }

}
