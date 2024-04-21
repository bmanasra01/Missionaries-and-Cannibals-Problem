package com.example.ai_pro1;

import java.util.*;

public class DFS {
    private static final int numOFm = 3; // max number of Missionaries
    private static final int numOFc = 3; // max number of Cannibals

    private static Map<State, State> parentMap = new HashMap<>(); // for hashing
    private static Set<State> visited = new HashSet<>(); //  to avoid loops.

    private static int solutionsFound = 0;
    private static final int MAX_SOLUTIONS = 1;


    /* algorithm*/
    public static String solve() {
        long startTime = System.nanoTime();  // Start time

        // initileze state
        State initialState = new State(numOFm, numOFc, true);
        Stack<State> stack = new Stack<>();
        stack.push(initialState);//push on stack

        StringBuilder result = new StringBuilder();
        State currentState = null;
        while (!stack.isEmpty() && solutionsFound < MAX_SOLUTIONS) {
             currentState = stack.pop();//remove from stack

            if (!visited.contains(currentState)) {
                visited.add(currentState);

                if (stateIsGoal(currentState)) {
                    result.append(printOut(currentState));
                    solutionsFound++;
                }


                List<State> nextStates = generateNextStates(currentState);
                for (State nextState : nextStates) {
                    if (!visited.contains(nextState)) {
                        parentMap.put(nextState, currentState);
                        stack.push(nextState);// add state to the stack
                    }
                }
            }
        }

        long endTime = System.nanoTime();  // End time

        // Calculate execution time in milliseconds
        long executionTime = (endTime - startTime) / 1_000_000;

        result.append("\nExecution Time: ").append(executionTime).append(" milliseconds\n");

        return result.toString();
    }


    /*Checks if a state is valid during the search */
    // on both side
    private static boolean isValidIntermediateState(State state) {
        int missionariesOnLeft = state.boat ? state.missionaries : numOFm - state.missionaries;
        int cannibalsOnLeft = state.boat ? state.cannibals : numOFc - state.cannibals;

        int missionariesOnRight = numOFm - missionariesOnLeft;
        int cannibalsOnRight = numOFc - cannibalsOnLeft;

        return (missionariesOnLeft >= cannibalsOnLeft || missionariesOnLeft == 0) &&
                (missionariesOnRight >= cannibalsOnRight || missionariesOnRight == 0);
    }

    // generate next state
    private static List<State> generateNextStates(State currentState) {
        List<State> nextStates = new ArrayList<>();

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
                        nextStates.add(nextState);
                    }
                }
            }
        }

        return nextStates;

    }
    /*Checks if a state is valid in general*/
    private static boolean isValidState(int missionaries, int cannibals) {
        return (missionaries >= 0 && cannibals >= 0 && missionaries <= numOFm && cannibals <= numOFc);
    }

    /*Checks if the current state is the goal*/
    private static boolean stateIsGoal(State state) {
        return state.missionaries == 0 && state.cannibals == 0 && !state.boat;
    }






    /* print the out put */
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
