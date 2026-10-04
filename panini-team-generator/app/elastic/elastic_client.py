from elasticsearch import Elasticsearch
import os

es = Elasticsearch(os.getenv("ELASTICSEARCH_URL"))

PLAYERS_INDEX = "user_players"

TEAMS_INDEX = "generated_teams"

PLAYER_PROPERTIES = {
    "id": {"type": "integer"},
    "name": {"type": "keyword"},
    "position": {"type": "keyword"},
    "ability": {"type": "integer"},
    "nationality": {"type": "keyword"},
}


def ensure_indices():
    """Create required indexes when missing, preserving any existing data."""
    indices = {
        PLAYERS_INDEX: {
            "properties": {
                "email": {"type": "keyword"},
                "players": {
                    "type": "object",
                    "properties": PLAYER_PROPERTIES,
                },
            }
        },
        TEAMS_INDEX: {
            "properties": {
                "team_id": {"type": "keyword"},
                "email": {"type": "keyword"},
                "team_name": {"type": "keyword"},
                "players": {
                    "type": "object",
                    "properties": PLAYER_PROPERTIES,
                },
            }
        },
    }

    for index, mappings in indices.items():
        if not es.indices.exists(index=index):
            es.indices.create(index=index, mappings=mappings)