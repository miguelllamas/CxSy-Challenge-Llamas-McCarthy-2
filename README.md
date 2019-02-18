# Background
This project primarily concerns observations of programmed virtual agent behaviors based on a hypothetical scenario involving the movement of agents to different pools. The scenario can be described as such: for 100 timesteps, 50 agents must independently decide which of three possible pools (stable, high, and low) to transfer to, with each pool paying out a particular monetary amount at the end of each time step. Agents must make their respective choices knowing only the number of agents that located at each pool, as well as each pool's payoff for all prior time steps. Note that switching pools costs an agent a payment of tau (where 0 <= tau; this value shall be defined as 2 here).

The nature of the payout for each pool at the end of a timestep can be summarized as such:
* Stable: $1
* High: 25% chance of $80 (divided by number of agents in pool); 75% chance of P0
* Low: 50% chance of $40 (divided by number of agents in pool); 50% chance of P0

For this project, various different algorithms have been constructed, each of which largely defines how the decision-making process for agents functions across all time steps. Observations regarding these algorithms are discussed in the succeeding sections below. Note that each algorithm has been run 5 times.

# Highest Average Earnings

Utilizing the highest average earnings algorithm, an agent selects a pool based on the average of the pool's final payouts over all preceding steps, with the pool with the highest average being selected.

Analyzing this algorithm, a "bandwagon" pattern with little variation was recorded in the sense that over the five runs, it was observable that by no more than the fifth step, all agents had transferred to a single pool, remaining in this pool until such point that the lack of agents in the other two pools would result in their average earnings gradually increasing, followed by a sudden shift by all agents towards the pool with the new highest average earnings. It was also observable that by the point that the agents had a reached a more stable state, their average earnings maintained a steady upward increase. Final earnings by the 100th timestep (averaged from all five test runs) using this algorithm for the 50 agents recorded no major variations, with all averages falling squarely within the low 90s-low 100s range.

![Image](https://raw.githubusercontent.com/miguelllamas/CxSy-Challenge-Llamas-McCarthy-2/master/A1.PNG)

After this series of tests, we then wanted to observe what behaviors would be observed when more variation was factored into the decision-making process. Hence, the algorithm was altered such that an agent had a 75% chance of utilizing the aforementioned highest average earnings algorithm and a 25% chance of selecting randomly. This was also attempted to verify whether alternating between selecting the pool with highest average earnings and a random (and potentially less-populated) pool might potentially increase earnings. Ultimately, this produced a much more varied range of results in terms of final average earnings.

![Image](https://raw.githubusercontent.com/miguelllamas/CxSy-Challenge-Llamas-McCarthy-2/master/A2.PNG)

# Lowest Average Agents

Utilizing the lowest average agents algorithm, an agent determines a pool based on the average of the pool's contained agents over all preceding steps, with the pool with the lowest average being selected. The selection of a pool with less agents would be favorable due to the condition applied to the high and low pools, wherein a payout is divided by the number of agents located within the pool (note that in the event of a tie between two pools for having the lowest average, an agent may randomly pick either of the two).

# Least Agents

Utilizing the least agents algorithm, an agent determines a pool based on whichever pool contained the least agents at the end of the immediately preceding step; hence, this constitutes an algorithm which is more greedy in nature.

# Randomized

Utilizing the randomized algorithm, an agent determines a pool without any set rules; that is, based on a random selection of the 3 pools.
