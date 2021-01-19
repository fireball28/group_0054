package conferencesim.controllers.cli;

import java.util.Scanner;

import conferencesim.usecases.*;

public abstract class CommandController implements Commandable {
	
	protected UserManager um_inst;
	protected UserStats us_inst;
	protected EventManager em_inst;
	protected EventStats es_inst;
	protected RoomManager rm_inst;
	protected Messenger m_inst;
	protected UserRequestsHelper urh_inst;
	
	protected Scanner console = new Scanner(System.in);
	
	@Override
	public void setCurrViews(Displayable d, EDisplayable e) {
		if (um_inst != null) {
			um_inst.setViewCaller(d);
			um_inst.seteViewCaller(e);
		}
		if (us_inst != null) {
			us_inst.setViewCaller(d);
			us_inst.seteViewCaller(e);
		}
		if (em_inst != null) {
			em_inst.setViewCaller(d);
			em_inst.seteViewCaller(e);
		}
		if (es_inst != null) {
			es_inst.setViewCaller(d);
			es_inst.seteViewCaller(e);
		}
		if (rm_inst != null) {
			rm_inst.setViewCaller(d);
			rm_inst.seteViewCaller(e);
		}
		if (m_inst != null) {
			m_inst.setViewCaller(d);
			m_inst.seteViewCaller(e);
		}
		if (urh_inst != null) {
			urh_inst.setViewCaller(d);
			urh_inst.seteViewCaller(e);
		}
	}
}
