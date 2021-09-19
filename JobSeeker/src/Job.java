/*  
Name: Luke Brown
IU Username: brownlk
Date Created: 06/07/2020
Assignment: Final Project
Worked With: Andrew Kim, Sebastian Jagiella
*/

/**
 * Creation of a job
 * @author lukebrown
 *
 */
public class Job implements java.io.Serializable{

	private static final long serialVersionUID = 5020463768459074284L;
	private String title;
	private String locaton;
	private String description;
	private String status;
	
	/**
	 * Constructor
	 * @param titleIn the title of the job
	 * @param locationIn the location of the job
	 * @param descriptionIn the description of the job
	 */
	public Job(String titleIn, String locationIn, String descriptionIn) {
		this.title = titleIn;
		this.locaton = locationIn;
		this.description = descriptionIn;
		this.setStatus("Pending");
	}

	/**
	 * Getter
	 * @return the status of the job
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Setter
	 * @param statusIn the status of the job
	 */
	public void setStatus(String statusIn) {
		this.status = statusIn;
	}

	/**
	 * Getter
	 * @return the title of the job
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter
	 * @param titleIn the title of the job
	 */
	public void setTitle(String titleIn) {
		this.title = titleIn;
	}

	/**
	 * Getter
	 * @return the location of the job
	 */
	public String getLocaton() {
		return locaton;
	}

	/**
	 * Setter
	 * @param locatonIn the location of the job
	 */
	public void setLocaton(String locatonIn) {
		this.locaton = locatonIn;
	}

	/**
	 * Getter
	 * @return the description of the job
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter
	 * @param descriptionIn the description of the job
	 */
	public void setDescription(String descriptionIn) {
		this.description = descriptionIn;
	}
	
	@Override
	public String toString(){
		return "TITLE: " + this.title + "  " + "LOCATION: " + this.locaton + "  " + "DESCRIPTION: " + this.description + "  " + "STATUS: " + this.status;
	}
}
