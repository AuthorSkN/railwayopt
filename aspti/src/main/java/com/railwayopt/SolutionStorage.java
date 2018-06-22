package com.railwayopt;

import com.railwayopt.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionStorage {

    private static Map<Integer, List<Solution>> solutions = new HashMap<>();
    private static int solutionId = 0;

    public static void  addSolution(Solution solution){
        solution.setId(solutionId++);
        if (!solutions.containsKey(solution.getProjectId())){
            solutions.put(solution.getProjectId(), new ArrayList<>());
        }
        solutions.get(solution.getProjectId()).add(solution);
    }

    public static List<Solution>  getAllSolution(int projectId){
        return solutions.get(projectId);
    }
}
