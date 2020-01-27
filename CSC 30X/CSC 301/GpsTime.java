package csc301w19Homework;   // 1 point penalty for not restoring the package before turning this in

// CSC 301 W19  HW6
// Fix the toDo items

/*  your name goes here */

import java.time.LocalDate;

public class GpsTime {
	private double longitude;
	private double latitude;
	private LocalDate when;
	
	//ToDo 1     implement the required constructor (see other file)
	public GpsTime( double longitude, double latitude, LocalDate date) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.when = date;
	}
	//ToDo 2      update the hashcode function below
	//            You can try your answer from hw5, or make up a new one
	public int hashCode() {
		int hash = 1;
		
		hash = 31 * ((Double) longitude).hashCode() * 31 * ((Double) latitude).hashCode() * when.hashCode();
		return hash;  // fix this
	}

	//ToDo 3    use the textbook 'recipe' to implement the equals function for this class
	public boolean equals( Object x) {
        if (x == null) return false;
        GpsTime that = (GpsTime) x;
        return (this.longitude == that.longitude) && (this.latitude == that.latitude) && (this.when.equals(that.when));

	}
	public String toString() {
		return  Double.toString(longitude)+ " "+Double.toString(latitude)+" "+when.toString();
	}
}
