from radicalIndividual import Individual
from radicalLandscape import Landscape
import numpy as np 
import matplotlib.pyplot as plt
import random
import math
from statistics import mean
import time

#Functions for data visualization and statistical manipulation

#make a grid of the particular individual's status's
def statusArray():
   grid = np.empty((city.size,city.size))
   for i in range(city.size):
      for j in range(city.size):
         person = city.pop[i][j]
         if isinstance(person, Individual):
            grid[i][j] = person.status
   return grid

#simple average function of a list
def avg(listIn):
   value = 0
   for i in range(len(listIn)):
      value = listIn[i] + value
   avg = value/len(listIn)
   return avg

#percent change of two values within two seperate lists (pre vs post COVID)
def percChange(listOne, listTwo):
   newList = []
   for i in range(4):
      value = ((listOne[i] - listTwo[i])/listTwo[i])*100
      newList.append(value)
   return newList

def graphIndividual(personIn):
   plt.plot(stepList, person.summedGrievanceList)
   for i in range(len(person.searchList)):
      day = person.searchList[i][0]
      yValue = person.searchList[i][1]
      plt.plot(day, yValue, color = 'red', marker = 'x')
   for i in range(len(person.postList)):
      day = person.postList[i][0]
      yValue = person.postList[i][1]
      plt.plot(day, yValue, color = 'orange', marker = '+')
   plt.axhspan(0, person.myStatus0, facecolor='green', alpha=0.3, label= 'STATUS: 0')
   plt.axhspan(person.myStatus0, person.myStatus1, facecolor='yellow', alpha=0.3, label = 'STATUS: 1')
   plt.axhspan(person.myStatus1, person.myStatus2, facecolor='orange', alpha=0.3, label = 'STATUS: 2')
   plt.axhspan(person.myStatus2, person.myStatus3, facecolor='red', alpha=0.3, label = 'STATUS: 3')
   plt.legend()
   plt.xlabel("Days")
   plt.ylabel("Grievance Value")
   plt.title("Grievance and Radicalization")
   plt.grid(b=True, which='major', color='#666666', linestyle='-')
   plt.minorticks_on()
   plt.grid(b=True, which='minor', color='#999999', linestyle='-', alpha=0.2)
   plt.show()

#The following are lists in order to hold data values at different time steps:
#each list will contain 4 values for each person within each of those statuses at that particular time
ar180 = []
ar210 = []
ar240 = []
ar270 = []
ar300 = []
ar330 = []
ar360 = []

#any list with 'Vid' is for statistical purposes with the second run (post-Covid)
ar180Vid = []
ar210Vid = []
ar240Vid = []
ar270Vid = []
ar300Vid = []
ar330Vid = []
ar360Vid = []

#Each of the following lists keep track of how many people are within each status per each run:
masterZeroList = []
masterOneList = []
masterTwoList = []
masterThreeList = []
masterSearchList = []

masterZeroListVid = []
masterOneListVid = []
masterTwoListVid = []
masterThreeListVid = []
masterSearchListVid = []

#The following lists keep track of the average severity for each status group:
masterSev0 = []
masterSev1 = []
masterSev2 = []
masterSev3 = []

masterSev0Vid = []
masterSev1Vid = []
masterSev2Vid = []
masterSev3Vid = []

#The following lists keep track of the average length of grievances for each status group:
status0GrievLen = []
status1GrievLen = []
status2GrievLen = []
status3GrievLen = []

status0GrievLenVid = []
status1GrievLenVid = []
status2GrievLenVid = []
status3GrievLenVid = []

'''
VARIABLES TO ALTER FOR THE SIMULATIONS
'''
numberOfSims = 5     #variable for the number of simulations run per each grouping
threshold = 100      #max threshold for each person
totalSteps = 366     #number of 'days' within the simulation (one year)
popSize = 40         #number of people within the population

#to get the x-axis for graphing purposes
stepList = []
for i in range(totalSteps):
   stepList.append(i)

