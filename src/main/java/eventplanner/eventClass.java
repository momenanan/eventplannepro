package eventplanner;

import java.sql.Date;

public class eventClass {

	private int e_idCla;
private String Per_ofEventCla;
private Date Date_event;
private String LocCla;
private int Num_gCla;
private String Desc_Cla;
private int User_FKCla;
private int ven_fk_Cla;
private int sp_fk_Cla;

public void set_eid(int x){
	e_idCla = x;	
}
public void set_period(String s){
	Per_ofEventCla = s;
}
public void set_Date(Date t){
	Date_event =t;	
}		
public void set_Loc(String s) {
	LocCla=s;
}	
public void set_Num_gCla(int n){		
	Num_gCla=n;
}
public void set_Desc(String s){
	Desc_Cla=s;
}
public void set_User(int u){
	User_FKCla=u;	
}

public void set_VenFK(int v) {
	ven_fk_Cla=v;
}

public void set_spFK(int s) {
	sp_fk_Cla=s;
}

}
