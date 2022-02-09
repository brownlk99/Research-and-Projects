'''
Title:      Final Programming Project: Pandemic Simulation

Purpose:    To understand and create a simulation of a pandemic

Name:       Luke Brown
'''

from pandemic_class import City   # from the file it imports the specific class
import numpy as np
import matplotlib.pyplot as plt
import random
import math as math

def Pandemic():

    # size, infectionChance, movementProb, deathProb, recoveryRate
    c = City(20, 0.2, 0.3, 0.05, 0.10)
    vacancies1 = c.populate()

    master_list = []        #creates the original empty list ready to be appended
    iterations = []         #creates the original empty list of iterations
    starting_iteration = 0 

    size = c.size                   # finds size of city
    one_step = size * size          # gives everyone a chance to move
    total_steps = 50
    Master_Stats = []               # list of lists with four values(infected, deaths, recovered, healthy)
    amount_infected1 = 0

    c.show_beginning_topographical("Topographical Starting Config. (Base Case)")      # names the title of the graph
    c.show_beginning_definitive("Definitive Starting Config. (Base Case)")

    print("")
    print("Please Wait... (Takes About 15 Seconds)") 

    for i in range(total_steps):        # goes through total_steps number of times
        for j in range(one_step):       # goes through size by size times to give everyone a chance to move (400)
            stats, infect_cntr = c.step()
            Master_Stats.append(stats)
            amount_infected1 = infect_cntr           # determines number of times a -1 appears     
            starting_iteration += 1                  # time component
            iterations.append(starting_iteration)

    c.show_ending_topographical("Topographical Ending Config. (Base Case)")
    c.show_ending_definitive("Definitive Ending Config. (Base Case)")

    Master_Stats = np.array(Master_Stats)       # creation of array
    infected = Master_Stats[:,0]                # take the specified column
    deaths = Master_Stats[:,1]
    recovered = Master_Stats[:,2]
    healthy = Master_Stats[:,3]

    infected = np.array(infected)           # format in to an array for graphing purposes
    deaths = np.array(deaths)
    recovered = np.array(recovered)
    healthy = np.array(healthy)
    iterations = np.array(iterations)
    iterations = iterations/24

    plt.plot(iterations, infected, color = 'red', label = "Infected")     # plots
    plt.plot(iterations, deaths, color = 'black', label = "Deaths")
    plt.plot(iterations, recovered, color = 'blue', label = "Recovered")
    plt.plot(iterations, healthy, color = 'green', label = "Healthy")
    plt.legend()
    plt.xlabel("Iterations --> Time, 'Days'")
    plt.ylabel("Individuals")
    plt.title("Pandemic Simulation (Base Case)")
    plt.show()

    """
    MORE STATS
    """

    initial_infected1 = infected[0]     # finds first value in array which determines how many were initially infected
    inital_healthy1 = healthy[0]

    remaining_healthy1 = healthy[(one_step*total_steps)-1]      # finds the last value in the array which determines how many remaining healthy
    remaining_infections1 = infected[(one_step*total_steps)-1]
    total_recovered1 = np.ndarray.max(recovered)                # find the max of those values to find the total
    total_deaths1 = np.ndarray.max(deaths)

    max_infected1 = np.ndarray.max(infected)
    max_date1 = np.where(infected == max_infected1)             # finds the time component where the max occurs (x-value)
    max_date1 = max_date1[0][0]                                 # only want the first value (not duration)
    max_date1 = math.floor(max_date1/24)                        # divide by 24 to obtain 'days'

    mortality_rate1 = total_deaths1/amount_infected1 * 100                 # calculation
    recovery_rate1 = total_recovered1/amount_infected1 * 100

    print("")
    print("")
    print("Statistical Summary (Base Case)")
    print("-------------------------------")
    print("")

    print("Initial Statistics (Base Case)")
    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    print("Area of Space: " + str(size * size))
    print("Population of Size: " + str((size*size) - vacancies1))
    print("Total Number of Randomized Empty Spaces to Move (Vacancies): " + str(vacancies1))
    print("Number of Initial Healthy Individuals: " + str(inital_healthy1))
    print("Number of Initial Infected Individuals: " + str(initial_infected1))

    print("")
    print("Concluding Statistics (Base Case)")
    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    print("Total Number of Infections: " + str(amount_infected1))
    print("Total Number of Recovered Individuals: " + str(total_recovered1))
    print("Total Number of Deaths: " + str(total_deaths1))
    print("Total Number of Remaining Infections: " + str(remaining_infections1))
    print("Total Number of Remaining Healthy Individuals: " + str(remaining_healthy1))
    print("Maximum Number of Infected Individuals at a Given Time: " + str(max_infected1))
    print("'Day' The Max Occurs: " + str(max_date1))
    print("Mortality Rate: " + str(mortality_rate1) + "%")
    print("Recovery Rate: " + str(recovery_rate1) +"%")
    print("")

    return (vacancies1, inital_healthy1, initial_infected1, amount_infected1, total_recovered1, total_deaths1, remaining_infections1, remaining_healthy1, max_infected1, max_date1, mortality_rate1, recovery_rate1)