#BEGINNING OF SIMULATION
for run in range(numberOfSims):
   print("RUNNING BASE CASE: " + str(run+1) + " out of " + str(numberOfSims))
   city = Landscape(popSize)                      #size of the city
   oneStep = city.size * city.size           #gives everyone a chance to move
   city.populate()                           #population of the city
   for i in range(totalSteps):               #time steps
      if(i == 180):                          #when it's 180 days in... record the following statistics, the same applies for the rest of the time steps
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar180.append(allStatus)
      if(i == 210):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar210.append(allStatus) 
      if(i == 240):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar240.append(allStatus) 
      if(i == 270):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar270.append(allStatus) 
      if(i ==300):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar300.append(allStatus)    
      if(i ==330):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar330.append(allStatus) 
      if(i ==360):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar360.append(allStatus)          
      for j in range(oneStep):
            city.step(threshold, i, city.searchProb)     #gives the opportunity of an event occurring, searching, and posting
      city.updateSummedGrievanceList()                   #calls to record current data as well as reset static variables for the next run
      city.findTotalStatusesFort()
      city.appendTotalStatuses()
      city.resetTotalStatuses()
      city.resetAndUpdateSearchers()
      if(i%5 == 0):
         print(str(i) + " out of 365")    #printing the duration of the simulation
   #getting the total data from the city
   city.severityLists()
   city.averageSeverities()
   city.averageGrievanceNum()
   plt.figure()
   #get individual's graphs
   for i in range(5):
      i,j = city.randomPerson() #pick a random person
      person = city.pop[i][j]
      graphIndividual(person)


   #appending the accumulated data to lists for more processing (stats & graphs)
   masterSev0.append(city.sev0Avg)
   masterSev1.append(city.sev1Avg)
   masterSev2.append(city.sev2Avg)
   masterSev3.append(city.sev3Avg)
   masterZeroList.append(city.zeroStatusList)
   masterOneList.append(city.oneStatusList)
   masterTwoList.append(city.twoStatusList)
   masterThreeList.append(city.threeStatusList)
   masterSearchList.append(city.searchersList)
   status0GrievLen.append(city.status0GrievNum)
   status1GrievLen.append(city.status1GrievNum)
   status2GrievLen.append(city.status2GrievNum)
   status3GrievLen.append(city.status3GrievNum)
   imageTopographical = plt.imshow(statusArray(), interpolation="bicubic", cmap="RdYlGn_r")
   cbar = plt.colorbar(imageTopographical, ticks=[0, 1, 2, 3], orientation='horizontal')
   cbar.ax.set_xticklabels(['STATUS:    0', '1', '2', '3'])  # horizontal colorbar
   plt.title("City at Day: " + str(i))
   plt.show()

   imageTopographical = plt.imshow(statusArray(), interpolation="nearest", cmap="RdYlGn_r")
   cbar = plt.colorbar(imageTopographical, ticks=[0, 1, 2, 3], orientation='horizontal')
   cbar.ax.set_xticklabels(['STATUS:    0', '1', '2', '3'])  # horizontal colorbar
   plt.title("City at Day: " + str(i))
   plt.show()
   print()

#converting the lists to np.arrays
masterZeroList = np.array(masterZeroList)
masterOneList = np.array(masterOneList)
masterTwoList = np.array(masterTwoList)
masterThreeList = np.array(masterThreeList)
masterSearchList = np.array(masterSearchList)

#Finding the average per each column (run)
avgMasterZeroList = np.mean(masterZeroList, axis = 0)
avgMasterOneList = np.mean(masterOneList, axis = 0)
avgMasterTwoList = np.mean(masterTwoList, axis = 0)
avgMasterThreeList = np.mean(masterThreeList, axis = 0)
avgMasterSearchList = np.mean(masterSearchList, axis = 0)

#Doing the same process with the specific time steps
ar180 = np.array(ar180)
ar210 = np.array(ar210)
ar240 = np.array(ar240)
ar270 = np.array(ar270)
ar300 = np.array(ar300)
ar330 = np.array(ar330)
ar360 = np.array(ar360)

avg180 = np.mean(ar180, axis = 0)
avg210 = np.mean(ar210, axis = 0)
avg240 = np.mean(ar240, axis = 0)
avg270 = np.mean(ar270, axis = 0)
avg300 = np.mean(ar300, axis = 0)
avg330 = np.mean(ar330, axis = 0)
avg360 = np.mean(ar360, axis = 0)

#Since the severities are just scalars, they can be averaged using my average function
sev0Avg = (avg(masterSev0))
sev1Avg = (avg(masterSev1))
sev2Avg = (avg(masterSev2))
sev3Avg = (avg(masterSev3))

