package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Collection;

public class MScoreboard
extends Mapping {
    public MappingMethod listPlayerScoresMethod;
    private static int[] scoreboardControlFlowState;
    public MappingMethod getPlayersTeamMethod;

    private Collection getPlayerScores(Object scoreboard, Object objective) {
        return (Collection)this.listPlayerScoresMethod.invokeObject(scoreboard, objective);
    }

    public static void setScoreboardControlFlowState(int[] state) {
        scoreboardControlFlowState = state;
    }

    public static Collection getPlayerScores(MScoreboard mapping, Object scoreboard, Object objective) {
        return mapping.getPlayerScores(scoreboard, objective);
    }


    public MScoreboard() {
        this(MScoreboard.getScoreboardControlFlowState());
    }

    private MScoreboard(int[] nArray) {
        super(MappedClasses.F6);
        int[] nArray2 = nArray;
        if (ForgeVersion.MC_1_7_10.L() && !Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            Class[] classArray = new Class[]{MappedClasses.Y};
            Class<Collection> clazz = Collection.class;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "func_96534_i";
            MScoreboard mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        } else if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray = new Class[]{MappedClasses.Y};
            Class<Collection> clazz = Collection.class;
            boolean bl = true;
            String string = "listPlayerScores";
            MScoreboard mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        } else if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray = new Class[]{MappedClasses.Y};
            Class<Collection> clazz = Collection.class;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "m_83498_";
            MScoreboard mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{MappedClasses.Y};
            Class<Collection> clazz = Collection.class;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "getSortedScores";
            MScoreboard mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        } else {
            Class[] classArray = new Class[]{MappedClasses.Y};
            Class<Collection> clazz = Collection.class;
            boolean bl = true;
            String string = "getSortedScores";
            MScoreboard mScoreboard = this;
            this.listPlayerScoresMethod = mScoreboard.Y(string, bl, clazz, classArray);
        }
        Class[] classArray = new Class[]{String.class};
        Class clazz = MappedClasses.u6;
        boolean bl = true;
        String string = "getPlayersTeam";
        MScoreboard mScoreboard = this;
        this.getPlayersTeamMethod = mScoreboard.Y(string, bl, clazz, classArray);
    }

    private Object getPlayersTeam(Object scoreboard, String playerName) {
        return this.getPlayersTeamMethod.invokeObject(scoreboard, playerName);
    }

    public static int[] getScoreboardControlFlowState() {
        return scoreboardControlFlowState;
    }

    static {
        MScoreboard.setScoreboardControlFlowState(new int[2]);
    }

    public static Object getPlayersTeam(MScoreboard mapping, Object scoreboard, String playerName) {
        return mapping.getPlayersTeam(scoreboard, playerName);
    }
}
