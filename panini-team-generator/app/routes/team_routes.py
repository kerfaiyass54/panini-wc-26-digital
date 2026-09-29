import logging
import json
from urllib.error import HTTPError, URLError
from urllib.parse import quote
from urllib.request import Request as UrlRequest, urlopen

from fastapi import APIRouter
from fastapi import Header, HTTPException
from elasticsearch import (
    ApiError,
    ConnectionError as ElasticsearchConnectionError,
)

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

logger = logging.getLogger(__name__)
router = APIRouter()


def fetch_wc_owned_players(email: str, authorization: str | None) -> list[dict]:
    if not authorization:
        raise HTTPException(
            status_code=401,
            detail="Authorization is required to load owned players"
        )

    request = UrlRequest(
        "http://localhost:9090/api/owned-players/"
        f"{quote(email, safe='')}",
        headers={"Authorization": authorization},
    )

    try:
        with urlopen(request, timeout=10) as response:
            players = json.loads(response.read().decode("utf-8"))
    except HTTPError as exc:
        raise HTTPException(
            status_code=exc.code,
            detail="WC backend could not load owned players"
        ) from exc
    except (URLError, TimeoutError) as exc:
        logger.exception("WC backend is unavailable")
        raise HTTPException(
            status_code=503,
            detail="WC backend is unavailable"
        ) from exc

    if not isinstance(players, list):
        raise HTTPException(
            status_code=502,
            detail="WC backend returned an invalid player list"
        )
    return players


@router.post("/teams/manual")
def create_manual_team(
        request: ManualTeamRequest,
        authorization: str | None = Header(default=None),
):

    if len(set(request.player_ids)) != len(request.player_ids):
        raise HTTPException(
            status_code=400,
            detail="Player selections must not contain duplicates"
        )

    players = fetch_wc_owned_players(request.email, authorization)
    players_by_id = {player["id"]: player for player in players}
    selected_players = [
        players_by_id[player_id]
        for player_id in request.player_ids
        if player_id in players_by_id
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

    strikers = len([
        p for p in selected_players
        if p["position"] == "STRIKER"
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

    if strikers != 5:
        raise HTTPException(
            status_code=400,
            detail="5 strikers required"
        )

    stored_players = [
        {
            **player,
            "position": (
                "FORWARD" if player["position"] == "STRIKER"
                else player["position"]
            ),
        }
        for player in selected_players
    ]

    team = {
        "team_id": str(uuid.uuid4()),
        "email": request.email,
        "team_name": request.team_name,
        "players": stored_players
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
        email: str,
        authorization: str | None = Header(default=None),
):
    return fetch_wc_owned_players(email, authorization)


@router.get("/teams/{email}")
def get_user_teams(
        email: str
):

    try:
        result = es.search(
            index=TEAMS_INDEX,
            query={
                "bool": {
                    "should": [
                        {"term": {"email.keyword": email}},
                        {"term": {"email": email}},
                    ],
                    "minimum_should_match": 1,
                }
            },
            size=100
        )
    except (ApiError, ElasticsearchConnectionError) as exc:
        raise HTTPException(
            status_code=503,
            detail="Team storage is unavailable"
        ) from exc

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
