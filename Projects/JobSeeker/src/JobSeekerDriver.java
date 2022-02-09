/*  
Name: Luke Brown
IU Username: brownlk
Date Created: 06/07/2020
Assignment: Final Project
Worked With: Andrew Kim, Sebastian Jagiella
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * To run the JobSeeker program!
 * @author lukebrown, andrekim, sebastianjagiella
 *
 */
public class JobSeekerDriver {
	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
		
		
		Scanner sc = new Scanner(System.in);									 //for user input
		
		boolean valid = true;
		
		//use "/terminate" in the username login page to terminate properly
		while(valid) {
			//USERNAME AND PASSWORD MANAGEMENT
			
			Map <String, String> masterUsernameAndPassword = new HashMap<String, String>(); // to hold ALL usernames and passwords so there are not duplicate accounts (i.e. one for a Seeker and one for an Employer)
			Map<String, String> seekerUsernameAndPassword = new HashMap<String, String>(); //to hold all usernames FOR SEEKER and their corresponding passwords
			Map<String, String> employerUsernameAndPassword = new HashMap<String, String>(); //to hold all usernames FOR EMPLOYER and their corresponding passwords
			String allLoginInfoString = "allLoginInfo.ser";					     //to save ALL usernames and passwords for later access
			File allLoginInfoFile = new File(allLoginInfoString);					 //the file itself for all
			
			if (allLoginInfoFile.exists()) {	//checks to make sure the file exists so an error won't result
				FileInputStream allUsernameAndPasswordInput = new FileInputStream(allLoginInfoFile); 
				ObjectInputStream allUsernameAndPasswordObject = new ObjectInputStream(allUsernameAndPasswordInput); //Deserialization
				masterUsernameAndPassword = (HashMap<String, String>)allUsernameAndPasswordObject.readObject(); //assinging the previously saved Map to the new Map variable
				allUsernameAndPasswordInput.close();
				allUsernameAndPasswordObject.close();
			}

			String seekerLoginInfoString = "seekerLoginInfo.ser";					 //to save the info to a .txt file for storage
			File seekerLoginInfoFile = new File(seekerLoginInfoString);				 //the file itself for the seeker
			if (seekerLoginInfoFile.exists()) {
				FileInputStream seekerUsernameAndPasswordInput = new FileInputStream(seekerLoginInfoFile);
				ObjectInputStream seekerUsernameAndPasswordObject = new ObjectInputStream(seekerUsernameAndPasswordInput);
				seekerUsernameAndPassword = (HashMap<String, String>)seekerUsernameAndPasswordObject.readObject();
				seekerUsernameAndPasswordInput.close();
				seekerUsernameAndPasswordObject.close();
			}

			
			String employerLoginInfoString = "employerLoginInfo.ser";				 //the name of the file for the employer's usernames and passwords
			File employerLoginInfoFile = new File(employerLoginInfoString);			 //the file itself for the employer
			if (employerLoginInfoFile.exists()) {
				FileInputStream employerUsernameAndPasswordInput = new FileInputStream(employerLoginInfoFile);
				ObjectInputStream employerUsernameAndPasswordObject = new ObjectInputStream(employerUsernameAndPasswordInput);
				employerUsernameAndPassword = (HashMap<String, String>)employerUsernameAndPasswordObject.readObject();
				employerUsernameAndPasswordInput.close();
				employerUsernameAndPasswordObject.close();
			}
			
			
		
			//DATABASE MANAGEMENT
			ArrayList<Seeker> allSeekers = new ArrayList<Seeker>();					 //ArrayList for all seekers made
			String allSeekersString = "allSeekers.ser";								 //name of the file for all seekers (to save)
			File allSeekersFile = new File(allSeekersString);						 //file itself
			if (allSeekersFile.exists()) {
				FileInputStream allSeekersInput = new FileInputStream(allSeekersFile);
				ObjectInputStream allSeekersObject = new ObjectInputStream(allSeekersInput);
				allSeekers = (ArrayList<Seeker>)allSeekersObject.readObject();
				allSeekersInput.close();
				allSeekersObject.close();
			}
			
			ArrayList<Employer> allEmployers = new ArrayList<Employer>();			 //ArrayList for all employers made
			String allEmployersString = "allEmployers.ser";							 //name of the file for all employers (to save)
			File allEmployersFile = new File(allEmployersString);					 //file itself
			if (allEmployersFile.exists()) {
				FileInputStream allEmployersInput = new FileInputStream(allEmployersFile);
				ObjectInputStream allEmployersObject = new ObjectInputStream(allEmployersInput);
				allEmployers = (ArrayList<Employer>)allEmployersObject.readObject();
				allEmployersInput.close();
				allEmployersObject.close();
			}
				
			ArrayList<Job> allJobs = new ArrayList<Job>();							//ArrayList for all jobs made
			for (int z = 0; z < allEmployers.size(); z++) {
				Employer employerInstance = allEmployers.get(z);
				ArrayList<Job> employersJobs = employerInstance.getJobsPosted();
				for(int a = 0; a < employersJobs.size(); a++) {
					Job jobInstance = employersJobs.get(a);
					allJobs.add(jobInstance);
				}
			}
			
			
			Map<String, ArrayList<Job>> jobMap = new HashMap<String, ArrayList<Job>>();			//Map tying the Company to the Job
			for(int v = 0; v < allEmployers.size(); v++) {
				Employer employerInstance = allEmployers.get(v);
				String employerInstanceName = employerInstance.getCompanyName();
				ArrayList<Job> thisCompanysJobs = employerInstance.getJobsPosted();
				jobMap.put(employerInstanceName, thisCompanysJobs);
			}
			
			Map<Seeker, ArrayList<Job>> appliedMap = new HashMap<Seeker, ArrayList<Job>>();		//Map tying the Seeker to the Job for applications
			for(int v = 0; v < allSeekers.size(); v++) {
				Seeker seekerInstance = allSeekers.get(v);
				ArrayList<Job> thisSeekersApplications = seekerInstance.getAppliedJobs();
				appliedMap.put(seekerInstance, thisSeekersApplications);
			}
			
			//STARTING SCREEN
			System.out.println("Welcome to JobSeeker!");
			System.out.print("Please provide your username (for newcomers and the returning users)!   ");
			String username = sc.next();
			//RESTRICTIONS ON INPUT
			if (username.contains(" ") || username.equals("r")) { //maybe other restrictions as well
				TimeUnit.MILLISECONDS.sleep(300);
				System.out.print("Invalid Username... Please make sure that the username contains no spaces AND/OR isn't our designated return key, " + "\"r\"");
				TimeUnit.MILLISECONDS.sleep(3000);
				System.out.println();
				TimeUnit.MILLISECONDS.sleep(250);
				System.out.println();
			}
			
			else if(username.equals("/terminate")) { //terminates the program
				return;
			}
			
			//VAILD USERNAME
			else {
				//TODO PASSWORD BEING "r"
				if(masterUsernameAndPassword.containsKey(username)) { 					//if the username exists then proceed to password
					
					
					
					//SEEKER SPECIFIC (PREVIOUS USER)
					if (seekerUsernameAndPassword.containsKey(username)){
						System.out.print("Please provide your password:   ");
						String blankTime = sc.nextLine();
						String password = sc.nextLine();
						String actualPassword = masterUsernameAndPassword.get(username);
						int passwordCntr = 0;											//counter for the number of times a password was entered
						while(!(actualPassword.equals(password) || passwordCntr > 2)) { //if the password was entered correctly or the password was entered 3 times inccorectly it spits back out
							if (passwordCntr == 2) {									//if the password was entered incorrectly three times
								System.out.println("Three incorrect password attempts... returning to the menu");
								System.out.println();
								passwordCntr++;
							}
							else{														//keep trying
								System.out.print("Invalid password, please try again   ");
								password = sc.next();
								passwordCntr++;
							}
						}
						if (actualPassword.equals(password)) {							//if the password is correct go into the account
							//SEND THE USER IN
							//TODO get the file from the username
							System.out.print("Loading");
							int dots = 0;							//"loading" maybe make a method out of this??
							while(dots < 3) {
								TimeUnit.MILLISECONDS.sleep(1000);
								System.out.print(".");
								dots++;
							}
							
							for(int i = 0; i < allSeekers.size(); i++) {
								Seeker person = allSeekers.get(i);
								String thisUsername = person.getUsername();		//get the person from the Seeker list based upon the username
								if (username.equals(thisUsername)) {
									username = thisUsername;
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(500);
									String name = person.getName();
									int j = name.indexOf(' ');							//checks for a space between first and last name		
									if (name.contains(" ")) {							//gets firstname
										String firstName = name.substring(0, j);
										System.out.println();
										System.out.println("Welcome " + firstName + "!");
										System.out.println();
									}
									else {												//only one String was typed in; therefore that's the name
										System.out.println();
										System.out.println("Welcome " + person.getName() + "!");
										System.out.println();
									}

									//SEEKER MENU
									boolean seekerMenu = true;
									while(seekerMenu) {
										TimeUnit.MILLISECONDS.sleep(350);
										System.out.println("---------------------------------");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("MENU:");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("To select an option please type in the appropriate number");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("1) View my profile");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("2) Update profile");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("3) Browse through open jobs");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("4) Track status of applied jobs");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("5) Sign out");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("6) Delete account");
										System.out.println();
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.print("User's Choice:" + "   ");

										String choice = sc.next();
										
										//OPTION 1
										if (choice.equals("1")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User selected 1");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Fetching profile");
											int dotsAgain = 0;							//"loading" maybe make a method out of this??
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											System.out.println();
											System.out.println();
											
											//DISPLAY SEEKER PROFILE
											boolean seekerProfile = true;
											while(seekerProfile) {
												System.out.println("PROFILE:");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Name: " + person.getName());
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Date of Birth: " + person.getDob());
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Education: " + person.getEducation());
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Previous Jobs: " + person.getPreviousJobs());
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Skills: " + person.getSkills());
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println();
												System.out.print("Press " + "\"r\"" + " to return to the MENU:" + "   ");		//r returns to the menu
												String userInput_2 = sc.next(); 
												System.out.println();
												if (userInput_2.equals("r")) {
													seekerProfile = false;
												}
												else {
													System.out.println("Invalid Input");
													System.out.println();
												}
											}
										}
										
										//UPDATING INFO
										else if (choice.equals("2")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User selceted 2");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											boolean update = true;
											while(update) {
												System.out.println("What do you wish to update?");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("1) Name");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("2) Date of Bith");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("3) Education");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("4) Previous Jobs");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("5) Skills");
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Press " + "\"r\"" + " to return to the MENU:" + "   ");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.print("User's Choice:" + "   ");
												String userInput_3 = sc.next();
												System.out.println();
												
												//CHANGE NAME
												if (userInput_3.equals("1")) {
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("User Selected 1");
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("What do you wish to change your name to?" + "   ");
													String blank = sc.nextLine();
													String newName = sc.nextLine();
													person.setName(newName);
													System.out.print("Updating");
													int dotsAgain = 0;
													while(dotsAgain < 3) {
														TimeUnit.MILLISECONDS.sleep(1000);
														System.out.print(".");
														dotsAgain++;
													}
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println();
													System.out.println();
													System.out.println("Name Updated!");
													System.out.println();
												}
												
												//CHANGE DATE OF BIRTH
												else if(userInput_3.equals("2")) {
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("User Selected 2");
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("What do you wish to change your date of birth to?" + "   ");
													String blank = sc.nextLine();
													String newDob = sc.nextLine();
													person.setDob(newDob);
													System.out.print("Updating");
													int dotsAgain = 0;
													while(dotsAgain < 3) {
														TimeUnit.MILLISECONDS.sleep(1000);
														System.out.print(".");
														dotsAgain++;
													}
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println();
													System.out.println();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("Date of Birth Updated!");
													System.out.println();
												}
												
												//CHANGE EDUCATION
												else if (userInput_3.equals("3")) {
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("User Selected 3");
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("What do you wish to change your education to?" + "   ");
													String blank = sc.nextLine();
													String newEdu = sc.nextLine();
													person.setEducation(newEdu);
													System.out.print("Updating");
													int dotsAgain = 0;
													while(dotsAgain < 3) {
														TimeUnit.MILLISECONDS.sleep(1000);
														System.out.print(".");
														dotsAgain++;
													}
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println();
													System.out.println();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("Education Updated!");
													System.out.println();
												}
												
												//CHANGE PREVIOUS JOBS
												else if(userInput_3.equals("4")) {
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("User Selected 4");
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("What do you wish to change your previous jobs to?" + "   ");
													String blank = sc.nextLine();
													String newPreviousJobs = sc.nextLine();
													person.setPreviousJobs(newPreviousJobs);
													System.out.print("Updating");
													int dotsAgain = 0;
													while(dotsAgain < 3) {
														TimeUnit.MILLISECONDS.sleep(1000);
														System.out.print(".");
														dotsAgain++;
													}
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println();
													System.out.println();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("Previous Jobs Updated!");
													System.out.println();
												}
												
												//CHANGE SKILLS
												else if (userInput_3.equals("5")) {
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("User Selected 5");
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("What do you wish to change your skills to?" + "   ");
													String blank = sc.nextLine();
													String newSkills = sc.nextLine();
													person.setSkills(newSkills);
													System.out.print("Updating");
													int dotsAgain = 0;
													while(dotsAgain < 3) {
														TimeUnit.MILLISECONDS.sleep(1000);
														System.out.print(".");
														dotsAgain++;
													}
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println();
													System.out.println();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("Skills Updated!");
													System.out.println();
												}
												
												//GO BACK
												else if (userInput_3.equals("r")) {
													System.out.print("Saving Changes");
													int dotsAgain = 0;
													while(dotsAgain < 3) {
														TimeUnit.MILLISECONDS.sleep(1000);
														System.out.print(".");
														dotsAgain++;
													}
													//UPDATE FILE
													allSeekers.remove(person);
													allSeekers.add(person);
													allSeekersFile = new File(allSeekersString);
													FileOutputStream seekerListOutFileUpdate = new FileOutputStream(allSeekersString);
													ObjectOutputStream seekerListOutUpdate = new ObjectOutputStream(seekerListOutFileUpdate);
													seekerListOutUpdate.writeObject(allSeekers);
													seekerListOutUpdate.flush();
													seekerListOutUpdate.close();
													update = false;
													System.out.println();
													System.out.println();
												}
												else {
													System.out.println("Invalid Input");
													System.out.println();
												}
											}
										}
										
										//BROWSE THROUGH OPEN JOBS
										else if (choice.equals("3")) {
											ArrayList<Job> appliedJobs = person.getAppliedJobs();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Retrieving all jobs");
											int dotsAgain = 0;										// creates a ... (artificial loading)
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											System.out.println();
											boolean retrievingJobs = true;
											System.out.println();
											for (Map.Entry<String, ArrayList<Job>> mapToSet : jobMap.entrySet()){
												ArrayList<Job> companysJobs = mapToSet.getValue();
												System.out.println("Company: " + mapToSet.getKey());
												for(int l = 0; l < companysJobs.size(); l++) {
													Job instanceJob = companysJobs.get(l);
													System.out.println(instanceJob);
												}
												System.out.println();
											}
											String blankAgain = sc.nextLine();
											while(retrievingJobs) {
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("1) Would you like to search based upon a desired title?");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("2) Would you like to search based upon a desired location?");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("3) Would you like to apply to any of these postions?");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.print("Please enter the desired number OR " + "\"r\"" + " to go back to the MENU screen:" +  "   ");
												String seekerInput_1 = sc.nextLine();
												if (seekerInput_1.equals("1")) {
													System.out.print("Please enter the title you would like to search for:" + "   ");
													String titleSearcher = sc.nextLine();
													System.out.println();
													int foundCntr = 0;
													int checkCntr = 0;
													for(int a = 0; a < allJobs.size(); a++) {
														Job instance = allJobs.get(a);
														String jobTitle = instance.getTitle();
														checkCntr++;
														if (titleSearcher.equals(jobTitle) || jobTitle.contains(titleSearcher)) {
															System.out.println(instance);
															foundCntr++;
														}
														else if(foundCntr == 0 && checkCntr == allJobs.size()){
															System.out.println("None exist with title " +  "\"" + titleSearcher + "\"");
														}
													}
													
												}
												else if(seekerInput_1.equals("2")) {
													System.out.print("Please enter the location you would like to search for:" + "   ");
													String locationSearcher = sc.nextLine();
													System.out.println();
													int foundCntr = 0;
													int checkCntr = 0;
													for(int a = 0; a < allJobs.size(); a++) {
														Job instance = allJobs.get(a);
														String jobLocation = instance.getLocaton();
														checkCntr++;
														if (locationSearcher.equals(jobLocation) || jobLocation.contains(locationSearcher)) {
															System.out.println(instance);
															foundCntr++;
														}
														else if(foundCntr == 0 && checkCntr == allJobs.size()){
															System.out.println("None exist with location " +  "\"" + locationSearcher + "\"");
														}
													}
												}
												
												else if(seekerInput_1.equals("3")) {
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("Please enter the name of the company of which the position belongs to:" + "   ");
													String wantedCompany = sc.nextLine();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("Please enter the title of the position:" + "   ");
													String wantedPosition = sc.nextLine();
													ArrayList<Job> jobsForCompany = jobMap.get(wantedCompany);
													int foundCntr = 0;
													for(int u = 0; u < jobsForCompany.size(); u++) {
														Job jobInstance = jobsForCompany.get(u);
														String actualTitle = jobInstance.getTitle();
														if(wantedPosition.equals(actualTitle)) {
															foundCntr = 1;
															appliedJobs.add(jobInstance);
															person.setAppliedJobs(appliedJobs);
															allSeekers.remove(person);
															allSeekers.add(person);
															allSeekersFile = new File(allSeekersString);
															FileOutputStream seekerListOutFileUpdate = new FileOutputStream(allSeekersString);
															ObjectOutputStream seekerListOutUpdate = new ObjectOutputStream(seekerListOutFileUpdate);
															seekerListOutUpdate.writeObject(allSeekers);
															seekerListOutUpdate.flush();
															seekerListOutUpdate.close();
															
															System.out.println("Job " +  "\"" + actualTitle + "\"" + " added to applied jobs!");
														}
														else if ((!(wantedPosition.equals(actualTitle))) && foundCntr == 0){
															System.out.println("Couldn't find that title");
														}
													}
												}
												
												else if(seekerInput_1.equals("r")) {
													System.out.println();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("Returning to MENU");
													retrievingJobs = false;
												}
												
												else {
													System.out.println();
													for (Map.Entry<String, ArrayList<Job>> mapToSet : jobMap.entrySet()){
														ArrayList<Job> companysJobs = mapToSet.getValue();
														System.out.println("Company: " + mapToSet.getKey());
														for(int l = 0; l < companysJobs.size(); l++) {
															Job instanceJob = companysJobs.get(l);
															System.out.println(instanceJob);
														}
														System.out.println();
													}
												}
											}
											
											System.out.println();
										
										}
										
										//TRACK STATUS OF APPLIED JOBS
										else if(choice.equals("4")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User selceted 4");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Retrieving Jobs");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											System.out.println();
											System.out.println();
											ArrayList<Job> appliedJobs = person.getAppliedJobs();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("APPLIED JOBS:");
											if(appliedJobs != null) {
												for (int l = 0; l < appliedJobs.size(); l++) {
													System.out.println(appliedJobs.get(l));
												}
												System.out.println();
											}
											else {
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Have yet to apply :(, select option 3 from the MENU to get started!");
												System.out.println();
											}
										}
										
										//SIGNING OUT
										else if (choice.equals("5")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User selceted 5");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Signing Out");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											name = person.getName();
											i = name.indexOf(' ');
											if (name.contains(" ")) {							//gets firstname
												String firstName = name.substring(0, j);
												System.out.println();
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Goodbye " + firstName + "!");
												System.out.println();
												System.out.println();
												
											}
											else {												//only one String was typed in; therefore that's the name
												System.out.println();
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Goodbye " + person.getName() + "!");
												System.out.println();
												System.out.println();
											}
											seekerMenu = false;	//return back to start
										}
									
										//DELETION OF ACCOUNT AND ALL THINGS TIED TO IT 
										//TODO MAKE SURE EVERYTHING TIED TO IT IS DELETED OR UPDATED ALSO CHECK THE 'R' PROBLEM ALSO MAYBE USE SOME WHILE LOOPS
										else if (choice.equals("6")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User selceted 6");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											
											//CHANGE ORDER OF MESSAGE??
											System.out.print("We're sad to see you go ");
											TimeUnit.MILLISECONDS.sleep(750);
											System.out.print(":");
											TimeUnit.MILLISECONDS.sleep(750);
											System.out.print("'");
											TimeUnit.MILLISECONDS.sleep(750);
											System.out.print("(");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(750);
											
											
											System.out.print("Please enter your username OR Press " + "\"r\"" +  " to return to the MENU:" + "   ");
											TimeUnit.MILLISECONDS.sleep(250);
											String userNameForDeletion = sc.next();
											if(userNameForDeletion.equals(person.getUsername())) { 					//if the username exists then proceed to password
												System.out.print("Please provide your password:   ");
												String passwordForDeletion = sc.next();
												String actualPasswordForDeletion = person.getPassword();
												int passwordCntrAgain = 0;											//counter for the number of times a password was entered
												while(!(actualPasswordForDeletion.equals(passwordForDeletion) || passwordCntrAgain > 2)) { //if the password was entered correctly or the password was entered 3 times inccorectly it spits back out
													if (passwordCntrAgain == 2) {									//if the password was entered incorrectly three times
														System.out.println("Three incorrect password attempts... returning to the menu");
														System.out.println();
														passwordCntrAgain++;
													}
													else{														//keep trying
														System.out.print("Invalid password, please try again   ");
														passwordForDeletion = sc.next();
														passwordCntrAgain++;
													}
												}
												if (actualPasswordForDeletion.equals(passwordForDeletion)) {							//if the password is correct go into the account
													
													System.out.println("Are you sure you wish to delete your profile?");
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("Please enter " + "\"y\"" +" to confirm" + " or " + "\"n\"" + " to go back to the MENU screen:" + "	");
													
													String userInput_4 = sc.next();
													
													if(userInput_4.contentEquals("y")) {
														person.deletePersonalFile();
														
														System.out.print("Deleting Account");
														int dotsAgain = 0;
														while(dotsAgain < 3) {
															TimeUnit.MILLISECONDS.sleep(1000);
															System.out.print(".");
															dotsAgain++;
														}
																	
														appliedMap.remove(person);
														
														allSeekers.remove(person);									//delete this person from the ArrayList
														allSeekersFile = new File(allSeekersString);				//new file w/o person
														FileOutputStream seekerListOutFileForDeletion = new FileOutputStream(allSeekersString);
														ObjectOutputStream seekerListOutForDeletion = new ObjectOutputStream(seekerListOutFileForDeletion);
														seekerListOutForDeletion.writeObject(allSeekers);
														seekerListOutForDeletion.flush();
														seekerListOutForDeletion.close();
														
														
														masterUsernameAndPassword.remove(username, password);	//remove this person's username and associated password from the master Map and as a result the file
														seekerUsernameAndPassword.remove(username, password);
														allLoginInfoFile = new File(allLoginInfoString);
														seekerLoginInfoFile = new File(seekerLoginInfoString);
														FileOutputStream masterOutFileForDeletion =  new FileOutputStream(allLoginInfoString);
														ObjectOutputStream masterOutMapForDeletion = new ObjectOutputStream(masterOutFileForDeletion);
														masterOutMapForDeletion.writeObject(masterUsernameAndPassword);
														masterOutMapForDeletion.flush();
														masterOutMapForDeletion.close();
														masterOutFileForDeletion.close();
														
														FileOutputStream seekerOutFileForDeletion =  new FileOutputStream(seekerLoginInfoString);	//remove this person's username and associated password from the seeker Map and as a result the file
														ObjectOutputStream seekerOutMapForDeletion = new ObjectOutputStream(seekerOutFileForDeletion);
														seekerOutMapForDeletion.writeObject(seekerUsernameAndPassword);
														seekerOutMapForDeletion.flush();
														seekerOutMapForDeletion.close();
														seekerOutFileForDeletion.close();
														
														
														if (name.contains(" ")) {							//gets firstname
															String firstName = name.substring(0, j);
															System.out.println();
															System.out.println();
															TimeUnit.MILLISECONDS.sleep(250);
															System.out.print("Goodbye " + firstName);
															TimeUnit.MILLISECONDS.sleep(1250);
															System.out.print(" FOREVER!");
															TimeUnit.MILLISECONDS.sleep(1000);
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															
														}
														else {												//only one String was typed in; therefore that's the name
															System.out.println();
															System.out.println();
															TimeUnit.MILLISECONDS.sleep(250);
															System.out.print("Goodbye " + person.getName());
															TimeUnit.MILLISECONDS.sleep(1250);
															System.out.print(" FOREVER!");
															TimeUnit.MILLISECONDS.sleep(1000);
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
															System.out.println();
														}
														seekerMenu = false;
													}
													
													else if(userInput_4.equals("n")) {
														TimeUnit.MILLISECONDS.sleep(250);
														System.out.println("Returning to MENU");
														System.out.println();
														TimeUnit.MILLISECONDS.sleep(250);
													}
													
													else {
														System.out.println("Invalid Input...");
														System.out.println();
													}
													
												}
											}
											
											//GO BACK
											else if (userNameForDeletion.equals("r")) {
												System.out.println();
											}
											
											else {
												System.out.println("Invalid Input...");
												System.out.println();
											}
										}
										else {
											System.out.println("Invalid Choice...");
											System.out.println();
										}
									}
								}
							}
						}
						
					}
					
