/*  
Name: Luke Brown
IU Username: brownlk
Date Created: 06/07/2020
Assignment: Final Project
Worked With: Andrew Kim, Sebastian Jagiella
*/


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Creation of a Seeker
 * @author lukebrown
 *
 */
public class Seeker implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String dob;
	private String education;
	private String previousJobs;
	private String skills;
	private String username;
	private String password;
	private ArrayList<Job> appliedJobs;
	
	private File inputFilePersonal;
	
	/**
	 * Constructor
	 * @param userNameIn the username
	 * @param passwordIn the password
	 * @param nameIn the name
	 * @param dobIn the date of birth
	 * @param educationIn the level of education
	 * @param previousJobsIn the previous jobs of the user
	 * @param skillsIn the skills
	 * @throws FileNotFoundException
	 */
	public Seeker(String userNameIn, String passwordIn, String nameIn, String dobIn, String educationIn, String previousJobsIn, String skillsIn) throws FileNotFoundException {
		this.username = userNameIn;
		this.password = passwordIn;
		this.name = nameIn;
		this.dob = dobIn;
		this.education = educationIn;
		this.previousJobs = previousJobsIn;
		this.skills = skillsIn;
		this.appliedJobs = new ArrayList<Job>();
		
		this.inputFilePersonal = new File(this.getUsername());
		PrintWriter outPersonal = new PrintWriter(this.getUsername());
		outPersonal.println(this.getName());
		outPersonal.println(this.getDob());
		outPersonal.println(this.getEducation());
		outPersonal.println(this.getPreviousJobs());
		outPersonal.println(this.getSkills());
		outPersonal.close();
		
	}
	
	//PERSONAL INFO SETTING
	/**
	 * Getter
	 * @return the name of the individual
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter
	 * @param nameIn the name of the individual
	 * @throws FileNotFoundException
	 */
	public void setName(String nameIn) throws FileNotFoundException {
		this.name = nameIn;
		this.updatePersonalFile();
	}

	/**
	 * Getter
	 * @return the date of birth of the individual
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * Setter
	 * @param dobIn the date of birth of the individual
	 * @throws FileNotFoundException
	 */
	public void setDob(String dobIn) throws FileNotFoundException {
		this.dob = dobIn;
		this.updatePersonalFile();
	}

	/**
	 * Getter
	 * @return the level of education
	 */
	public String getEducation() {
		return education;
	}
	
	/**
	 * Setter
	 * @param educationIn the level of education
	 * @throws FileNotFoundException
	 */
	public void setEducation(String educationIn) throws FileNotFoundException {
		this.education = educationIn;
		this.updatePersonalFile();
	}

	/**
	 * Getter
	 * @return the previous jobs of the individual
	 */
	public String getPreviousJobs() {
		return previousJobs;
	}

	/**
	 * Setter
	 * @param previousJobsIn the previous jobs of the individual
	 * @throws FileNotFoundException
	 */
	public void setPreviousJobs(String previousJobsIn) throws FileNotFoundException {
		this.previousJobs = previousJobsIn;
		this.updatePersonalFile();
	}

	/**
	 * Getter
	 * @return the skills of the individual
	 */
	public String getSkills() {
		return skills;
	}

	/**
	 * Setter
	 * @param skillsIn the skills of the indoividual
	 * @throws FileNotFoundException
	 */
	public void setSkills(String skillsIn) throws FileNotFoundException {
		this.skills = skillsIn;
		this.updatePersonalFile();
	}
	
	
	//USERNAME AND PASSWORD
	/**
	 * Getter
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	//MAYBE A POSSIBILITY TO CHANGE??
	/**
	 * Setter
	 * @param username the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	//MAYBE A POSSIBILITY TO CHANGE??
	/**
	 * Setter
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter
	 * @return the user's applied jobs
	 */
	public ArrayList<Job> getAppliedJobs() {
		return appliedJobs;
	}

	/**
	 * Setter
	 * @param appliedJobsIn the user's applied jobs
	 */
	public void setAppliedJobs(ArrayList<Job> appliedJobsIn) {
		this.appliedJobs = appliedJobsIn;
	}

	/**
	 * Updates the user's file (called every time a Setter is called)
	 * @throws FileNotFoundException
	 */
	public void updatePersonalFile() throws FileNotFoundException {
		this.inputFilePersonal = new File(this.getUsername());
		PrintWriter outPersonal = new PrintWriter(this.getUsername());
		outPersonal.println(this.getName());
		outPersonal.println(this.getDob());
		outPersonal.println(this.getEducation());
		outPersonal.println(this.getPreviousJobs());
		outPersonal.println(this.getSkills());
		outPersonal.close();
	}
	
	/**
	 * Getter
	 * @return the personal file
	 */
	public File getPersonalFile() {
		return this.inputFilePersonal;
	}
	
	/**
	 * Deletes the user's file
	 */
	public void deletePersonalFile() {
		this.inputFilePersonal.delete();
	}
	
	@Override
	public String toString() {
		return "Name: " + this.getName() + "  " + "Date of Birith: " + this.getDob() + "  " + "Education: " + this.getEducation() + "  " + "Previous Jobs: " + this.getPreviousJobs() + "  " + "Skills: " + this.getSkills();
	}

}
