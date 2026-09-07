import random

from app.models.match_result import (
    MatchResult,
    GoalScorer
)


class Simulator:

    POSITION_WEIGHTS = {
        "GOALKEEPER": 0.05,
        "DEFENDER": 0.40,
        "MIDFIELDER": 0.80,
        "FORWARD": 1.50
    }

    @staticmethod
    def simulate(request):

        home_xi = Simulator.select_starting_eleven(
            request.homeTeam.players
        )

        away_xi = Simulator.select_starting_eleven(
            request.awayTeam.players
        )

        home_strength = Simulator.calculate_team_strength(
            home_xi
        )

        away_strength = Simulator.calculate_team_strength(
            away_xi
        )

        goals_home, goals_away = (
            Simulator.generate_score(
                home_strength,
                away_strength
            )
        )

        scorers = []

        for _ in range(goals_home):

            player = Simulator.pick_scorer(
                home_xi
            )

            scorers.append(
                GoalScorer(
                    playerId=player.id,
                    minute=random.randint(1, 90)
                )
            )

        for _ in range(goals_away):

            player = Simulator.pick_scorer(
                away_xi
            )

            scorers.append(
                GoalScorer(
                    playerId=player.id,
                    minute=random.randint(1, 90)
                )
            )

        scorers.sort(
            key=lambda scorer: scorer.minute
        )

        return MatchResult(
            matchId=request.matchId,
            goalsHome=goals_home,
            goalsAway=goals_away,
            scorers=scorers
        )

    @staticmethod
    def select_starting_eleven(players):

        goalkeepers = sorted(
            [
                p for p in players
                if p.position.upper() == "GOALKEEPER"
            ],
            key=lambda p: p.ability,
            reverse=True
        )[:1]

        defenders = sorted(
            [
                p for p in players
                if p.position.upper() == "DEFENDER"
            ],
            key=lambda p: p.ability,
            reverse=True
        )[:4]

        midfielders = sorted(
            [
                p for p in players
                if p.position.upper() == "MIDFIELDER"
            ],
            key=lambda p: p.ability,
            reverse=True
        )[:3]

        forwards = sorted(
            [
                p for p in players
                if p.position.upper() == "FORWARD"
            ],
            key=lambda p: p.ability,
            reverse=True
        )[:3]

        return (
                goalkeepers
                + defenders
                + midfielders
                + forwards
        )

    @staticmethod
    def calculate_team_strength(players):

        total = 0

        for player in players:

            position = (
                player.position.upper()
                if player.position
                else ""
            )

            if position == "GOALKEEPER":
                total += player.ability * 1.1

            elif position == "DEFENDER":
                total += player.ability * 1.0

            elif position == "MIDFIELDER":
                total += player.ability * 1.2

            elif position == "FORWARD":
                total += player.ability * 1.3

            else:
                total += player.ability

        return total / len(players)

    @staticmethod
    def generate_score(
            home_strength,
            away_strength
    ):

        gap = home_strength - away_strength

        if gap > 30:

            return random.choice([
                (4, 0),
                (4, 1),
                (5, 0),
                (5, 1),
                (3, 0)
            ])

        elif gap > 15:

            return random.choice([
                (2, 0),
                (2, 1),
                (3, 0),
                (3, 1),
                (4, 1)
            ])

        elif gap < -30:

            return random.choice([
                (0, 4),
                (1, 4),
                (0, 5),
                (1, 5),
                (0, 3)
            ])

        elif gap < -15:

            return random.choice([
                (0, 2),
                (1, 2),
                (0, 3),
                (1, 3),
                (1, 4)
            ])

        else:

            return random.choice([
                (0, 0),
                (1, 0),
                (0, 1),
                (1, 1),
                (2, 1),
                (1, 2),
                (2, 2),
                (3, 2),
                (2, 3)
            ])

    @staticmethod
    def pick_scorer(players):

        weights = []

        for player in players:

            position = (
                player.position.upper()
                if player.position
                else ""
            )

            position_weight = (
                Simulator.POSITION_WEIGHTS
                .get(position, 1.0)
            )

            weights.append(
                player.ability * position_weight
            )

        return random.choices(
            population=players,
            weights=weights,
            k=1
        )[0]