					//TODO EMPLOYER SPECFIC
					else {
						System.out.print("Please provide your password:   ");
						String password = sc.next();
						String actualPassword = masterUsernameAndPassword.get(username);
						int passwordCntr = 0;											//counter for the number of times a password was entered
						while(!(actualPassword.equals(password) || passwordCntr > 2)) { //if the password was entered correctly or the password was entered 3 times inccorectly it spits back out
							if (passwordCntr == 2) {									//if the password was entered incorrectly three times
								System.out.println("Three incorrect password attempts... returning to the menu");
								System.out.println();
								passwordCntr++;
							}
							else{														//keep trying
								System.out.print("Invalid password, please try again   ");
								password = sc.next();
								passwordCntr++;
							}
						}
						if (actualPassword.equals(password)) {							//if the password is correct go into the account
							//SEND THE USER IN
							//TODO get it from the ArrayList
							System.out.print("Loading");
							int dots = 0;							//"loading" maybe make a method out of this??
							while(dots < 3) {
								TimeUnit.MILLISECONDS.sleep(1000);
								System.out.print(".");
								dots++;
							}
							System.out.println();
							for(int i = 0; i < allEmployers.size(); i++) {
								Employer company = allEmployers.get(i);
								String thisUsername = company.getUsername();
								if(username.equals(thisUsername)) {
									username = thisUsername;
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(500);
									String name = company.getCompanyName();
									System.out.println("Welcome " + name + "!");
									System.out.println();
									boolean employerMenu = true;
									while(employerMenu) {
										TimeUnit.MILLISECONDS.sleep(350);
										System.out.println("---------------------------------");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("MENU:");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("To select an option please type in the appropriate number");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("1) View my company's profile");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("2) Update my company's profile");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("3) Create a new job post");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("4) View and or update the list of jobs posted");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("5) Browse through all Seekers");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("6) Sign out");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("7) Delete account");
										System.out.println();
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.print("User's Choice:" + "   ");

										String choice = sc.next();
										
										//DISPLAY PROFILE
										if (choice.equals("1")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User selceted 1");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Fetching profile");
											int dotsAgain = 0;							//"loading" maybe make a method out of this??
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											System.out.println();
											System.out.println();
											
											//DISPLAY SEEKER PROFILE
											boolean seekerProfile = true;
											while(seekerProfile) {
												System.out.println("PROFILE:");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Company Name: " + company.getCompanyName());
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Description: " + company.getDescription());
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println();
												System.out.print("Press " + "\"r\"" + " to return to the MENU:" + "   ");		//r returns to the menu
												String userInput_2 = sc.next(); 
												System.out.println();
												if (userInput_2.equals("r")) {
													seekerProfile = false;
												}
												else {
													System.out.println("Invalid Input");
													System.out.println();
												}
											}
										}
										
										//UPDATE PROFILE
										else if(choice.equals("2")){
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User selceted 2");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											boolean update = true;
											while(update) {
												System.out.println("What do you wish to update?");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("1) Company Name");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("2) Company Description");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Press " + "\"r\"" + " to return to the MENU:" + "   ");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.print("User's Choice:" + "   ");
												String userInput_3 = sc.next();
												System.out.println();
												
												//CHANGE NAME
												if (userInput_3.equals("1")) {
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("User Selected 1");
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("What do you wish to change the name to?" + "   ");
													String blank = sc.nextLine();
													String newName = sc.nextLine();
													company.setCompanyName(newName);
													System.out.print("Updating");
													int dotsAgain = 0;
													while(dotsAgain < 3) {
														TimeUnit.MILLISECONDS.sleep(1000);
														System.out.print(".");
														dotsAgain++;
													}
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println();
													System.out.println();
													System.out.println("Name Updated!");
													System.out.println();
												}
												
												//CHANGE DESCRIPTION
												else if(userInput_3.equals("2")) {
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("User Selected 2");
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("What do you wish to change the description to?" + "   ");
													String blank = sc.nextLine();
													String newDescription = sc.nextLine();
													company.setDescription(newDescription);
													System.out.print("Updating");
													int dotsAgain = 0;
													while(dotsAgain < 3) {
														TimeUnit.MILLISECONDS.sleep(1000);
														System.out.print(".");
														dotsAgain++;
													}
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println();
													System.out.println();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("Description Updated!");
													System.out.println();
												}
												else if (userInput_3.equals("r")) {
													allEmployers.remove(company);
													allEmployers.add(company);
													allEmployersFile = new File(allEmployersString);
													FileOutputStream employerListOutFileUpdated = new FileOutputStream(allEmployersString);
													ObjectOutputStream employerListOutUpdated = new ObjectOutputStream(employerListOutFileUpdated);
													employerListOutUpdated.writeObject(allEmployers);
													employerListOutUpdated.flush();
													employerListOutUpdated.close();
													update = false;
													System.out.println();
												}
												else {
													System.out.println("Invalid Input");
													System.out.println();
												}
											}
										}
										
										//CREATE A NEW JOB
										else if (choice.equals("3")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User Selected 3");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("Couple of quick questions concerning the new job!");
											System.out.println();
											String anotherBlank = sc.nextLine();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("What is the title of the position?" + "   ");
											String titleIn = sc.nextLine();
											System.out.print("Where is the location of the position?" + "   ");
											String locationIn = sc.nextLine();
											System.out.print("What is the job description?" + "   ");
											String jobDescriptionIn = sc.nextLine();

											System.out.print("Generating new job, please wait");
											
											int dotsAgain = 0;										// creates a ... (artificial loading)
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											
											company.setJob(titleIn, locationIn, jobDescriptionIn);
											Job position = company.getJob();
											
											allEmployers.remove(company);
											allEmployers.add(company);
											allEmployersFile = new File(allEmployersString);
											FileOutputStream employerListOutFileUpdated = new FileOutputStream(allEmployersString);
											ObjectOutputStream employerListOutUpdated = new ObjectOutputStream(employerListOutFileUpdated);
											employerListOutUpdated.writeObject(allEmployers);
											employerListOutUpdated.flush();
											employerListOutUpdated.close();
											System.out.println();
											
											
											System.out.println();
											System.out.println();
											System.out.println("Job " + "\"" + position.getTitle() + "\"" + " has been posted!");
											System.out.println();


										}
										
										//VIEW LIST OF JOBS POSTED
										else if (choice.equals("4")) {
											boolean update = true;
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User Selected: 4");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Retrieving Jobs Posted");

											int dotsAgain = 0;										// creates a ... (artificial loading)
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											System.out.println();
											while (update) {
												System.out.println();
												ArrayList<Job> jobs = company.getJobsPosted();
												for(int j = 0; j < jobs.size(); j++) {
													int number = j+1;
													TimeUnit.MILLISECONDS.sleep(250);
													Job job = jobs.get(j);
													String thisTitle = job.getTitle();
													String thisLocation = job.getLocaton();
													System.out.println("JOB: " + number + " " + jobs.get(j));
													int cntr = 1;
													for (Map.Entry<Seeker, ArrayList<Job>> mapToSet : appliedMap.entrySet()){
														ArrayList<Job> seekersApps = mapToSet.getValue();
														for(int l = 0; l < seekersApps.size(); l++) {
															Job instanceJob = seekersApps.get(l);
															String thatTitle = instanceJob.getTitle();
															String thatLocation = instanceJob.getLocaton();
															if(cntr == 1) {
																System.out.println("INTERESTED SEEKERS:");
																cntr++;
															}
															if(thisTitle.equals(thatTitle) && thisLocation.equals(thatLocation)) {
																System.out.println(mapToSet.getKey());
															}
														}
													}
													System.out.println();
												}
												
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Would you like to update any of these job postings?");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.print("Please enter " + "\"y\"" +" to confirm" + " or " + "\"n\"" + " to go back to the MENU screen:" +  "   ");
												String employerInput = sc.next();
												if(employerInput.equals("y")) {
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("Please enter the number of the job post that you would like to alter:" + "   "); //TODO else incorrect type input (String)
													String numberChoice = sc.next();
													int numberChoiceInt = Integer.parseInt(numberChoice);
													int actualNumber = numberChoiceInt - 1;
													Job toChange = null;
													
													if(actualNumber <= jobs.size()) {
														for (int p = 0; p < jobs.size(); p++) {
															toChange = jobs.get(actualNumber);
														}
													}
													
													System.out.print("Please enter the attribute you wish to alter:" + "    ");
													String newChange = sc.next();
													String newChangeUpper = newChange.toUpperCase();
													if(newChangeUpper.equals("TITLE")) {
														String blankAgain = sc.nextLine();
														System.out.print("What would you like to change the title to?" + "    ");
														String newTitle = sc.nextLine();
														toChange.setTitle(newTitle);
													}
													else if(newChangeUpper.equals("LOCATION")) {
														String blankAgain = sc.nextLine();
														System.out.print("What would you like to change the location to?" + "   ");
														String newLoc = sc.nextLine();
														toChange.setLocaton(newLoc);
													}
													else if(newChangeUpper.equals("DESCRIPTION")) {
														String blankAgain = sc.nextLine();
														System.out.print("What would you like to change the description to?" + "   ");
														String newDescription = sc.nextLine();
														toChange.setDescription(newDescription);
													}
													else if (newChangeUpper.equals("STATUS")) {
														String blankAgain = sc.nextLine();
														System.out.print("What would you like to change the status to?" + "   ");
														String newStatus = sc.nextLine();
														toChange.setStatus(newStatus);
													}
													else {
														System.out.println("Invalid Input");
													}
												}
												else if (employerInput.equals("n")) {
													allEmployers.remove(company);
													allEmployers.add(company);
													allEmployersFile = new File(allEmployersString);
													FileOutputStream employerListOutFileUpdated = new FileOutputStream(allEmployersString);
													ObjectOutputStream employerListOutUpdated = new ObjectOutputStream(employerListOutFileUpdated);
													employerListOutUpdated.writeObject(allEmployers);
													employerListOutUpdated.flush();
													employerListOutUpdated.close();
													
													jobMap.replace(company.getCompanyName(), company.getJobsPosted());
													
													System.out.println();
													
													update = false;
												}
												else {
													System.out.println("Invalid Input");
												}
											}

										}
										
										
										//BROWSE THROUGH SEEKERS
										else if (choice.equals("5")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Retrieving all Seekers");
											int dotsAgain = 0;										// creates a ... (artificial loading)
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											System.out.println();
											boolean retrievingSeekers = true;
											System.out.println();
											for(int q = 0; q < allSeekers.size(); q++) {
												System.out.println(allSeekers.get(q));
											}
											String blankAgain = sc.nextLine();
											while(retrievingSeekers) {
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Would you like to search based upon a desired skill?");
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.print("Please enter " + "\"y\"" +" to confirm" + " or " + "\"n\"" + " to go back to the MENU screen:" +  "   ");
												String companyInput_1 = sc.nextLine();
												if (companyInput_1.equals("y")) {
													System.out.print("Please enter the skill you would like to search for:" + "   ");
													String skillSearcher = sc.nextLine();
													System.out.println();
													int checkCntr = 0;
													for(int a = 0; a < allSeekers.size(); a++) {
														Seeker instance = allSeekers.get(a);
														String seekerSkills = instance.getSkills();
														checkCntr++;
														if (skillSearcher.equals(seekerSkills) || seekerSkills.contains(skillSearcher)) {
															System.out.println(instance);
														}
														else if(checkCntr == allSeekers.size()){
															System.out.println("None exist with skill " +  "\"" + skillSearcher + "\"");
														}
													}
													
												}
												else if(companyInput_1.equals("n")) {
													System.out.println();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.println("Returning to MENU");
													retrievingSeekers = false;
												}
											}
											
											System.out.println();
										}
										
										//SIGN OUT
										else if (choice.equals("6")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User selceted 6");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Signing Out");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
																						
											System.out.println();
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("Goodbye " + company.getCompanyName() + "!");
											System.out.println();
											System.out.println();
											
											employerMenu = false;	//return back to start
										
										}
										
