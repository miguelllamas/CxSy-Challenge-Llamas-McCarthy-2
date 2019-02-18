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

After this series of tests, we then wanted to observe what behaviors would be observed when more variation was factored into the decision-making process. Hence, the algorithm was altered such that an agent had a 75% chance of utilizing the aforementioned highest average earnings algorithm and a 25% chance of selecting randomly. This was also attempted to verify whether alternating between selecting the pool with highest average earnings and a random (and potentially less-populated) pool might potentially increase earnings. Ultimately, this produced a much more varied range of results in terms of final average earnings, though this did not result in significantly increased or decreased earnings.

![Image](https://raw.githubusercontent.com/miguelllamas/CxSy-Challenge-Llamas-McCarthy-2/master/A2.PNG)

# Lowest Average Agents

Utilizing the lowest average agents algorithm, an agent determines a pool based on the average of the pool's contained agents over all preceding steps, with the pool with the lowest average being selected. The selection of a pool with less agents would be favorable due to the condition applied to the high and low pools, wherein a payout is divided by the number of agents located within the pool (note that in the event of a tie between two pools for having the lowest average, an agent may randomly pick either of the two).

Analyzing this algorithm, certain patterns were evident; somewhat similarly to the previous algorithm, timesteps were observed in which all agents could be found in the same pool. However, compared to the highest average earnings algorithm, this "stable" state was not maintained for consecutive timesteps, resulting in a more dynamic change of pools throughout the 100 timesteps. However, this seemed to negatively affect final earnings by the final timestep, as a congested pool produced somewhat minimal earnings, which would then be subtracted due to the tau cost of switching pools in the next timestep. The introduction of randomization to the algorithm (maintaining the same 75% - 25% chance utilized with the previous algorithm) was found to result in significantly higher final earnings. This could perhaps be attributed to the randomization providing agents with a higher chance of remaining in their current pools, thus reducing the need to spend tau and the risk of ending up in a congested, low-earning pool.

![Image](https://raw.githubusercontent.com/miguelllamas/CxSy-Challenge-Llamas-McCarthy-2/master/B1.PNG)
![Image](https://raw.githubusercontent.com/miguelllamas/CxSy-Challenge-Llamas-McCarthy-2/master/B2.PNG)

# Least Agents

Utilizing the least agents algorithm, an agent determines a pool based on whichever pool contained the least agents at the end of the immediately preceding step; hence, this constitutes an algorithm which is more greedy in nature.

Analyzing this algorithm, it was observed that the algorithm performed somewhat similarly to the lowest average agents, in the sense that low overall average final earnings were recorded among the vast majority of the agents by the end of the 100th timestep for all five runs, as a result of tau deductions and lowered payouts due to pool congestion. This was particularly evident when tau was reduced from 2 to 1, which resulted in increased final earnings. The introduction of randomization to the algorithm (maintaining the same 75% - 25% chance utilized with the previous algorithms) resulted in a fluctuating range of recorded final earnings. It was also observed that a more substantial percentage of agents were able to register final earnings  higher than the $20 mark, which could also be attributed to a reduced need to spend tau, similarly to when randomization was applied to the lowest average agents algorithm.

![Image](https://raw.githubusercontent.com/miguelllamas/CxSy-Challenge-Llamas-McCarthy-2/master/C1.PNG)
![Image](https://raw.githubusercontent.com/miguelllamas/CxSy-Challenge-Llamas-McCarthy-2/master/C2.PNG)

# Conclusions

Overall, it can be concluded that the consistent adherence to explicitly-set rules across all timesteps may have ultimately resulted in lower agent earnings, which is perhaps indicative of the apparent unsuitability of extremely rigid algorithms to simulate agent behaviors in these cases. This was also supported by the relatively low average earnings observed during test runs relying entirely on set rules to determine agent choice as compared to test runs utilizing both rule-based and randomized decision-making.

We would suggest that for future simulations of this scenario, tests could be run in which different groups of agents each utilize a different decision-making algorithm (rule-based, randomized, and a mixture of the two) within the same 100-timestep-test run, so as to compare whether certain groups registered higher earnings over others. Additionally, we could perhaps further explore more dynamic decision-making in which agents switch between different decision-making algorithms over the course of a single test run, this time based on defined conditions (i.e. if agent's earnings have declined for consecutive timesteps) instead of the 75%-25% probability of switching algorithms explored in this project.
