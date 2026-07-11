from elasticsearch import Elasticsearch

es = Elasticsearch(
    "http://localhost:9200"
)

PLAYERS_INDEX = "user_players"

TEAMS_INDEX = "generated_teams"