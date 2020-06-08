package kelly.simulation.domain;

import java.util.ArrayList;

public class CommunityManager {
    private ArrayList<Bound> communities;

    public CommunityManager(boolean quarantineOn, int[] fieldSize, int rows, int cols, int boundaryDist) {
        updateCommunities(quarantineOn, fieldSize, rows, cols, boundaryDist);
    }

    public void updateCommunities(boolean quarantineOn, int[] fieldSize, int rows, int cols, int boundaryDist) {
        communities = new ArrayList<>();
        double[] lo;
        double[] dimensions;

        if(quarantineOn) {
            dimensions = new double[] {
                (fieldSize[0] - (boundaryDist * (cols + 2))) / (cols + 1)
                , (fieldSize[1] - (boundaryDist * (rows + 1))) / rows
            };
            communities.add(new Bound(new double[] {boundaryDist, boundaryDist}, dimensions));
        } else {
            dimensions = new double[] {
                (fieldSize[0] - (boundaryDist * (cols + 1))) / cols
                , (fieldSize[1] - (boundaryDist * (rows + 1))) / rows
            };
        }

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(quarantineOn) {
                    lo = new double[] {((boundaryDist + dimensions[0]) * (j + 1)) + boundaryDist, ((boundaryDist + dimensions[1]) * i + boundaryDist)};
                } else {
                    lo = new double[] {((boundaryDist + dimensions[0]) * j) + boundaryDist, ((boundaryDist + dimensions[1]) * i + boundaryDist)};
                }
                communities.add(new Bound(lo, dimensions));
            }
        }
    }

    public Bound getCommunity(int idx) {
        return communities.get(idx);
    }
    public ArrayList<Bound> getCommunities() {
        return communities;
    }
}
