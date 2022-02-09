'''
Title:      Final Programming Project: Pandemic Simulation

Purpose:    To understand and create a simulation of a pandemic

Name:       Luke Brown
'''

import random
import numpy as np
import matplotlib.pyplot as plt

class City():

    def __init__(self, size, infectionChance, movementProb, deathProb, recoveryRate):
        self.size = size                    # Size of the neighborhood measured along one dimension
        self.pop = np.zeros((size,size))    # Houses in a neighborhood
        self.emptyProb = 0.05               # Probability that a house will be empty
        self.intitialInfectionProb = 0.01   # Probability that a household will be infected
        self.infectionChance = infectionChance      # probability of infection when selected (based around infected neighbors)
        self.movementProb = movementProb            # probability that you'll move when selected
        self.deathProb = deathProb                  # probability that death will occur if infected
        self.recoveryRate = recoveryRate            # probability that recovery will occur if infected
        self.initzeros = 0                          # keep track of the number of vacancies
        self.infect_cntr = 0                        # keep track of the number of infections

    def populate(self):
        # Populate the neighborhood at random
        for i in range(self.size):          # through column
            for j in range(self.size):      # through row
                # Flip a coin to see if the house is vacant or not
                if random.random() < self.emptyProb:
                    self.pop[i][j] = 0
                    self.initzeros += 1
                else:
                    # Flip another coin to see if the house will be infected or healthy
                    if random.random() < self.intitialInfectionProb:
                        self.pop[i][j] = -1       # -1 == infected
                        self.infect_cntr += 1
                    else:
                        self.pop[i][j] = 1        # 1 == healthy
        vacancies = self.initzeros
        return vacancies

    def show_beginning_topographical(self,title):
        image_topographical = plt.imshow(self.pop, interpolation="bicubic", cmap="RdYlGn")      # topographical map (looks neat)
        cbar = plt.colorbar(image_topographical, ticks=[-1, 0, 1], orientation='horizontal')    # definitive map (shows the data)
        cbar.ax.set_xticklabels(['Infected', 'Vacant', 'Healthy'])  # horizontal colorbar
        
        plt.xticks([])
        plt.yticks([])
        plt.title(title)
        plt.show()

    def show_beginning_definitive(self, title):
        image_definitive = plt.imshow(self.pop, interpolation="nearest", cmap="RdYlGn")
        cbar = plt.colorbar(image_definitive, ticks=[-1, 0, 1], orientation='horizontal')
        cbar.ax.set_xticklabels(['Infected', 'Vacant', 'Healthy'])  # horizontal colorbar

        plt.xticks([])
        plt.yticks([])
        plt.title(title)
        plt.show()

    def show_ending_topographical(self,title):
        image_topographical = plt.imshow(self.pop, interpolation="bicubic", cmap="hot")
        cbar = plt.colorbar(image_topographical, ticks=[-2, -1, 0, 1, 2], orientation='horizontal')
        cbar.ax.set_xticklabels(['Dead', 'Infected', 'Vacant', 'Healthy', 'Recovered'])  # horizontal colorbar

        plt.xticks([])
        plt.yticks([])
        plt.title(title)
        plt.show()

    def show_ending_definitive(self,title):
        image_definitive = plt.imshow(self.pop, interpolation="nearest", cmap="hot")
        cbar = plt.colorbar(image_definitive, ticks=[-2, -1, 0, 1, 2], orientation='horizontal')
        cbar.ax.set_xticklabels(['Dead', 'Infected', 'Vacant', 'Healthy', 'Recovered'])  # horizontal colorbar
        
        plt.xticks([])
        plt.yticks([])
        plt.title(title)
        plt.show()    


    def step(self):
        # pick random house (individual)
        i,j = self.randomHouse()
        # chance of becoming infected
        if self.pop[i][j] == 1:                                        # if healthy
            if self.infectedNeighbors(i,j) < 0.125:        # no infected neighbors
                self.move(i,j)
            elif self.infectedNeighbors(i,j) < 0.25:        # 1 infected neighbor
                if random.random() < self.infectionChance:
                    self.pop[i][j] = -1                     # infect the person
                    self.infect_cntr += 1                   # move the counter up
                    self.move(i,j)                          # move the individual (depending on probability)
                else:
                    self.move(i,j)
            elif self.infectedNeighbors(i,j) < 0.375:        # 2 infected neighbors
                if random.random() < self.infectionChance * 1.5:
                    self.pop[i][j] = -1
                    self.infect_cntr += 1
                    self.move(i,j)
                else:
                    self.move(i,j)
            elif self.infectedNeighbors(i,j) < 0.50:        # 3 infected neighbors
                if random.random() < self.infectionChance * 2:
                    self.pop[i][j] = -1
                    self.infect_cntr += 1
                    self.move(i,j)
                else:
                    self.move(i,j)        
            elif self.infectedNeighbors(i,j) < 0.625:        # 4 infected neighbors
                if random.random() < self.infectionChance * 2.5:
                    self.pop[i][j] = -1
                    self.infect_cntr += 1
                    self.move(i,j)
                else:
                    self.move(i,j)        
            elif self.infectedNeighbors(i,j) < 0.75:        # 5 infected neighbors
                if random.random() < self.infectionChance * 3:
                    self.pop[i][j] = -1
                    self.infect_cntr += 1
                    self.move(i,j)
                else:
                    self.move(i,j)        
            elif self.infectedNeighbors(i,j) < 0.875:        # 6 infected neighbors
                if random.random() < self.infectionChance * 3.5:
                    self.pop[i][j] = -1
                    self.infect_cntr += 1
                    self.move(i,j)
                else:
                    self.move(i,j)        
            elif self.infectedNeighbors(i,j) < 1:        # 7 infected neighbors
                if random.random() < self.infectionChance * 4:
                    self.pop[i][j] = -1
                    self.infect_cntr += 1
                    self.move(i,j)
                else:
                    self.move(i,j)    
            else:                                          # 8 people infected
                if random.random() < self.infectionChance * 4.5:
                    self.pop[i][j] = -1
                    self.infect_cntr += 1
                    self.move(i,j)
                else:
                    self.move(i,j)
        elif self.pop[i][j] == -1:                                 # if infected
            if random.random() < self.deathProb:                   # chance of death
                self.pop[i][j] = -2                                # -2 == dead
            elif random.random() < self.recoveryRate:              # chance of recovering (keep track of number of times selected?)
                self.pop[i][j] = 2                                 # 2 == recovered   
                self.move(i,j)                              
        elif self.pop[i][j] == 2:                                  # if recovered move
            self.move(i,j)
        stats = self.Statistics()                                  # call stats
        infect_cntr = self.infect_cntr
        return stats, infect_cntr

    def randomHouse(self):
        found = False
        while not found:
            i = random.randint(0,self.size-1)       # selects a random individual making sure it's occupied (!=0)
            j = random.randint(0,self.size-1)
            if self.pop[i][j] != 0:
                found = True
        return i, j

    def randomVacant(self):
        found = False
        while not found:
            i = random.randint(0,self.size-1)       # selects a random vacant space making sure it's unoccupied (==0)
            j = random.randint(0,self.size-1)
            if self.pop[i][j] == 0:
                found = True
        return i, j

    def infectedNeighbors(self, i, j):
        me = self.pop[i][j]                 # selected individual
        infected = 0                        # counter
        # check infected neighbors
        for x in range(i-1,i+2):            # up & down
            ni = x%self.size
            for y in range(j-1,j+2):        # left % right
                nj = y%self.size            # diagonal as well
                if self.pop[ni][nj] == -1:  # if infected increase the cntr
                    infected += 1
        return infected/8                   # take the counter and divide by the number of people looked at to get the value (probability)

    def move(self, i, j):
        if self.pop[i][j] == 1:                                  # if healthy
            if random.random() < self.movementProb * 1.5:        # more likely to move       MAKE SURE < 1
                newi,newj = self.randomVacant()             
                self.pop[newi][newj] = self.pop[i][j]
                self.pop[i][j] = 0                               # move and make vacant
        elif self.pop[i][j] == -1:                               # if infected
            if random.random() < self.movementProb / 2:          # less likely to move
                newi,newj = self.randomVacant()     
                self.pop[newi][newj] = self.pop[i][j]
                self.pop[i][j] = 0
        elif self.pop[i][j] == 2:                                # if recovered
            if random.random() < self.movementProb * 2:          # more likely to move
                newi,newj = self.randomVacant()     
                self.pop[newi][newj] = self.pop[i][j]
                self.pop[i][j] = 0     

    '''
    STATISTICS SECTION

        Return a list of these values (index in to get the value to graph)
        Keep track of number of infected (red)

        Keep track of number of fatalities (white or yellow)

        Keep track of number of recovered (blue)

        Keep track of number of healthy (green)

    '''

    def Statistics(self):
        # create an array with zeros
        # first index is infected
        # second is fatalities
        # third is recovered
        # fourth is healthy
        stats = np.zeros(4)
        for i in range(self.size):
            for j in range(self.size):
                if self.pop[i][j] == -1:
                    stats[0] += 1
                elif self.pop[i][j] == -2:
                    stats[1] += 1
                elif self.pop[i][j] == 2:
                    stats[2] += 1
                elif self.pop[i][j] == 1:
                    stats[3] += 1
        return stats