def different_Pandemic():

    vacancies1, inital_healthy1, initial_infected1, amount_infected1, total_recovered1, total_deaths1, remaining_infections1, remaining_healthy1, max_infected1, max_date1, mortality_rate1, recovery_rate1 = Pandemic()

    """
    LOW INFECTION CHANCE
    """

    # size, LOWERED infectionChance, movementProb, deathProb, recoveryRate
    c = City(20, 0.085, 0.3, 0.05, 0.10)
    vacancies = c.populate()

    master_list = []       #creates the original empty list ready to be appended
    iterations = []     #creates the original empty list of iterations
    starting_iteration = 0 

    size = c.size                   # finds size of city
    one_step = size * size          # gives everyone a chance to move
    total_steps = 50
    Master_Stats = []               # list of lists with four values(infected, deaths, recovered, healthy)
    amount_infected = 0

    c.show_beginning_topographical("Topographical Starting Config. (Low Infection Chance)")      # names the title of the graph
    c.show_beginning_definitive("Definitive Starting Config. (Low Infection Chance)")


    for i in range(total_steps):        
        for j in range(one_step):       # goes through size by size times to give everyone a chance to move
            stats, infect_cntr = c.step()
            Master_Stats.append(stats)
            amount_infected = infect_cntr           # determines number of times a -1 appears     
            starting_iteration += 1         # time component
            iterations.append(starting_iteration)

    c.show_ending_topographical("Topographical Ending Config. (Low Infection Chance)")
    c.show_ending_definitive("Definitive Ending Config. (Low Infection Chance)")

    Master_Stats = np.array(Master_Stats)       # creation of array
    infected = Master_Stats[:,0]                 # take the specified column
    deaths = Master_Stats[:,1]
    recovered = Master_Stats[:,2]
    healthy = Master_Stats[:,3]

    infected = np.array(infected)           # format in to an array for graphing purposes
    deaths = np.array(deaths)
    recovered = np.array(recovered)
    healthy = np.array(healthy)
    iterations = np.array(iterations)
    iterations = iterations/24

    plt.plot(iterations, infected, color = 'red', label = "Infected")     # plots
    plt.plot(iterations, deaths, color = 'black', label = "Deaths")
    plt.plot(iterations, recovered, color = 'blue', label = "Recovered")
    plt.plot(iterations, healthy, color = 'green', label = "Healthy")
    plt.legend()
    plt.xlabel("Iterations --> Time, 'Days'")
    plt.ylabel("Individuals")
    plt.title("Pandemic Simulation (Low Infection Chance)")
    plt.show()

    """
    MORE STATS
    """

    initial_infected = infected[0]
    inital_healthy = healthy[0]

    remaining_healthy = healthy[(one_step*total_steps)-1]
    remaining_infections = infected[(one_step*total_steps)-1]
    total_recovered = np.ndarray.max(recovered)         # find the max of those values to find the total
    total_deaths = np.ndarray.max(deaths)

    max_infected = np.ndarray.max(infected)
    max_date = np.where(infected == max_infected)
    max_date = max_date[0][0]
    max_date = math.floor(max_date/24)

    mortality_rate = total_deaths/amount_infected * 100                  # calculation
    recovery_rate = total_recovered/amount_infected * 100


    print("")
    print("")
    print("Statistical Summary (Lowered Infection Chance)  (Deviation From Base Case)")
    print("----------------------------------------------   ------------------------ ")
    print("")

    print("Initial Statistics")
    print("~~~~~~~~~~~~~~~~~~")
    print("Area of Space: " + str(size * size))
    print("Population of Size: " + str((size*size) - vacancies) + "  (" + str(((size*size) - vacancies) - ((size*size - vacancies1))) + ")")
    print("Total Number of Randomized Empty Spaces to Move (Vacancies): " + str(vacancies) + "  (" + str(vacancies - vacancies1) + ")")
    print("Number of Initial Healthy Individuals: " + str(inital_healthy) + "  (" + str(inital_healthy - inital_healthy1) + ")")
    print("Number of Initial Infected Individuals: " + str(initial_infected) + "  (" + str(initial_infected - initial_infected1) + ")")

    print("")
    print("Concluding Statistics")
    print("~~~~~~~~~~~~~~~~~~~~~")
    print("Total Number of Infections: " + str(amount_infected) + "  (" + str(amount_infected - amount_infected1) + ")")
    print("Total Number of Recovered Individuals: " + str(total_recovered) + "  (" + str(total_recovered - total_recovered1) + ")")
    print("Total Number of Deaths: " + str(total_deaths) + "  (" + str(total_deaths - total_deaths1) + ")")
    print("Total Number of Remaining Infections: " + str(remaining_infections) + "  (" + str(remaining_infections - remaining_infections1) + ")")
    print("Total Number of Remaining Healthy Individuals: " + str(remaining_healthy) + "  (" + str(remaining_healthy - remaining_healthy1) + ")")
    print("Maximum Number of Infected Individuals at a Given Time: " + str(max_infected) + "  (" + str(max_infected - max_infected1) + ")")
    print("'Day' The Max Occurs: " + str(max_date) + "  (" + str(max_date - max_date1) + ")")
    print("Mortality Rate: " + str(mortality_rate) + "%" + "  (" + str(mortality_rate - mortality_rate1) + "%)")
    print("Recovery Rate: " + str(recovery_rate) +"%" + "  (" + str(mortality_rate - mortality_rate1) + "%)")
    print("")



    """
    LOW MOVEMENT PROBABILITY
    """

    # size, infectionChance, LOWERED movementProb, deathProb, recoveryRate
    c = City(20, 0.2, 0.08, 0.05, 0.10)
    vacancies = c.populate()

    master_list = []       #creates the original empty list ready to be appended
    iterations = []     #creates the original empty list of iterations
    starting_iteration = 0 

    size = c.size                   # finds size of city
    one_step = size * size          # gives everyone a chance to move
    total_steps = 50
    Master_Stats = []               # list of lists with four values(infected, deaths, recovered, healthy)
    amount_infected = 0

    c.show_beginning_topographical("Topographical Starting Config. (Low Movement)")      # names the title of the graph
    c.show_beginning_definitive("Definitive Starting Config. (Low Movement)")

    for i in range(total_steps):        
        for j in range(one_step):       # goes through size by size times to give everyone a chance to move 
            stats, infect_cntr = c.step()
            Master_Stats.append(stats)
            amount_infected = infect_cntr           # determines number of times a -1 appears     
            starting_iteration += 1         # time component
            iterations.append(starting_iteration)

    c.show_ending_topographical("Topographical Ending Config. (Low Movement)")
    c.show_ending_definitive("Definitive Ending Config. (Low Movement)")

    Master_Stats = np.array(Master_Stats)       # creation of array
    infected = Master_Stats[:,0]                 # take the specified column
    deaths = Master_Stats[:,1]
    recovered = Master_Stats[:,2]
    healthy = Master_Stats[:,3]

    infected = np.array(infected)           # format in to an array for graphing purposes
    deaths = np.array(deaths)
    recovered = np.array(recovered)
    healthy = np.array(healthy)
    iterations = np.array(iterations)
    iterations = iterations/24

    plt.plot(iterations, infected, color = 'red', label = "Infected")     # plots
    plt.plot(iterations, deaths, color = 'black', label = "Deaths")
    plt.plot(iterations, recovered, color = 'blue', label = "Recovered")
    plt.plot(iterations, healthy, color = 'green', label = "Healthy")
    plt.legend()
    plt.xlabel("Iterations --> Time, 'Days'")
    plt.ylabel("Individuals")
    plt.title("Pandemic Simulation (Low Movement)")
    plt.show()

    """
    MORE STATS
    """

    initial_infected = infected[0]
    inital_healthy = healthy[0]

    remaining_healthy = healthy[(one_step*total_steps)-1]
    remaining_infections = infected[(one_step*total_steps)-1]
    total_recovered = np.ndarray.max(recovered)         # find the max of those values to find the total
    total_deaths = np.ndarray.max(deaths)

    max_infected = np.ndarray.max(infected)
    max_date = np.where(infected == max_infected)
    max_date = max_date[0][0]
    max_date = math.floor(max_date/24)
    mortality_rate = total_deaths/amount_infected * 100                  # calculation
    recovery_rate = total_recovered/amount_infected * 100

    print("")
    print("")
    print("Statistical Summary (Low Movement Probability)")
    print("----------------------------------------------")
    print("")

    print("Initial Statistics")
    print("~~~~~~~~~~~~~~~~~~")
    print("Area of Space: " + str(size * size))
    print("Population of Size: " + str((size*size) - vacancies) + "  (" + str(((size*size) - vacancies) - ((size*size - vacancies1))) + ")")
    print("Total Number of Randomized Empty Spaces to Move (Vacancies): " + str(vacancies) + "  (" + str(vacancies - vacancies1) + ")")
    print("Number of Initial Healthy Individuals: " + str(inital_healthy) + "  (" + str(inital_healthy - inital_healthy1) + ")")
    print("Number of Initial Infected Individuals: " + str(initial_infected) + "  (" + str(initial_infected - initial_infected1) + ")")

    print("")
    print("Concluding Statistics")
    print("~~~~~~~~~~~~~~~~~~~~~")
    print("Total Number of Infections: " + str(amount_infected) + "  (" + str(amount_infected - amount_infected1) + ")")
    print("Total Number of Recovered Individuals: " + str(total_recovered) + "  (" + str(total_recovered - total_recovered1) + ")")
    print("Total Number of Deaths: " + str(total_deaths) + "  (" + str(total_deaths - total_deaths1) + ")")
    print("Total Number of Remaining Infections: " + str(remaining_infections) + "  (" + str(remaining_infections - remaining_infections1) + ")")
    print("Total Number of Remaining Healthy Individuals: " + str(remaining_healthy) + "  (" + str(remaining_healthy - remaining_healthy1) + ")")
    print("Maximum Number of Infected Individuals at a Given Time: " + str(max_infected) + "  (" + str(max_infected - max_infected1) + ")")
    print("'Day' The Max Occurs: " + str(max_date) + "  (" + str(max_date - max_date1) + ")")
    print("Mortality Rate: " + str(mortality_rate) + "%" + "  (" + str(mortality_rate - mortality_rate1) + "%)")
    print("Recovery Rate: " + str(recovery_rate) +"%" + "  (" + str(mortality_rate - mortality_rate1) + "%)")
    print("")

    """
    LOW INFECTION CHANCE & MOVEMENT PROBABILITY
    """

    # size, LOWERED infectionChance, LOWERED movementProb, deathProb, recoveryRate
    c = City(20, 0.085, 0.08, 0.05, 0.10)
    vacancies = c.populate()

    master_list = []       #creates the original empty list ready to be appended
    iterations = []     #creates the original empty list of iterations
    starting_iteration = 0 

    size = c.size                   # finds size of city
    one_step = size * size          # gives everyone a chance to move
    total_steps = 50
    Master_Stats = []               # list of lists with four values(infected, deaths, recovered, healthy)
    amount_infected = 0

    c.show_beginning_topographical("Topographical Starting Config. (Low Infection Chance & Movement)")      # names the title of the graph
    c.show_beginning_definitive("Definitive Starting Config. (Low Infection Chance & Movement)")

    for i in range(total_steps):        
        for j in range(one_step):       # goes through size by size times to give everyone a chance to move 
            stats, infect_cntr = c.step()
            Master_Stats.append(stats)
            amount_infected = infect_cntr           # determines number of times a -1 appears     
            starting_iteration += 1         # time component
            iterations.append(starting_iteration)

    c.show_ending_topographical("Topographical Ending Config. (Low Infection Chance & Movement)")
    c.show_ending_definitive("Definitive Ending Config. (Low Infection Chance & Movement)")

    Master_Stats = np.array(Master_Stats)       # creation of array
    infected = Master_Stats[:,0]                 # take the specified column
    deaths = Master_Stats[:,1]
    recovered = Master_Stats[:,2]
    healthy = Master_Stats[:,3]

    infected = np.array(infected)           # format in to an array for graphing purposes
    deaths = np.array(deaths)
    recovered = np.array(recovered)
    healthy = np.array(healthy)
    iterations = np.array(iterations)
    iterations = iterations/24

    plt.plot(iterations, infected, color = 'red', label = "Infected")     # plots
    plt.plot(iterations, deaths, color = 'black', label = "Deaths")
    plt.plot(iterations, recovered, color = 'blue', label = "Recovered")
    plt.plot(iterations, healthy, color = 'green', label = "Healthy")
    plt.legend()
    plt.xlabel("Iterations --> Time, 'Days'")
    plt.ylabel("Individuals")
    plt.title("Pandemic Simulation (Low Movement)")
    plt.show()

    """
    MORE STATS
    """

    initial_infected = infected[0]
    inital_healthy = healthy[0]

    remaining_healthy = healthy[(one_step*total_steps)-1]
    remaining_infections = infected[(one_step*total_steps)-1]
    total_recovered = np.ndarray.max(recovered)         # find the max of those values to find the total
    total_deaths = np.ndarray.max(deaths)

    max_infected = np.ndarray.max(infected)
    max_date = np.where(infected == max_infected)
    max_date = max_date[0][0]
    max_date = math.floor(max_date/24)
    mortality_rate = total_deaths/amount_infected * 100                  # calculation
    recovery_rate = total_recovered/amount_infected * 100

    print("")
    print("")
    print("Statistical Summary (Low Infection Chance & Movement)")
    print("-----------------------------------------------------")
    print("")

    print("Initial Statistics")
    print("~~~~~~~~~~~~~~~~~~")
    print("Area of Space: " + str(size * size))
    print("Population of Size: " + str((size*size) - vacancies) + "  (" + str(((size*size) - vacancies) - ((size*size - vacancies1))) + ")")
    print("Total Number of Randomized Empty Spaces to Move (Vacancies): " + str(vacancies) + "  (" + str(vacancies - vacancies1) + ")")
    print("Number of Initial Healthy Individuals: " + str(inital_healthy) + "  (" + str(inital_healthy - inital_healthy1) + ")")
    print("Number of Initial Infected Individuals: " + str(initial_infected) + "  (" + str(initial_infected - initial_infected1) + ")")

    print("")
    print("Concluding Statistics")
    print("~~~~~~~~~~~~~~~~~~~~~")
    print("Total Number of Infections: " + str(amount_infected) + "  (" + str(amount_infected - amount_infected1) + ")")
    print("Total Number of Recovered Individuals: " + str(total_recovered) + "  (" + str(total_recovered - total_recovered1) + ")")
    print("Total Number of Deaths: " + str(total_deaths) + "  (" + str(total_deaths - total_deaths1) + ")")
    print("Total Number of Remaining Infections: " + str(remaining_infections) + "  (" + str(remaining_infections - remaining_infections1) + ")")
    print("Total Number of Remaining Healthy Individuals: " + str(remaining_healthy) + "  (" + str(remaining_healthy - remaining_healthy1) + ")")
    print("Maximum Number of Infected Individuals at a Given Time: " + str(max_infected) + "  (" + str(max_infected - max_infected1) + ")")
    print("'Day' The Max Occurs: " + str(max_date) + "  (" + str(max_date - max_date1) + ")")
    print("Mortality Rate: " + str(mortality_rate) + "%" + "  (" + str(mortality_rate - mortality_rate1) + "%)")
    print("Recovery Rate: " + str(recovery_rate) +"%" + "  (" + str(mortality_rate - mortality_rate1) + "%)")
    print("")

    """
    HIGH DEATH PROBABILITY
    """

    # size, infectionChance, movementProb, INCREASED deathProb, recoveryRate
    c = City(20, 0.2, 0.3, 0.55, 0.10)
    vacancies = c.populate()

    master_list = []       #creates the original empty list ready to be appended
    iterations = []     #creates the original empty list of iterations
    starting_iteration = 0 

    size = c.size                   # finds size of city
    one_step = size * size          # gives everyone a chance to move
    total_steps = 50
    Master_Stats = []               # list of lists with four values(infected, deaths, recovered, healthy)
    amount_infected = 0

    c.show_beginning_topographical("Topographical Starting Config. (High Death Probability)")      # names the title of the graph
    c.show_beginning_definitive("Definitive Starting Config. (High Death Probability)")

    for i in range(total_steps):       
        for j in range(one_step):       # goes through size by size times to give everyone a chance to move
            stats, infect_cntr = c.step()
            Master_Stats.append(stats)
            amount_infected = infect_cntr           # determines number of times a -1 appears     
            starting_iteration += 1         # time component
            iterations.append(starting_iteration)

    c.show_ending_topographical("Topographical Ending Config. (High Death Probability)")
    c.show_ending_definitive("Definitive Ending Config. (High Death Probability)")

    Master_Stats = np.array(Master_Stats)       # creation of array
    infected = Master_Stats[:,0]                 # take the specified column
    deaths = Master_Stats[:,1]
    recovered = Master_Stats[:,2]
    healthy = Master_Stats[:,3]

    infected = np.array(infected)           # format in to an array for graphing purposes
    deaths = np.array(deaths)
    recovered = np.array(recovered)
    healthy = np.array(healthy)
    iterations = np.array(iterations)
    iterations = iterations/24

    plt.plot(iterations, infected, color = 'red', label = "Infected")     # plots
    plt.plot(iterations, deaths, color = 'black', label = "Deaths")
    plt.plot(iterations, recovered, color = 'blue', label = "Recovered")
    plt.plot(iterations, healthy, color = 'green', label = "Healthy")
    plt.legend()
    plt.xlabel("Iterations --> Time, 'Days'")
    plt.ylabel("Individuals")
    plt.title("Pandemic Simulation (High Death Probability)")
    plt.show()

    """
    MORE STATS
    """

    initial_infected = infected[0]
    inital_healthy = healthy[0]

    remaining_healthy = healthy[(one_step*total_steps)-1]
    remaining_infections = infected[(one_step*total_steps)-1]
    total_recovered = np.ndarray.max(recovered)         # find the max of those values to find the total
    total_deaths = np.ndarray.max(deaths)

    max_infected = np.ndarray.max(infected)
    max_date = np.where(infected == max_infected)
    max_date = max_date[0][0]
    max_date = math.floor(max_date/24)
    mortality_rate = total_deaths/amount_infected * 100                  # calculation
    recovery_rate = total_recovered/amount_infected * 100

    print("")
    print("")
    print("Statistical Summary (High Death Probability)")
    print("--------------------------------------------")
    print("")

    print("Initial Statistics")
    print("~~~~~~~~~~~~~~~~~~")
    print("Area of Space: " + str(size * size))
    print("Population of Size: " + str((size*size) - vacancies) + "  (" + str(((size*size) - vacancies) - ((size*size - vacancies1))) + ")")
    print("Total Number of Randomized Empty Spaces to Move (Vacancies): " + str(vacancies) + "  (" + str(vacancies - vacancies1) + ")")
    print("Number of Initial Healthy Individuals: " + str(inital_healthy) + "  (" + str(inital_healthy - inital_healthy1) + ")")
    print("Number of Initial Infected Individuals: " + str(initial_infected) + "  (" + str(initial_infected - initial_infected1) + ")")

    print("")
    print("Concluding Statistics")
    print("~~~~~~~~~~~~~~~~~~~~~")
    print("Total Number of Infections: " + str(amount_infected) + "  (" + str(amount_infected - amount_infected1) + ")")
    print("Total Number of Recovered Individuals: " + str(total_recovered) + "  (" + str(total_recovered - total_recovered1) + ")")
    print("Total Number of Deaths: " + str(total_deaths) + "  (" + str(total_deaths - total_deaths1) + ")")
    print("Total Number of Remaining Infections: " + str(remaining_infections) + "  (" + str(remaining_infections - remaining_infections1) + ")")
    print("Total Number of Remaining Healthy Individuals: " + str(remaining_healthy) + "  (" + str(remaining_healthy - remaining_healthy1) + ")")
    print("Maximum Number of Infected Individuals at a Given Time: " + str(max_infected) + "  (" + str(max_infected - max_infected1) + ")")
    print("'Day' The Max Occurs: " + str(max_date) + "  (" + str(max_date - max_date1) + ")")
    print("Mortality Rate: " + str(mortality_rate) + "%" + "  (" + str(mortality_rate - mortality_rate1) + "%)")
    print("Recovery Rate: " + str(recovery_rate) +"%" + "  (" + str(mortality_rate - mortality_rate1) + "%)")
    print("")



different_Pandemic()