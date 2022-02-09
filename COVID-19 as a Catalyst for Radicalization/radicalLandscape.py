from radicalIndividual import Individual
import numpy as np 
import matplotlib.pyplot as plt
import random
import time
import math

class Landscape():

   def __init__(self, size):
      self.size = size        #size of the population                    
      self.pop = np.empty(shape = (size,size), dtype = 'object')  #grid containing each Individual
      self.totalPeople = self.size * self.size  #total size of the grid/city

      self.day = 0                  #keep track of the day

      #keep track of how many people fall into each category (for graphing purposes)
      self.zeroStatusList = []
      self.oneStatusList = []
      self.twoStatusList = []
      self.threeStatusList = []
      self.searchersList = []

      #counters to append to the associated previously initialized lists
      self.zeroStatusCntr = 0
      self.oneStatusCntr = 0
      self.twoStatusCntr = 0
      self.threeStatusCntr = 0

      self.searchProb = 0.15 #estimated probability that one will search

      self.sev3 = []          #severities per each status
      self.sev2 = []
      self.sev1 = []
      self.sev0 = []

      self.sev3Avg = 0        #average of the previously initialized lists (in order to see an interaction of severity)
      self.sev2Avg = 0
      self.sev1Avg = 0
      self.sev0Avg = 0

      self.status0GrievNum = 0      #in order to see an interaction of the total number of events and ones grievance
      self.status1GrievNum = 0
      self.status2GrievNum = 0
      self.status3GrievNum = 0

   #function to initialize and populate the city
   def populate(self):
      idCntr = 0
      for i in range(self.size):
         for j in range(self.size):
            self.pop[i][j] = Individual(0, 0, idCntr)
            idCntr = idCntr + 1

   #one day for one individual within the city
   def step(self, thresholdIn, dayIn, searchProbIn):
      i,j = self.randomPerson() #pick a random person
      person = self.pop[i][j]    #is the individual
      if (isinstance(person, Individual)):         #make sure that the person is an individual (in case I wanted to add an indoctrinator (original poster))... somewhat redundant
         person.createMyStatuses(thresholdIn)      #create the individuals personalized status thresholds
         person.event()                            #have an event occur
         person.findTotalGrievanceValue()          #find the persons overall grievance value
         person.checkStatus(thresholdIn)           #update the person's status
         status = person.status                    #obtain their status
         if (status == 1):
            person.search(dayIn, searchProbIn)     #if they fall into the first category, have them search
         elif (status == 2):                       
            person.post(dayIn)                     #if they fall into the second category have them post
         person.grievDecay()                       #have their grievances decay
         person.findTotalGrievanceValue()          #find the value post-decay

   #selection of the random individual within the city
   def randomPerson(self):
      i = random.randint(0,self.size-1)
      j = random.randint(0,self.size-1)
      return i, j

   #find the number of searchers in the city on one day
   def resetAndUpdateSearchers(self):
      self.searchersList.append(Individual.searchers)
      Individual.searchers = 0  

   #resets the city's counters for the number of individuals that fall within a particular status
   def resetTotalStatuses(self):
      self.zeroStatusCntr = 0
      self.oneStatusCntr = 0
      self.twoStatusCntr = 0
      self.threeStatusCntr = 0

   #create a list of the number of people within each status for graphing purposes
   def appendTotalStatuses(self):
      self.zeroStatusList.append(self.zeroStatusCntr)
      self.oneStatusList.append(self.oneStatusCntr)
      self.twoStatusList.append(self.twoStatusCntr)
      self.threeStatusList.append(self.threeStatusCntr)

   #find the total number of people per each status
   def findTotalStatusesFort(self):
      for i in range(self.size):
         for j in range(self.size):
            person = self.pop[i][j]
            if isinstance(person, Individual):     #once again, it's slightly redundant but it's there in case I wanted to add an Indoctrinator
               status = person.status
               if (status == 0):                   #update the counters
                  self.zeroStatusCntr = self.zeroStatusCntr + 1
               elif (status == 1):
                  self.oneStatusCntr = self.oneStatusCntr + 1
               elif (status == 2):
                  self.twoStatusCntr = self.twoStatusCntr + 1
               else:
                  self.threeStatusCntr = self.threeStatusCntr + 1

   #update the persons total accumulation of grievance
   def updateSummedGrievanceList(self):
      for i in range(self.size):
         for j in range(self.size):
            person = self.pop[i][j]
            if (isinstance(person, Individual)):
               person.summedGrievanceList.append(person.summedGrievance)

   #update the lists of the severities for people in seperate statuses (to observe severity's effect on status value)
   def severityLists(self):
      for k in range(self.size):
         for m in range(self.size):
            person = self.pop[k][m]
            if (person.status == 3):
               for i in range(len(person.totalGrievance)):
                  person.sevSum = person.sevSum + person.totalGrievance[i][2]    #update the sum of all of the individuals severity
                  self.status3GrievNum = self.status3GrievNum + 1                #increase the count for the number of grievances that have accrued
               person.avgSeverity = person.sevSum/len(person.totalGrievance)     #average that person's severity and tie it to them
               self.sev3.append(person.avgSeverity)                              #add that person's average severity to the city's tracker of severity per status
            elif (person.status == 2):                                           #repeat the previous steps for each status...
               for i in range(len(person.totalGrievance)):
                  person.sevSum = person.sevSum + person.totalGrievance[i][2]
                  self.status2GrievNum = self.status2GrievNum + 1
               person.avgSeverity = person.sevSum/len(person.totalGrievance)
               self.sev2.append(person.avgSeverity)      
            elif (person.status == 1):
               for i in range(len(person.totalGrievance)):
                  person.sevSum = person.sevSum + person.totalGrievance[i][2]
                  self.status1GrievNum = self.status1GrievNum + 1
               person.avgSeverity = person.sevSum/len(person.totalGrievance)
               self.sev1.append(person.avgSeverity)
            else:
               for i in range(len(person.totalGrievance)):
                  person.sevSum = person.sevSum + person.totalGrievance[i][2]
                  self.status0GrievNum = self.status0GrievNum + 1
               person.avgSeverity = person.sevSum/len(person.totalGrievance)
               self.sev0.append(person.avgSeverity)

   #find the average degree of severity per each status within the population
   def averageSeverities(self):
      for i in range(len(self.sev3)):
         self.sev3Avg = self.sev3Avg + self.sev3[i]      #add the list up
      self.sev3Avg = self.sev3Avg/len(self.sev3)         #divide by the length of the list
      for i in range(len(self.sev2)):                    #repeat
         self.sev2Avg = self.sev2Avg + self.sev2[i]
      self.sev2Avg = self.sev2Avg/len(self.sev2)
      for i in range(len(self.sev1)):
         self.sev1Avg = self.sev1Avg + self.sev1[i]
      self.sev1Avg = self.sev1Avg/len(self.sev1)
      for i in range(len(self.sev0)):
         self.sev0Avg = self.sev0Avg + self.sev0[i]
      self.sev0Avg = self.sev0Avg/len(self.sev0)

   #find the average total number of events that have occurred to each person within a particular status level
   def averageGrievanceNum(self):
      self.status0GrievNum = self.status0GrievNum/self.zeroStatusList[len(self.zeroStatusList)-1]
      self.status1GrievNum = self.status1GrievNum/self.oneStatusList[len(self.oneStatusList)-1]
      self.status2GrievNum = self.status2GrievNum/self.twoStatusList[len(self.twoStatusList)-1]
      self.status3GrievNum = self.status3GrievNum/self.threeStatusList[len(self.twoStatusList)-1]

   #function for simulating COVID-19's effects on the radicalization process
   def pandemic(self):
      self.searchProb = self.searchProb + .21 #(Moonshot CV#, 2021)
      for i in range(self.size):
         for j in range(self.size):
            person= self.pop[i][j]
            if (isinstance(person, Individual)):
               prob = random.random()
               severity = 0
               if (prob < 0.78):    #for 78% of the pandemic has been a significant source of stress (APA, 2020)
                  severityProb = random.random()      #give the event itself a mid to high severity
                  if(severityProb < 0.5):
                     severity = 3
                  else:
                     severity = 4
                  grievanceVal = (math.exp(severity)/4)*random.random()
                  eventList = [grievanceVal, 0, severity]
                  person.totalGrievance.append(eventList)      #add it to that person's grievances
               probForEvent = random.random()
               if (probForEvent < 0.67):  #for 67%, there has been an increased amount of stress during the pandemic (APA, 2020)
                  person.eventProb = person.eventProb + random.random()*.15      #increase the probability that an event will occur for 67% of the population

