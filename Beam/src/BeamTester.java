/**
 * Created by chengqian on 11/4/16.
 */
public class BeamTester {
    //main method for testing
    public static void main(String[] args)
    {
        //edges of specitic graph, 5 vertices,
        //vertices numbered from 0
        int[][] edges = {
                {1, 2, 5},
                {1, 3, 6},
                {1, 4, 3},
                {2, 3, 4},
                {2, 4, 8},
                {3, 4, 2}
        };

/*
        int[][] edges = {
                {2, 1, 12},
                {3, 1, 12},
                {3, 2, 15},
                {4, 1, 20},
                {4, 2, 11},
                {4, 3, 6},
                {5, 1, 3},
                {5, 2, 15},
                {5, 3, 8},
                {5, 4, 12},
                {6, 1, 2},
                {6, 2, 14},
                {6, 3, 8},
                {6, 4, 2},
                {6, 5, 14},
                {7, 1, 14},
                {7, 2, 20},
                {7, 3, 3},
                {7, 4, 7},
                {7, 5, 14},
                {7, 6, 14},
                {8, 1, 12},
                {8, 2, 5},
                {8, 3, 2},
                {8, 4, 15},
                {8, 5, 10},
                {8, 6, 16},
                {8, 7, 2},
                {9, 1, 11},
                {9, 2, 8},
                {9, 3, 9},
                {9, 4, 14},
                {9, 5, 3},
                {9, 6, 15},
                {9, 7, 2},
                {9, 8, 20},
                {10, 1, 17},
                {10, 2, 20},
                {10, 3, 6},
                {10, 4, 13},
                {10, 5, 19},
                {10, 6, 12},
                {10, 7, 18},
                {10, 8, 7},
                {10, 9, 6},
                {11, 1, 3},
                {11, 2, 12},
                {11, 3, 11},
                {11, 4, 3},
                {11, 5, 19},
                {11, 6, 18},
                {11, 7, 7},
                {11, 8, 13},
                {11, 9, 20},
                {11, 10, 8},
                {12, 1, 15},
                {12, 2, 1},
                {12, 3, 16},
                {12, 4, 20},
                {12, 5, 9},
                {12, 6, 14},
                {12, 7, 5},
                {12, 8, 14},
                {12, 9, 12},
                {12, 10, 16},
                {12, 11, 19},
                {13, 1, 20},
                {13, 2, 13},
                {13, 3, 13},
                {13, 4, 5},
                {13, 5, 10},
                {13, 6, 5},
                {13, 7, 15},
                {13, 8, 8},
                {13, 9, 16},
                {13, 10, 15},
                {13, 11, 18},
                {13, 12, 14},
                {14, 1, 19},
                {14, 2, 6},
                {14, 3, 5},
                {14, 4, 5},
                {14, 5, 2},
                {14, 6, 15},
                {14, 7, 4},
                {14, 8, 16},
                {14, 9, 10},
                {14, 10, 13},
                {14, 11, 10},
                {14, 12, 9},
                {14, 13, 3},
                {15, 1, 18},
                {15, 2, 8},
                {15, 3, 1},
                {15, 4, 2},
                {15, 5, 2},
                {15, 6, 11},
                {15, 7, 8},
                {15, 8, 17},
                {15, 9, 19},
                {15, 10, 18},
                {15, 11, 16},
                {15, 12, 5},
                {15, 13, 7},
                {15, 14, 8}
        };

        int[][] edges = {
                {3, 2, 15},
                {4, 1, 17},
                {4, 3, 6},
                {5, 2, 1},
                {5, 3, 9},
                {5, 4, 16},
                {6, 2, 12},
                {6, 3, 20},
                {6, 4, 7},
                {7, 1, 14},
                {8, 3, 4},
                {8, 4, 15},
                {8, 5, 9},
                {8, 6, 1},
                {9, 2, 10},
                {9, 3, 7},
                {9, 5, 14},
                {9, 6, 6},
                {10, 3, 19},
                {10, 4, 2},
                {10, 7, 15},
                {10, 8, 17},
                {11, 1, 20},
                {11, 2, 9},
                {11, 5, 3},
                {11, 6, 15},
                {11, 7, 9},
                {11, 8, 11},
                {12, 3, 18},
                {12, 7, 14},
                {12, 10, 2},
                {12, 11, 4},
                {13, 3, 12},
                {13, 5, 17},
                {13, 10, 5},
                {13, 11, 1},
                {13, 12, 16},
                {14, 1, 17},
                {14, 2, 12},
                {14, 6, 13},
                {14, 10, 17},
                {14, 11, 18},
                {14, 12, 17},
                {15, 1, 4},
                {15, 7, 16},
                {15, 8, 13},
                {15, 10, 10},
                {16, 1, 14},
                {16, 3, 17},
                {16, 5, 17},
                {16, 6, 13},
                {16, 7, 3},
                {16, 9, 18},
                {16, 10, 5},
                {16, 12, 18},
                {16, 13, 18},
                {17, 2, 10},
                {17, 4, 5},
                {17, 5, 6},
                {17, 6, 1},
                {17, 7, 1},
                {17, 9, 5},
                {17, 13, 16},
                {17, 16, 4},
                {18, 2, 2},
                {18, 3, 4},
                {18, 5, 6},
                {18, 7, 9},
                {18, 9, 10},
                {18, 11, 1},
                {18, 15, 5},
                {18, 17, 10},
                {19, 1, 5},
                {19, 3, 11},
                {19, 4, 16},
                {19, 6, 9},
                {19, 9, 18},
                {19, 10, 15},
                {19, 15, 11},
                {20, 4, 15},
                {20, 7, 3},
                {20, 8, 1},
                {20, 12, 9},
                {20, 13, 2},
                {20, 14, 8},
                {20, 15, 1},
                {20, 19, 17}
        };
*/
        //find a travelling salesman cycle
        Beam b = new Beam(4, edges);
        b.solve();
    }
}
