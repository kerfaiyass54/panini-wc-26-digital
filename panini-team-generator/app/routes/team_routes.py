from fastapi import APIRouter
from fastapi import HTTPException

from app.elastic.elastic_client import (
    es,
    PLAYERS_INDEX,
    TEAMS_INDEX
)

from app.models.manual_team import (
    ManualTeamRequest
)

import uuid

from app.services.team_generator import (
    generate_team
)

router = APIRouter()


@router.post("/teams/manual")
def create_manual_team(
        request: ManualTeamRequest
):

    try:

        document = es.get(
            index=PLAYERS_INDEX,
            id=request.email
        )

    except:

        raise HTTPException(
            status_code=404,
            detail="Players not found"
        )

    players = document["_source"]["players"]

    selected_players = [
        player
        for player in players
        if player["id"] in request.player_ids
    ]

    if len(selected_players) != 17:

        raise HTTPException(
            status_code=400,
            detail="A team must contain exactly 17 players"
        )

    goalkeepers = len([
        p for p in selected_players
        if p["position"] == "GOALKEEPER"
    ])

    defenders = len([
        p for p in selected_players
        if p["position"] == "DEFENDER"
    ])

    midfielders = len([
        p for p in selected_players
        if p["position"] == "MIDFIELDER"
    ])

    forwards = len([
        p for p in selected_players
        if p["position"] == "FORWARD"
    ])

    if goalkeepers != 2:
        raise HTTPException(
            status_code=400,
            detail="2 goalkeepers required"
        )

    if defenders != 5:
        raise HTTPException(
            status_code=400,
            detail="5 defenders required"
        )

    if midfielders != 5:
        raise HTTPException(
            status_code=400,
            detail="5 midfielders required"
        )

    if forwards != 5:
        raise HTTPException(
            status_code=400,
            detail="5 forwards required"
        )

    team = {
        "team_id": str(uuid.uuid4()),
        "email": request.email,
        "team_name": request.team_name,
        "players": selected_players
    }

    es.index(
        index=TEAMS_INDEX,
        id=team["team_id"],
        document=team
    )

    return team

@router.post("/teams/auto")
def auto_generate_team(
        email: str,
        team_name: str
):

    try:

        document = es.get(
            index=PLAYERS_INDEX,
            id=email
        )

    except:

        raise HTTPException(
            status_code=404,
            detail="Player collection not found"
        )

    source = document["_source"]

    generated_team = generate_team(
        email=email,
        team_name=team_name,
        players=source["players"]
    )

    es.index(
        index=TEAMS_INDEX,
        id=generated_team.team_id,
        document=generated_team.model_dump()
    )

    return generated_team


@router.get("/players/{email}")
def get_owned_players(
        email: str
):

    try:

        document = es.get(
            index=PLAYERS_INDEX,
            id=email
        )

        return document["_source"]

    except:

        raise HTTPException(
            status_code=404,
            detail="Players not found"
        )


@router.get("/teams/{email}")
def get_user_teams(
        email: str
):

    result = es.search(
        index=TEAMS_INDEX,
        query={
            "term": {
                "email.keyword": email
            }
        },
        size=100
    )

    return [
        hit["_source"]
        for hit in result["hits"]["hits"]
    ]


@router.get("/team/{team_id}")
def get_team(
        team_id: str
):

    try:

        document = es.get(
            index=TEAMS_INDEX,
            id=team_id
        )

        return document["_source"]

    except:

        raise HTTPException(
            status_code=404,
            detail="Team not found"
        )


@router.delete("/team/{team_id}")
def delete_team(
        team_id: str
):

    try:

        es.delete(
            index=TEAMS_INDEX,
            id=team_id
        )

        return {
            "message": "Team deleted"
        }

    except:

        raise HTTPException(
            status_code=404,
            detail="Team not found"
        )