#The same as above can be applied to the amount of grievances per each status
status0GrievNumAvg = avg(status0GrievLen)
status1GrievNumAvg = avg(status1GrievLen)
status2GrievNumAvg = avg(status2GrievLen)
status3GrievNumAvg = avg(status3GrievLen)

#Plotting
plt.plot(stepList, avgMasterZeroList, color = 'green', label = "Status: 0")
plt.plot(stepList, avgMasterOneList, color = 'yellow', label = "Status: 1")
plt.plot(stepList, avgMasterTwoList, color = 'orange', label = "Status: 2")
plt.plot(stepList, avgMasterThreeList, color = 'red', label = "Status: 3")
plt.plot(stepList, avgMasterSearchList, color = 'cyan', label = "Searchers")
plt.legend()
plt.xlabel("Days")
plt.ylabel("Number of People")
plt.title("Radical City")
plt.show()








#The following is the same as above, however it includes the pandemic function halfway through the simulation, 
# it also includes different lists to append to in order to compare the effects of change between pre-covid and post-covid
for run in range(numberOfSims):
   print("RUNNING POST-COVID19 CASE: " + str(run+1) + " out of " + str(numberOfSims))
   city = Landscape(popSize)
   oneStep = city.size * city.size         
   city.populate()
   for i in range(totalSteps):
      if(i == 180):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar180Vid.append(allStatus)
      if(i == 210):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar210Vid.append(allStatus) 
      if(i == 240):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar240Vid.append(allStatus) 
      if(i == 270):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar270Vid.append(allStatus) 
      if(i ==300):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar300Vid.append(allStatus)    
      if(i ==330):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar330Vid.append(allStatus) 
      if(i ==360):
         zeroStatus = city.zeroStatusList[len(city.zeroStatusList)-1]
         oneStatus = city.oneStatusList[len(city.oneStatusList)-1]
         twoStatus = city.twoStatusList[len(city.twoStatusList)-1]  
         threeStatus = city.threeStatusList[len(city.threeStatusList)-1]
         allStatus = [zeroStatus, oneStatus, twoStatus, threeStatus]
         ar360Vid.append(allStatus)             
      if (i == totalSteps/2):       #calling the pandemic function at the halfway point
         city.pandemic()
      if (i > totalSteps/2 and i < 195 and numberOfSims ==1):     #display the changes in people after each day for a short period
         imageTopographical = plt.imshow(statusArray(), interpolation="bicubic", cmap="RdYlGn_r")
         cbar = plt.colorbar(imageTopographical, ticks=[0, 1, 2, 3], orientation='horizontal')
         cbar.ax.set_xticklabels(['STATUS:    0', '1', '2', '3'])  # horizontal colorbar
         plt.title("City at Day: " + str(i))
         plt.show()

         imageTopographical = plt.imshow(statusArray(), interpolation="nearest", cmap="RdYlGn_r")
         cbar = plt.colorbar(imageTopographical, ticks=[0, 1, 2, 3], orientation='horizontal')
         cbar.ax.set_xticklabels(['STATUS:    0', '1', '2', '3'])  # horizontal colorbar
         plt.title("City at Day: " + str(i))
         plt.show()
      for j in range(oneStep):
            city.step(threshold, i, city.searchProb)
      city.updateSummedGrievanceList()
      city.findTotalStatusesFort()
      city.appendTotalStatuses()
      city.resetTotalStatuses()
      city.resetAndUpdateSearchers()
      if(i%5 == 0):
         print(str(i) + " out of 365")

   city.severityLists()
   city.averageSeverities()
   city.averageGrievanceNum()
   masterSev0Vid.append(city.sev0Avg)
   masterSev1Vid.append(city.sev1Avg)
   masterSev2Vid.append(city.sev2Avg)
   masterSev3Vid.append(city.sev3Avg)
   masterZeroListVid.append(city.zeroStatusList)
   masterOneListVid.append(city.oneStatusList)
   masterTwoListVid.append(city.twoStatusList)
   masterThreeListVid.append(city.threeStatusList)
   masterSearchListVid.append(city.searchersList)
   status0GrievLenVid.append(city.status0GrievNum)
   status1GrievLenVid.append(city.status1GrievNum)
   status2GrievLenVid.append(city.status2GrievNum)
   status3GrievLenVid.append(city.status3GrievNum)
   #get individual's graphs
   for i in range(5):
      i,j = city.randomPerson() #pick a random person
      person = city.pop[i][j]
      graphIndividual(person)
   imageTopographical = plt.imshow(statusArray(), interpolation="bicubic", cmap="RdYlGn_r")
   cbar = plt.colorbar(imageTopographical, ticks=[0, 1, 2, 3], orientation='horizontal')
   cbar.ax.set_xticklabels(['STATUS:    0', '1', '2', '3'])  # horizontal colorbar
   plt.title("City at Day: " + str(i))
   plt.show()

   imageTopographical = plt.imshow(statusArray(), interpolation="nearest", cmap="RdYlGn_r")
   cbar = plt.colorbar(imageTopographical, ticks=[0, 1, 2, 3], orientation='horizontal')
   cbar.ax.set_xticklabels(['STATUS:    0', '1', '2', '3'])  # horizontal colorbar
   plt.title("City at Day: " + str(i))
   plt.show()
   print()

