package java_base.leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kled
 * @date 2021/12/2 7:59 下午
 */
public class CourseFinish {
    //超时
    boolean canFinish = true;
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        for (int i = 0; i < numCourses-1 && canFinish; i++) {
            dfs(prerequisites, i, new ArrayList<>());
        }
        return canFinish;
    }

    public void dfs(int[][] prerequisites, int numCourses, List<Integer> usedDependencyCourse) {
        List<Integer> dependencyCourses = dependencyCourses(prerequisites, numCourses);
        if (dependencyCourses.isEmpty()) {
            return;
        }

        for (int i = 0; i < dependencyCourses.size() && canFinish; i++) {
            Integer course = dependencyCourses.get(i);
            if (usedDependencyCourse.contains(dependencyCourses.get(i))) {
                canFinish = false;
                return;
            }
            usedDependencyCourse.add(course);
            dfs(prerequisites, course, usedDependencyCourse);
            usedDependencyCourse.remove(usedDependencyCourse.size() - 1);
        }
    }

    public List<Integer> dependencyCourses(int[][] prerequisites, int numCourses) {
        List<Integer> dependencyCourses = new ArrayList<>();
        for (int i = 0; i < prerequisites.length; i++) {
            if (prerequisites[i][0] == numCourses) {
                dependencyCourses.add(prerequisites[i][1]);
            }
        }
        return dependencyCourses;
    }

    public static void main(String[] args) {
        System.out.println(new CourseFinish().canFinish(2, new int[][]{{1,0}, {0,1}}));
    }
}