										//DELETE ACCOUNT
										else if (choice.equals("7")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User selceted 7");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											
											//CHANGE ORDER OF MESSAGE??
											System.out.print("We're sad to see you go ");
											TimeUnit.MILLISECONDS.sleep(750);
											System.out.print(":");
											TimeUnit.MILLISECONDS.sleep(750);
											System.out.print("'");
											TimeUnit.MILLISECONDS.sleep(750);
											System.out.print("(");
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(750);
											
											
											System.out.print("Please enter your username OR Press " + "\"r\"" +  " to return to the MENU:" + "   ");
											TimeUnit.MILLISECONDS.sleep(250);
											String userNameForDeletion = sc.next();
											if(userNameForDeletion.equals(company.getUsername())) { 					//if the username exists then proceed to password
												System.out.print("Please provide your password:   ");
												String passwordForDeletion = sc.next();
												String actualPasswordForDeletion = company.getPassword();
												int passwordCntrAgain = 0;											//counter for the number of times a password was entered
												while(!(actualPasswordForDeletion.equals(passwordForDeletion) || passwordCntrAgain > 2)) { //if the password was entered correctly or the password was entered 3 times inccorectly it spits back out
													if (passwordCntrAgain == 2) {									//if the password was entered incorrectly three times
														System.out.println("Three incorrect password attempts... returning to the menu");
														System.out.println();
														passwordCntrAgain++;
													}
													else{														//keep trying
														System.out.print("Invalid password, please try again   ");
														passwordForDeletion = sc.next();
														passwordCntrAgain++;
													}
												}
												if (actualPasswordForDeletion.equals(passwordForDeletion)) {							//if the password is correct go into the account
													
													System.out.println("Are you sure you wish to delete your profile?");
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("Please enter " + "\"y\"" +" to confirm" + " or " + "\"n\"" + " to go back to the MENU screen:" + "	");
													
													String userInput_4 = sc.next();
													
													//UPDATE FILE
													if(userInput_4.contentEquals("y")) {
														company.deleteEmployerFile();
														
														jobMap.remove(company.getCompanyName());
														
														allEmployers.remove(company);
														allEmployersFile = new File(allEmployersString);
														FileOutputStream employerListOutFileForDeletion = new FileOutputStream(allEmployersString);
														ObjectOutputStream employerListOutForDeletion = new ObjectOutputStream(employerListOutFileForDeletion);
														employerListOutForDeletion.writeObject(allEmployers);
														employerListOutForDeletion.flush();
														employerListOutForDeletion.close();
														
														
														masterUsernameAndPassword.remove(username, passwordForDeletion);
														employerUsernameAndPassword.remove(username, passwordForDeletion);
														allLoginInfoFile = new File(allLoginInfoString);
														employerLoginInfoFile = new File(employerLoginInfoString);
														FileOutputStream masterOutFileForDeletion =  new FileOutputStream(allLoginInfoString);
														ObjectOutputStream masterOutMapForDeletion = new ObjectOutputStream(masterOutFileForDeletion);
														masterOutMapForDeletion.writeObject(masterUsernameAndPassword);
														masterOutMapForDeletion.flush();
														masterOutMapForDeletion.close();
														masterOutFileForDeletion.close();
														
														FileOutputStream employerOutFileForDeletion =  new FileOutputStream(employerLoginInfoString);
														ObjectOutputStream employerOutMapForDeletion = new ObjectOutputStream(employerOutFileForDeletion);
														employerOutMapForDeletion.writeObject(employerUsernameAndPassword);
														employerOutMapForDeletion.flush();
														employerOutMapForDeletion.close();
														employerOutFileForDeletion.close();
														
														
														
														
														System.out.print("Deleting Account");
														int dotsAgain = 0;
														while(dotsAgain < 3) {
															TimeUnit.MILLISECONDS.sleep(1000);
															System.out.print(".");
															dotsAgain++;
														}
														
														
														System.out.println();
														System.out.println();
														TimeUnit.MILLISECONDS.sleep(250);
														System.out.print("Goodbye " + company.getCompanyName());
														TimeUnit.MILLISECONDS.sleep(1250);
														System.out.print(" FOREVER!");
														TimeUnit.MILLISECONDS.sleep(1000);
														System.out.println();
														System.out.println();
														System.out.println();
														System.out.println();
														System.out.println();
														System.out.println();
														System.out.println();
														System.out.println();
														System.out.println();
													
														employerMenu = false;
													}
													
													else if(userInput_4.equals("n")) {
														TimeUnit.MILLISECONDS.sleep(250);
														System.out.println("Returning to MENU");
														System.out.println();
														TimeUnit.MILLISECONDS.sleep(250);
													}
													
													else {
														System.out.println("Invalid Input...");
														System.out.println();
													}
													
												}
											}
											
											//GO BACK
											else if (userNameForDeletion.equals("r")) {
												System.out.println();
											}
											
											else {
												System.out.println("Invalid Input...");
												System.out.println();
											}
										}
										
										
										//OTHER INPUT (INVALID)
										else {
											System.out.println("Invalid Choice");
										}
									}
								}
							}
						}
					}
					
				}
				
				//OFFER TO CREATE AN ACCOUNT
				else if(masterUsernameAndPassword.containsKey(username) == false) {		//if the username cannot be found offer to make a new account
					System.out.println();
					System.out.println("We couldn't find that username...");
					System.out.println("Would you like to create an account?");
					System.out.print("Please enter " + "\"y\"" +" to create an account" + " or " + "\"n\"" + " to go back to the login screen:" + "	");
					String userInput = sc.next();
					if(userInput.equals("n")) {
						System.out.println();										//returns to the menu
					}
					
					//ACCOUNT CREATION PATH
					else if (userInput.equals("y")) {
						System.out.print("Please enter a password for your account:" + "   ");
						String passwordForNewAccount = sc.next();					//user's password
						masterUsernameAndPassword.put(username, passwordForNewAccount);	//add the username and password to the map for later retrieval
						String blank = sc.nextLine(); 								//to get rid of the blank from going from next to nextLine
						System.out.println();
						System.out.println("Couple of quick questions to get you started on the path to find the perfect employment opportunites!");
						System.out.print("Are you a Job Seeker or an Employer:" + "   ");
						String userTypeInput = sc.nextLine();
						String lowerCaseUserTypeInput = userTypeInput.toLowerCase();
						while(!(lowerCaseUserTypeInput.equals("job seeker") || lowerCaseUserTypeInput.equals("employer"))){	//to determine which object to use
							System.out.println();
							System.out.println("Invalid Input"); //maybe be more specific
							System.out.print("Are you a Job Seeker or an Employer:" + "   ");
							userTypeInput = sc.nextLine(); //to recheck the input
							lowerCaseUserTypeInput = userTypeInput.toLowerCase();
						}
						
						
						//JOB SEEKER CREATION
						if(lowerCaseUserTypeInput.equals("job seeker")){
							//String userNameIn, String passwordIn, String nameIn, String dobIn, String educationIn, String previousJobsIn, String skillsIn
							System.out.print("Please enter your name:" + "   ");
							String nameIn = sc.nextLine();
							System.out.print("Please enter your date of birth:" + "   ");
							String dobIn = sc.nextLine();
							System.out.print("Please enter your level of education:" + "   ");
							String educationIn = sc.nextLine();
							System.out.print("Please enter your previous jobs:" + "   ");
							String previousJobsIn = sc.nextLine();
							System.out.print("Please enter your skills:" + "   ");
							String skillsIn = sc.nextLine();
							
							System.out.print("Generating account, please wait");
							
							int dots = 0;										// creates a ... (artificial loading)
							while(dots < 3) {
								TimeUnit.MILLISECONDS.sleep(1000);
								System.out.print(".");
								dots++;
							}
							
							Seeker person = new Seeker(username, passwordForNewAccount, nameIn, dobIn, educationIn, previousJobsIn, skillsIn); //pass in the parameters
							
							allSeekers.add(person);
							allSeekersFile = new File(allSeekersString);
							FileOutputStream seekerListOutFile = new FileOutputStream(allSeekersString);
							ObjectOutputStream seekerListOut = new ObjectOutputStream(seekerListOutFile);
							seekerListOut.writeObject(allSeekers);
							seekerListOut.flush();
							seekerListOut.close();
							
							masterUsernameAndPassword.put(username, passwordForNewAccount);
							seekerUsernameAndPassword.put(username, passwordForNewAccount);
							allLoginInfoFile = new File(allLoginInfoString);
							seekerLoginInfoFile = new File(seekerLoginInfoString);
							FileOutputStream masterOutFile =  new FileOutputStream(allLoginInfoString);
							ObjectOutputStream masterOutMap = new ObjectOutputStream(masterOutFile);
							masterOutMap.writeObject(masterUsernameAndPassword);
							masterOutMap.flush();
							masterOutMap.close();
							masterOutFile.close();
							
							FileOutputStream seekerOutFile =  new FileOutputStream(seekerLoginInfoString);
							ObjectOutputStream seekerOutMap = new ObjectOutputStream(seekerOutFile);
							seekerOutMap.writeObject(seekerUsernameAndPassword);
							seekerOutMap.flush();
							seekerOutMap.close();
							seekerOutFile.close();

							
							
							System.out.println();
							TimeUnit.MILLISECONDS.sleep(1000);
							String name = person.getName();
							int i = name.indexOf(' ');							//checks for a space between first and last name		
							if (name.contains(" ")) {							//gets firstname
								String firstName = name.substring(0, i);
								System.out.println();
								System.out.println("Welcome " + firstName + "!");
								System.out.println();
							}
							else {												//only one String was typed in; therefore that's the name
								System.out.println();
								System.out.println("Welcome " + person.getName() + "!");
								System.out.println();
							}

							//SEEKER MENU
							boolean seekerMenu = true;
							while(seekerMenu) {
								TimeUnit.MILLISECONDS.sleep(350);
								System.out.println("---------------------------------");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("MENU:");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("To select an option please type in the appropriate number");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("1) View my profile");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("2) Update profile");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("3) Browse through open jobs");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("4) Track status of applied jobs");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("5) Sign out");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("6) Delete account");
								System.out.println();
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.print("User's Choice:" + "   ");

								String choice = sc.next();
								
								//OPTION 1
								if (choice.equals("1")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User selected 1");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.print("Fetching profile");
									int dotsAgain = 0;							//"loading" maybe make a method out of this??
									while(dotsAgain < 3) {
										TimeUnit.MILLISECONDS.sleep(1000);
										System.out.print(".");
										dotsAgain++;
									}
									System.out.println();
									System.out.println();
									
									//DISPLAY SEEKER PROFILE
									boolean seekerProfile = true;
									while(seekerProfile) {
										System.out.println("PROFILE:");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Name: " + person.getName());
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Date of Birth: " + person.getDob());
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Education: " + person.getEducation());
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Previous Jobs: " + person.getPreviousJobs());
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Skills: " + person.getSkills());
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println();
										System.out.print("Press " + "\"r\"" + " to return to the MENU:" + "   ");		//r returns to the menu
										String userInput_2 = sc.next(); 
										System.out.println();
										if (userInput_2.equals("r")) {
											seekerProfile = false;
										}
										else {
											System.out.println("Invalid Input");
											System.out.println();
										}
									}
								}
								
								//UPDATING INFO
								//UPDATING INFO
								else if (choice.equals("2")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User selceted 2");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									boolean update = true;
									while(update) {
										System.out.println("What do you wish to update?");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("1) Name");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("2) Date of Bith");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("3) Education");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("4) Previous Jobs");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("5) Skills");
										System.out.println();
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Press " + "\"r\"" + " to return to the MENU:" + "   ");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.print("User's Choice:" + "   ");
										String userInput_3 = sc.next();
										System.out.println();
										
										//CHANGE NAME
										if (userInput_3.equals("1")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User Selected 1");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("What do you wish to change your name to?" + "   ");
											String blankAgain = sc.nextLine();
											String newName = sc.nextLine();
											person.setName(newName);
											System.out.print("Updating");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println();
											System.out.println();
											System.out.println("Name Updated!");
											System.out.println();
										}
										
										//CHANGE DATE OF BIRTH
										else if(userInput_3.equals("2")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User Selected 2");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("What do you wish to change your date of birth to?" + "   ");
											String blankAgain = sc.nextLine();
											String newDob = sc.nextLine();
											person.setDob(newDob);
											System.out.print("Updating");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println();
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("Date of Birth Updated!");
											System.out.println();
										}
										
										//CHANGE EDUCATION
										else if (userInput_3.equals("3")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User Selected 3");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("What do you wish to change your education to?" + "   ");
											String blankAgain = sc.nextLine();
											String newEdu = sc.nextLine();
											person.setEducation(newEdu);
											System.out.print("Updating");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println();
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("Education Updated!");
											System.out.println();
										}
										
										//CHANGE PREVIOUS JOBS
										else if(userInput_3.equals("4")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User Selected 4");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("What do you wish to change your previous jobs to?" + "   ");
											String blankAgain = sc.nextLine();
											String newPreviousJobs = sc.nextLine();
											person.setPreviousJobs(newPreviousJobs);
											System.out.print("Updating");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println();
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("Previous Jobs Updated!");
											System.out.println();
										}
										
										//CHANGE SKILLS
										else if (userInput_3.equals("5")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User Selected 5");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("What do you wish to change your skills to?" + "   ");
											String blankAgain = sc.nextLine();
											String newSkills = sc.nextLine();
											person.setSkills(newSkills);
											System.out.print("Updating");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println();
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("Skills Updated!");
											System.out.println();
										}
										
										//GO BACK
										else if (userInput_3.equals("r")) {
											System.out.print("Saving Changes");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											allSeekers.remove(person);
											allSeekers.add(person);
											allSeekersFile = new File(allSeekersString);
											FileOutputStream seekerListOutFileUpdate = new FileOutputStream(allSeekersString);
											ObjectOutputStream seekerListOutUpdate = new ObjectOutputStream(seekerListOutFileUpdate);
											seekerListOutUpdate.writeObject(allSeekers);
											seekerListOutUpdate.flush();
											seekerListOutUpdate.close();
											update = false;
											System.out.println();
											System.out.println();
										}
										else {
											System.out.println("Invalid Input");
											System.out.println();
										}
									}
								}
								
								//BROWSE THROUGH OPEN JOBS
								else if (choice.equals("3")) {
									ArrayList<Job> appliedJobs = person.getAppliedJobs();
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.print("Retrieving all jobs");
									int dotsAgain = 0;										// creates a ... (artificial loading)
									while(dotsAgain < 3) {
										TimeUnit.MILLISECONDS.sleep(1000);
										System.out.print(".");
										dotsAgain++;
									}
									System.out.println();
									boolean retrievingJobs = true;
									System.out.println();
									for (Map.Entry<String, ArrayList<Job>> mapToSet : jobMap.entrySet()){
										ArrayList<Job> companysJobs = mapToSet.getValue();
										System.out.println("Company: " + mapToSet.getKey());
										for(int l = 0; l < companysJobs.size(); l++) {
											Job instanceJob = companysJobs.get(l);
											System.out.println(instanceJob);
										}
										System.out.println();
									}
									String blankAgain = sc.nextLine();
									while(retrievingJobs) {
										System.out.println();
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("1) Would you like to search based upon a desired title?");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("2) Would you like to search based upon a desired location?");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("3) Would you like to apply to any of these postions?");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.print("Please enter the desired number OR " + "\"r\"" + " to go back to the MENU screen:" +  "   ");
										String seekerInput_1 = sc.nextLine();
										if (seekerInput_1.equals("1")) {
											System.out.print("Please enter the title you would like to search for:" + "   ");
											String titleSearcher = sc.nextLine();
											System.out.println();
											int foundCntr = 0;
											int checkCntr = 0;
											for(int a = 0; a < allJobs.size(); a++) {
												Job instance = allJobs.get(a);
												String jobTitle = instance.getTitle();
												checkCntr++;
												if (titleSearcher.equals(jobTitle) || jobTitle.contains(titleSearcher)) {
													System.out.println(instance);
													foundCntr++;
												}
												else if(foundCntr == 0 && checkCntr == allJobs.size()){
													System.out.println("None exist with title " +  "\"" + titleSearcher + "\"");
												}
											}
											
										}
										else if(seekerInput_1.equals("2")) {
											System.out.print("Please enter the location you would like to search for:" + "   ");
											String locationSearcher = sc.nextLine();
											System.out.println();
											int foundCntr = 0;
											int checkCntr = 0;
											for(int a = 0; a < allJobs.size(); a++) {
												Job instance = allJobs.get(a);
												String jobLocation = instance.getLocaton();
												checkCntr++;
												if (locationSearcher.equals(jobLocation) || jobLocation.contains(locationSearcher)) {
													System.out.println(instance);
													foundCntr++;
												}
												else if(foundCntr == 0 && checkCntr == allJobs.size()){
													System.out.println("None exist with location " +  "\"" + locationSearcher + "\"");
												}
											}
										}
										
										else if(seekerInput_1.equals("3")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Please enter the name of the company of which the position belongs to:" + "   ");
											String wantedCompany = sc.nextLine();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Please enter the title of the position:" + "   ");
											String wantedPosition = sc.nextLine();
											ArrayList<Job> jobsForCompany = jobMap.get(wantedCompany);
											int foundCntr = 0;
											for(int u = 0; u < jobsForCompany.size(); u++) {
												Job jobInstance = jobsForCompany.get(u);
												String actualTitle = jobInstance.getTitle();
												if(wantedPosition.equals(actualTitle)) {
													foundCntr = 1;
													appliedJobs.add(jobInstance);
													person.setAppliedJobs(appliedJobs);
													allSeekers.remove(person);
													allSeekers.add(person);
													allSeekersFile = new File(allSeekersString);
													FileOutputStream seekerListOutFileUpdate = new FileOutputStream(allSeekersString);
													ObjectOutputStream seekerListOutUpdate = new ObjectOutputStream(seekerListOutFileUpdate);
													seekerListOutUpdate.writeObject(allSeekers);
													seekerListOutUpdate.flush();
													seekerListOutUpdate.close();
													System.out.println();
													System.out.println("Job " +  "\"" + actualTitle + "\"" + " added to applied jobs!");
												}
												else if ((!(wantedPosition.equals(actualTitle))) && foundCntr == 0){
													System.out.println("Couldn't find that title");
												}
											}
										}
										
										else if(seekerInput_1.equals("r")) {
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("Returning to MENU");
											retrievingJobs = false;
										}
										
										else {
											System.out.println();
											for (Map.Entry<String, ArrayList<Job>> mapToSet : jobMap.entrySet()){
												ArrayList<Job> companysJobs = mapToSet.getValue();
												System.out.println("Company: " + mapToSet.getKey());
												for(int l = 0; l < companysJobs.size(); l++) {
													Job instanceJob = companysJobs.get(l);
													System.out.println(instanceJob);
												}
												System.out.println();
											}
										}
									}
									
									System.out.println();
								
								}
								
								//TRACK STATUS OF APPLIED JOBS
								else if(choice.equals("4")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User selceted 4");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.print("Retrieving Jobs");
									int dotsAgain = 0;
									while(dotsAgain < 3) {
										TimeUnit.MILLISECONDS.sleep(1000);
										System.out.print(".");
										dotsAgain++;
									}
									System.out.println();
									System.out.println();
									ArrayList<Job> appliedJobs = person.getAppliedJobs();
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("APPLIED JOBS:");
									if(appliedJobs != null) {
										for (int l = 0; l < appliedJobs.size(); l++) {
											System.out.println(appliedJobs.get(l));
										}
										System.out.println();
									}
									else {
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Have yet to apply :(, select option 3 from the MENU to get started!");
										System.out.println();
									}
								}
								
								//SIGNING OUT
								else if (choice.equals("5")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User selceted 5");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.print("Signing Out");
									int dotsAgain = 0;
									while(dotsAgain < 3) {
										TimeUnit.MILLISECONDS.sleep(1000);
										System.out.print(".");
										dotsAgain++;
									}
									name = person.getName();
									i = name.indexOf(' ');
									if (name.contains(" ")) {							//gets firstname
										String firstName = name.substring(0, i);
										System.out.println();
										System.out.println();
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Goodbye " + firstName + "!");
										System.out.println();
										System.out.println();
										
									}
									else {												//only one String was typed in; therefore that's the name
										System.out.println();
										System.out.println();
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Goodbye " + person.getName() + "!");
										System.out.println();
										System.out.println();
									}
									seekerMenu = false;	//return back to start
								}
							
								//DELETION OF ACCOUNT AND ALL THINGS TIED TO IT 
								//TODO MAKE SURE EVERYTHING TIED TO IT IS DELETED OR UPDATED ALSO CHECK THE 'R' PROBLEM ALSO MAYBE USE SOME WHILE LOOPS
								else if (choice.equals("6")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User selceted 6");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									
									//CHANGE ORDER OF MESSAGE??
									System.out.print("We're sad to see you go ");
									TimeUnit.MILLISECONDS.sleep(750);
									System.out.print(":");
									TimeUnit.MILLISECONDS.sleep(750);
									System.out.print("'");
									TimeUnit.MILLISECONDS.sleep(750);
									System.out.print("(");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(750);
									
									
									System.out.print("Please enter your username OR Press " + "\"r\"" +  " to return to the MENU:" + "   ");
									TimeUnit.MILLISECONDS.sleep(250);
									String userNameForDeletion = sc.next();
									if(userNameForDeletion.equals(person.getUsername())) { 					//if the username exists then proceed to password
										System.out.print("Please provide your password:   ");
										String passwordForDeletion = sc.next();
										String actualPasswordForDeletion = person.getPassword();
										int passwordCntr = 0;											//counter for the number of times a password was entered
										while(!(actualPasswordForDeletion.equals(passwordForDeletion) || passwordCntr > 2)) { //if the password was entered correctly or the password was entered 3 times inccorectly it spits back out
											if (passwordCntr == 2) {									//if the password was entered incorrectly three times
												System.out.println("Three incorrect password attempts... returning to the menu");
												System.out.println();
												passwordCntr++;
											}
											else{														//keep trying
												System.out.print("Invalid password, please try again   ");
												passwordForDeletion = sc.next();
												passwordCntr++;
											}
										}
										if (actualPasswordForDeletion.equals(passwordForDeletion)) {							//if the password is correct go into the account
											
											System.out.println("Are you sure you wish to delete your profile?");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Please enter " + "\"y\"" +" to confirm" + " or " + "\"n\"" + " to go back to the MENU screen:" + "	");
											
											String userInput_4 = sc.next();
											
											if(userInput_4.contentEquals("y")) {
												person.deletePersonalFile();
												
												System.out.print("Deleting Account");
												int dotsAgain = 0;
												while(dotsAgain < 3) {
													TimeUnit.MILLISECONDS.sleep(1000);
													System.out.print(".");
													dotsAgain++;
												}
												
												allSeekers.remove(person);
												allSeekersFile = new File(allSeekersString);
												FileOutputStream seekerListOutFileForDeletion = new FileOutputStream(allSeekersString);
												ObjectOutputStream seekerListOutForDeletion = new ObjectOutputStream(seekerListOutFileForDeletion);
												seekerListOutForDeletion.writeObject(allSeekers);
												seekerListOutForDeletion.flush();
												seekerListOutForDeletion.close();
												
												
												masterUsernameAndPassword.remove(username, passwordForNewAccount);
												seekerUsernameAndPassword.remove(username, passwordForNewAccount);
												allLoginInfoFile = new File(allLoginInfoString);
												seekerLoginInfoFile = new File(seekerLoginInfoString);
												FileOutputStream masterOutFileForDeletion =  new FileOutputStream(allLoginInfoString);
												ObjectOutputStream masterOutMapForDeletion = new ObjectOutputStream(masterOutFileForDeletion);
												masterOutMapForDeletion.writeObject(masterUsernameAndPassword);
												masterOutMapForDeletion.flush();
												masterOutMapForDeletion.close();
												masterOutFileForDeletion.close();
												
												FileOutputStream seekerOutFileForDeletion =  new FileOutputStream(seekerLoginInfoString);
												ObjectOutputStream seekerOutMapForDeletion = new ObjectOutputStream(seekerOutFileForDeletion);
												seekerOutMapForDeletion.writeObject(seekerUsernameAndPassword);
												seekerOutMapForDeletion.flush();
												seekerOutMapForDeletion.close();
												seekerOutFileForDeletion.close();
												
												if (name.contains(" ")) {							//gets firstname
													String firstName = name.substring(0, i);
													System.out.println();
													System.out.println();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("Goodbye " + firstName);
													TimeUnit.MILLISECONDS.sleep(1250);
													System.out.print(" FOREVER!");
													TimeUnit.MILLISECONDS.sleep(1000);
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													
												}
												else {												//only one String was typed in; therefore that's the name
													System.out.println();
													System.out.println();
													TimeUnit.MILLISECONDS.sleep(250);
													System.out.print("Goodbye " + person.getName());
													TimeUnit.MILLISECONDS.sleep(1250);
													System.out.print(" FOREVER!");
													TimeUnit.MILLISECONDS.sleep(1000);
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
													System.out.println();
												}
												seekerMenu = false;
											}
											
											else if(userInput_4.equals("n")) {
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Returning to MENU");
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
											}
											
											else {
												System.out.println("Invalid Input...");
												System.out.println();
											}
											
										}
									}
									
									//GO BACK
									else if (userNameForDeletion.equals("r")) {
										System.out.println();
									}
									
									else {
										System.out.println("Invalid Input...");
										System.out.println();
									}
								}
								else {
									System.out.println("Invalid Choice...");
									System.out.println();
								}
							}

						}
						
						//EMPLOYER CREATION
						else if (lowerCaseUserTypeInput.equals("employer")){
							//TODO create employer
							System.out.print("Please enter the name of your company:" + "   ");
							String companyNameIn = sc.nextLine();
							System.out.print("Please enter a short description of your company:" + "   ");
							String descriptionIn = sc.nextLine();
							
							
							System.out.print("Generating account, please wait");
							
							int dots = 0;										// creates a ... (artificial loading)
							while(dots < 3) {
								TimeUnit.MILLISECONDS.sleep(1000);
								System.out.print(".");
								dots++;
							}
							
							Employer company = new Employer(username, passwordForNewAccount, companyNameIn, descriptionIn);
							
							allEmployers.add(company);
							allEmployersFile = new File(allEmployersString);
							FileOutputStream employerListOutFile = new FileOutputStream(allEmployersString);
							ObjectOutputStream employerListOut = new ObjectOutputStream(employerListOutFile);
							employerListOut.writeObject(allEmployers);
							employerListOut.flush();
							employerListOut.close();
							
							masterUsernameAndPassword.put(username, passwordForNewAccount);
							employerUsernameAndPassword.put(username, passwordForNewAccount);
							allLoginInfoFile = new File(allLoginInfoString);
							employerLoginInfoFile = new File(employerLoginInfoString);
							FileOutputStream masterOutFile =  new FileOutputStream(allLoginInfoString);
							ObjectOutputStream masterOutMap = new ObjectOutputStream(masterOutFile);
							masterOutMap.writeObject(masterUsernameAndPassword);
							masterOutMap.flush();
							masterOutMap.close();
							masterOutFile.close();
							
							FileOutputStream employerOutFile =  new FileOutputStream(employerLoginInfoString);
							ObjectOutputStream employerOutMap = new ObjectOutputStream(employerOutFile);
							employerOutMap.writeObject(employerUsernameAndPassword);
							employerOutMap.flush();
							employerOutMap.close();
							employerOutFile.close();
							
							
							System.out.println();
							TimeUnit.MILLISECONDS.sleep(1000);
							String name = company.getCompanyName();
										
							//DISPLAY MESSAGE
							System.out.println();
							System.out.println("Welcome " + company.getCompanyName() + "!");
							System.out.println();
							
							//EMPLOYER MENU //TODO CHANGE 4 into 5??
							boolean employerMenu = true;
							while(employerMenu) {
								TimeUnit.MILLISECONDS.sleep(350);
								System.out.println("---------------------------------");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("MENU:");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("To select an option please type in the appropriate number");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("1) View my company's profile");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("2) Update my company's profile");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("3) Create a new job post");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("4) View and or update the list of jobs posted");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("5) Browse through all Seekers");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("6) Sign out");
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.println("7) Delete account");
								System.out.println();
								TimeUnit.MILLISECONDS.sleep(250);
								System.out.print("User's Choice:" + "   ");

								String choice = sc.next();
								
								//DISPLAY PROFILE
								if (choice.equals("1")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User selceted 1");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.print("Fetching profile");
									int dotsAgain = 0;							//"loading" maybe make a method out of this??
									while(dotsAgain < 3) {
										TimeUnit.MILLISECONDS.sleep(1000);
										System.out.print(".");
										dotsAgain++;
									}
									System.out.println();
									System.out.println();
									
									//DISPLAY SEEKER PROFILE
									boolean seekerProfile = true;
									while(seekerProfile) {
										System.out.println("PROFILE:");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Company Name: " + company.getCompanyName());
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Description: " + company.getDescription());
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println();
										System.out.print("Press " + "\"r\"" + " to return to the MENU:" + "   ");		//r returns to the menu
										String userInput_2 = sc.next(); 
										System.out.println();
										if (userInput_2.equals("r")) {
											seekerProfile = false;
										}
										else {
											System.out.println("Invalid Input");
											System.out.println();
										}
									}
								}
								
								//UPDATE PROFILE
								else if(choice.equals("2")){
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User selceted 2");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									boolean update = true;
									while(update) {
										System.out.println("What do you wish to update?");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("1) Company Name");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("2) Company Description");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println();
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Press " + "\"r\"" + " to return to the MENU:" + "   ");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.print("User's Choice:" + "   ");
										String userInput_3 = sc.next();
										System.out.println();
										
										//CHANGE NAME
										if (userInput_3.equals("1")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User Selected 1");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("What do you wish to change the name to?" + "   ");
											String blankAgain = sc.nextLine();
											String newName = sc.nextLine();
											company.setCompanyName(newName);
											System.out.print("Updating");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println();
											System.out.println();
											System.out.println("Name Updated!");
											System.out.println();
										}
										
										//CHANGE DESCRIPTION
										else if(userInput_3.equals("2")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("User Selected 2");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("What do you wish to change the description to?" + "   ");
											String blankAgain = sc.nextLine();
											String newDescription = sc.nextLine();
											company.setDescription(newDescription);
											System.out.print("Updating");
											int dotsAgain = 0;
											while(dotsAgain < 3) {
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.print(".");
												dotsAgain++;
											}
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println();
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("Description Updated!");
											System.out.println();
										}
										else if (userInput_3.equals("r")) {
											allEmployers.remove(company);
											allEmployers.add(company);
											allEmployersFile = new File(allEmployersString);
											FileOutputStream employerListOutFileUpdated = new FileOutputStream(allEmployersString);
											ObjectOutputStream employerListOutUpdated = new ObjectOutputStream(employerListOutFileUpdated);
											employerListOutUpdated.writeObject(allEmployers);
											employerListOutUpdated.flush();
											employerListOutUpdated.close();
											update = false;
											System.out.println();
										}
										else {
											System.out.println("Invalid Input");
											System.out.println();
										}
									}
								}
								
								//CREATE A NEW JOB
								else if (choice.equals("3")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User Selected 3");
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("Couple of quick questions concerning the new job!");
									System.out.println();
									String anotherBlank = sc.nextLine();
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.print("What is the title of the position?" + "   ");
									String titleIn = sc.nextLine();
									System.out.print("Where is the location of the position?" + "   ");
									String locationIn = sc.nextLine();
									System.out.print("What is the job description?" + "   ");
									String jobDescriptionIn = sc.nextLine();

									System.out.print("Generating new job, please wait");
									
									int dotsAgain = 0;										// creates a ... (artificial loading)
									while(dotsAgain < 3) {
										TimeUnit.MILLISECONDS.sleep(1000);
										System.out.print(".");
										dotsAgain++;
									}
									
									company.setJob(titleIn, locationIn, jobDescriptionIn);
									Job position = company.getJob();
									
									allEmployers.remove(company);
									allEmployers.add(company);
									allEmployersFile = new File(allEmployersString);
									FileOutputStream employerListOutFileUpdated = new FileOutputStream(allEmployersString);
									ObjectOutputStream employerListOutUpdated = new ObjectOutputStream(employerListOutFileUpdated);
									employerListOutUpdated.writeObject(allEmployers);
									employerListOutUpdated.flush();
									employerListOutUpdated.close();
									System.out.println();
									
									
									System.out.println();
									System.out.println();
									System.out.println("Job " + "\"" + position.getTitle() + "\"" + " has been posted!");
									System.out.println();


								}
								
								//VIEW LIST OF JOBS POSTED
								else if (choice.equals("4")) {
									boolean update = true;
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User Selected: 4");
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.print("Retrieving Jobs Posted");

									int dotsAgain = 0;										// creates a ... (artificial loading)
									while(dotsAgain < 3) {
										TimeUnit.MILLISECONDS.sleep(1000);
										System.out.print(".");
										dotsAgain++;
									}
									System.out.println();
									while (update) {
										System.out.println();
										ArrayList<Job> jobs = company.getJobsPosted();
										for(int j = 0; j < jobs.size(); j++) {
											int number = j+1;
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("JOB: " + number + " " + jobs.get(j));	
										}
										
										System.out.println();
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Would you like to update any of these job postings?");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.print("Please enter " + "\"y\"" +" to confirm" + " or " + "\"n\"" + " to go back to the MENU screen:" +  "   ");
										String employerInput = sc.next();
										if(employerInput.equals("y")) {
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Please enter the number of the job post that you would like to alter:" + "   "); //TODO else incorrect type input (String)
											String numberChoice = sc.next();
											int numberChoiceInt = Integer.parseInt(numberChoice);
											int actualNumber = numberChoiceInt - 1;
											Job toChange = null;
											
											if(actualNumber <= jobs.size()) {
												for (int p = 0; p < jobs.size(); p++) {
													toChange = jobs.get(actualNumber);
												}
											}
											
											System.out.print("Please enter the attribute you wish to alter:" + "    ");
											String newChange = sc.next();
											String newChangeUpper = newChange.toUpperCase();
											if(newChangeUpper.equals("TITLE")) {
												String blankAgain = sc.nextLine();
												System.out.print("What would you like to change the title to?" + "    ");
												String newTitle = sc.nextLine();
												toChange.setTitle(newTitle);
											}
											else if(newChangeUpper.equals("LOCATION")) {
												String blankAgain = sc.nextLine();
												System.out.print("What would you like to change the location to?" + "   ");
												String newLoc = sc.nextLine();
												toChange.setLocaton(newLoc);
											}
											else if(newChangeUpper.equals("DESCRIPTION")) {
												String blankAgain = sc.nextLine();
												System.out.print("What would you like to change the description to?" + "   ");
												String newDescription = sc.nextLine();
												toChange.setDescription(newDescription);
											}
											else if (newChangeUpper.equals("STATUS")) {
												String blankAgain = sc.nextLine();
												System.out.print("What would you like to change the status to?" + "   ");
												String newStatus = sc.nextLine();
												toChange.setStatus(newStatus);
											}
											else {
												System.out.println("Invalid Input");
											}
										}
										else if (employerInput.equals("n")) {
											allEmployers.remove(company);
											allEmployers.add(company);
											allEmployersFile = new File(allEmployersString);
											FileOutputStream employerListOutFileUpdated = new FileOutputStream(allEmployersString);
											ObjectOutputStream employerListOutUpdated = new ObjectOutputStream(employerListOutFileUpdated);
											employerListOutUpdated.writeObject(allEmployers);
											employerListOutUpdated.flush();
											employerListOutUpdated.close();
											
											System.out.println();
											
											update = false;
										}
										else {
											System.out.println("Invalid Input");
										}
									}

								}
								
								
								//BROWSE THROUGH SEEKERS
								else if (choice.equals("5")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.print("Retrieving all Seekers");
									int dotsAgain = 0;										// creates a ... (artificial loading)
									while(dotsAgain < 3) {
										TimeUnit.MILLISECONDS.sleep(1000);
										System.out.print(".");
										dotsAgain++;
									}
									System.out.println();
									boolean retrievingSeekers = true;
									System.out.println();
									for(int q = 0; q < allSeekers.size(); q++) {
										System.out.println(allSeekers.get(q));
									}
									String blankAgain = sc.nextLine();
									while(retrievingSeekers) {
										System.out.println();
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.println("Would you like to search based upon a desired skill?");
										TimeUnit.MILLISECONDS.sleep(250);
										System.out.print("Please enter " + "\"y\"" +" to confirm" + " or " + "\"n\"" + " to go back to the MENU screen:" +  "   ");
										String companyInput_1 = sc.nextLine();
										if (companyInput_1.equals("y")) {
											System.out.print("Please enter the skill you would like to search for:" + "   ");
											String skillSearcher = sc.nextLine();
											System.out.println();
											int checkCntr = 0;
											for(int a = 0; a < allSeekers.size(); a++) {
												Seeker instance = allSeekers.get(a);
												String seekerSkills = instance.getSkills();
												checkCntr++;
												if (skillSearcher.equals(seekerSkills) || seekerSkills.contains(skillSearcher)) {
													System.out.println(instance);
												}
												else if(checkCntr == allSeekers.size()){
													System.out.println("None exist with skill " +  "\"" + skillSearcher + "\"");
												}
											}
											
										}
										else if(companyInput_1.equals("n")) {
											System.out.println();
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.println("Returning to MENU");
											retrievingSeekers = false;
										}
									}
									
									System.out.println();
								}
								
								//SIGN OUT
								else if (choice.equals("6")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User selceted 6");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.print("Signing Out");
									int dotsAgain = 0;
									while(dotsAgain < 3) {
										TimeUnit.MILLISECONDS.sleep(1000);
										System.out.print(".");
										dotsAgain++;
									}
																				
									System.out.println();
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("Goodbye " + company.getCompanyName() + "!");
									System.out.println();
									System.out.println();
									
									employerMenu = false;	//return back to start
								
								}
								
								//DELETE ACCOUNT
								else if (choice.equals("7")) {
									TimeUnit.MILLISECONDS.sleep(250);
									System.out.println("User selceted 7");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(250);
									
									//CHANGE ORDER OF MESSAGE??
									System.out.print("We're sad to see you go ");
									TimeUnit.MILLISECONDS.sleep(750);
									System.out.print(":");
									TimeUnit.MILLISECONDS.sleep(750);
									System.out.print("'");
									TimeUnit.MILLISECONDS.sleep(750);
									System.out.print("(");
									System.out.println();
									TimeUnit.MILLISECONDS.sleep(750);
									
									
									System.out.print("Please enter your username OR Press " + "\"r\"" +  " to return to the MENU:" + "   ");
									TimeUnit.MILLISECONDS.sleep(250);
									String userNameForDeletion = sc.next();
									if(userNameForDeletion.equals(company.getUsername())) { 					//if the username exists then proceed to password
										System.out.print("Please provide your password:   ");
										String passwordForDeletion = sc.next();
										String actualPasswordForDeletion = company.getPassword();
										int passwordCntrAgain = 0;											//counter for the number of times a password was entered
										while(!(actualPasswordForDeletion.equals(passwordForDeletion) || passwordCntrAgain > 2)) { //if the password was entered correctly or the password was entered 3 times inccorectly it spits back out
											if (passwordCntrAgain == 2) {									//if the password was entered incorrectly three times
												System.out.println("Three incorrect password attempts... returning to the menu");
												System.out.println();
												passwordCntrAgain++;
											}
											else{														//keep trying
												System.out.print("Invalid password, please try again   ");
												passwordForDeletion = sc.next();
												passwordCntrAgain++;
											}
										}
										if (actualPasswordForDeletion.equals(passwordForDeletion)) {							//if the password is correct go into the account
											
											System.out.println("Are you sure you wish to delete your profile?");
											TimeUnit.MILLISECONDS.sleep(250);
											System.out.print("Please enter " + "\"y\"" +" to confirm" + " or " + "\"n\"" + " to go back to the MENU screen:" + "	");
											
											String userInput_4 = sc.next();
											
											if(userInput_4.contentEquals("y")) {
												company.deleteEmployerFile();
												
												
												allEmployers.remove(company);
												allEmployersFile = new File(allEmployersString);
												FileOutputStream employerListOutFileForDeletion = new FileOutputStream(allEmployersString);
												ObjectOutputStream employerListOutForDeletion = new ObjectOutputStream(employerListOutFileForDeletion);
												employerListOutForDeletion.writeObject(allEmployers);
												employerListOutForDeletion.flush();
												employerListOutForDeletion.close();
												
												
												masterUsernameAndPassword.remove(username, passwordForDeletion);
												employerUsernameAndPassword.remove(username, passwordForDeletion);
												allLoginInfoFile = new File(allLoginInfoString);
												employerLoginInfoFile = new File(employerLoginInfoString);
												FileOutputStream masterOutFileForDeletion =  new FileOutputStream(allLoginInfoString);
												ObjectOutputStream masterOutMapForDeletion = new ObjectOutputStream(masterOutFileForDeletion);
												masterOutMapForDeletion.writeObject(masterUsernameAndPassword);
												masterOutMapForDeletion.flush();
												masterOutMapForDeletion.close();
												masterOutFileForDeletion.close();
												
												FileOutputStream employerOutFileForDeletion =  new FileOutputStream(employerLoginInfoString);
												ObjectOutputStream employerOutMapForDeletion = new ObjectOutputStream(employerOutFileForDeletion);
												employerOutMapForDeletion.writeObject(employerUsernameAndPassword);
												employerOutMapForDeletion.flush();
												employerOutMapForDeletion.close();
												employerOutFileForDeletion.close();
												
												
												
												
												System.out.print("Deleting Account");
												int dotsAgain = 0;
												while(dotsAgain < 3) {
													TimeUnit.MILLISECONDS.sleep(1000);
													System.out.print(".");
													dotsAgain++;
												}
												
												
												System.out.println();
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.print("Goodbye " + company.getCompanyName());
												TimeUnit.MILLISECONDS.sleep(1250);
												System.out.print(" FOREVER!");
												TimeUnit.MILLISECONDS.sleep(1000);
												System.out.println();
												System.out.println();
												System.out.println();
												System.out.println();
												System.out.println();
												System.out.println();
												System.out.println();
												System.out.println();
												System.out.println();
											
												employerMenu = false;
											}
											
											else if(userInput_4.equals("n")) {
												TimeUnit.MILLISECONDS.sleep(250);
												System.out.println("Returning to MENU");
												System.out.println();
												TimeUnit.MILLISECONDS.sleep(250);
											}
											
											else {
												System.out.println("Invalid Input...");
												System.out.println();
											}
											
										}
									}
									
									//GO BACK
									else if (userNameForDeletion.equals("r")) {
										System.out.println();
									}
									
									else {
										System.out.println("Invalid Input...");
										System.out.println();
									}
								}
								
								
								//OTHER INPUT (INVALID)
								else {
									System.out.println("Invalid Choice");
								}
							}
						}
					}
				}
			}
		}
	}
}