#convert to np.arrays for statistical reasons
masterZeroListVid = np.array(masterZeroListVid)
masterOneListVid = np.array(masterOneListVid)
masterTwoListVid = np.array(masterTwoListVid)
masterThreeListVid = np.array(masterThreeListVid)
masterSearchListVid = np.array(masterSearchListVid)

#calculating the mean of all of the runs
avgMasterZeroListVid = np.mean(masterZeroListVid, axis = 0)
avgMasterOneListVid = np.mean(masterOneListVid, axis = 0)
avgMasterTwoListVid = np.mean(masterTwoListVid, axis = 0)
avgMasterThreeListVid = np.mean(masterThreeListVid, axis = 0)
avgMasterSearchListVid = np.mean(masterSearchListVid, axis = 0)

#same process as above
ar180Vid = np.array(ar180Vid)
ar210Vid = np.array(ar210Vid)
ar240Vid = np.array(ar240Vid)
ar270Vid = np.array(ar270Vid)
ar300Vid = np.array(ar300Vid)
ar330Vid = np.array(ar330Vid)
ar360Vid = np.array(ar360Vid)

avg180Vid = np.mean(ar180Vid, axis = 0)
avg210Vid = np.mean(ar210Vid, axis = 0)
avg240Vid = np.mean(ar240Vid, axis = 0)
avg270Vid = np.mean(ar270Vid, axis = 0)
avg300Vid = np.mean(ar300Vid, axis = 0)
avg330Vid = np.mean(ar330Vid, axis = 0)
avg360Vid = np.mean(ar360Vid, axis = 0)

#scalar value, so called my average function
status0GrievNumAvgVid = avg(status0GrievLenVid)
status1GrievNumAvgVid = avg(status1GrievLenVid)
status2GrievNumAvgVid = avg(status2GrievLenVid)
status3GrievNumAvgVid = avg(status3GrievLenVid)

#Plotting post-COVID data
plt.plot(stepList, avgMasterZeroListVid, color = 'green', label = "Status: 0")
plt.plot(stepList, avgMasterOneListVid, color = 'yellow', label = "Status: 1")
plt.plot(stepList, avgMasterTwoListVid, color = 'orange', label = "Status: 2")
plt.plot(stepList, avgMasterThreeListVid, color = 'red', label = "Status: 3")
plt.plot(stepList, avgMasterSearchListVid, color = 'cyan', label = "Searchers")
plt.legend()
plt.xlabel("Days")
plt.ylabel("Number of People")
plt.title("Radical City (post-Covid)")
plt.show()

#scalar value, so called my average function
sev0AvgVid = (avg(masterSev0Vid))
sev1AvgVid = (avg(masterSev1Vid))
sev2AvgVid = (avg(masterSev2Vid))
sev3AvgVid = (avg(masterSev3Vid))

#Technically this is percent change, NOT percent difference... 
# since it's a scalar I just calculate it here with no function call
sev0PercDiff = ((sev0AvgVid-sev0Avg)/sev0Avg)*100
sev1PercDiff = ((sev1AvgVid-sev1Avg)/sev1Avg)*100
sev2PercDiff = ((sev2AvgVid-sev2Avg)/sev2Avg)*100
sev3PercDiff = ((sev3AvgVid-sev3Avg)/sev3Avg)*100

