package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import Model.GameArchive.GameRecord;

public class Leaderboard {
    private final List<LeaderboardRecord> leaderboard;
    private final GameArchive archive;

    public Leaderboard(GameArchive archive){
        leaderboard=new ArrayList<>();
        this.archive=archive;
    }

    public void build(){
        HashMap<String, Integer> pointMap=new HashMap<>();
        List<GameRecord> games=archive.getGameList();
        String player1;
        String player2;
        int p1_pointInGame;
        int p2_pointInGame;
        for (GameRecord gr : games){
            player1=gr.getPlayer1();
            player2=gr.getPlayer2();
            p1_pointInGame= gr.getWinner() ? 0 : 1;
            p2_pointInGame= gr.getWinner() ? 1 : 0;
            if (pointMap.containsKey(player1))
                pointMap.put(player1, pointMap.get(player1)+p1_pointInGame);
            else
                pointMap.put(player1, p1_pointInGame);
            if (pointMap.containsKey(player2))
                pointMap.put(player2, pointMap.get(player2)+p2_pointInGame);
            else
                pointMap.put(player2, p2_pointInGame);
        }
        //Now I convert the HashMap entries to an ArrayList
        List<LeaderboardRecord> fullLeaderboard=new ArrayList<>();
        for (Entry<String, Integer> entry : pointMap.entrySet()){
            fullLeaderboard.add(new LeaderboardRecord(entry.getKey(), entry.getValue()));
        }
        //I sort it
        fullLeaderboard.sort( (a, b)->(b.getScore()-a.getScore()) );  //descending order
        //And pick the top 10 entries
        leaderboard.clear();
        for (int i=0; i<10 && i<fullLeaderboard.size(); i++){
            leaderboard.add(fullLeaderboard.get(i));
        }
    }

    //Nested class, an entry of the leaderboard.
    private class LeaderboardRecord{
        private final String name;
        private final int score;

        private LeaderboardRecord(String name, int score){
            this.name=name;
            this.score=score;
        }

        public int getScore(){
            return score;
        }
    }
}
