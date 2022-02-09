import numpy as np 
import matplotlib.pyplot as plt
import random
import math

class Individual():

   totalPosts = 0             #static variable for tracking ALL posts
   todaysPosts = 0            #tracks todays posts
   postList = []              #all posts by all individuals
   searchers = 0              #number of people searching

   def __init__(self, totalGrievance, status, idIn):
      self.totalGrievance = []         #list storing all grievances
      self.status = 0                  #one's status
      self.summedGrievance = 0         #total grievance summed
      self.posts = 0                   #number of posts
      self.id = idIn                   #individuals id
      self.summedGrievanceList = []    #list of summed grievance at the end of the day
      self.searchList = []             #searched grievances
      self.postList = []               #posted grievances

      self.myStatus0 = 0               #individual customized thresholds
      self.myStatus1 = 0
      self.myStatus2 = 0
      self.myStatus3 = 0
      self.previouslyCreated = False   #variable for creating the thresholds (I made it unnecessarily complicated)

      self.postIndexes = []            #keeping track of what they've already posted, so they dont post the same thing again
      self.searchIndexes = []          #keeping track of what they've already searched for, so they aren't influence by the same post multiple times
      self.curiosity = random.random() #individuals willingness to search
      self.eventProb = 0.377           #probability of a stressful event occuring at any day throughout the week (Almeida et al, 2002)
      self.network = []                #rudimentary social network - keeps track of who the searcher has found
      self.sevSum = 0                  #individual's total severity (of all grievances)
      self.avgSeverity = 0             #average of the severity sum

   #sums one's total grievances
   def findTotalGrievanceValue(self):
      summedTotalGrievance = 0
      for i in range(len(self.totalGrievance)):
         summedTotalGrievance = summedTotalGrievance + self.totalGrievance[i][0]    #grievance value is in the 0th index
      self.summedGrievance = summedTotalGrievance

   #setter for posts
   def setPosts(self, postsIn):
      self.posts = postsIn

   #setter for status
   def setStatus(self, statusIn):
      self.status = statusIn

   #setter for grievance
   def setTotalGrievance(self, listIn):
      self.totalGrievance = listIn

   #decay function for each grievance value
   def grievDecay(self):
      #decay is in months
      for i in range(len(self.totalGrievance)):
         value = self.totalGrievance[i][0]
         #sigmoid decay... value betweeen 0 and 1 shifted to the right on the x axis to make asymptote 1 as it crosses the x-axis
         iteration = self.totalGrievance[i][1]           #day since event occured is kept in the 1st index of the grievance          
         day = self.totalGrievance[i][1]/30.4167         #iteration/day based upon sigmoid equation (months) (30.4167 days/month/year)
         severity = self.totalGrievance[i][2]            #weighted per each severity
         if(severity == 1):                              #for minor grievance
            if (iteration > 50):                         #in order to not obtain an overflow error due to how small the number is, the day is permantly kept at 50 (value is practically 0)
               day = 50/30.4167
            newGrievVal = value * 1/(1+ (math.exp(65*day-4.8)))            #extremely quick decay (about 2 days) for minor inconveniences
         elif(severity == 2):
            newGrievVal = value * 1/(1+ (math.exp(9*day-4.8)))             #quick decay (about 1 month) for inconveniences
         elif(severity == 3):
            newGrievVal = value * 1/(1+ (math.exp(2.7*day-4.8)))           #slow decay (about 3 months) for grievance
         else:
            newGrievVal = value * 1/(1+ (math.exp(0.9*day-4.8)))           #extremely slow decay (about 9 months) for major grievances
        
         self.totalGrievance[i][0] = round(newGrievVal,12)                        #update the grievance value after decay
         self.totalGrievance[i][1] = self.totalGrievance[i][1] + 1                #update the day by one for that particular grievance

   #possible event to accrue grievance
   def event(self):
         randSeverity = random.random()   #based upon the average reported subjective response to a stressful event out of 5
         event = False                    #somewhat unnecessary, indicates whether the event has occurred or not
         if (randSeverity < 0.20):
            severity = 1
         elif (randSeverity < 0.40):
            severity = 2
         elif (randSeverity < 0.70):
            severity = 3
         else:
            severity = 4
         randEvent = random.random()
         if (randEvent < self.eventProb):    #if random prob is less than the probability that an event will occur
            event = True      
         if (event):                         #if the event occurred
            grievanceVal = (math.exp(severity)/4)*random.random()       #with grievance heavily weighted by a percentage of the exponent of the severity divided by 4
            eventList = [grievanceVal, 0, severity]                     #put the event in a list with the day always being 0
            self.totalGrievance.append(eventList)                       #append to their grievances

   #determining what status one is at a given time
   def checkStatus(self, thresholdIn):
      status = self.status                      #get the current status "level"
      summedGrievance = self.summedGrievance    #get the current summed grievance
      # ~0-25 stage 0, ~25-50 stage 1, ~50-75 stage 2, ~75-100 stage 3 (all have personalized thresholds per each status)
      if (summedGrievance < self.myStatus0):
         self.setStatus(0)
      elif (summedGrievance < self.myStatus1):
         self.setStatus(1)
      elif (summedGrievance < self.myStatus2):
         self.setStatus(2)
      elif (summedGrievance < self.myStatus3):
         self.setStatus(3)

   #updates the post list
   def post(self, dayIn):
      point = [dayIn, 0]               #plotting and statistical purposes
      self.postList.append(point) 
      posts = self.posts + 1
      Individual.totalPosts = Individual.totalPosts + 1
      Individual.todaysPosts = Individual.todaysPosts + 1
      self.setPosts(posts)

      maxVal = 0     #finding the one's maximum grievance
      index = 0      #finding where that grievance occurs in the list
      for i in range(len(self.totalGrievance)):
         if (self.totalGrievance[i][0] > maxVal and i not in self.postIndexes):  #make sure that it's the max AND they haven't already posted that event
            maxVal =  self.totalGrievance[i][0]
            index = i
      self.postIndexes.append(index)               #updating what they've already posted
      maxEvent = self.totalGrievance[index]        #finding the event with the max
      value = maxEvent[0]                          #obtaining the grievance value
      severity = maxEvent[2]                       #obtaining the severity
      newList = [value, severity, self.id]         #creating a new list for posts
      Individual.postList.append(newList)          #appending their post to their list of posts

   #allows one to search the posts for an event similar to theirs (confirmation bias)
   def search(self, dayIn, searchProbIn):
      searchProb = searchProbIn     #the likelihood to search... estimated from Moonshot CVE's data
      if (self.curiosity < searchProb):
         Individual.searchers = Individual.searchers + 1    #number of searchers is updated
         point = [dayIn, 0]               #plotting purposes
         self.searchList.append(point)             
         topFiveIndex = []                #list containing their top 5 grievances
         for j in range(5):
            index = 0                     #where their event occurs
            maxVal = 0                    #the value of the event
            for i in range(len(self.totalGrievance)):
               if (self.totalGrievance[i][0] > maxVal and i not in topFiveIndex):      #go through the grievances and make sure the grievance is NOT already in the top 5 list
                  maxVal =  self.totalGrievance[i][0]
                  index = i
            topFiveIndex.append(index)
         randomInt = random.randint(0, 4)       #random number in order to index into the top 5 grievance values
         grievanceLoc = topFiveIndex[randomInt] #random top 5 event
         grievance = self.totalGrievance[grievanceLoc]   #finding that event in the overall list based upon the value within the top 5 list
         myValue = grievance[0]                 #finding the value of the event
         mySeverity = grievance[2]              #finding the severity of the event
         limit = 0                              #putting a cap on the amount of searches one can do in a day
         for i in range(len(Individual.postList)):    #go thru the posts
            if (limit >= 4):                          #if they exceed their maximum number of searches
               return                                 #it prevents them from continuing
            value = Individual.postList[i][0]      #obtaining the relevant information from the posts
            severity = Individual.postList[i][1]
            ident = Individual.postList[i][2]

            valueDifference = abs(value-myValue)         #find the differences to see how similar the events are
            severityDifference = abs(severity-mySeverity)
            rando = random.random()
            if (valueDifference < 0.01 and severityDifference == 0 and ident!=self.id and i not in self.searchIndexes and limit < 4):  #if the values are close enough, it's not a post from THIS person, they havent looked at it before, and haven't exceeded their maximum number of searches
               self.searchIndexes.append(i)           #prevents them from looking at the same post twice
               self.totalGrievance[grievanceLoc][0] = self.totalGrievance[grievanceLoc][0] + random.random() #updating their grievance due to confirmation bias
               self.network.append(ident)       #keeps track of who's posts they've read
               limit = limit + 1                #increase the counter for the limit

   #creates the individualized statuses           
   def createMyStatuses(self, thresholdIn):
      if (not self.previouslyCreated):
         self.previouslyCreated = True    #this was made so their thresholds are not continuously updated throughout the step loop
         self.myStatus0 = (thresholdIn * 3/6) + (random.randint(-5,5) * random.random())     #slight variation of 3/6 of the total threshold
         self.myStatus1 = (thresholdIn * 4/6) + (random.randint(-5,5) * random.random())     #slight variation of 4/6 of the total threshold
         self.myStatus2 = (thresholdIn * 5/6) + (random.randint(-5,5) * random.random())     #slight variation of 5/6 of the total threshold
         self.myStatus3 = (thresholdIn) + (random.randint(-5,5) * random.random())           #slight vairiation of the max threshold
