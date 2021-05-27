package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import database.Connector;

public class Stats {
	private Comune comune;
	private int totalSeg;
	private int waitingSeg;
	
	public Stats(Comune comune, int totalSeg, int waitingSeg) {
		this.comune = comune;
		this.totalSeg = totalSeg;
		this.waitingSeg = waitingSeg;
	}
	
	public Stats(String comune) {
		this.comune=new Comune(comune);
		this.totalSeg=0;
		this.waitingSeg=0;
	}
	
	public static Stats of(Connector conn, String comune) {
		Stats res=new Stats(comune);
		PreparedStatement ps;
		ResultSet rs;		
		
		 try {
			ps = conn.prepare("SELECT COUNT(s.stato=7)"
					+ "FROM Segnalazioni s JOIN Comuni c ON s.Comune = c.name"
					+ "WHERE c.name = ? ");
			ps.setString(1, comune);
			rs = ps.executeQuery();
			res.setTotalSeg(rs.getInt(1));
			
			ps = conn.prepare("SELECT COUNT(s.stato=1)"
					+ "FROM Segnalazioni s JOIN Comuni c ON s.Comune = c.name"
					+ "WHERE c.name = ? ");
			ps.setString(1, comune);
			rs = ps.executeQuery();
			res.setWaitingSeg(rs.getInt(1));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return res;
	}
	
	public Comune getComune() {
		return comune;
	}
	public void setComune(Comune comune) {
		this.comune = comune;
	}
	public int getTotalSeg() {
		return totalSeg;
	}
	public void setTotalSeg(int totalSeg) {
		this.totalSeg = totalSeg;
	}
	public int getWaitingSeg() {
		return waitingSeg;
	}
	public void setWaitingSeg(int waitingSeg) {
		this.waitingSeg = waitingSeg;
	}
	
	
}
