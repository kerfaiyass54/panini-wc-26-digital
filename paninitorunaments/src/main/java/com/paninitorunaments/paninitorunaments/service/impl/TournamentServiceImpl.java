package com.paninitorunaments.paninitorunaments.service.impl;



import com.paninitorunaments.paninitorunaments.dto.*;
import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.entity.Match;
import com.paninitorunaments.paninitorunaments.entity.Standing;
import com.paninitorunaments.paninitorunaments.entity.Team;
import com.paninitorunaments.paninitorunaments.exception.TournamentNotFoundException;
import com.paninitorunaments.paninitorunaments.repository.*;
import com.paninitorunaments.paninitorunaments.service.MatchService;
import com.paninitorunaments.paninitorunaments.service.TournamentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final ChampionnatRepository championnatRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final StandingRepository standingRepository;
    private final MatchService matchService;
    private final GoalRepository goalRepository;


    @Override
    @Transactional
    public TournamentStatisticsResponse getStatistics(
            Long tournamentId
    ) {

        Championnat championnat =
                championnatRepository.findById(
                        tournamentId
                ).orElseThrow(
                        () -> new TournamentNotFoundException(
                                tournamentId
                        )
                );

        int totalMatches =
                championnat.getMatches().size();

        int playedMatches =
                (int) championnat.getMatches()
                        .stream()
                        .filter(
                                match ->
                                        Boolean.TRUE.equals(
                                                match.getPlayed()
                                        )
                        )
                        .count();

        int totalGoals =
                championnat.getMatches()
                        .stream()
                        .mapToInt(
                                match ->
                                        match.getGoalsHome()
                                                +
                                                match.getGoalsAway()
                        )
                        .sum();

        double averageGoals =
                playedMatches == 0
                        ? 0
                        : (double) totalGoals
                        / playedMatches;

        List<Long> matchIds =
                championnat.getMatches()
                        .stream()
                        .map(Match::getId)
                        .toList();

        String topScorer = null;
        Long topScorerGoals = 0L;

        List<Object[]> scorers =
                goalRepository
                        .getTournamentTopScorers(
                                matchIds
                        );

        if (!scorers.isEmpty()) {

            topScorer =
                    (String) scorers.get(0)[0];

            topScorerGoals =
                    (Long) scorers.get(0)[1];
        }

        List<Standing> standings =
                standingRepository
                        .findByChampionnatId(
                                tournamentId
                        );

        Standing bestAttackStanding =
                standings.stream()
                        .max(
                                Comparator.comparingInt(
                                        Standing::getGoalsFor
                                )
                        )
                        .orElse(null);

        Standing bestDefenseStanding =
                standings.stream()
                        .min(
                                Comparator.comparingInt(
                                        Standing::getGoalsAgainst
                                )
                        )
                        .orElse(null);

        Match highestScoringMatch =
                championnat.getMatches()
                        .stream()
                        .max(
                                Comparator.comparingInt(
                                        match ->
                                                match.getGoalsHome()
                                                        +
                                                        match.getGoalsAway()
                                )
                        )
                        .orElse(null);

        return TournamentStatisticsResponse
                .builder()
                .tournamentId(
                        championnat.getId()
                )
                .tournamentName(
                        championnat.getTournament()
                )
                .totalMatches(
                        totalMatches
                )
                .playedMatches(
                        playedMatches
                )
                .totalGoals(
                        totalGoals
                )
                .averageGoalsPerMatch(
                        averageGoals
                )
                .topScorer(
                        topScorer
                )
                .topScorerGoals(
                        topScorerGoals
                )
                .bestAttack(
                        bestAttackStanding != null
                                ? bestAttackStanding
                                .getTeam()
                                .getName()
                                : null
                )
                .bestAttackGoals(
                        bestAttackStanding != null
                                ? bestAttackStanding
                                .getGoalsFor()
                                : 0
                )
                .bestDefense(
                        bestDefenseStanding != null
                                ? bestDefenseStanding
                                .getTeam()
                                .getName()
                                : null
                )
                .bestDefenseGoalsConceded(
                        bestDefenseStanding != null
                                ? bestDefenseStanding
                                .getGoalsAgainst()
                                : 0
                )
                .highestScoringMatch(
                        highestScoringMatch != null
                                ? highestScoringMatch
                                .getTeam1()
                                .getName()
                                + " "
                                + highestScoringMatch
                                .getGoalsHome()
                                + "-"
                                + highestScoringMatch
                                .getGoalsAway()
                                + " "
                                + highestScoringMatch
                                .getTeam2()
                                .getName()
                                : null
                )
                .build();
    }


    @Override
    @Transactional
    public TournamentStatusResponse getStatus(
            Long tournamentId
    ) {

        Championnat championnat =
                championnatRepository.findById(
                        tournamentId
                ).orElseThrow(
                        () -> new TournamentNotFoundException(
                                tournamentId
                        )
                );

        int totalMatches =
                championnat.getMatches().size();

        int playedMatches =
                (int) championnat.getMatches()
                        .stream()
                        .filter(
                                match ->
                                        Boolean.TRUE.equals(
                                                match.getPlayed()
                                        )
                        )
                        .count();

        int remainingMatches =
                totalMatches - playedMatches;

        Integer currentJourney =
                championnat.getMatches()
                        .stream()
                        .filter(
                                match ->
                                        !Boolean.TRUE.equals(
                                                match.getPlayed()
                                        )
                        )
                        .map(Match::getJourney)
                        .min(Integer::compareTo)
                        .orElse(null);

        boolean finished =
                remainingMatches == 0;

        String champion = null;

        if (finished) {

            champion =
                    standingRepository
                            .findByChampionnatIdOrderByPointsDescGoalDifferenceDescGoalsForDesc(
                                    championnat.getId()
                            )
                            .stream()
                            .findFirst()
                            .map(
                                    standing ->
                                            standing.getTeam().getName()
                            )
                            .orElse(null);
        }

        return TournamentStatusResponse
                .builder()
                .tournamentId(
                        championnat.getId()
                )
                .tournamentName(
                        championnat.getTournament()
                )
                .totalMatches(
                        totalMatches
                )
                .playedMatches(
                        playedMatches
                )
                .remainingMatches(
                        remainingMatches
                )
                .currentJourney(
                        currentJourney
                )
                .finished(
                        finished
                )
                .champion(
                        champion
                )
                .build();
    }

    @Override
    public Integer getNextJourney(Long tournamentId) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(
                                () -> new TournamentNotFoundException(
                                        tournamentId
                                )
                        );

        return championnat.getMatches()
                .stream()
                .filter(
                        match ->
                                !Boolean.TRUE.equals(
                                        match.getPlayed()
                                )
                )
                .map(Match::getJourney)
                .min(Integer::compareTo)
                .orElse(null);
    }

    @Override
    public void playJourney(
            Long tournamentId,
            Integer journey
    ) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(
                                () -> new TournamentNotFoundException(
                                        tournamentId
                                )
                        );

        championnat.getMatches()
                .stream()
                .filter(
                        match ->
                                match.getJourney()
                                        .equals(journey)
                )
                .filter(
                        match ->
                                !Boolean.TRUE.equals(
                                        match.getPlayed()
                                )
                )
                .forEach(
                        match ->
                                matchService.startMatch(
                                        match.getId()
                                )
                );
    }

    @Override
    public List<Match> getTournamentMatches(Long tournamentId) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(
                                () -> new TournamentNotFoundException(tournamentId)
                        );

        return championnat.getMatches();
    }

    @Override
    @Transactional
    public Championnat initializeStandings(Long tournamentId) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(
                                () -> new TournamentNotFoundException(tournamentId)
                        );

        standingRepository.deleteAll(
                championnat.getStandings()
        );

        List<Standing> standings = new ArrayList<>();

        for (Team team : championnat.getTeams()) {

            Standing standing = Standing.builder()
                    .championnat(championnat)
                    .team(team)
                    .played(0)
                    .won(0)
                    .drawn(0)
                    .lost(0)
                    .goalsFor(0)
                    .goalsAgainst(0)
                    .goalDifference(0)
                    .points(0)
                    .build();

            standings.add(
                    standingRepository.save(standing)
            );
        }

        championnat.setStandings(standings);

        return championnatRepository.save(championnat);
    }

    @Override
    public List<Standing> getStandings(Long tournamentId) {

        return standingRepository
                .findByChampionnatIdOrderByPointsDescGoalDifferenceDescGoalsForDesc(
                        tournamentId
                );
    }

    @Override
    public List<TopScorerResponse> getTournamentTopScorers(
            Long tournamentId
    ) {

        Championnat championnat =
                championnatRepository.findById(
                        tournamentId
                ).orElseThrow(
                        () -> new TournamentNotFoundException(
                                tournamentId
                        )
                );

        List<Long> matchIds =
                championnat.getMatches()
                        .stream()
                        .map(Match::getId)
                        .toList();

        return goalRepository
                .getTournamentTopScorers(matchIds)
                .stream()
                .map(row ->
                        TopScorerResponse.builder()
                                .player((String) row[0])
                                .goals((Long) row[1])
                                .build()
                )
                .toList();
    }

    @Override
    public List<JourneyDto> getJourneys(
            Long tournamentId
    ) {

        Championnat championnat =
                championnatRepository.findById(
                        tournamentId
                ).orElseThrow(
                        () -> new TournamentNotFoundException(
                                tournamentId
                        )
                );

        return championnat.getMatches()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Match::getJourney
                        )
                )
                .entrySet()
                .stream()
                .sorted(
                        Map.Entry.comparingByKey()
                )
                .map(entry ->
                        new JourneyDto(
                                entry.getKey(),
                                entry.getValue().size()
                        )
                )
                .toList();
    }

    @Override
    public List<MatchResultDto> getResults(
            Long tournamentId
    ) {

        Championnat championnat =
                championnatRepository.findById(
                        tournamentId
                ).orElseThrow(
                        () -> new TournamentNotFoundException(
                                tournamentId
                        )
                );

        return championnat.getMatches()
                .stream()
                .filter(
                        match ->
                                Boolean.TRUE.equals(
                                        match.getPlayed()
                                )
                )
                .map(match ->
                        MatchResultDto.builder()
                                .id(match.getId())
                                .homeTeam(
                                        match.getTeam1().getName()
                                )
                                .awayTeam(
                                        match.getTeam2().getName()
                                )
                                .goalsHome(
                                        match.getGoalsHome()
                                )
                                .goalsAway(
                                        match.getGoalsAway()
                                )
                                .journey(
                                        match.getJourney()
                                )
                                .build()
                )
                .toList();
    }

    @Override
    public Championnat createTournament(String name) {

        Championnat championnat = Championnat.builder()
                .tournament(name)
                .build();

        return championnatRepository.save(championnat);
    }

    @Override
    public Championnat addTeams(Long tournamentId, List<Long> teamIds) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(
                                () -> new TournamentNotFoundException(tournamentId)
                        );

        List<Team> teams = teamRepository.findAllById(teamIds);

        championnat.getTeams().clear();
        championnat.getTeams().addAll(teams);

        return championnatRepository.save(championnat);
    }

    @Override
    @Transactional
    public Championnat generateFixtures(Long tournamentId) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(
                                () -> new TournamentNotFoundException(
                                        tournamentId
                                )
                        );

        List<Team> teams =
                new ArrayList<>(championnat.getTeams());

        if (teams.size() < 2) {

            throw new IllegalStateException(
                    "At least two teams are required"
            );
        }

        if (teams.size() % 2 != 0) {

            throw new IllegalStateException(
                    "Number of teams must be even"
            );
        }

        championnat.getMatches().clear();

        List<Match> matches = new ArrayList<>();

        int numberOfTeams = teams.size();

        int journeys = numberOfTeams - 1;

        List<Team> rotation = new ArrayList<>(teams);

        for (int journey = 1; journey <= journeys; journey++) {

            for (int i = 0; i < numberOfTeams / 2; i++) {

                Team home = rotation.get(i);

                Team away =
                        rotation.get(
                                numberOfTeams - 1 - i
                        );

                Match match =
                        Match.builder()
                                .team1(home)
                                .team2(away)
                                .journey(journey)
                                .goalsHome(0)
                                .goalsAway(0)
                                .played(false)
                                .build();

                matches.add(
                        matchRepository.save(match)
                );
            }

            Team fixed = rotation.get(0);

            List<Team> temp =
                    new ArrayList<>();

            temp.add(fixed);

            temp.add(
                    rotation.get(
                            numberOfTeams - 1
                    )
            );

            temp.addAll(
                    rotation.subList(
                            1,
                            numberOfTeams - 1
                    )
            );

            rotation = temp;
        }

        championnat.getMatches().addAll(matches);

        return championnatRepository.save(championnat);
    }

    @Override
    public Championnat getTournament(Long id) {

        return championnatRepository.findById(id)
                .orElseThrow(
                        () -> new TournamentNotFoundException(id)
                );
    }

    @Override
    public List<Championnat> getAll() {

        return championnatRepository.findAll();
    }
}
