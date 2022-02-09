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
import java.util.Map;

/**
 * To create an emoloyer
 * @author lukebrown
 *
 */
public class Employer implements java.io.Serializable{
	
	private static final long serialVersionUID = 5318644145798707307L;
	private String username;
	private String password;
	private String companyName;
	private String description;
	private Job post;
	private ArrayList<Job> jobList;
	
	private File employerFile;
	
	/**
	 * Constructor
	 * @param usernameIn the user's username
	 * @param passwordIn the user's password
	 * @param companyNameIn the user's company
	 * @param descriptionIn the user's description
	 * @throws FileNotFoundException
	 */
	public Employer(String usernameIn, String passwordIn, String companyNameIn, String descriptionIn) throws FileNotFoundException {
		this.jobList = new ArrayList<Job>();
		this.username = usernameIn;
		this.password = passwordIn;
		this.companyName = companyNameIn;
		this.description = descriptionIn;
		
		this.employerFile = new File(this.getUsername());
		PrintWriter companyInfoOut =  new PrintWriter(this.getUsername());
		companyInfoOut.println(this.getCompanyName());
		companyInfoOut.println(this.getDescription());
		companyInfoOut.println(this.getJobsPosted());
		companyInfoOut.close();
		
	}

	/**
	 * Getter
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter
	 * @param usernameIn the username
	 * @throws FileNotFoundException
	 */
	public void setUsername(String usernameIn) throws FileNotFoundException {
		this.username = usernameIn;
		this.updateFile();
	}

	/**
	 * Getter
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter
	 * @param passwordIn the password
	 * @throws FileNotFoundException
	 */
	public void setPassword(String passwordIn) throws FileNotFoundException {
		this.password = passwordIn;
		this.updateFile();
	}

	
	// TO UPDATE PROFILE
	/**
	 * Getter
	 * @return the company's name
	 */
	public String getCompanyName() {
		return companyName;
	}
	
	/**
	 * Setter
	 * @param companyNameIn the company's name
	 * @throws FileNotFoundException
	 */
	public void setCompanyName(String companyNameIn) throws FileNotFoundException {
		this.companyName = companyNameIn;
		this.updateFile();
	}

	/**
	 * Getter
	 * @return the company's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter
	 * @param descriptionIn the company's description
	 * @throws FileNotFoundException
	 */
	public void setDescription(String descriptionIn) throws FileNotFoundException {
		this.description = descriptionIn;
		this.updateFile();
	}

	
	//JOB CREATION
	/**
	 * Creation of a new job tied to the company
	 * @param titleIn the title of the job
	 * @param locationIn the location of the job
	 * @param descriptionIn the description of the job
	 * @throws FileNotFoundException
	 */
	public void setJob(String titleIn, String locationIn, String descriptionIn) throws FileNotFoundException {
		this.post = new Job(titleIn, locationIn, descriptionIn);
		this.jobList.add(this.post);
		this.updateFile();
	}
	
	/**
	 * Getter
	 * @return the job itself
	 */
	public Job getJob() {
		return this.post;
	}
	
	/**
	 * An ArrayList of all the jobs the company has posted
	 * @return
	 */
	public ArrayList<Job> getJobsPosted(){
		return this.jobList;
	}
	
	
	
	//SAVING / REMOVING THE DATA
	/**
	 * Method to update the user's file (automatically called when a Setter is called)
	 * @throws FileNotFoundException
	 */
	public void updateFile() throws FileNotFoundException {
		//maybe have to delete old one first (maybe it will just override since they're the same name)
		this.employerFile = new File(this.getUsername());
		PrintWriter outInfo = new PrintWriter(this.getUsername());
		outInfo.println(this.getCompanyName());
		outInfo.println(this.getDescription());
		outInfo.println(this.getJobsPosted());
		outInfo.close();
	}
	
	/**
	 * Method to delete file
	 */
	public void deleteEmployerFile() {
		this.employerFile.delete();
	}

}