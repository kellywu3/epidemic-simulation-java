package kelly.simulation.domain;

import java.awt.*;
import java.util.ArrayList;

public class CommunityManager {
    private ArrayList<Bound> communities;

    public CommunityManager() {
    }

    public void updateCommunities(boolean quarantineOn, int[] fieldSize, int rows, int cols, int boundaryDist) {
        communities = new ArrayList<>();
        int width = 0;
        int length = 0;
        int lo[];
        int hi[];

//        if(quarantineOn) {
//            communities.add(new Bound(new int[] {boundaryDist, boundaryDist}, new int[] {width, length}));
//        }

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(quarantineOn) {
                    width = (fieldSize[0] - (boundaryDist * (cols + 2))) / (cols + 1);
                    length = (fieldSize[1] - (boundaryDist * (rows + 1))) / rows;
                    lo = new int[] {((boundaryDist + width) * (j + 1)) + boundaryDist, ((boundaryDist + length) * i + boundaryDist)};
                    hi = new int[] {lo[0] + width, lo[1] + length};
                } else {
                    width = (fieldSize[0] - (boundaryDist * (cols + 1))) / cols;
                    length = (fieldSize[1] - (boundaryDist * (rows + 1))) / rows;
                    lo = new int[] {((boundaryDist + width) * j) + boundaryDist, ((boundaryDist + length) * i + boundaryDist)};
                    hi = new int[] {lo[0] + width, lo[1] + length};
                }
                communities.add(new Bound(lo, hi));
            }
        }
    }

    public Bound getCommunity(int idx) {
        return communities.get(idx);
    }
}