#Call my percChange function when dealing with lists in order to determine pre vs post COVID change
pc180 = percChange(avg180Vid, avg180)
pc210 = percChange(avg210Vid, avg210)
pc240 = percChange(avg240Vid, avg240)
pc270 = percChange(avg270Vid, avg270)
pc300 = percChange(avg300Vid, avg300)
pc330 = percChange(avg330Vid, avg330)
pc360 = percChange(avg360Vid, avg360)

#Format the values in order for them to print neatly
avg180 = ['{:.2f}'.format(elem) for elem in avg180]
avg210 = ['{:.2f}'.format(elem) for elem in avg210]
avg240 = ['{:.2f}'.format(elem) for elem in avg240]
avg270 = ['{:.2f}'.format(elem) for elem in avg270]
avg300 = ['{:.2f}'.format(elem) for elem in avg300]
avg330 = ['{:.2f}'.format(elem) for elem in avg330]
avg360 = ['{:.2f}'.format(elem) for elem in avg360]
avg180Vid = ['{:.2f}'.format(elem) for elem in avg180Vid]
avg210Vid = ['{:.2f}'.format(elem) for elem in avg210Vid]
avg240Vid = ['{:.2f}'.format(elem) for elem in avg240Vid]
avg270Vid = ['{:.2f}'.format(elem) for elem in avg270Vid]
avg300Vid = ['{:.2f}'.format(elem) for elem in avg300Vid]
avg330Vid = ['{:.2f}'.format(elem) for elem in avg330Vid]
avg360Vid = ['{:.2f}'.format(elem) for elem in avg360Vid]

#Finding the amount of searchers at the end of the simulation (for both BASE CASE & POST COVID)
searchers = avgMasterSearchList[len(avgMasterSearchList)-1]
searchersVid = avgMasterSearchListVid[len(avgMasterSearchListVid)-1]

#Calculating percent change for each scalar (pre vs post COVID)
pcSearchers = ((searchersVid-searchers)/(searchers))*100
pcSev0 = ((sev0AvgVid-sev0Avg)/(sev0Avg))*100
pcSev1 = ((sev1AvgVid-sev1Avg)/(sev1Avg))*100
pcSev2 = ((sev2AvgVid-sev2Avg)/(sev2Avg))*100
pcSev3 = ((sev3AvgVid-sev3Avg)/(sev3Avg))*100
pcNum0 = ((status0GrievNumAvgVid-status0GrievNumAvg)/(status0GrievNumAvg))*100
pcNum1 = ((status1GrievNumAvgVid-status1GrievNumAvg)/(status1GrievNumAvg))*100
pcNum2 = ((status2GrievNumAvgVid-status2GrievNumAvg)/(status2GrievNumAvg))*100
pcNum3 = ((status3GrievNumAvgVid-status3GrievNumAvg)/(status3GrievNumAvg))*100


