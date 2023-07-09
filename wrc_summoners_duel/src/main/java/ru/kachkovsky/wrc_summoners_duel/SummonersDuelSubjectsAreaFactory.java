package ru.kachkovsky.wrc_summoners_duel;

import ru.kachkovsky.wrc_summoners_duel.player.Player;
import ru.kachkovsky.wrc_summoners_duel.player.PlayerUtils;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SummonersDuelSubjectsAreaFactory {
    private static final int PLAYERS_COUNT = 2;

    public static SummonersDuelSubjectsArea createNewGameArea(int hp) {
        Player[] teams = {
                new Player(hp, 1, new ArrayList<>()),
                new Player(hp, 0, new ArrayList<>())
        };
        return new SummonersDuelSubjectsArea(teams, 0, 0, true, true);
    }

    public static SummonersDuelSubjectsArea createAreaAfterBuy(SummonersDuelSubjectsArea area, List<Unit> unitsToBuy) {
        Player[] teams = Arrays.copyOf(area.getTeams(), PLAYERS_COUNT);
        teams[area.getCurrentTeamIndex()] = PlayerUtils.copyAndAddUnits(teams[area.getCurrentTeamIndex()], unitsToBuy);
        return new SummonersDuelSubjectsArea(teams, area.getReversePlayerIndex(), 0, true, false);
    }

    public static SummonersDuelSubjectsArea createAreaAfterSplashAttack(SummonersDuelSubjectsArea area) {
        Player[] teams = Arrays.copyOf(area.getTeams(), PLAYERS_COUNT);
        teams[area.getReversePlayerIndex()] = PlayerUtils.copyAndDoSplashAttack(teams[area.getReversePlayerIndex()], teams[area.getCurrentTeamIndex()].getUnits().get(area.getCurrentPlayerUnitIndex()).getAtk());
        return new SummonersDuelSubjectsArea(teams, area.getCurrentTeamIndex(), area.getCurrentPlayerUnitIndex() + 1, false, false);
    }

    public static SummonersDuelSubjectsArea createAreaAfterUnitAttack(SummonersDuelSubjectsArea area, int unitToAttack) {
        Player[] teams = Arrays.copyOf(area.getTeams(), PLAYERS_COUNT);
        teams[area.getReversePlayerIndex()] = PlayerUtils.doAttack(teams[area.getReversePlayerIndex()], teams[area.getCurrentTeamIndex()].getUnits().get(area.getCurrentPlayerUnitIndex()).getAtk(), unitToAttack);
        return new SummonersDuelSubjectsArea(teams, area.getCurrentTeamIndex(), area.getCurrentPlayerUnitIndex() + 1, false, false);
    }

    public static SummonersDuelSubjectsArea createAreaAfterPlayerAttack(SummonersDuelSubjectsArea area) {
        Player[] teams = Arrays.copyOf(area.getTeams(), PLAYERS_COUNT);
        teams[area.getReversePlayerIndex()]= PlayerUtils.copyAndAttackPlayer(teams[area.getReversePlayerIndex()],teams[area.getCurrentTeamIndex()].getUnits().get(area.getCurrentPlayerUnitIndex()).getAtk());
        return new SummonersDuelSubjectsArea(teams, area.getCurrentTeamIndex(), area.getCurrentPlayerUnitIndex() + 1, false, false);
    }
}
