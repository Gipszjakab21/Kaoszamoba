package Controller;

import java.util.List;

import Model.Army;
import Model.Leaderboard.LeaderboardRecord;

public interface Actions {
    public void toMenu();
    public void toLeaderBoard();
    public void toArmy();
    public Army getDefArmy();
    public List<LeaderboardRecord> getLeaderboard();
    public void saveDefArmy(Army newArmy);
    public void loadArmy(String fileName);
}