#Printing out the statistics into the terminal
print()
print()
print("---------------------------------------------------------------------")
print()
print("STATISTICS: BASE CASE")
print("~~~~~~~~~~~~~~~~~~~~~")
print("NUMBER OF PEOPLE PER EACH STATUS:")
print("  STATUS:            0         1        2       3")
print("  TIME STEP 180 " + str(avg180))
print("  TIME STEP 210 " + str(avg210))
print("  TIME STEP 240 " + str(avg240))
print("  TIME STEP 270 " + str(avg270))
print("  TIME STEP 300 " + str(avg300))
print("  TIME STEP 330 " + str(avg330))
print("  TIME STEP 360 " + str(avg360))
print()
print("AVERAGE SEVERITY VALUE PER EACH STATUS:")
print("  STATUS 0: " + str(round(sev0Avg, 3)))
print("  STATUS 1: " + str(round(sev1Avg, 3)))
print("  STATUS 2: " + str(round(sev2Avg, 3)))
print("  STATUS 3: " + str(round(sev3Avg, 3)))
print()
print("AVERAGE NUMBER OF GRIEVANCES PER EACH STATUS:")
print("  STATUS 0: " + str(round(status0GrievNumAvg, 3)))
print("  STATUS 1: " + str(round(status1GrievNumAvg, 3)))
print("  STATUS 2: " + str(round(status2GrievNumAvg, 3)))
print("  STATUS 3: " + str(round(status3GrievNumAvg, 3)))
print()
print("NUMBER OF SEARCHERS: " + str(searchers))
print()
print("  -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -  ")
print()
print("STATISTICS: POST-COVID 19")
print("~~~~~~~~~~~~~~~~~~~~~~~~~")
print("NUMBER OF PEOPLE PER EACH STATUS:")
print("  STATUS:            0         1        2       3")
print("  TIME STEP 180 " + str(avg180Vid))
print("  TIME STEP 210 " + str(avg210Vid))
print("  TIME STEP 240 " + str(avg240Vid))
print("  TIME STEP 270 " + str(avg270Vid))
print("  TIME STEP 300 " + str(avg300Vid))
print("  TIME STEP 330 " + str(avg330Vid))
print("  TIME STEP 360 " + str(avg360Vid))
print()
print("AVERAGE SEVERITY VALUE PER EACH STATUS:")
print("  STATUS 0: " + str(round(sev0AvgVid, 3)))
print("  STATUS 1: " + str(round(sev1AvgVid, 3)))
print("  STATUS 2: " + str(round(sev2AvgVid, 3)))
print("  STATUS 3: " + str(round(sev3AvgVid, 3)))
print()
print("AVERAGE NUMBER OF GRIEVANCES PER EACH STATUS:")
print("  STATUS 0: " + str(round(status0GrievNumAvgVid, 3)))
print("  STATUS 1: " + str(round(status1GrievNumAvgVid, 3)))
print("  STATUS 2: " + str(round(status2GrievNumAvgVid, 3)))
print("  STATUS 3: " + str(round(status3GrievNumAvgVid, 3)))
print()
print("NUMBER OF SEARCHERS: " + str(searchersVid))
print()
print("  -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -  ")
print()
print("PERCENT CHANGE BETWEEN BASE CASE AND POST-COVID 19:")
print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
print("STATUS:            0       1        2       3")
print("  TIME STEP 180 " + str(np.round(pc180, decimals = 3)))
print("  TIME STEP 210 " + str(np.round(pc210, decimals = 3)))
print("  TIME STEP 240 " + str(np.round(pc240, decimals = 3)))
print("  TIME STEP 270 " + str(np.round(pc270, decimals = 3)))
print("  TIME STEP 300 " + str(np.round(pc300, decimals = 3)))
print("  TIME STEP 330 " + str(np.round(pc330, decimals = 3)))
print("  TIME STEP 360 " + str(np.round(pc360, decimals = 3)))
print()
print("AVERAGE SEVERITY PER EACH STATUS:")
print("  STATUS 0: " + str(round(pcSev0, 3)))
print("  STATUS 1: " + str(round(pcSev1, 3)))
print("  STATUS 2: " + str(round(pcSev2, 3)))
print("  STATUS 3: " + str(round(pcSev3, 3)))
print()
print("AVERAGE NUMBER OF GRIEVANCES PER EACH STATUS:")
print("  STATUS 0: " + str(round(pcNum0, 3)))
print("  STATUS 1: " + str(round(pcNum1, 3)))
print("  STATUS 2: " + str(round(pcNum2, 3)))
print("  STATUS 3: " + str(round(pcNum3, 3)))
print()
print("PERCENT CHANGE OF SEARCHERS: " + str(round(pcSearchers, 3)))
print()
print("---------------------------------------------------------------------")

'''
plt.figure()
person = city.pop[1][1]
plt.plot(stepList, person.summedGrievanceList)
for i in range(len(person.searchList)):
   day = person.searchList[i][0]
   yValue = person.searchList[i][1]
   plt.plot(day, yValue, color = 'red', marker = 'x')
for i in range(len(person.postList)):
   day = person.postList[i][0]
   yValue = person.postList[i][1]
   plt.plot(day, yValue, color = 'red', marker = '+')
plt.axhspan(0, person.myStatus0, facecolor='green', alpha=0.3, label= 'STATUS: 0')
plt.axhspan(person.myStatus0, person.myStatus1, facecolor='yellow', alpha=0.3, label = 'STATUS: 1')
plt.axhspan(person.myStatus1, person.myStatus2, facecolor='orange', alpha=0.3, label = 'STATUS: 2')
plt.axhspan(person.myStatus2, person.myStatus3, facecolor='red', alpha=0.3, label = 'STATUS: 3')
plt.legend()
plt.xlabel("Days")
plt.ylabel("Grievance Value")
plt.title("Grievance and Radicalization")
plt.grid(b=True, which='major', color='#666666', linestyle='-')
plt.minorticks_on()
plt.grid(b=True, which='minor', color='#999999', linestyle='-', alpha=0.2)
plt.show()
'''

