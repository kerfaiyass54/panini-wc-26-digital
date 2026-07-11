from uuid import uuid4

from app.models.generated_team import (
    GeneratedPlayer,
    GeneratedTeam
)


def generate_team(
        email: str,
        team_name: str,
        players: list
) -> GeneratedTeam:

    goalkeepers = sorted(
        [
            p for p in players
            if p["position"] == "GOALKEEPER"
        ],
        key=lambda p: p["ability"],
        reverse=True
    )[:2]

    defenders = sorted(
        [
            p for p in players
            if p["position"] == "DEFENDER"
        ],
        key=lambda p: p["ability"],
        reverse=True
    )[:5]

    midfielders = sorted(
        [
            p for p in players
            if p["position"] == "MIDFIELDER"
        ],
        key=lambda p: p["ability"],
        reverse=True
    )[:5]

    forwards = sorted(
        [
            p for p in players
            if p["position"] == "FORWARD"
        ],
        key=lambda p: p["ability"],
        reverse=True
    )[:5]

    selected_players = (
            goalkeepers
            + defenders
            + midfielders
            + forwards
    )

    return GeneratedTeam(
        team_id=str(uuid4()),
        email=email,
        team_name=team_name,
        players=[
            GeneratedPlayer(**player)
            for player in selected_players
        ]
    )