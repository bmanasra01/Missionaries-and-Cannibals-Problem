package com.example.ai_pro1;

import java.util.*;

public class BFS {
    private static final int numOFm = 3; // max number of Missionaries
    private static final int numOFc = 3; // max number of Cannibals

    private static Map<State, State> parentMap = new HashMap<>();  // for hashing , as find steps , parent state , relations between states
    private static Set<State> visited = new HashSet<>();  //  to avoid loops.
    private static Queue<State> queue = new LinkedList<>();

    private static int solutionsFound = 0;
    private static final int MAX_SOLUTIONS = 1;

    public static String solve() {
        long startTime = System.nanoTime();  // Start time

        /* initializes the search with the starting state
        all missionaries and cannibals on the left side */

        State initialState = new State(numOFm, numOFc, true);
        queue.add(initialState);
        visited.add(initialState);

        StringBuilder result = new StringBuilder();
        State currentState = null;

       /* we use while loop to keep exploring states from the queue until
         a solution is found or the queue is empty */
        while (!queue.isEmpty() && solutionsFound < MAX_SOLUTIONS) {
            currentState = queue.poll(); // here we remove state from Q as if statement event

            processState(currentState);
        }

        result.append(printOut(currentState));

        long endTime = System.nanoTime();  // End time

        // Calculate execution time
        long executionTime = (endTime - startTime) / 1_000_000;


        //result.append("\nExecution Time: ").append(executionTime).append(" ms\n");

        return result.toString();
    }



    private static void processState(State state) {

        // here we check if there are more cannibals than missionaries on one side of the river
        if (!isValidIntermediateState(state)) {
            return;
        }

        // here we go to next states
        List<State> nextStates = generateNextStates(state);

        // check if state visited or not
        for (State nextState : nextStates) {
            if (!visited.contains(nextState)) {
                visited.add(nextState);

                parentMap.put(nextState, state);

                if (stateIsGoal(nextState)) {
                    solutionsFound++;

                    if (solutionsFound == MAX_SOLUTIONS) {
                        return; // stop when we have  number of solutions found
                    }
                }

                queue.add(nextState);
            }
        }
    }
    /*checks if a state is valid during the search */

    // on boath side
    private static boolean isValidIntermediateState(State state) {
        int missionariesOnLeft = state.boat ? state.missionaries : numOFm - state.missionaries;
        int cannibalsOnLeft = state.boat ? state.cannibals : numOFc - state.cannibals;

        int missionariesOnRight = numOFm - missionariesOnLeft;
        int cannibalsOnRight = numOFc - cannibalsOnLeft;

        return (missionariesOnLeft >= cannibalsOnLeft || missionariesOnLeft == 0) &&
                (missionariesOnRight >= cannibalsOnRight || missionariesOnRight == 0);
    }


    private static List<State> generateNextStates(State currentState) {
        // in this method we generate next state
        List<State> nextStates = new ArrayList<>();

        //max number of m,c
        int maxMissionariesOnBoat = Math.min(numOFm, 2);
        int maxCannibalsOnBoat = Math.min(numOFc, 2);

        //nested loop to check valid state in boat
        for (int m = 0; m <= maxMissionariesOnBoat; m++) {
            for (int c = 0; c <= maxCannibalsOnBoat; c++) {
                if (m + c >= 1 && m + c <= 2 && m + c <= currentState.missionaries + currentState.cannibals) {
                    int nextMissionaries, nextCannibals;

                    // here we determine a valid state after boat move

                    if (currentState.boat) {
                        nextMissionaries = currentState.missionaries - m;
                        nextCannibals = currentState.cannibals - c;
                    } else {
                        nextMissionaries = currentState.missionaries + m;
                        nextCannibals = currentState.cannibals + c;
                    }

                    //check new state is valid or not
                    if (isValidState(nextMissionaries, nextCannibals) &&
                            isValidState(numOFm - nextMissionaries, numOFc - nextCannibals) &&
                            isValidIntermediateState(new State(nextMissionaries, nextCannibals, !currentState.boat))) {
                        State nextState = new State(nextMissionaries, nextCannibals, !currentState.boat);
                        nextStates.add(nextState); //add valid next state
                    }
                }
            }
        }

        return nextStates;
    }
 /*   Checks if a state is valid in general*/
    private static boolean isValidState(int missionaries, int cannibals) {
        return (missionaries >= 0 && cannibals >= 0 && missionaries <= numOFm && cannibals <= numOFc);
    }
    /*Checks if the current state is the goal*/
    private static boolean stateIsGoal(State state) {
        return state.missionaries == 0 && state.cannibals == 0 && !state.boat;
    }



    private static String printOut(State goalState) {
        List<State> steps = new ArrayList<>();
        State currentState = goalState;

        while (currentState != null) {
            steps.add(currentState);
            currentState = parentMap.get(currentState);
        }

        Collections.reverse(steps);

        StringBuilder solutionString = new StringBuilder("Steps:  (M, C,   B , M, C)\n");
        for (int i = 1; i <= steps.size(); i++) {
            State step = steps.get(i - 1);
            solutionString.append("Step ").append(i).append(": (")
                    .append(step.missionaries).append(", ")
                    .append(step.cannibals).append(", ")
                    .append(step.boat ? "left" : "right").append(", ")
                    .append(numOFm - step.missionaries).append(", ")
                    .append(numOFc - step.cannibals).append(")\n");
        }

        return solutionString.toString();
    }
}
